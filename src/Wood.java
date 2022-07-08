import javax.swing.*;
import java.awt.*;

public class Wood extends Product {

    String shape;
    String type;
    ImageIcon icon;
    ImageIcon selIcon;
    String iconShape;
    String material = "Wood";

    public Wood(String type, String shape) {


        switch (type.toLowerCase()) {
            case "buche" -> this.type = "beech";
            case "eiche" -> this.type = "oak";
            case "kiefer" -> this.type = "pine";
        }

        switch (shape.toLowerCase()) {
            case "bretter" -> {
                this.shape = "board";
                this.iconShape = "cut";
            }
            case "balken" -> {
                this.shape = "beam";
                this.iconShape = "cut";
            }
            case "scheit" -> {
                this.shape = "log";
                this.iconShape = "log";
            }
        }

        this.icon = new ImageIcon("assets/wood/" + this.iconShape + "/" + this.type + ".png");
        this.selIcon = new ImageIcon("assets/wood/" + this.iconShape + "/" + this.type + "_selected.png");


    }

    public String getAttributes() {
        return material + ": " + this.type + " " + this.shape;
    }

    public String getAttributesShort() {
        return this.type + " " + this.shape;
    }

    public ImageIcon getIcon() {
        return icon;
    }
    public ImageIcon getSelectedIcon() {
        return selIcon;
    }
    public ImageIcon getLargeIcon(){
        return new ImageIcon(icon.getImage().getScaledInstance(32,32, Image.SCALE_DEFAULT));
    }

}
