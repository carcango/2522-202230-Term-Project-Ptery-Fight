package ca.bcit.comp2522.termproject.secretwonders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents a main menu screen.
 * @author Rhys Mahannah
 * @version 17 November 2022
 */
public class Driver extends Application {

    private final String menuWindowTitle  = "Secret Wonders";
    private final int    menuWindowHeight = 300;
    private final int    menuWindowWidth  = 400;

    /**
     * Creates a main menu screen.
     * @param stage a scene
     * @throws IOException if FXML file cannot be found.
     */
    @Override
    public void start(final Stage stage) throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Driver.class.getResource("/main-menu.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), menuWindowWidth, menuWindowHeight);
            stage.setTitle(menuWindowTitle);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ioe) {
            throw new IOException("Cannot find file");
        }
    }

    /**
     * Drives the game.
     * @param args arguments.
     */
    public static void main(final String[] args) {
        launch();
    }
}
