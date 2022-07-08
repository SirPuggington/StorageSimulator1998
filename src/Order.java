import javax.swing.*;

public record Order(int id, boolean in, Product product, int reward) {


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

    public String getProductAttributes() {
        return product.getAttributes();
    }

    public Icon getProductIcon() {
        return product.getIcon();
    }

    public Icon getSelectedProductIcon() {
        return product.getSelectedIcon();
    }

    public String getOrderInfo() {
        String orderInfo;
        if (in) {
            orderInfo = "INCOMING - " + getProductAttributes() + " - Reward: " + reward + "$";
        } else {
            orderInfo = "OUTGOING - " + getProductAttributes() + " - Reward: " + reward + "$";

        }
        return orderInfo;
    }

}



