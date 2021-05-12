package boardgame.controller;

import boardgame.BoardGameApplication;
import boardgame.model.Data;
import boardgame.model.Players;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

@Slf4j
public class MainSceneController {

    @FXML
    private TextField playerNameText;

    @FXML
    private Button startButton;

    @FXML
    private Label errorLabel;

    @FXML
    void startAction(ActionEvent event) {
        if (playerNameText.getText().trim().isEmpty()) {
            errorLabel.setText("* Player Name is required!!");
            log.error("Player Name is not Provided");
        }
        else {
            try {
                FXMLLoader loader = new FXMLLoader(BoardGameApplication.class.getResource("/fxml/ChessScene.fxml"));
                Data.setPlayer1(playerNameText.getText());
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Main Board Game");
                stage.toFront();
                stage.show();

                ((Stage) startButton.getScene().getWindow()).close();
                log.info("Game Scene is Loading");

                JAXBContext jaxbContext = JAXBContext.newInstance(Players.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

                Players player = (Players) jaxbUnmarshaller.unmarshal( new File("result.xml") );

                Data.setPlayerList(player.getPlayers());

            } catch (IOException  x) {
                log.error("Can't Load Scene, Error is: {}", x);
            }
            catch (JAXBException x){
                log.error("Can't Read Data from persistence, Error is {}",x);
            }
            log.info("Player 1 Name is set to {}, loading game scene.", playerNameText.getText());
        }

    }


}
