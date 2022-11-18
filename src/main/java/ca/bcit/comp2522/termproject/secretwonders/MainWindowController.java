package ca.bcit.comp2522.termproject.secretwonders;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainWindowController {

    @FXML public Button startGameButton;
    @FXML public Button instructionsButton;
    @FXML public Button quitGameButton;

    @FXML
    protected void onStartGameButtonClick(final ActionEvent event) {}

    @FXML
    protected void onInstructionsButtonClick(final ActionEvent event) {}

    @FXML
    protected void onQuitGameButtonClick(final ActionEvent event) {
        Stage stage = (Stage) quitGameButton.getScene().getWindow();
        stage.close();
    }
}
