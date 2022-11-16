package ca.bcit.comp2522.termproject.secretwonders;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Represents a main menu for an application.
 * @author Rhys Mahannah
 * @version 12 November 2022
 */
public class MainMenu extends Application {

    private static final int BUTTON_SPACING = 10;

    private final int    menuWindowHeight = 300;
    private final int    menuWindowWidth  = 400;
    private final String menuWindowTitle  = "Secret Wonders";
    private final Text   mainMenuTitle    = new Text("Secret Wonders");
    private final Text   gameDesigners    = new Text("A game by Carson Olafson and Rhys Mahannah");

    /**
     * Stages a main menu for a game.
     * Contains a header, a footer and three buttons: Start Game, Instructions, Quit Game.
     *
     * @param stage a scene
     */
    @Override
    public void start(final Stage stage) {

        // Menu buttons
        Button startGameButton = new Button();
        startGameButton.setText("Start Game");
        startGameButton.setOnAction(new EventHandler<>() {
            /**
             * Opens a new game window.
             *
             * @param actionEvent opens game window
             */
            @Override
            public void handle(final ActionEvent actionEvent) {

                stage.close(); // Closes the main menu window.

                final int gameWindowHeight = 800;
                final int gameWindowWidth = 800;
                final String gameWindowTitle = "Secret Wonders";

                Pane gamePane = new Pane();
                Scene gameScene = new Scene(gamePane, gameWindowWidth, gameWindowHeight);
                Stage gameStage = new Stage();
                gameStage.setTitle(gameWindowTitle);
                gameStage.setScene(gameScene);

                // Specifies modality for new window
                gameStage.initModality(Modality.APPLICATION_MODAL);
                gameStage.show();
            }
        });


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

        // Set up parent for VBox
        BorderPane borderPane = new BorderPane();
        BorderPane.setAlignment(mainMenuTitle, Pos.CENTER);
        BorderPane.setAlignment(gameDesigners, Pos.CENTER);

        // Center borderpane children
        borderPane.setTop(mainMenuTitle);
        borderPane.setCenter(vbox);
        borderPane.setBottom(gameDesigners);

        // Add CSS styling ID's to header and footer
        mainMenuTitle.setId("main-menu-title");
        gameDesigners.setId("game-designers");

        // Set stage, scene
        Scene scene = new Scene(borderPane, menuWindowWidth, menuWindowHeight);
        scene.getStylesheets().add("/style.css");
        stage.setScene(scene);
        stage.setTitle(menuWindowTitle);
        stage.show();
    }

    /**
     * Launches the program.
     * @param args command line arguments.
     */
    public static void main(final String[] args) {
        launch();
    }
}
