package projects.blackjack;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>(52);
    private int size;
    public int getSize(){
        return deck.size();
    }

    public Deck() {
        for (int j = 0; j <4; j++) {
            String suit="";
            switch (j){
                case 0:
                    suit = "diamonds.png";
                    break;
                case 1:
                    suit = "clubs.png";
                    break;
                case 2:
                    suit = "spades.png";
                    break;
                case 3:
                    suit = "hearts.png";
                    break;
            }

            for (Integer i = 1; i < 14; i++) {
                String name = i.toString();
                if (i == 1) {
                    Image image = new Image("file:src/main/resources/projects/blackjack/PNG-cards-1.3/" + "ace_of_" + suit);
                    name = "Ace";
                    Card card = new Card(11, name,image);
                    card.setSecondValue(1);
                    addCard(card);
                } else if (i == 11) {
                    Image image = new Image("file:src/main/resources/projects/blackjack/PNG-cards-1.3/" + "jack_of_" + suit);
                    name = "Jack";
                    Card card = new Card(10, name,image);
                    addCard(card);
                } else if (i == 12) {
                    Image image = new Image("file:src/main/resources/projects/blackjack/PNG-cards-1.3/" + "queen_of_" + suit);
                    name = "Queen";
                    Card card = new Card(10, name,image);
                    addCard(card);
                } else if (i == 13) {
                    Image image = new Image("file:src/main/resources/projects/blackjack/PNG-cards-1.3/" + "king_of_" + suit);
                    name = "King";
                    Card card = new Card(10, name,image);
                    addCard(card);
                } else {
                    Image image = new Image("file:src/main/resources/projects/blackjack/PNG-cards-1.3/" + i+"_of_" + suit);
                    addCard(new Card(i, name,image));
                }
            }
        }
    }

    void addCard(Card card){
        for (int i = 0; i < 7; i++) {
            Card carded = new Card(card.getValue(), card.getName(), card.getImage());
            carded.setSecondValue(card.getSecondValue());
            carded.setNum(i);
            deck.add(carded);
        }
    }

    public Card getCard(){
        Random ran = new Random();
        int number = ran.nextInt(deck.size());
        Card card = deck.get(number);
        deck.remove(number);
        return card;
    }
}
