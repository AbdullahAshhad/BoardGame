package boardgame;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <p>The Class responsible for starting GUI operations.</p>
 *
 */
public class BoardGameApplication extends Application {

    /**
     * <p>This method will launch the main scene of the game and set the attributes.</p>
     * @param stage is the main scene of the game.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainScene.fxml"));
        stage.setTitle("Board Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
