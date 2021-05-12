package boardgame.controller;

import boardgame.model.Data;
import boardgame.model.GameState;

import boardgame.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * Controller Class for ResultScene.fxml.
 * <p>
 *
 */
@Slf4j
public class ResultSceneController {

    @FXML
    private TableView<Player> resultTable;

    @FXML
    private TableColumn<Player, String> player;

    @FXML
    private TableColumn<Player, Integer> numSteps;

    @FXML
    private TableColumn<Player, LocalDateTime> gameStarted;

    @FXML
    private TableColumn<Player, LocalDateTime> gameFinished;

    @FXML
    private TableColumn<Player, Boolean> goalAchieved;



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
        stage.setTitle("Main Game Board");
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
        List<Player> players = Data.getPlayerList();
        Collections.sort(players);
        List<Player> topTenList =  players.stream().skip(0).limit(10).collect(Collectors.toList());
        players.stream().map(Player::getNumSteps).forEach(System.out::println);

        resultTable.setItems(null);
        player.setCellValueFactory(new PropertyValueFactory<>("name"));
        numSteps.setCellValueFactory(new PropertyValueFactory<>("numSteps"));
        gameStarted.setCellValueFactory(new PropertyValueFactory<>("gameStarted"));
        gameFinished.setCellValueFactory(new PropertyValueFactory<>("gameFinished"));
        goalAchieved.setCellValueFactory(new PropertyValueFactory<>("isGoalAchieved"));



        gameFinished.setCellFactory(column -> {
            TableCell<Player, LocalDateTime> cell = new TableCell<Player, LocalDateTime>() {
                private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd - HH:mm:ss");

                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.format(formatter));
                    }
                }
            };

            return cell;
        });

        ObservableList<Player> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(topTenList);

        resultTable.setItems(observableResult);
    }

}
