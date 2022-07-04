import java.io.FileNotFoundException;
import java.util.Scanner;

public class Start {
    public static void main(String[] args) throws FileNotFoundException {


       Storage s=new Storage();
       CsvImport imp=new CsvImport();

       Order o= imp.importOrders().get(1);
       if(s.action(o,1,2,0)) {
           System.out.println("product places successfully");
       }else{
           System.out.println("ERR");
       }


    }
}
