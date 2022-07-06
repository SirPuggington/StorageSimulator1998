import javax.swing.*;

public class Stone extends Product{

    String material="Stone";
    String type;
    int weight;
    String weightString;
    ImageIcon icon;


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
        this.icon=new ImageIcon("assets/"+this.type+".png");


    }

    public String getAttributes(){
        return material+": "+this.weightString+" "+this.type;
    }

    public ImageIcon getIcon() {
        return icon;
    }

}
