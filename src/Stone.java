import javax.swing.*;

public class Stone extends Product{

    String type;
    int weight;
    ImageIcon icon;


    public Stone(String type, String weight) {

        switch (type.toLowerCase()) {
            case "granit" -> this.type = "granite";
            case "marmor" -> this.type = "marble";
            case "sandstein" -> this.type = "sandstone";
        }

        switch (weight.toLowerCase()) {
            case "leicht" -> this.weight = 1;
            case "mittel" -> this.weight = 2;
            case "schwer" -> this.weight = 3;
        }
        this.icon=new ImageIcon("../assets/"+this.type);
    }
}
