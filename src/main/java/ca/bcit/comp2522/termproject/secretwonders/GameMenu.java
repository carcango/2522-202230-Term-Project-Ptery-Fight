package ca.bcit.comp2522.termproject.secretwonders;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class GameMenu extends Application {
    private static final int BUTTON_SPACING = 10;

    private final int    gameWindowHeight   = 300;
    private final int    gameWindowWidth    = 400;
    private final String gameWindowTitle    = "Secret Wonders";

    @Override
    public void start(final Stage stage) throws IOException {

        // Game buttons
        Button startGameButton = new Button();
        startGameButton.setText("Start Game");
        startGameButton.setOnAction(actionEvent ->
                startGameButton.setText("Start game button pressed"));

        Button instructionsButton = new Button();
        instructionsButton.setText("Instructions");
        instructionsButton.setOnAction(actionEvent ->
                instructionsButton.setText("Instructions button pressed"));

        Button quitGameButton = new Button();
        quitGameButton.setText("Quit Game");
        quitGameButton.setOnAction(actionEvent ->
                quitGameButton.setText("Quit Game button pressed"));

        // Window layout
        VBox vbox = new VBox();
        vbox.getChildren().addAll(startGameButton, instructionsButton, quitGameButton);
        vbox.setSpacing(BUTTON_SPACING);
        vbox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(vbox);

        Scene scene = new Scene(borderPane, gameWindowWidth, gameWindowHeight);
        stage.setScene(scene);
        stage.setTitle(gameWindowTitle);
        stage.show();
    }

    public static void main(final String[] args) {
        launch();
    }
}
