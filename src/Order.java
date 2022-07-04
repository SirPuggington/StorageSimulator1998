public class Order {


    final Product product;
    final boolean in;
    final int reward;
    final int id;


    public Order(int id, boolean in, Product product, int reward){
        this.id = id;
        this.in = in;
        this.product = product;
        this.reward = reward;

    }

}



