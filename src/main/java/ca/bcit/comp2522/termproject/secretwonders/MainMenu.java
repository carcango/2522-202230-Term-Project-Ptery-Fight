package ca.bcit.comp2522.termproject.secretwonders;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.IOException;

public class MainMenu extends Application {
    private static final int BUTTON_SPACING = 10;

    private final int    gameWindowHeight   = 300;
    private final int    gameWindowWidth    = 400;
    private final String gameWindowTitle    = "Secret Wonders";
    private final Text   mainMenuTitle      = new Text("Secret Wonders");
    private final Text   gameDesigners = new Text("A game by Carson Olafson and Rhys Mahannah");

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
        BorderPane.setAlignment(mainMenuTitle, Pos.CENTER);
        BorderPane.setAlignment(gameDesigners, Pos.CENTER);

        borderPane.setTop(mainMenuTitle);
        borderPane.setBottom(gameDesigners);

        borderPane.setCenter(vbox);
        mainMenuTitle.setId("main-menu-title");
        gameDesigners.setId("game-designers");

        try {
            Scene scene = new Scene(borderPane, gameWindowWidth, gameWindowHeight);
            scene.getStylesheets().add("/style.css");
            stage.setScene(scene);
            stage.setTitle(gameWindowTitle);
            stage.show();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Launches the program.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        launch();
    }
}
