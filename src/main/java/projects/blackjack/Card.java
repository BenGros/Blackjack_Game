package projects.blackjack;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView {
    private int value;
    private String name;
    private int secondValue;
    private int num;

    private boolean used;
    public Card(){

    }

    public Card(int value, String name, Image image){

        this.value =value;
        this.name = name;
        used = false;
        secondValue = 0;
        this.setImage(image);
        this.setFitWidth(69);
        this.setFitHeight(100);
    }

    public void setSecondValue(int secondValue) {
        this.secondValue = secondValue;
    }
    public String getName(){return name;}

    public void setUsed(boolean used){
        this.used = used;
    }
    public boolean getUsed(){
        return used;
    }
    public int getValue(){
        return value;
    }
    public int getSecondValue(){
        return secondValue;
    }
    public void setNum(int num){
        this.num = num;
    }
    public int getNum(){return num;}



    public String toString(){
        return name;
    }
}
