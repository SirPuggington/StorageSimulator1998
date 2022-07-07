import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

public class Actions {

    private final Gui gui;
    private final Finances finances;
    private final Storage storage;
    private final CsvImport imp;

    private boolean startOfGame;
    private boolean moveMode;
    private boolean scrapMode;
    private String logString;
    private int[] moveFrom;
    private int nextOrderId;


    private ArrayList<Order> orders;
    private Order[] availableOrders;
    private Order currentOrder;
    private Timer timer;

    public Actions(Gui gui, Finances fin, Storage sto, CsvImport imp){
        this.gui=gui;
        this.finances = fin;
        this.storage=sto;
        this.imp=imp;
        gui.setStartBtnAction(auxBtnListener);
    }

    public void startGame() throws FileNotFoundException {
        timer = new Timer(1500, auxBtnListener);
        availableOrders=new Order[3];


        gui.removeStartBtn();
        nextOrderId = 0;
        startOfGame = true;
        gui.displayStorage(mainBtnListener);
        gui.displayOrders(auxBtnListener,checkboxListener);
        gui.displayMoney(auxBtnListener,finances);

        orders = imp.importOrders();
        gui.reload();
    }

    public void updateOrders() {

        try {
            currentOrder = orders.get(nextOrderId);
        } catch (RuntimeException E) {
            //getting first order again, if there are no new ones left
            currentOrder = orders.get(0);
        }

        nextOrderId = currentOrder.getId();
        if(availableOrders[0]!=null) {
            nextOrderId = Math.max(nextOrderId, availableOrders[1].getId());
        }
        if(availableOrders[1]!=null) {
            nextOrderId = Math.max(nextOrderId, availableOrders[1].getId());
        }
        if(availableOrders[2]!=null){
            nextOrderId=Math.max(nextOrderId,availableOrders[2].getId());
        }

        gui.setCurrentOrderLabelText(currentOrder.getOrderInfo());

        gui.setCurrentOrderLabelIcon(currentOrder.getProductIcon());


        gui.setSkipBtnText("Skip current Order (-" + currentOrder.getReward() + "$)");

        gui.reload();
    }

    private final ActionListener checkboxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String mode = e.getActionCommand();

            if (Objects.equals(mode, "scrap")) {

                scrapMode = !scrapMode;
                moveMode = false;
                gui.setMoveBox(false);


            } else {

                moveMode = !moveMode;
                scrapMode = false;
                gui.setScrapBox(false);

            }

        }
    };

    private final ActionListener auxBtnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            switch (command) {
                case "skip":
                    if (startOfGame) {
                        startOfGame = false;
                    } else {
                        logString = "SKIPPED: " + currentOrder.getProductAttributes();
                        finances.updateFunds(currentOrder.getReward() * (-1), logString);
                    }
                    updateOrders();

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

                case "timer":

                    if(startOfGame){
                        gui.setCurrentOrderLabelText("");
                        gui.setCurrentOrderLabelIcon(null);
                    }else {
                        gui.setCurrentOrderLabelText(currentOrder.getOrderInfo());
                        gui.setCurrentOrderLabelIcon(currentOrder.getProductIcon());
                    }
                    timer.stop();

            }

            gui.setWallet(String.valueOf(finances.getFunds()));

        }
    };

    private final ActionListener mainBtnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!startOfGame) {

                String c = e.getActionCommand();
                String[] cs = c.split(":");
                int x = Integer.parseInt(cs[0]);
                int y = Integer.parseInt(cs[1]);
                int z = Integer.parseInt(cs[2]);

                if (moveMode) {
                    if (moveFrom != null) {
                        logString = storage.move(x, y, z, moveFrom[0], moveFrom[1], moveFrom[2]);
                        if (logString.startsWith("MOVED:")) {
                            finances.updateFunds(-100, logString);
                        }
                        System.out.println(logString);
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
                            logString = "NOTHING TO MOVE";
                        }
                    }
                } else if (scrapMode) {
                    logString = storage.scrap(x, y, z);
                    if (!Objects.equals(logString, "NOTHING TO SCRAP")) {
                        gui.updateStorage(storage);
                        finances.updateFunds(-500, logString);
                    }
                } else
                    //STANDARD MODE
                    if (storage.action(currentOrder, x, y, z)) {
                        gui.updateStorage(storage);
                        if (currentOrder.isIn()) {
                            logString = "STORED: " + currentOrder.getProductAttributes();
                        } else {
                            logString = "CLEARED: " + currentOrder.getProductAttributes();
                        }
                        finances.updateFunds(currentOrder.getReward(), logString);
                        updateOrders();


                    }
            }else {
                logString="REQUEST YOUR FIRST ORDER ->";
            }



            gui.setWallet(String.valueOf(finances.getFunds()));


            gui.setCurrentOrderLabelText(logString);
            gui.setCurrentOrderLabelIcon(null);

            if(!timer.isRunning()&&!logString.startsWith("MOVING")) {

                timer.setActionCommand("timer");
                timer.start();

            }
        }
    };

}
