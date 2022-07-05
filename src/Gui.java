import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Gui {
    private final JFrame frame;

    private JPanel head;
    private JPanel foot;

    private JLabel logo;


    private JButton[] storageSlots = new JButton[24];
    


    public Gui() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");

        this.frame = new JFrame("StorageSimulator1998");
        this.head = new JPanel();
        this.foot = new JPanel();
        this.foot.setLayout(new BorderLayout());


        this.logo = new JLabel(new ImageIcon("assets/logo.png"));

        this.head.add(logo);


        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLayout(new BorderLayout());


        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        this.frame.setVisible(true);
        this.frame.add(head, BorderLayout.PAGE_START);
        this.frame.add(foot, BorderLayout.PAGE_END);
        this.displayStorage();
        this.displayOrders();
        this.displayMoney();
    }





    private void displayStorage() {
        JPanel storagePanel = new JPanel();
        storagePanel.setBorder(BorderFactory.createTitledBorder("STORAGE"));

        storagePanel.setLayout(new GridLayout(1, 2));

        JPanel frontPanel=new JPanel();
        frontPanel.setLayout(new GridLayout(3, 1));
        frontPanel.setBorder(BorderFactory.createTitledBorder("Front Shelf"));

        JPanel backPanel=new JPanel();
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

                    slotBtn.setToolTipText("Slot " + coordsToSlot(coords));
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
        JPanel modesPanel =new JPanel();
        JPanel rightSide =new JPanel();
        JLabel currentOrder=new JLabel();
        JButton skipOrderBtn = new JButton("Skip Current Order");
        JCheckBox moveMode =new JCheckBox("Move");
        JCheckBox scrapMode = new JCheckBox("Scrap");

        currentOrder.setHorizontalAlignment(SwingConstants.CENTER);
        currentOrder.setText(" - "+" - ");
        currentOrder.setIcon(new ImageIcon("assets/"+"/"+".png"));

        rightSide.setLayout(new BorderLayout());

        orderPanel.setLayout(new GridLayout(1,2));
        orderPanel.setBorder(BorderFactory.createTitledBorder("ORDERS"));
        modesPanel.setLayout(new GridLayout(2,1));
        modesPanel.setPreferredSize(new Dimension(100, 50));


        modesPanel.add(moveMode);
        modesPanel.add(scrapMode);
        rightSide.add(modesPanel, BorderLayout.LINE_END);
        rightSide.add(skipOrderBtn, BorderLayout.CENTER);
        orderPanel.add(currentOrder);
        orderPanel.add(rightSide);


        this.foot.add(orderPanel, BorderLayout.CENTER);
    }

    private void displayMoney() {
        JPanel financePanel=new JPanel();
        financePanel.setBorder(BorderFactory.createTitledBorder("FINANCES"));

        JPanel moneyPanel = new JPanel();
        JButton balanceBtn = new JButton("Show Balance");
        JLabel icon = new JLabel(new ImageIcon("assets/dollar.png"));
        JLabel wallet = new JLabel("0");
        financePanel.setLayout(new GridLayout(1,2));
        moneyPanel.setLayout(new BorderLayout(10,0));
        moneyPanel.add(icon, BorderLayout.LINE_START);
        moneyPanel.add(wallet, BorderLayout.CENTER);
        balanceBtn.setPreferredSize(new Dimension(100,20));

        financePanel.add(moneyPanel);
        financePanel.add(balanceBtn);

        this.foot.add(financePanel, BorderLayout.SOUTH);

    }



    public String coordsToSlot(String coords) {
        int x = Integer.parseInt(coords.split(":")[0]);
        int y = Integer.parseInt(coords.split(":")[1]);
        int z = Integer.parseInt(coords.split(":")[2]);
        String slot;

        if (z == 0) {
            slot = x + 1 + (y * 4) + " Front";
        } else {
            slot = x + 1 + (y * 4) + " Back";
        }

        return slot;
    }

    public void formatStoragePanels(JPanel panel){
        panel.setLayout(new GridLayout(1, 4));
        panel.setBorder(BorderFactory.createEtchedBorder());
    }

}
