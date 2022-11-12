package ca.bcit.comp2522.termproject.secretwonders;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenu extends Application {
    private final int gameWindowHeight   = 300;
    private final int gameWindowWidth    = 300;
    private final String gameWindowTitle = "Secret Wonders";

    @Override
    public void start(final Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(GameMenu.class.getResource("MainMenuContent.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), gameWindowWidth, gameWindowHeight);
        stage.setTitle(gameWindowTitle);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        launch();
    }
}
