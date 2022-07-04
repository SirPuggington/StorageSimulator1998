import javax.swing.*;

public class Paper extends Product{

    String color;
    String size;
    String name="Paper";
    ImageIcon icon;

    public Paper(String color, String size){
        switch (color.toLowerCase()) {
            case "blau" -> {
                this.color = "blue";
            }
            case "gruen" -> {
                this.color = "green";
            }
            case "weiss" -> {
                this.color = "white";
            }
        }
        this.size=size;
        this.icon = new ImageIcon("../assets/paper/"+this.color);

    }




}
