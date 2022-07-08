import javax.swing.*;
import java.awt.*;

public class Product {

    ImageIcon icon;

    public Product() {
        icon=new ImageIcon("");
    }

    public String getAttributes() {
        return "";
    }


    public Icon getIcon() {
        return icon;
    }

    public String getAttributesShort() {
        return "";
    }

    public ImageIcon getLargeIcon(){
        return new ImageIcon(icon.getImage().getScaledInstance(32,32, Image.SCALE_DEFAULT));
    }

    public Icon getSelectedIcon() {
        return icon;
    }
}
