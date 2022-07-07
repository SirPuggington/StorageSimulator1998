import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class Gui {
    private final JFrame frame;

    private final JPanel foot;
    private final JButton startBtn;
    private JButton skipOrderBtn;
    private JCheckBox moveBox;
    private JCheckBox scrapBox;
    private JLabel currentOrderLabel;
    private JLabel wallet;
    private final JButton[] storageSlots = new JButton[24];
    private final JLabel[] storageSlotLabels = new JLabel[24];


    ButtonGroup availableOrders = new ButtonGroup();

    JButton[] newOrderBtns = new JButton[3];
    JRadioButton[] orderRadios = new JRadioButton[3];
    JLabel[] orderLabels = new JLabel[3];
    JPanel[] orderPanels = new JPanel[3];
    JPanel messagePanel= new JPanel();
    JLabel messageLabel =new JLabel();

    public void setWallet(String val) {
        wallet.setText(val);
    }

    public void setMoveBox(boolean b) {
        moveBox.setSelected(b);
    }

    public void setScrapBox(boolean b) {
        scrapBox.setSelected(b);
    }

    public void setCurrentOrderLabelText(String text) {
        currentOrderLabel.setText(text);
    }

    public void setCurrentOrderLabelIcon(Icon icon) {
        currentOrderLabel.setIcon(icon);
    }

    public void setSkipBtnText(String text){
        skipOrderBtn.setText(text);
    }

    public void reload(){
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void removeStartBtn(){
        frame.remove(startBtn);
    }
    public void setStartBtnAction(ActionListener auxBtnListener){
        this.startBtn.addActionListener(auxBtnListener);
    }
    public JFrame getFrame(){
        return frame;
    }

    public Gui() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

        this.frame = new JFrame("StorageSimulator1998");
        JPanel head = new JPanel();
        this.foot = new JPanel();
        this.foot.setLayout(new BorderLayout());
        this.startBtn = new JButton("START");
        this.startBtn.setPreferredSize(new Dimension(100, 50));


        JLabel logo = new JLabel(new ImageIcon("assets/logo.png"));
        head.add(logo);

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLayout(new BorderLayout());


        this.frame.add(head, BorderLayout.PAGE_START);
        this.frame.add(foot, BorderLayout.PAGE_END);
        frame.add(startBtn);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.startBtn.setActionCommand("start");

        for (int i=0; i<3; i++){
            orderRadios[i]=new JRadioButton();
            orderLabels[i]=new JLabel();
            newOrderBtns[i]=new JButton("New Order");
            orderPanels[i]=new JPanel();
            orderPanels[i].setLayout(new BorderLayout());
            orderPanels[i].add(orderLabels[i],BorderLayout.CENTER);
            orderPanels[i].add(orderRadios[i],BorderLayout.PAGE_START);
            orderPanels[i].add(newOrderBtns[i],BorderLayout.CENTER);
            orderRadios[i].setVisible(false);
            availableOrders.add(orderRadios[i]);
            orderLabels[i].setVisible(false);
        }





    }




    public void displayStorage(ActionListener mainBtnListener) {
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
                    this.storageSlotLabels[i] = slotLabel;
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

    public void displayOrders(ActionListener auxBtnListener, ActionListener checkboxListener) {
        JPanel leftSide = new JPanel();
        JPanel rightSide = new JPanel();
        JPanel orderListPanel=new JPanel();
        JPanel modesPanel = new JPanel();
        currentOrderLabel = new JLabel();
        skipOrderBtn = new JButton("Get First Order");
        moveBox = new JCheckBox("Move (-100$)");
        scrapBox = new JCheckBox("Scrap (-300$)");

        currentOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);

        rightSide.setLayout(new BorderLayout());

        skipOrderBtn.setActionCommand("skip");
        skipOrderBtn.addActionListener(auxBtnListener);
        moveBox.setActionCommand("move");
        scrapBox.setActionCommand("scrap");
        moveBox.addActionListener(checkboxListener);
        scrapBox.addActionListener(checkboxListener);

        leftSide.setLayout(new GridLayout(1, 2));
        leftSide.setBorder(BorderFactory.createTitledBorder("ORDERS"));
        modesPanel.setLayout(new GridLayout(2, 1));
        modesPanel.setPreferredSize(new Dimension(100, 50));
        orderListPanel.setLayout(new GridLayout(3,1));


        modesPanel.add(moveBox);
        modesPanel.add(scrapBox);

        messageLabel=new JLabel("Welcome to Storage Simulator 1998");
        messagePanel=new JPanel();
        messagePanel.setBorder(BorderFactory.createTitledBorder("MESSAGES"));
        messagePanel.add(messageLabel);


        rightSide.add(modesPanel, BorderLayout.LINE_END);
        rightSide.add(skipOrderBtn, BorderLayout.CENTER);
        leftSide.add(orderListPanel);
        leftSide.add(rightSide);
        for (JPanel jp:
                orderPanels) {
            orderListPanel.add(jp);
        }

        this.foot.add(leftSide, BorderLayout.CENTER);
        this.foot.add(messagePanel,BorderLayout.NORTH);
    }

    public void displayMoney(ActionListener auxBtnListener, Finances finances) {
        JPanel financePanel = new JPanel();
        financePanel.setBorder(BorderFactory.createTitledBorder("FINANCES"));

        JPanel moneyPanel = new JPanel();
        JButton balanceBtn = new JButton("Show Balance");
        JLabel icon = new JLabel(new ImageIcon("assets/dollar.png"));
        wallet = new JLabel(String.valueOf(finances.getFunds()));
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



    public void updateStorage(Storage storage) {
        int[] coordsArr;
        for (int i = 0; i < 24; i++) {
            coordsArr = slotToCoords(i);
            if (storage.shelf[coordsArr[0]][coordsArr[1]][coordsArr[2]] != null) {
                storageSlots[i].setIcon(storage.shelf[coordsArr[0]][coordsArr[1]][coordsArr[2]].getLargeIcon());
                storageSlotLabels[i].setText(storage.shelf[coordsArr[0]][coordsArr[1]][coordsArr[2]].getAttributesShort());
            } else {
                storageSlots[i].setIcon(null);
                storageSlotLabels[i].setText("Empty");
            }

        }
    }


//    public String coordsToSlot(String coords) {
//        int x = Integer.parseInt(coords.split(":")[0]);
//        int y = Integer.parseInt(coords.split(":")[1]);
//        int z = Integer.parseInt(coords.split(":")[2]);
//        String slot;
//
//        slot = String.valueOf(x + (y * 4) + 12 * z);
//
//
//        return slot;
//    }

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

//    private ActionListener checkboxListener = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String mode = e.getActionCommand();
//
//            if (Objects.equals(mode, "scrap")) {
//
//                scrapMode = !scrapMode;
//                moveMode = false;
//                moveBox.setSelected(false);
//
//
//            } else {
//
//                moveMode = !moveMode;
//                scrapMode = false;
//                scrapBox.setSelected(false);
//
//            }
//
//        }
//    };

//    private ActionListener auxBtnListener = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//            String command = e.getActionCommand();
//
//            switch (command) {
//                case "skip":
//                    if (startOfGame) {
//                        startOfGame = false;
//                    } else {
//                        logString = "SKIPPED: " + currentOrder.getProductAttributes();
//                        finances.updateFunds(currentOrder.getReward() * (-1), logString);
//                    }
//                    updateOrders();
//
//                    break;
//                case "start":
//                    try {
//                        start();
//                    } catch (FileNotFoundException ex) {
//                        ex.printStackTrace();
//                    }
//                    break;
//
//                case "balance":
//
//                    finances.openBalance(frame);
//
//                case "timer":
//
//                    if(startOfGame){
//                        currentOrderLabel.setText("");
//                        currentOrderLabel.setIcon(null);
//                    }else {
//                        currentOrderLabel.setText(currentOrder.getProductAttributes());
//                        currentOrderLabel.setIcon(currentOrder.getProductIcon());
//                    }
//                    timer.stop();
//
//            }
//
//            wallet.setText(String.valueOf(finances.getFunds()));
//
//        }
//    };
//
//    private ActionListener mainBtnListener = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//            if (!startOfGame) {
//
//                String c = e.getActionCommand();
//                String[] cs = c.split(":");
//                int x = Integer.parseInt(cs[0]);
//                int y = Integer.parseInt(cs[1]);
//                int z = Integer.parseInt(cs[2]);
//
//                if (moveMode) {
//                    if (moveFrom != null) {
//                        logString = storage.move(x, y, z, moveFrom[0], moveFrom[1], moveFrom[2]);
//                        if (logString.startsWith("MOVED:")) {
//                            finances.updateFunds(-100, logString);
//                        }
//                        System.out.println(logString);
//                        moveFrom = null;
//                        updateStorage();
//                    } else {
//                        if (storage.shelf[x][y][z] != null) {
//                            moveFrom = new int[3];
//                            moveFrom[0] = x;
//                            moveFrom[1] = y;
//                            moveFrom[2] = z;
//                            logString = "MOVING: " + storage.shelf[x][y][z].getAttributes();
//                        } else {
//                            logString = "NOTHING TO MOVE";
//                        }
//                    }
//                } else if (scrapMode) {
//                    logString = storage.scrap(x, y, z);
//                    if (logString != "NOTHING TO SCRAP") {
//                        updateStorage();
//                        finances.updateFunds(-500, logString);
//                    }
//                } else
//                    //STANDARD MODE
//                    if (storage.action(currentOrder, x, y, z)) {
//                        updateStorage();
//                        if (currentOrder.isIn()) {
//                            logString = "STORED: " + currentOrder.getProductAttributes();
//                        } else {
//                            logString = "CLEARED: " + currentOrder.getProductAttributes();
//                        }
//                        finances.updateFunds(currentOrder.getReward(), logString);
//                        updateOrders();
//
//
//                    }
//            }else {
//                logString="REQUEST YOUR FIRST ORDER ->";
//            }
//
//
//
//            wallet.setText(String.valueOf(finances.getFunds()));
//
//
//            currentOrderLabel.setText(logString);
//            currentOrderLabel.setIcon(null);
//
//            if(!timer.isRunning()&&!logString.startsWith("MOVING")) {
//
//                timer.setActionCommand("timer");
//                timer.start();
//
//            }
//        }
//    };


}
