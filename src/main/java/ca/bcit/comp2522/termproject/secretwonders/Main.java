package ca.bcit.comp2522.termproject.secretwonders;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        GameEngine engine = new GameEngine();
        primaryStage.setTitle("Secret Wonders");
        primaryStage.setScene(engine.getScene());
        primaryStage.show();
    }
}
