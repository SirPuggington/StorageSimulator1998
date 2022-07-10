import javax.swing.*;


public class Start {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {


        CsvImport imp = new CsvImport();
        Finances fin = new Finances();
        Storage sto = new Storage();
        Actions act = new Actions(fin, sto, imp);

        act.openMainMenu();


    }
}
