import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Actions {

    private Gui gui;
    private final Finances finances;
    private final Storage storage;
    private final CsvImport imp;

    private boolean moveMode;
    private boolean scrapMode;
    private String logString;
    private int[] moveFrom;
    private int nextOrderId;
    private ImageIcon errorIcon;
    private ImageIcon successIcon;


    private ArrayList<Order> orders;
    private Order[] availableOrders;
    private Order currentOrder;
    private Order nextOrder;
    private Timer timer;

    public Actions(Finances fin, Storage sto, CsvImport imp) {

        this.finances = fin;
        this.storage = sto;
        this.imp = imp;

    }

    public void openMainMenu() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.gui = new Gui();
        gui.setAuxBtnAction(auxBtnListener);
    }

    private void startGame() throws FileNotFoundException {
        timer = new Timer(2500, auxBtnListener);
        successIcon = new ImageIcon("assets/success.png");
        errorIcon = new ImageIcon("assets/error.png");
        availableOrders = new Order[3];

        gui.removeStartBtn();
        nextOrderId = 0;
        gui.displayStorage(mainBtnListener);
        gui.displayOrders();
        gui.displayMoney(finances);
        gui.setCheckboxAction(checkboxListener);

        orders = imp.importOrders();
        gui.reload();
    }

    private void getNextOrder() {

        try {
            nextOrder = orders.get(nextOrderId);
        } catch (RuntimeException E) {
            //getting first order again, if there are no new ones left
            nextOrder = orders.get(0);
        }
    }

    private void updateOrders() {

        nextOrderId = currentOrder.getId();
        if (availableOrders[0] != null) {
            nextOrderId = Math.max(nextOrderId, availableOrders[0].getId());
        }
        if (availableOrders[1] != null) {
            nextOrderId = Math.max(nextOrderId, availableOrders[1].getId());
        }
        if (availableOrders[2] != null) {
            nextOrderId = Math.max(nextOrderId, availableOrders[2].getId());
        }
        gui.setSkipBtnText("Reject Selected Order (-" + currentOrder.getReward() + "$)");


        gui.reload();
    }

    private final ActionListener checkboxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "scrap" -> {
                    scrapMode = !scrapMode;
                    moveMode = false;
                    gui.setMoveBox(false);
                }
                case "move" -> {
                    moveMode = !moveMode;
                    scrapMode = false;
                    gui.setScrapBox(false);
                }
                case "radio" -> {
                    setCurrentOrder();
                    System.out.println(currentOrder);
                    updateOrders();
                }
            }
        }
    };

    private final ActionListener auxBtnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            switch (command) {
                case "skip":
                    if (currentOrder!=null) {

                        gui.hideOrder();


                        logString = "SKIPPED: " + currentOrder.getProductAttributes();
                        finances.updateFunds(currentOrder.getReward() * (-1), logString);
                        updateOrders();
                        currentOrder=null;
                        gui.setSkipBtnText("");

                    } else {
                        logString="E: SELECT AN ORDER TO SKIP";
                    }

                    break;

                case "start":
                    try {
                        startGame();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case "balance":

                    finances.openBalance(gui.getFrame());
                    break;

                case "timer":

                    gui.setMessageLabelText("");
                    gui.setMessageLabelIcon(null);
                    timer.stop();
                    break;

                case "info":

                    try {
                        gui.showInfo();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case "close":
                    gui.hideInfo();

                default:
                    //NEW ORDER BUTTONS
                    if (command.startsWith("new")) {
                        getNextOrder();

                        int index = Integer.parseInt(command.split(" ")[1]);

                        gui.newOrder(index,nextOrder);
                        availableOrders[index] = nextOrder;
                        setCurrentOrder();

                        updateOrders();
                        System.out.println(nextOrder.getOrderInfo());
                        gui.reload();
                    }

            }
            if(!command.equals("info")&&(!command.equals("close"))) {
                gui.setWallet(String.valueOf(finances.getFunds()));
            }
        }
    };

    private void setCurrentOrder() {
        int orderIndex = gui.getSelectedRadio();
        System.out.println(orderIndex);
        currentOrder = availableOrders[orderIndex];
    }

    private final ActionListener mainBtnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {


            String c = e.getActionCommand();
            String[] cs = c.split(":");
            int x = Integer.parseInt(cs[0]);
            int y = Integer.parseInt(cs[1]);
            int z = Integer.parseInt(cs[2]);

            if (moveMode) {
                if (moveFrom != null) {

                    if (storage.move(x, y, z, moveFrom[0], moveFrom[1], moveFrom[2])){
                        logString = storage.getLogString();
                        finances.updateFunds(-100, logString.substring(3));
                    }
                    logString = storage.getLogString();

                    moveFrom = null;
                    gui.updateStorage(storage);
                } else {
                    if (storage.shelf[x][y][z] != null) {
                        moveFrom = new int[3];
                        moveFrom[0] = x;
                        moveFrom[1] = y;
                        moveFrom[2] = z;
                        logString = "MOVING: " + storage.shelf[x][y][z].getAttributes();
                    } else {
                        logString = "E: NOTHING TO MOVE";
                    }
                }
            } else if (scrapMode) {

                if (storage.scrap(x, y, z)) {
                    logString = storage.getLogString();
                    gui.updateStorage(storage);
                    finances.updateFunds(-300, logString.substring(3));
                }logString = storage.getLogString();
            } else
                //STANDARD MODE
                if (currentOrder != null) {
                    if (storage.action(currentOrder, x, y, z)) {
                        logString=storage.getLogString();
                        gui.updateStorage(storage);
                        if (currentOrder.isIn()) {
                            logString = "S: STORED: " + currentOrder.getProductAttributes();
                        } else {
                            logString = "S: CLEARED: " + currentOrder.getProductAttributes();
                        }
                        finances.updateFunds(currentOrder.getReward(), logString);
                        gui.hideOrder();
                        updateOrders();
                        gui.setSkipBtnText("");
                        currentOrder = null;
                    }else{
                        logString=storage.getLogString();
                    }

                } else {
                    logString = "E: SELECT AN ORDER OR REQUEST A NEW ONE";

                }


            gui.setWallet(String.valueOf(finances.getFunds()));

            if (logString.startsWith("E:")) {
                gui.setMessageLabelText(logString.substring(3));
                gui.setMessageLabelIcon(errorIcon);
            } else if (logString.startsWith("S:")) {
                gui.setMessageLabelText(logString.substring(3));
                gui.setMessageLabelIcon(successIcon);
            } else {
                gui.setMessageLabelText(logString);
                gui.setMessageLabelIcon(null);
            }

            timer.stop();
            if (!logString.startsWith("MOVING")) {

                timer.setActionCommand("timer");
                timer.start();

            }
        }
    };

}
