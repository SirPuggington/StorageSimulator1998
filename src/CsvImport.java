import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class CsvImport {

    public ArrayList<Order> importOrders() throws FileNotFoundException {
        ArrayList<Order> orders = new ArrayList<>();
        String current;

        File csv = new File("Orders.csv");

        Scanner sc = new Scanner(csv);

        while (sc.hasNextLine()) {

            current = sc.nextLine();
            if (current.startsWith("A")) {
                continue;
            }
            Order order = this.stringToOrder(current);
            orders.add(order);
        }
        sc.close();
        return orders;
    }

    private Order stringToOrder(String current) {
        String[] orderArray = current.split(";");

        int orderNumber = Integer.parseInt(orderArray[0]);
        boolean orderIn = (Objects.equals(orderArray[1], "Einlagerung"));
        int reward = Integer.parseInt(orderArray[5]);

        Product product = switch (orderArray[2].toLowerCase()) {
            case "holz" -> new Wood(orderArray[3], orderArray[4]);
            case "papier" -> new Paper(orderArray[3], orderArray[4]);
            case "stein" -> new Stone(orderArray[3], orderArray[4]);
            default -> throw new RuntimeException("ERROR: Unknown Product Type.");
        };

        return new Order(orderNumber, orderIn, product, reward);

    }
}

