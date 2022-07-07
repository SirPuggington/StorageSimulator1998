import javax.swing.*;

public class Order {



    final private Product product;
    final private boolean in;
    final private int reward;
    final private int id;


    public Order(int id, boolean in, Product product, int reward){
        this.id = id;
        this.in = in;
        this.product = product;
        this.reward = reward;

    }

    public Product getProduct() {
        return product;
    }

    public boolean isIn() {
        return in;
    }

    public int getReward() {
        return reward;
    }

    public int getId() {
        return id;
    }

    public String getProductAttributes(){
        return product.getAttributes();
    }
    public Icon getProductIcon(){
        return product.getIcon();
    }
    public String getOrderInfo(){
        String orderInfo;
        if (in) {
            orderInfo="INCOMING - " + getProductAttributes() + " - Reward: " + reward;
        } else {
            orderInfo="OUTGOING - " + getProductAttributes() + " - Reward: " + reward;

        }
        return orderInfo;
    }

}



