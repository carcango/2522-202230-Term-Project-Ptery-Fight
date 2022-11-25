package ca.bcit.comp2522.termproject.secretwonders;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DriverController {

    @FXML public Button startGameButton;
    @FXML public Button quitGameButton;

    @FXML
    protected void onStartGameButtonClick() {
        GameEngine gameEngine = new GameEngine();
        Stage primaryScreen = new Stage();
        primaryScreen.setTitle("Bug Blaster");
        primaryScreen.setScene(gameEngine.getScene());
        primaryScreen.show();
    }

    @FXML
    protected void onInstructionsButtonClick() { }

    @FXML
    protected void onQuitGameButtonClick() {
        Stage stage = (Stage) quitGameButton.getScene().getWindow();
        stage.close();
    }
}
