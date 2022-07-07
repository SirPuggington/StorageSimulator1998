import javax.swing.*;
import java.awt.*;

public class Paper extends Product{

    String color;
    String size;
    String material="Paper";
    ImageIcon icon;

    public Paper(String color, String size){

        switch (color.toLowerCase()) {
            case "blau" -> this.color = "blue";
            case "gruen" -> this.color = "green";
            case "weiss" -> this.color = "white";
        }
        this.size=size;
        this.icon = new ImageIcon("assets/paper/"+this.color+".png");

    }

    public String getAttributes(){
        return material+": "+this.color+" "+this.size;
    }

    public String getAttributesShort() {
        return this.size;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public ImageIcon getLargeIcon(){
        return new ImageIcon(icon.getImage().getScaledInstance(32,32, Image.SCALE_DEFAULT));
    }

}
