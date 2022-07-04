import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

public class Gui {
    private final JFrame frame;

    private JPanel panel;

    private JLabel label;
public Gui(){
        this.frame = new JFrame("StorageSimulator1998");
        this.panel = new JPanel();
        this.label = new JLabel();

    }
    private void start(){
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setSize(800,600);
        this.frame.setLayout(new BorderLayout());


        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        this.frame.setVisible(true);
    }
}
