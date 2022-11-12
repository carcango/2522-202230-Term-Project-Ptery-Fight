package ca.bcit.comp2522.termproject.secretwonders;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainMenuContentController {

    @FXML private Text newGameButtonAction;
    @FXML private Text instructionsButtonAction;
    @FXML private Text quitGameButtonAction;

    // New Game Button Action
    @FXML protected void handleNewGameButtonAction(final ActionEvent event) {
        newGameButtonAction.setText("New Game button pressed");
    }

    // Instruction Button Action
    @FXML protected void handleInstructionsButtonAction(final ActionEvent event) {
        instructionsButtonAction.setText("Instructions button pressed");
    }

    // Quit Game Button Action
    @FXML protected void handleQuitGameButtonAction(final ActionEvent event) {
        quitGameButtonAction.setText("Quit Game button pressed");
    }
}
