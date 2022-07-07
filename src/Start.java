import javax.swing.*;
import java.io.FileNotFoundException;


public class Start {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException {


        Gui gui = new Gui();
        CsvImport imp=new CsvImport();
        Finances fin = new Finances();
        Storage sto =new Storage();
        Actions act = new Actions(gui, fin, sto, imp);




    }
}
