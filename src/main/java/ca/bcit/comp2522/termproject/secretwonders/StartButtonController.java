package ca.bcit.comp2522.termproject.secretwonders;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class StartButtonController {
    private Button       newGameButton;
    private final int    gameWindowHeight = 800;
    private final int    gameWindowWidth  = 800;
    private final String gameWindowTitle  = "Secret Wonders";

    /**
     * Opens a new window where the game is played.
     * @param actionEvent
     * @throws IOException
     */
    public void newGameButtonOpensNewWindow(final ActionEvent actionEvent) throws IOException {
        Pane gamePane   = new Pane();
        Scene gameScene = new Scene(gamePane, gameWindowWidth, gameWindowHeight);
        Stage gameStage = new Stage();
        gameStage.setTitle(gameWindowTitle);
        gameStage.setScene(gameScene);

        // Specifies modality for new window
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.show();
    }
}
