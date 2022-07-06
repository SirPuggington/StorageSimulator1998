import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;


public class Gui {
    private final JFrame frame;

    private JPanel head;
    private JPanel foot;
    private final JButton startBtn;
    private JButton skipOrderBtn;
    private JCheckBox moveBox;
    private JCheckBox scrapBox;
    private JLabel currentOrderLabel;
    private JLabel wallet;

    private int currentOrderId;
    private int funds;
    private boolean moveMode = false;
    private boolean scrapMode = false;
    private boolean startOfGame;

    private ArrayList<Order> orders;
    private Order currentOrder;
    private Storage storage;

    private JButton[] storageSlots = new JButton[24];
    private JLabel[] storageSlotLabels = new JLabel[24];


    public Gui() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

        this.frame = new JFrame("StorageSimulator1998");
        this.head = new JPanel();
        this.foot = new JPanel();
        this.foot.setLayout(new BorderLayout());
        this.startBtn = new JButton("START");
        this.startBtn.setPreferredSize(new Dimension(100, 50));

        this.startBtn.setActionCommand("start");
        this.startBtn.addActionListener(auxBtnListener);

        JLabel logo = new JLabel(new ImageIcon("assets/logo.png"));
        this.head.add(logo);

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLayout(new BorderLayout());


        this.frame.add(head, BorderLayout.PAGE_START);
        this.frame.add(foot, BorderLayout.PAGE_END);
        frame.add(startBtn);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

    }

    private void start() throws FileNotFoundException {

        frame.remove(startBtn);
        funds = 0;
        currentOrderId = 0;
        startOfGame = true;
        displayStorage();
        displayOrders();
        displayMoney();
        storage = new Storage();
        CsvImport imp = new CsvImport();
        orders = imp.importOrders();
        SwingUtilities.updateComponentTreeUI(frame);

    }


    private void displayStorage() {
        JPanel storagePanel = new JPanel();
        storagePanel.setBorder(BorderFactory.createTitledBorder("STORAGE"));

        storagePanel.setLayout(new GridLayout(1, 2));

        JPanel frontPanel = new JPanel();
        frontPanel.setLayout(new GridLayout(3, 1));
        frontPanel.setBorder(BorderFactory.createTitledBorder("Front Shelf"));

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new GridLayout(3, 1));
        backPanel.setBorder(BorderFactory.createTitledBorder("Back Shelf"));

        JPanel frontPanel1 = new JPanel();
        formatStoragePanels(frontPanel1);

        JPanel backPanel1 = new JPanel();
        formatStoragePanels(backPanel1);

        JPanel frontPanel2 = new JPanel();
        formatStoragePanels(frontPanel2);

        JPanel backPanel2 = new JPanel();
        formatStoragePanels(backPanel2);

        JPanel frontPanel3 = new JPanel();
        formatStoragePanels(frontPanel3);

        JPanel backPanel3 = new JPanel();
        formatStoragePanels(backPanel3);


        for (int i = 0, z = 0; z < 2; z++) {
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 4; x++) {
                    String coords = x + ":" + y + ":" + z;
                    JPanel slot = new JPanel();
                    slot.setLayout(new BorderLayout(1, 0));
                    JButton slotBtn = new JButton();
                    JLabel slotLabel = new JLabel("Empty");

                    slotBtn.setToolTipText("Slot " + i);
                    slotBtn.setActionCommand(coords);
                    slotBtn.addActionListener(mainBtnListener);
                    slotLabel.setLabelFor(slotBtn);

                    slot.add(slotBtn, BorderLayout.CENTER);
                    slot.add(slotLabel, BorderLayout.PAGE_END);


                    if (z == 0)
                        switch (y) {
                            case 2 -> frontPanel1.add(slot);
                            case 1 -> frontPanel2.add(slot);
                            case 0 -> frontPanel3.add(slot);
                        }

                    else
                        switch (y) {
                            case 2 -> backPanel1.add(slot);
                            case 1 -> backPanel2.add(slot);
                            case 0 -> backPanel3.add(slot);
                        }

                    this.storageSlots[i] = slotBtn;
                    this.storageSlotLabels[i]=slotLabel;
                    i++;
                }
            }
        }
        backPanel.add(backPanel1);
        backPanel.add(backPanel2);
        backPanel.add(backPanel3);

        frontPanel.add(frontPanel1);
        frontPanel.add(frontPanel2);
        frontPanel.add(frontPanel3);

        storagePanel.add(frontPanel);
        storagePanel.add(backPanel);
        this.frame.add(storagePanel, BorderLayout.CENTER);
    }

    private void displayOrders() {
        JPanel orderPanel = new JPanel();
        JPanel modesPanel = new JPanel();
        JPanel rightSide = new JPanel();
        currentOrderLabel = new JLabel();
        skipOrderBtn = new JButton("Get First Order");
        moveBox = new JCheckBox("Move");
        scrapBox = new JCheckBox("Scrap");

        currentOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        rightSide.setLayout(new BorderLayout());

        skipOrderBtn.setActionCommand("skip");
        skipOrderBtn.addActionListener(auxBtnListener);
        moveBox.setActionCommand("move");
        scrapBox.setActionCommand("scrap");
        moveBox.addActionListener(checkboxListener);
        scrapBox.addActionListener(checkboxListener);

        orderPanel.setLayout(new GridLayout(1, 2));
        orderPanel.setBorder(BorderFactory.createTitledBorder("ORDERS"));
        modesPanel.setLayout(new GridLayout(2, 1));
        modesPanel.setPreferredSize(new Dimension(100, 50));


        modesPanel.add(moveBox);
        modesPanel.add(scrapBox);
        rightSide.add(modesPanel, BorderLayout.LINE_END);
        rightSide.add(skipOrderBtn, BorderLayout.CENTER);
        orderPanel.add(currentOrderLabel);
        orderPanel.add(rightSide);


        this.foot.add(orderPanel, BorderLayout.CENTER);
    }

    private void displayMoney() {
        JPanel financePanel = new JPanel();
        financePanel.setBorder(BorderFactory.createTitledBorder("FINANCES"));

        JPanel moneyPanel = new JPanel();
        JButton balanceBtn = new JButton("Show Balance");
        JLabel icon = new JLabel(new ImageIcon("assets/dollar.png"));
        wallet = new JLabel(String.valueOf(funds));
        financePanel.setLayout(new GridLayout(1, 2));
        moneyPanel.setLayout(new BorderLayout(10, 0));
        moneyPanel.add(icon, BorderLayout.LINE_START);
        moneyPanel.add(wallet, BorderLayout.CENTER);

        balanceBtn.setActionCommand("balance");
        balanceBtn.addActionListener(auxBtnListener);

        financePanel.add(moneyPanel);
        financePanel.add(balanceBtn);

        this.foot.add(financePanel, BorderLayout.SOUTH);

    }

    private void updateOrders() {

        try {
            currentOrder = orders.get(currentOrderId);
        } catch (RuntimeException E) {
            //GET FIRST ORDER AGAIN, WHEN THERE ARE NO NEW ONES LEFT
            currentOrder = orders.get(0);
        }

        if (currentOrder.in) {
            currentOrderLabel.setText("INCOMING - " + currentOrder.product.getAttributes() + " - Reward: " + currentOrder.reward);
        } else {
            currentOrderLabel.setText("OUTGOING - " + currentOrder.product.getAttributes() + " - Reward: " + currentOrder.reward);

        }
        currentOrderLabel.setIcon(currentOrder.product.getIcon());
        currentOrderId = currentOrder.id;
        skipOrderBtn.setText("Skip current Order (-" + currentOrder.reward + "$)");

        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void updateStorage(){
        int[] coordsArr;
        for (int i = 0; i < 24; i++) {
            coordsArr = slotToCoords(i);
            if(storage.shelf[coordsArr[0]][coordsArr[1]][coordsArr[2]]!=null) {
                storageSlots[i].setIcon(storage.shelf[coordsArr[0]][coordsArr[1]][coordsArr[2]].getIcon());
                storageSlotLabels[i].setText(storage.shelf[coordsArr[0]][coordsArr[1]][coordsArr[2]].getAttributesShort());
            }else {
                storageSlots[i].setIcon(null);
                storageSlotLabels[i].setText("Empty");
            }

        }
    }


    public String coordsToSlot(String coords) {
        int x = Integer.parseInt(coords.split(":")[0]);
        int y = Integer.parseInt(coords.split(":")[1]);
        int z = Integer.parseInt(coords.split(":")[2]);
        String slot;

        slot = String.valueOf(x + (y * 4) + 12 * z);


        return slot;
    }

    public int[] slotToCoords(int i) {
        int[] coords = {0, 0, 0};

        while (i > 11) {
            coords[2]++;
            i = i - 12;
        }
        while (i > 3) {
            coords[1]++;
            i = i - 4;
        }
        while (i > 0) {
            coords[0]++;
            i--;
        }

        return coords;
    }

    public void formatStoragePanels(JPanel panel) {
        panel.setLayout(new GridLayout(1, 4));
        panel.setBorder(BorderFactory.createEtchedBorder());
    }

    private ActionListener checkboxListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String mode = e.getActionCommand();

            if (Objects.equals(mode, "scrap")) {

                scrapMode = !scrapMode;
                moveMode = false;
                moveBox.setSelected(false);

                System.out.println("scrapping " + scrapMode);

            } else {

                moveMode = !moveMode;
                scrapMode = false;
                scrapBox.setSelected(false);

                System.out.println("moving " + moveMode);

            }

        }
    };

    private ActionListener auxBtnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String command = e.getActionCommand();

            switch (command) {
                case "skip":
                    if (startOfGame) {
                        startOfGame = false;
                    } else {
                        funds = funds - currentOrder.reward;
                    }
                    updateOrders();

                    break;
                case "start":
                    try {
                        start();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    break;

                case "balance":
                    JFrame balanceWindow=new JFrame("Balance");
                    balanceWindow.setSize(new Dimension(400, 400));
                    balanceWindow.setLayout(new BorderLayout());

                    JScrollPane balanceScoll = new JScrollPane();
                    JPanel balanceTotal = new JPanel();

                    String[] testNames = new String[2];
                    String[][] testVals = new String[1000][2];

                    testNames[0]="Action";
                    testNames[1]="Value";

                    testVals[0][0]="Test";
                    testVals[1][0]="Test";
                    testVals[2][0]="Test";
                    testVals[3][0]="Test";
                    testVals[0][1]="100";
                    testVals[1][1]="750";
                    testVals[2][1]="-30";
                    testVals[3][1]="Test";


                    ArrayList<String[]> Test = new ArrayList<>();

                    Test.add(testNames);
                    Test.add(testNames);
                    Test.add(testNames);
                    Test.add(testNames);
                    Test.add(testNames);

                    String[][] a= new String[Test.size()][];
                    a=Test.toArray(a);


                    System.out.println(testVals);
                    System.out.println(testNames);

                    JTable balanceRecordsTable =new JTable(a,testNames);

                    balanceTotal.setLayout(new GridLayout(1,2));


                    JLabel totalText = new JLabel("Total Funds");
                    JLabel totalMoney = new JLabel(Integer.toString(funds));

                    totalText.setHorizontalAlignment(JLabel.CENTER);
                    totalMoney.setHorizontalAlignment(JLabel.CENTER);

                    balanceScoll.getViewport().add(balanceRecordsTable);

                    balanceWindow.add(balanceScoll, BorderLayout.CENTER);
                    balanceWindow.add(balanceTotal, BorderLayout.PAGE_END);

                    balanceTotal.add(totalText);
                    balanceTotal.add(totalMoney);


                    balanceWindow.setLocationRelativeTo(frame);
                    balanceWindow.setVisible(true);

            }

            wallet.setText(String.valueOf(funds));

        }
    };

    private ActionListener mainBtnListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            String c = e.getActionCommand();
            String[] cs = c.split(":");
            int x = Integer.parseInt(cs[0]);
            int y = Integer.parseInt(cs[1]);
            int z = Integer.parseInt(cs[2]);

            if (moveMode) {

            }
            if (scrapMode) {
                if(storage.scrap(x,y,z)){
                    updateStorage();
                    funds=funds-500;
                }
            }else{
                if (storage.action(currentOrder, x, y, z)) {
                    updateStorage();
                    funds=funds+currentOrder.reward;
                    updateOrders();


                }
            }






            wallet.setText(String.valueOf(funds));


        }
    };


}
