module projects.blackjack {
    requires javafx.controls;
    requires javafx.fxml;


    opens projects.blackjack to javafx.fxml;
    exports projects.blackjack;
}