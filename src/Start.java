import javax.swing.*;
import java.io.FileNotFoundException;


public class Start {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, FileNotFoundException {


        new Gui();
        CsvImport imp=new CsvImport();
        System.out.println(imp.importOrders().get(1).product.getAttributes());
        System.out.println(imp.importOrders().get(1).reward);
        System.out.println(imp.importOrders().get(1).id);

    }
}
