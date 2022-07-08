import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Objects;


public class Gui {
    private final JFrame frame;
    private final JPanel foot;
    private final JButton startBtn;
    private final JButton skipOrderBtn;
    private final JButton balanceBtn;
    private JCheckBox moveBox;
    private JCheckBox scrapBox;
    private JLabel wallet;
    private JLabel messageLabel =new JLabel();
    private final ButtonGroup availableOrders = new ButtonGroup();
    private final JPanel[] orderPanels = new JPanel[3];
    private final JButton[] storageSlots = new JButton[24];
    private final JButton[] newOrderBtns = new JButton[3];
    private final JLabel[] storageSlotLabels = new JLabel[24];
    private final JRadioButton[] orderRadios = new JRadioButton[3];

    public JFrame getFrame(){
        return frame;
    }

    public void setWallet(String val) {
        wallet.setText(val);
    }

    public void setMoveBox(boolean b) {
        moveBox.setSelected(b);
    }

    public void setScrapBox(boolean b) {
        scrapBox.setSelected(b);
    }

    public void setMessageLabelText(String text) {
        messageLabel.setText(text);
    }

    public void setMessageLabelIcon(Icon icon) {
        messageLabel.setIcon(icon);
    }

    public void setSkipBtnText(String text){
        skipOrderBtn.setText(text);
        skipOrderBtn.setVisible(!Objects.equals(skipOrderBtn.getText(), ""));
    }

    public void reload(){
        SwingUtilities.updateComponentTreeUI(frame);
    }

    public void removeStartBtn(){
        frame.remove(startBtn);
    }

    public int getSelectedRadio(){
        int i=0;
        for (Enumeration<AbstractButton> buttons = availableOrders.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void hideOrder(){
        int index=getSelectedRadio();
        orderRadios[index].setVisible(false);
        newOrderBtns[index].setVisible(true);
    }

    public void newOrder(int index, Order nextOrder){
        newOrderBtns[index].setVisible(false);
        orderRadios[index].setIcon(nextOrder.getProductIcon());
        orderRadios[index].setSelectedIcon(nextOrder.getSelectedProductIcon());
        orderRadios[index].setText(nextOrder.getOrderInfo());
        orderRadios[index].setVisible(true);
        orderRadios[index].setSelected(true);
    }
    public void setAuxBtnAction(ActionListener auxBtnListener){
        startBtn.setActionCommand("start");
        startBtn.addActionListener(auxBtnListener);

        for (int i=0; i<3;i++){
            newOrderBtns[i].addActionListener(auxBtnListener);
            newOrderBtns[i].setActionCommand("new "+i);
        }

        skipOrderBtn.setActionCommand("skip");
        skipOrderBtn.addActionListener(auxBtnListener);

        balanceBtn.setActionCommand("balance");
        balanceBtn.addActionListener(auxBtnListener);
    }
    public void setCheckboxAction(ActionListener checkboxListener){
        for (JRadioButton jr:
             orderRadios) {
            jr.setActionCommand("radio");
            jr.addActionListener(checkboxListener);
            moveBox.setActionCommand("move");
            scrapBox.setActionCommand("scrap");
            moveBox.addActionListener(checkboxListener);
            scrapBox.addActionListener(checkboxListener);
        }
    }

    public Gui() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

        this.frame = new JFrame("StorageSimulator1998");
        JPanel head = new JPanel();
        this.foot = new JPanel();
        this.foot.setLayout(new BorderLayout());
        this.startBtn = new JButton("START");
        this.balanceBtn = new JButton("Show Balance");
        skipOrderBtn = new JButton("");
        skipOrderBtn.setVisible(false);
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


        for (int i=0; i<3; i++){
            orderRadios[i]=new JRadioButton();
            newOrderBtns[i]=new JButton("New Order");
            orderPanels[i]=new JPanel();
            orderPanels[i].setLayout(new BorderLayout());
            orderPanels[i].add(orderRadios[i],BorderLayout.CENTER);
            orderPanels[i].add(newOrderBtns[i],BorderLayout.PAGE_END);
            orderRadios[i].setVisible(false);
            availableOrders.add(orderRadios[i]);

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

    public void displayOrders() {
        moveBox = new JCheckBox("Move (-100$)");
        scrapBox = new JCheckBox("Scrap (-300$)");
        JPanel leftSide = new JPanel();
        JPanel rightSide = new JPanel();
        JPanel orderListPanel=new JPanel();
        JPanel modesPanel = new JPanel();
        rightSide.setLayout(new BorderLayout());
        leftSide.setLayout(new GridLayout(1, 2));
        leftSide.setBorder(BorderFactory.createTitledBorder("ORDERS"));
        leftSide.setPreferredSize(new Dimension(800,90));
        modesPanel.setLayout(new GridLayout(2, 1));
        modesPanel.setPreferredSize(new Dimension(100, 50));
        orderListPanel.setLayout(new GridLayout(3,1));

        modesPanel.add(moveBox);
        modesPanel.add(scrapBox);

        messageLabel=new JLabel("Welcome to Storage Simulator 1998");
        messageLabel.setMinimumSize(new Dimension(0,16));
        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createTitledBorder("MESSAGES"));
        messagePanel.setPreferredSize(new Dimension(800,50));
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

    public void displayMoney(Finances finances) {
        JPanel financePanel = new JPanel();
        financePanel.setBorder(BorderFactory.createTitledBorder("FINANCES"));
        JPanel moneyPanel = new JPanel();
        JLabel icon = new JLabel(new ImageIcon("assets/dollar.png"));
        wallet = new JLabel(String.valueOf(finances.getFunds()));
        financePanel.setLayout(new GridLayout(1, 2));
        moneyPanel.setLayout(new BorderLayout(10, 0));
        moneyPanel.add(icon, BorderLayout.LINE_START);
        moneyPanel.add(wallet, BorderLayout.CENTER);

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



}
