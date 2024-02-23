package projects.blackjack;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cards = new ArrayList<>();
    public String name;
    private double money;
    public Player(String name){
        this.name = name;
    }
    public void addCard(Card card){
        cards.add(card);
    }
    public Card getCard(int index){
        return cards.get(index);
    }
    public void removeCard(int index){
        cards.remove(index);
    }
    public void setMoney(double money){
        this.money = money;
    }
    public double getMoney(){
        return money;
    }
    public void betIncrease(double bet){
        money = money + bet;
    }
    public int cardSize(){return cards.size();}

    public int total(){
        int aceIndex = -1;
        int total = 0;
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            int value = card.getValue();
            total += value;
            if(value == 11){
                aceIndex = i;
            }
        }
        while(total > 21 && aceIndex !=-1){
            total = 0;
            int nextAceIndex = -1;
            for (int i = 0; i <cards.size(); i++) {
                Card card = cards.get(i);
                if(card.getUsed()){
                    int value = card.getSecondValue();
                    total += value;
                }
                else if(i == aceIndex){
                    int value = card.getSecondValue();
                    total += value;
                    card.setUsed(true);
                    aceIndex = -1;
                }
                else{
                    int value = card.getValue();
                    total += value;
                    if(value == 11 && !card.getUsed()){
                        nextAceIndex = i;
                    }
                }
            }
            aceIndex = nextAceIndex;
        }
        return total;
    }

    public void resetPlayer(){
        while(cards.size()>0){
            cards.remove(0);
        }
    }

    public String printDealer(){
        String string = "\nDealer";
        string += "\nCard 1: Hidden";
        string += "\nCard 2: " + cards.get(1);
        string += "\nTotal: " + (total() - cards.get(0).getValue());
        return string;
    }

    public String toString(){
        String string = "\n"+ name;
        for (int i = 0; i < cards.size(); i++) {
            string += "\nCard " + (i+1) +": " + cards.get(i);
        }
        string += "\nTotal: " + total();
        return string;
    }
}
