package boardgame.controller;

import boardgame.BoardGameApplication;
import boardgame.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ChessSceneController {

    private Knight[] blackKnights = new Knight[3];
    private Knight[] whiteKnights = new Knight[3];
    private Knight clickedKnight;
    GameState gameState = new GameState();


    @FXML
    private GridPane chessBoardView;

    @FXML
    private Label tunLabel;

    @FXML
    private Button giveUpButton;

    @FXML
    void GiveUpClicked(ActionEvent event) {

        try {
            persistData();
        } catch (JAXBException e) {
            log.error("Error while persistance, {} ", e);
        }
        showResult();
    }

    @FXML
    void paneMouseClicked(MouseEvent event) {
        gameState.getPlayer().incrementStep();
        int rowIndex = (GridPane.getRowIndex((Node) event.getSource()) == null) ? 0 : GridPane.getRowIndex((Node) event.getSource());
        int colIndex = (GridPane.getColumnIndex((Node) event.getSource()) == null) ? 0 : GridPane.getColumnIndex((Node) event.getSource());
        if (availablePanesToMove.contains(event.getSource()) && event.getSource() instanceof Pane) {

            GameState.RemoveRestricted(clickedKnight.getCurrentLocation());

            availablePanesToMove.forEach((pane) -> resetColors(rowIndex, colIndex, pane));
            GridPane.setConstraints(clickedKnight, colIndex, rowIndex);
            clickedKnight.setRow(rowIndex);
            clickedKnight.setCol(colIndex);
            availablePanesToMove = FXCollections.observableArrayList();

            Point restrictedPSquare = clickedKnight.getCurrentLocation();
            GameState.AddRestricted(restrictedPSquare);
            log.info("Added new Pane in Restricted Squares with Row: {} and Col: {}", restrictedPSquare.x, restrictedPSquare.y);
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

        //Marshal the players list in console
        jaxbMarshaller.marshal(players, System.out);

        //Marshal the players list in file
        jaxbMarshaller.marshal(players, new File("result.xml"));
    }

    private void showResult() {
        try {
            FXMLLoader loader = new FXMLLoader(BoardGameApplication.class.getResource("/fxml/ResultScene.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Result");
            stage.toFront();
            stage.show();

            ((Stage) giveUpButton.getScene().getWindow()).close();
            log.info("Result Scene is Loading");
        } catch (IOException x) {
            log.error("Can't Load Scene, Error is: {}", x);
        }
    }


    private void setPaneClickEvent() {
        gameState.setWhiteMove(!gameState.isWhiteMove());
        if (gameState.isWhiteMove()) {
            Arrays.stream(gameState.getPlayer().getBlackKnights()).forEach(knight -> {
                knight.setOnMouseClicked(null);
            });

            Arrays.stream(gameState.getPlayer().getWhiteKnights()).forEach(knight -> {
                knight.setOnMouseClicked(this::knightOnMouseClicked);
            });

            tunLabel.setText("White's Turn");
        }
        else {
            Arrays.stream(gameState.getPlayer().getBlackKnights()).forEach(knight -> {
                knight.setOnMouseClicked(this::knightOnMouseClicked);
            });

            Arrays.stream(gameState.getPlayer().getWhiteKnights()).forEach(knight -> {
                knight.setOnMouseClicked(null);
            });

            tunLabel.setText("Black's Turn");
        }
    }

    private ObservableList<Pane> availablePanesToMove = FXCollections.observableArrayList();

    /**
     * <p>
     * This method will reset color of Pane after movement of knight.</p>
     *
     * <p>
     * It will be called for every pane exists.</p>
     *
     * @param rowIndex row Index of clicked pane
     * @param colIndex col Index of clicked Pane
     * @param pane     pane
     * @throws IOException
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

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

        gameState = new GameState(new Player(Data.getPlayer1()));
        GameState.resetRestricted();

        Arrays.stream(gameState.getPlayer().getWhiteKnights())
                .forEach(whiteKnight -> {
                    chessBoardView.add(whiteKnight, whiteKnight.getCol(), whiteKnight.getRow());
                    GameState.AddRestricted(whiteKnight.getCurrentLocation());
                    whiteKnight.setOnMouseClicked(this::knightOnMouseClicked);
                });
        Arrays.stream(gameState.getPlayer().getBlackKnights())
                .forEach(blackKnight -> {
                    chessBoardView.add(blackKnight, blackKnight.getCol(), blackKnight.getRow());
                    GameState.AddRestricted(blackKnight.getCurrentLocation());
                    blackKnight.setOnMouseClicked(null);
                });

        tunLabel.setText(gameState.isWhiteMove() ? "White's Turn" : "Black's Turn");
    }

    /**
     *
     * @param e
     */

    @FXML
    private void knightOnMouseClicked(MouseEvent e) {
        int rowIndex = (GridPane.getRowIndex((Node) e.getSource()) == null) ? 0 : GridPane.getRowIndex((Node) e.getSource());
        int colIndex = (GridPane.getColumnIndex((Node) e.getSource()) == null) ? 0 : GridPane.getColumnIndex((Node) e.getSource());

        availablePanesToMove.forEach((pane) -> resetColors(rowIndex, colIndex, pane));

        clickedKnight = (Knight) e.getSource();
        ArrayList<Point> clickedKnightMoves = gameState.getKnightMoves(clickedKnight);

        for (Point move : clickedKnightMoves) {
            ObservableList<Node> chessBoardViewChildren = chessBoardView.getChildren();
            for (Node node : chessBoardViewChildren) {
                int row_index = (GridPane.getRowIndex(node)) == null ? 0 : GridPane.getRowIndex(node);
                int col_index = (GridPane.getColumnIndex(node)) == null ? 0 : GridPane.getColumnIndex(node);


                if (row_index == move.x && col_index == move.y) {

                    try {
                        availablePanesToMove.add((Pane) node);
                    }catch(Exception x){}
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
