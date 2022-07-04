import javax.swing.*;

public class Wood extends Product {

    String shape;
    String type;
    ImageIcon icon;
    String iconShape;

    public Wood(String type, String shape) {

        switch (type.toLowerCase()) {
            case "birke" -> this.type = "beech";
            case "eiche" -> this.type = "oak";
            case "fichte" -> this.type = "spruce";
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

        this.icon=new ImageIcon( "../assets/wood/" + this.iconShape + "/" + this.type);




    }

}
