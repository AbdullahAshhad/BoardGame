package boardgame.controller;

import boardgame.BoardGameApplication;
import boardgame.model.Data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MainSceneController {

    @FXML
    private TextField player1nameText;

    @FXML
    private Button startButton;

    @FXML
    private Label errorLabel;

    @FXML
    void startAction(ActionEvent event) {
        if (player1nameText.getText().trim().isEmpty()) {
            errorLabel.setText("* Player Names are required!!");
            log.error("Player Names are not Provided");
        }
        else {
            try {
                FXMLLoader loader = new FXMLLoader(BoardGameApplication.class.getResource("/fxml/ChessScene.fxml"));
                Data.setPlayer1(player1nameText.getText());
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Main Chess");
                stage.toFront();
                stage.show();

                ((Stage) startButton.getScene().getWindow()).close();
                log.info("Game Scene is Loading");
            } catch (IOException x) {
                log.error("Can't Load Scene, Error is: {}", x);
            }
            log.info("Player 1 Name is set to {}, loading game scene.", player1nameText.getText());
        }

    }


}
