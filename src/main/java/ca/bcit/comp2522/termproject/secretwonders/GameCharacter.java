package ca.bcit.comp2522.termproject.secretwonders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

/**
 * Represents a game character entity.
 * @author Olafson and Mahannah
 * @version November 2022
 */
public class GameCharacter extends GameEntity {

    private static final float INITIAL_CHARACTER_ANGLE = 0;
    private static final int CHARACTER_MAX_ROTATION    = 360;
    private static final int CHARACTER_MIN_ROTATION    = 0;
    private static final int DEFAULT_MAX_HEALTH        = 100;
    private static final int MOVEMENT_FACTOR           = 5;

    /**
     * The Boolean status of the turn-left button.
     */
    protected boolean turnLeft = false;

    /**
     * The Boolean status of the turn-right button.
     */
    protected boolean turnRight = false;

    /**
     * The Boolean status of the go-forward button.
     */
    protected boolean goForward = false;

    /**
     * The Boolean status of the go-backward button.
     */
    protected boolean goBackward = false;

    private final Rotate rotatePlayer = new Rotate();
    private final Point2D initialDirection = new Point2D(0, -1);
    private final GHealthMonitor gameHealthMonitor;
    private final int maxHealth;
    private IntegerProperty health = new SimpleIntegerProperty(100);


    private float angle;


    /**
     * Creates an object of type GameCharacter.
     *
     * @param entityWidth the width of the game character (double).
     * @param entityHeight the height of the game character (double).
     * @param entitySpriteFileName the filename of the game character's sprite (String).
     */
    public GameCharacter(final double entityWidth, final double entityHeight, final String entitySpriteFileName,
                         final int maxHealth) {
        super(entityWidth, entityHeight, entitySpriteFileName);
        this.gameHealthMonitor = new GHealthMonitor(this);
        this.maxHealth = DEFAULT_MAX_HEALTH;
        health.set(maxHealth);
        this.angle = INITIAL_CHARACTER_ANGLE;
    }

    /**
     * Returns the game character's max health.
     * @return game character's max health (int).
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns the game character's current health.
     * @return game character's current health (int).
     */
    public IntegerProperty getHealth() {
        return health;
    }

    /**
     * Returns the game character's health monitor.
     * @return game character's health monitor (HealthMonitor)
     */
    public GHealthMonitor getHealthMonitor() {
        return gameHealthMonitor;
    }

    /**
     * Subtracts from the character's current health.
     *
     * @param subtractedHealth the game character's current health.
     */
    public void subtractHealth(final int subtractedHealth) {
        health.set(health.get() - subtractedHealth);
    }

    /**
     *
     * @return
     */
    public ReadOnlyIntegerProperty healthPropertyUnmodifiable() {
        return health;
    }

    // MOVEMENT METHODS
    private void setInitialPosition() {
        setX(Constants.SCREEN_WIDTH - getWidth());
        setY((Constants.SCREEN_HEIGHT - getHeight()));

        rotatePlayer.setPivotX(getCenterX());
        rotatePlayer.setPivotY(getCenterY());

        getTransforms().addAll(rotatePlayer);
    }

    private void movePlayer(final double changeInX, final double changeInY) {

        // If no change in movement, just return.
        if (changeInX == 0 && changeInY == 0) {
            return;
        }
        if (changeInX <= Constants.SCREEN_WIDTH && changeInX > 0
                &&
        changeInY <= Constants.SCREEN_HEIGHT && changeInY > 0) {
            setX(changeInX + getX());
            setY(changeInY + getY());
        }
    }

    /**
     * Changes the angle of -- the direction faced by -- the game character.
     * @param newAngle the new angle (direction) for the game character.
     */
    public void rotatePlayer(final float newAngle) {
        this.angle += rotatePlayer.getAngle();
        if (this.angle == CHARACTER_MAX_ROTATION) {
            this.angle = CHARACTER_MIN_ROTATION;
        }
        rotatePlayer.setAngle(newAngle);
    }

    /**
     * Starts the movement for the game character.
     * @param direction the direction of movement (boolean).
     */
    public void startMovement(final GameEngine.Direction direction) {
        switch (direction) {
            case UP -> goForward = true;
            case DOWN -> goBackward = true;
            case LEFT -> turnLeft = true;
            case RIGHT -> turnRight = true;
        }
    }

    /**
     * Changes the game character's on-screen position.
     */
    public void doMovement() {
        rotatePlayer.setPivotX(getCenterX());
        rotatePlayer.setPivotY(getCenterY());
        double movementChangePlayerOne = 0;

        if (goForward) {
            movementChangePlayerOne += MOVEMENT_FACTOR;
        }
        if (goBackward) {
            movementChangePlayerOne -= MOVEMENT_FACTOR;
        }
        if (turnRight) {
            rotatePlayer(MOVEMENT_FACTOR);
        }
        if (turnLeft) {
            rotatePlayer(-MOVEMENT_FACTOR);
        }

        Point2D pt1 = rotatePlayer.deltaTransform(initialDirection.multiply(movementChangePlayerOne));
        movePlayer(pt1.getX(), pt1.getY());
    }

    public void stopMovement(GameEngine.Direction dir) {
        switch (dir) {
            case UP -> goForward = false;
            case DOWN -> goBackward = false;
            case LEFT -> turnLeft = false;
            case RIGHT -> turnRight = false;
        }
    }

    public void doTick() {
        doMovement();
    }
}