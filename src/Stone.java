import javax.swing.*;
import java.awt.*;

public class Stone extends Product{

    String material="Stone";
    String type;
    int weight;
    String weightString;
    ImageIcon icon;
    ImageIcon selIcon;


    public Stone(String type, String weight) {


        switch (type.toLowerCase()) {
            case "granit" -> this.type = "granite";
            case "marmor" -> this.type = "marble";
            case "sandstein" -> this.type = "sandstone";
        }

        switch (weight.toLowerCase()) {
            case "leicht" -> {
                this.weight = 1;
                this.weightString="light";
            }
            case "mittel" -> {
                this.weight = 2;
                this.weightString="medium";
            }
            case "schwer" -> {
                this.weight = 3;
                this.weightString="heavy";
            }
        }
        this.icon=new ImageIcon("assets/stone/"+this.type+".png");
        this.selIcon=new ImageIcon("assets/stone/"+this.type+"_selected.png");


    }

    public String getAttributes(){
        return material+": "+this.weightString+" "+this.type;
    }

    public String getAttributesShort() {
        return this.weightString + " " + this.type;
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
