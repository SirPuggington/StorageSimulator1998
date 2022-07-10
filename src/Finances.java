import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Finances {

    private final ArrayList<String[]> financeLog = new ArrayList<>();
    private final String[] tableHeader = {"Action", "Payment"};
    private int funds;

    public Finances() {
        funds = 0;
    }

    public void updateFunds(int val, String desc) {
        String[] logEntry = new String[2];
        if (desc.startsWith("E:") || desc.startsWith("S:")) {
            logEntry[0] = desc.substring(3);
        } else {
            logEntry[0] = desc;
        }

        logEntry[1] = String.valueOf(val);

        financeLog.add(logEntry);

        funds += val;


    }

    public int getFunds() {
        return funds;
    }

    public void openBalance(JFrame frame) {
        JFrame balanceWindow = new JFrame("Balance");
        balanceWindow.setSize(new Dimension(400, 400));
        balanceWindow.setLayout(new BorderLayout());

        JScrollPane balanceScroll = new JScrollPane();
        JPanel balanceTotal = new JPanel();

        String[][] logArray = new String[financeLog.size()][];
        logArray = financeLog.toArray(logArray);


        JTable balanceRecordsTable = new JTable(logArray, tableHeader);

        balanceTotal.setLayout(new GridLayout(1, 2));


        JLabel totalText = new JLabel("Total Funds");
        JLabel totalMoney = new JLabel(Integer.toString(funds));

        totalText.setHorizontalAlignment(JLabel.CENTER);
        totalMoney.setHorizontalAlignment(JLabel.CENTER);

        balanceScroll.getViewport().add(balanceRecordsTable);

        balanceWindow.add(balanceScroll, BorderLayout.CENTER);
        balanceWindow.add(balanceTotal, BorderLayout.PAGE_END);

        balanceTotal.add(totalText);
        balanceTotal.add(totalMoney);


        balanceWindow.setLocationRelativeTo(frame);
        balanceWindow.setVisible(true);

    }
}


