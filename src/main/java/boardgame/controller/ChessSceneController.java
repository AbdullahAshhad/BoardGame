package boardgame.controller;

import boardgame.BoardGameApplication;
import boardgame.controller.FunctionsLibrary.FunctionsLib;
import boardgame.controller.customControls.KnightController;
import boardgame.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class handle the Game and control the whole Chess.
 */
@Slf4j
public class ChessSceneController {

    GameState gameState = new GameState();
    private KnightController[] blackKnights = new KnightController[3];
    private KnightController[] whiteKnights = new KnightController[3];
    private KnightController clickedKnight;
    @FXML
    private GridPane chessBoardView;

    @FXML
    private Label tunLabel;

    @FXML
    private Button giveUpButton;
    private ObservableList<Pane> availablePanesToMove = FXCollections.observableArrayList();

    /**
     * <p>It will persist data into JSON and then show the Result window.</p>
     */
    @FXML
    void GiveUpClicked() {
        try {
            persistData();
        } catch (JAXBException e) {
            log.error("Error while persistence, {} ", e);
        }
        showResult();
    }

    /**
     * <p>It will move the knight to the pane clicked if that is in the available panes to move.</p>
     *
     * It will check the restricted panes and move knight accordingly and then change the restricted squares.
     * It will also  check if goal achieved on every move.
     *
     * @param event fired by the pane click.
     */
    @FXML
    void paneMouseClicked(MouseEvent event) {
        gameState.getPlayer().incrementStep();
        int rowIndex = (GridPane.getRowIndex((Node) event.getSource()) == null) ? 0 : GridPane.getRowIndex((Node) event.getSource());
        int colIndex = (GridPane.getColumnIndex((Node) event.getSource()) == null) ? 0 : GridPane.getColumnIndex((Node) event.getSource());
        if (availablePanesToMove.contains(event.getSource()) && event.getSource() instanceof Pane) {

            GameState.RemoveRestricted(clickedKnight.getKnight().getCurrentLocation());

            availablePanesToMove.forEach((pane) -> resetColors(rowIndex, colIndex, pane));
            GridPane.setConstraints(clickedKnight, colIndex, rowIndex);
            clickedKnight.getKnight().setRow(rowIndex);
            clickedKnight.getKnight().setCol(colIndex);
            availablePanesToMove = FXCollections.observableArrayList();

            Pair<Integer, Integer> restrictedPSquare = clickedKnight.getKnight().getCurrentLocation();
            GameState.AddRestricted(restrictedPSquare);
            log.info("Added new Pane in Restricted Squares with Row: {} and Col: {}", restrictedPSquare.getLeft(), restrictedPSquare.getRight());
            setPaneClickEvent();
            if (gameState.isGoalAchieved()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Goal Achieved, Congratulations!", ButtonType.OK);
                a.showAndWait();
                gameState.getPlayer().setIsGoalAchieved(true);

                try {
                    persistData();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
                showResult();


            }

        } else {
            log.warn("Goal Achieved in {} steps", gameState.getPlayer().getNumSteps());
        }
    }

    /**
     * <p>This will persists Data in JSON format.</p>
     *
     * @throws JAXBException , it throws the JAXB exception.
     */
    private void persistData() throws JAXBException {
        gameState.getPlayer().setGameFinished(LocalDateTime.now());
        List<Player> playerList = Data.getPlayerList();
        playerList.add(gameState.getPlayer());
        Data.setPlayerList(playerList);

        Players players = new Players();
        players.setPlayers(Data.getPlayerList());

        JAXBContext jaxbContext = JAXBContext.newInstance(Players.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(players, new File("result.xml"));
        log.info("Player Data persisted to File.");
    }

    /**
     * <p>This will show the Result window.</p>
     *
     * It will also override the onCloseEvent.
     */
    private void showResult() {
        try {
            FXMLLoader loader = new FXMLLoader(BoardGameApplication.class.getResource("/fxml/ResultScene.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Result");
            stage.toFront();
            stage.setOnCloseRequest(FunctionsLib.confirmCloseEventHandler);
            stage.show();

            ((Stage) giveUpButton.getScene().getWindow()).close();
            log.info("Result Scene is Loading");
        } catch (IOException x) {
            log.error("Can't Load Scene, Error is: {}", x);
        }
    }

    /**
     * <p>It will change the knight OnMouseClick method on every move.</p>
     *
     * It will actually check the knight move according to color and then change the Event accordingly.
     */
    private void setPaneClickEvent() {
        gameState.setWhiteMove(!gameState.isWhiteMove());
        if (gameState.isWhiteMove()) {
            Arrays.stream(blackKnights).forEach(knight -> knight.setOnMouseClicked(null));
            Arrays.stream(whiteKnights).forEach(knight -> knight.setOnMouseClicked(this::knightOnMouseClicked));

            tunLabel.setText("White's Turn");
        } else {
            Arrays.stream(blackKnights).forEach(knight -> knight.setOnMouseClicked(this::knightOnMouseClicked));
            Arrays.stream(whiteKnights).forEach(knight -> knight.setOnMouseClicked(null));

            tunLabel.setText("Black's Turn");
        }
    }

    /**
     * <p>
     * This method will reset color of Pane after movement of knight.</p>
     *
     * <p>
     * It will be called for every pane exists.</p>
     *
     * @param rowIndex row Index of clicked pane
     * @param colIndex col Index of clicked Pane
     * @param pane     pane whose color to be changed.
     */
    private void resetColors(int rowIndex, int colIndex, Pane pane) {
        if (rowIndex % 2 == 0 && colIndex % 2 == 0) {

            pane.setStyle("-fx-background-color: white;");
        } else if (rowIndex % 2 != 0 && colIndex % 2 == 0) {
            pane.setStyle("-fx-background-color: white;");
        } else if (rowIndex % 2 == 0 && colIndex % 2 != 0) {
            pane.setStyle("-fx-background-color: white;");
        } else {
            pane.setStyle("-fx-background-color: white;");
        }

    }

    /**
     * <p>This method is called by the FXMLLoader when initialization is complete.</p>
     *
     * It will initialize the game state and put knight on the relevant positions.
     */
    @FXML
    void initialize() {

        gameState = new GameState(new Player(Data.getPlayer1()));
        GameState.resetRestricted();

        BlackKnight[] tempBlackNight = gameState.getPlayer().getBlackKnights();
        Knight[] tempWhiteNight = gameState.getPlayer().getWhiteKnights();

        IntStream.range(0, tempBlackNight.length)
                .forEach(i -> blackKnights[i] = new KnightController(tempBlackNight[i]));

        IntStream.range(0, tempWhiteNight.length)
                .forEach(i -> whiteKnights[i] = new KnightController(tempWhiteNight[i]));

        Arrays.stream(whiteKnights)
                .forEach(whiteKnight -> {
                    chessBoardView.add(whiteKnight, whiteKnight.getKnight().getCol(), whiteKnight.getKnight().getRow());
                    GameState.AddRestricted(whiteKnight.getKnight().getCurrentLocation());
                    whiteKnight.setOnMouseClicked(this::knightOnMouseClicked);
                });
        Arrays.stream(blackKnights)
                .forEach(blackKnight -> {
                    chessBoardView.add(blackKnight, blackKnight.getKnight().getCol(), blackKnight.getKnight().getRow());
                    GameState.AddRestricted(blackKnight.getKnight().getCurrentLocation());
                    blackKnight.setOnMouseClicked(null);
                });

        tunLabel.setText(gameState.isWhiteMove() ? "White's Turn" : "Black's Turn");
    }

    /**
     * <p>It will highlight the possible moves after click on knight.</p>
     * It will check the available moves and highlight them, and it will also reset the color.
     * @param e Mouse Event initiated after clicking on Knight.
     */

    @FXML
    private void knightOnMouseClicked(MouseEvent e) {
        int rowIndex = (GridPane.getRowIndex((Node) e.getSource()) == null) ? 0 : GridPane.getRowIndex((Node) e.getSource());
        int colIndex = (GridPane.getColumnIndex((Node) e.getSource()) == null) ? 0 : GridPane.getColumnIndex((Node) e.getSource());

        availablePanesToMove.forEach((pane) -> resetColors(rowIndex, colIndex, pane));

        clickedKnight = (KnightController) e.getSource();
        ArrayList<Pair<Integer, Integer>> clickedKnightMoves = gameState.getKnightMoves(clickedKnight.getKnight());

        for (Pair<Integer, Integer> move : clickedKnightMoves) {
            ObservableList<Node> chessBoardViewChildren = chessBoardView.getChildren();
            for (Node node : chessBoardViewChildren) {
                int row_index = (GridPane.getRowIndex(node)) == null ? 0 : GridPane.getRowIndex(node);
                int col_index = (GridPane.getColumnIndex(node)) == null ? 0 : GridPane.getColumnIndex(node);


                if (row_index == move.getLeft() && col_index == move.getRight()) {

                    try {
                        availablePanesToMove.add((Pane) node);
                    } catch (Exception x) {
                    }
                    node.setStyle("-fx-background-color: #FFFF00;");

                }
            }
        }

        if (availablePanesToMove.size() == 0 || clickedKnightMoves.size() == 0) {
            Alert a = new Alert(Alert.AlertType.INFORMATION, "No More Moves Available for this knight, try another one!", ButtonType.OK);
            a.showAndWait();
        }
    }

}