package ca.bcit.comp2522.termproject.secretwonders;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GamePane extends Pane {

    private GameEngine engine;

    private Rectangle healthBar1;
    private Rectangle healthBar2;

    public GamePane() {
        this.setBackground(new Background(new BackgroundFill(Color.rgb(255,255,255), null, null)));


        healthBar1 = new Rectangle(Constants.SCREEN_WIDTH, Constants.HEALTH_BAR_SIZE, Color.RED);
        healthBar1.setX(0);
        healthBar1.setY(0);
        healthBar2 = new Rectangle(Constants.SCREEN_WIDTH, Constants.HEALTH_BAR_SIZE, Color.RED);
        healthBar2.setX(0);
        healthBar2.setY(630);

        this.getChildren().addAll(healthBar1, healthBar2);
    }

    public void bindHealthOne(final ReadOnlyIntegerProperty health) {
        healthBar1.widthProperty().bind(health.multiply(Constants.SCREEN_WIDTH).divide(Constants.PLAYER_ONE_HEALTH));
    }
    public void bindHealthTwo(final ReadOnlyIntegerProperty health) {
        healthBar2.widthProperty().bind(health.multiply(Constants.SCREEN_WIDTH).divide(Constants.PLAYER_TWO_HEALTH));
    }


    public GameEngine getEngine() {
        return engine;
    }

    public void setEngine(final GameEngine engine) {
        this.engine = engine;
    }
}