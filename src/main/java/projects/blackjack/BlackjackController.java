package projects.blackjack;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;


public class BlackjackController implements Initializable{
    @FXML
    private TextField betAmount;

    @FXML
    private Button dbl;

    @FXML
    private Button deal;

    @FXML
    private Label dealAmount;

    @FXML
    private GridPane dealCards;

    @FXML
    private Button hit;

    @FXML
    private GridPane playCards;

    @FXML
    private Label playerAmount, playName, showBet, outcome, totalMoney, errorBet,resetBtn;

    @FXML
    private Button split;

    @FXML
    private Button stand;
    @FXML
    private Circle resetShape;

    private int startingAmount;
    private int bet = 0;
    private boolean blackjack = false;

    private Player player = new Player("Ben");
    private Player dealer = new Player("Dealer");
    private Deck deck = new Deck();

    ImageView node;
    private void dealCards(){
        ImageView backCard = new ImageView(new Image("file:src/main/resources/projects/blackjack/PNG-cards-1.3/stock-card.png"));
        backCard.setFitHeight(100);
        backCard.setFitWidth(69);
        node = backCard;
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), e -> {
            Card card = deck.getCard();
            player.addCard(card);
            playCards.add(card,0,0);
        }));

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            Card card = deck.getCard();
            dealer.addCard(card);
            dealCards.add(card,0,0);
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), e -> {
            Card card = deck.getCard();
            player.addCard(card);
            playCards.add(card,1,0);
        }));
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(3), e -> {
            dealer.addCard(deck.getCard());
            dealCards.add(backCard,1,0);
        }));

        timeline.play();
    }
    private void score(Player player, Player dealer){
        double winnings = 0;
        if(player.total() > 21){
            outcome.setText("BUST: YOU LOSE");
            winnings = -bet;
        }
        else if (dealer.total() >21){
            winnings = bet;
            outcome.setText("YOU WIN: $"+(int)winnings);
        }
        else if (dealer.total() == player.total()){
            outcome.setText("PUSH");
            winnings = 0;
        }
        else if(blackjack){
            winnings = bet*1.5;
            outcome.setText("YOU WIN: BLACKJACK $"+(int)winnings);
        }
        else if (player.total() > dealer.total()){
            winnings = bet;
            outcome.setText("YOU WIN: $"+(int)winnings);
        }
        else {
            winnings = -bet;
            outcome.setText("YOU LOSE");
        }
        player.betIncrease(winnings);
        String money = String.format("$%.2f", player.getMoney());
        totalMoney.setText(money);

    }

    private void endGame(){
        dealCards.getChildren().remove(node);
        dealCards.add(dealer.getCard(1),1,0);
        dealAmount.setText(""+dealer.total());
        ColumnConstraints column = new ColumnConstraints();
        column.setPrefWidth(69);
        Timeline timeline = new Timeline();
        Runnable addCardAndCheck = () ->
        {
        if(dealer.total()<17){
            Card card = deck.getCard();
            dealer.addCard(card);
            dealCards.getColumnConstraints().add(column);
            dealCards.add(card,dealer.cardSize()-1,0);
            dealAmount.setText(""+dealer.total());
        }
        else{
            timeline.stop();
            score(player, dealer);
            blackjack=false;
            if(player.getMoney()>0) {
                deal.setDisable(false);
                betAmount.setDisable(false);
            } else{ 
                outcome.setText("GAME OVER! OUT OF MONEY");
                resetShape.setVisible(true);
                resetBtn.setText("Reset");

            }
        }
        };
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1.5), e -> {
            addCardAndCheck.run();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();



    }

    private void reset(){
            int count = player.cardSize() - 1;
            Card card;
            while (player.cardSize() > 0) {
                card = player.getCard(count);
                player.removeCard(count);
                playCards.getChildren().remove(card);
                if (count > 1) {
                    playCards.getColumnConstraints().remove(count);
                }
                count--;
            }
            count = dealer.cardSize() - 1;
            while (dealer.cardSize() > 0) {
                card = dealer.getCard(count);
                dealer.removeCard(count);
                dealCards.getChildren().remove(card);
                if (count > 1) {
                    dealCards.getColumnConstraints().remove(count);
                }
                count--;
            }
            outcome.setText("");
            dealAmount.setText("");
            playerAmount.setText("");
            deck = new Deck();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startingAmount = LoginController.amountToStart;
        player.setMoney(startingAmount);
        String money = String.format("$%.2f", player.getMoney());
        totalMoney.setText(money);
        playName.setText(LoginController.personName);
        resetShape.setVisible(false);
        hit.setDisable(true);
        split.setDisable(true);
        dbl.setDisable(true);
        stand.setDisable(true);
    }

    @FXML
    private void bet(ActionEvent event){
            try {
                reset();
                Timeline timeline = new Timeline();
                bet = Integer.parseInt(betAmount.getText());
                if(bet>player.getMoney()){
                    errorBet.setText("Bet Too Large! Try Again");
                    throw new Exception("Illegal Bet");
                }
                errorBet.setText("");
                deal.setDisable(true);
                betAmount.setDisable(true);
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0), e -> {
                    dealCards();
                }));
                timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), e -> {
                    playerAmount.setText(""+player.total());
                    dealAmount.setText(""+dealer.getCard(0).getValue());
                    if(player.total()<21) {
                        hit.setDisable(false);
                        stand.setDisable(false);
                        if(bet<=player.getMoney()/2) {
                            dbl.setDisable(false);
                        }
                    }
                    if(player.total()==21){
                        blackjack = true;
                        endGame();

                    }
                }));

                timeline.play();
            }
            catch(Exception e){
                betAmount.clear();
                if(errorBet.getText().equals("")){
                    errorBet.setText("Illegal Character! Try Again");
                }
            }
    }

    @FXML
    private void hitMe(ActionEvent event){
        dbl.setDisable(true);
        ColumnConstraints column = new ColumnConstraints();
        column.setPrefWidth(69);
        playCards.getColumnConstraints().add(column);
        Card card = deck.getCard();
        player.addCard(card);
        playCards.add(card,player.cardSize()-1,0);
        playerAmount.setText("" + player.total());
        if(player.total()>=21){
            hit.setDisable(true);
            stand.setDisable(true);
            endGame();
        }
    }

    @FXML
    private void stand(ActionEvent event){
        hit.setDisable(true);
        stand.setDisable(true);
        dbl.setDisable(true);
        split.setDisable(true);
        endGame();
    }
    @FXML
    private void doubleDown(ActionEvent event){
        hit.setDisable(true);
        stand.setDisable(true);
        dbl.setDisable(true);
        bet = bet*2;
        ColumnConstraints column = new ColumnConstraints();
        column.setPrefWidth(69);
        playCards.getColumnConstraints().add(column);
        Card card = deck.getCard();
        player.addCard(card);
        playCards.add(card,player.cardSize()-1,0);
        playerAmount.setText("" + player.total());
        endGame();
    }

    @FXML
    private void fullReset(MouseEvent event){
        player.setMoney(startingAmount);
        reset();
        deal.setDisable(false);
        betAmount.clear();
        betAmount.setDisable(false);
        resetShape.setVisible(false);
        String money = String.format("$%.2f",player.getMoney());
        totalMoney.setText(money);
    }

}