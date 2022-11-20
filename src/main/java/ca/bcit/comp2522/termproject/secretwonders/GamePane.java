package ca.bcit.comp2522.termproject.secretwonders;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GamePane extends Pane {

    private GameEngine engine;
    private Rectangle healthBar1;
    private Rectangle healthBar2;

    public GamePane() {

        // Background image for game.
        Image background = new Image("background-img.jpeg");
        BackgroundImage backgroundImage = new BackgroundImage(
                background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background bgImg = new Background(backgroundImage);
        this.setBackground(bgImg);

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
