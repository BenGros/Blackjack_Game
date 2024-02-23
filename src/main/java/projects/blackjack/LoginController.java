package projects.blackjack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField startAmount, playerName;
    @FXML
    private Button go;
    public static int amountToStart = 0;
    public static String personName;

    @FXML
    private void start(ActionEvent event) throws IOException {
        amountToStart = Integer.parseInt(startAmount.getText());
        personName= playerName.getText();
        FXMLLoader fxmlLoader = new FXMLLoader(BlackjackApplication.class.getResource("blackjack-view.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(fxmlLoader.load(), 1000,600));
        stage.setTitle("BLACKJACK");
        stage.show();

        Stage stage2 = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage2.close();
    }
}
