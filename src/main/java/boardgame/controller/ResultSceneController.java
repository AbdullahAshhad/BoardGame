package boardgame.controller;

import boardgame.model.GameState;

import boardgame.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Controller Class for ResultScene.fxml.
 * <p>
 *
 */
@Slf4j
public class ResultSceneController {

    @FXML
    private TableView<Player> toptenTable;

    @FXML
    private TableColumn<Player, String> player;

    @FXML
    private TableColumn<Player, Integer> winCount;

    @FXML
    private TableColumn<Player, ZonedDateTime> created;



    /**
     * <p>
     * It will call the main Scene Again and close this one.</p>
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    private void back(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(GameState.class.getResource("/fxml/MainScene.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Main Chess");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.toFront();
        stage.show();

        ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow()).close();

        log.info("Loading Main scene.");
    }


    /**
     * <p>
     * It will load Data in TableView from Database.</p>
     */
    @FXML
    public void initialize() {

    }

}
