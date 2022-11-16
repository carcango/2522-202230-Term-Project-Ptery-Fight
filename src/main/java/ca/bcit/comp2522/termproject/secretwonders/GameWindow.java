package ca.bcit.comp2522.termproject.secretwonders;

import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Represents a video game window.
 * @author Rhys Mahannah
 * @version 15 November 2022
 */
public class GameWindow extends Application implements Initializable {

    private final int    gameWindowHeight = 800;
    private final int    gameWindowWidth  = 800;
    private final String gameWindowTitle  = "Secret Wonders";

    /**
     * Creates game window.
     */
    public GameWindow() { }

    /**
     * Stages a game window.
     * @param stage a scene
     */
    @Override
    public void start(final Stage stage) {

        // Window Layout
        Pane pane = new Pane();
        Scene scene = new Scene(pane, gameWindowWidth, gameWindowHeight);
        stage.setScene(scene);
        stage.setTitle(gameWindowTitle);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
