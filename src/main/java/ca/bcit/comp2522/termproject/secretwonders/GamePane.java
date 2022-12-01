package ca.bcit.comp2522.termproject.secretwonders;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 * GamePane. Keeps track of all graphical elements of the game.
 * @author Olafson and Mahannah.
 * @version 2022.
 */
public class GamePane extends Pane {
    /**
     * the game logic engine.
     */
    private GameEngine engine;
    /**
     * Health bar of player 1.
     */
    private Rectangle healthBar1;
    /**
     * health bar of player 2.
     */
    private Rectangle healthBar2;

    Label playerOneScoreLabel = new Label("Score: ");
    Label playerTwoScoreLabel = new Label("Score: ");

    /**
     * Constructs GamePane.
     */
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

        //Creates health bars of both players and sets them on top and bottom of screen.
        healthBar1 = new Rectangle(Constants.SCREEN_WIDTH, Constants.HEALTH_BAR_SIZE, Color.RED);
        healthBar1.setX(0);
        healthBar1.setY(0);
        healthBar2 = new Rectangle(Constants.SCREEN_WIDTH, Constants.HEALTH_BAR_SIZE, Color.RED);
        healthBar2.setX(0);
        healthBar2.setY(630);
        playerOneScoreLabel.setTextFill(Color.WHITE);
        playerOneScoreLabel.setFont(new Font("Arial", 24));
        playerTwoScoreLabel.setTextFill(Color.WHITE);
        playerTwoScoreLabel.setFont(new Font("Arial", 24));

        this.getChildren().addAll(healthBar1, healthBar2, playerOneScoreLabel, playerTwoScoreLabel);
        playerOneScoreLabel.relocate(0, 0);
        playerTwoScoreLabel.relocate(0, 600);
    }

    /**
     * binds health bar to player one.
     * @param health the current health of player one, an IntegerProperty that implements comparable.
     */
    public void bindHealthOne(final ReadOnlyIntegerProperty health) {
        healthBar1.widthProperty().bind(health.multiply(Constants.SCREEN_WIDTH).divide(Constants.PLAYER_ONE_HEALTH));
    }
    /**
     * binds health bar to player two.
     * @param health the current health of player two, an IntegerProperty that implements comparable.
     */
    public void bindHealthTwo(final ReadOnlyIntegerProperty health) {
        healthBar2.widthProperty().bind(health.multiply(Constants.SCREEN_WIDTH).divide(Constants.PLAYER_TWO_HEALTH));
    }

    /**
     * gets current engine.
     * @return GameEngine.
     */
    public GameEngine getEngine() {
        return engine;
    }

    /**
     * Sets GameEngine.
     * @param engine a gameEngine.
     */
    public void setEngine(final GameEngine engine) {
        this.engine = engine;
    }
}
