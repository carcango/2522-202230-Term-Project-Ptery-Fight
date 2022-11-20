package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;

/**
 * Represents a playable character.
 * @author Rhys Mahannah & Carson Olafson
 * @version 20 November 2022
 */
public class GamePlayer extends GameEntity implements MovableCharacter, ShootingCharacter {

    private final Image spriteImage;
    private final double playerWidth;
    private final double playerHeight;

    private final int playerHealth;

    Point2D initialDirection = new Point2D(0, -1);
    private final Rotate rotatePlayer = new Rotate();
    private boolean goForward;
    private boolean goBackward;
    private boolean turnLeft;
    private boolean turnRight;

    GamePlayer(final Image spriteImage, final double playerHeight, final double playerWidth, final int playerHealth) {
        this.spriteImage  = spriteImage;
        this.playerHeight = playerHeight;
        this.playerWidth  = playerWidth;
        this.playerHealth = playerHealth;

        this.goForward  = false;
        this.goBackward = false;
        this.turnLeft   = false;
        this.turnRight  = false;

        // Set the player's starting position.
        setInitialPosition();
    }

    /*
    The center of the player (x-coordinate).
     */
    private double getPlayerCenterX() {
        return playerWidth / 2;
    }

    /*
    The center of the player (y-coordinate).
     */
    private double getPlayerCenterY() {
        return playerHeight / 2;
    }

    /*
    Sets the player's initial position on the screen.
     */
    private void setInitialPosition() {
        setX(0);
        setY(Constants.SCREEN_HEIGHT - playerHeight);
        rotatePlayer.setPivotX(getPlayerCenterX());
        rotatePlayer.setPivotY(getPlayerCenterY());
        getTransforms().add(rotatePlayer);
    }

    private void movePlayer(final double changeInX, final double changeInY) {
        if (changeInX == 0 && changeInY == 0) {
            return;
        }
        double x = changeInX + getX();
        double y = changeInY + getY();
        setX(x);
        setY(y);
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    @Override
    public void startMovement(final GameEngine.Direction direction) {
        switch (direction) {
            case UP:    goForward  = true; break;
            case DOWN:  goBackward = true; break;
            case LEFT:  turnLeft   = true; break;
            case RIGHT: turnRight  = true; break;
        }
    }

    @Override
    public void stopMovement(final GameEngine.Direction direction) {
        switch (direction) {
            case UP:    goForward  = false; break;
            case DOWN:  goBackward = false; break;
            case LEFT:  turnLeft   = false; break;
            case RIGHT: turnRight  = false; break;
        }
    }

    @Override
    public void doMovement() {
        rotatePlayer.setPivotX(getPlayerCenterX());
        rotatePlayer.setPivotY(getPlayerCenterY());

        double movementChangePlayer = 0;

        if (goForward)  { movementChangePlayer += Constants.PLAYER_ONE_MOVEMENT; }
        if (goBackward) { movementChangePlayer += Constants.PLAYER_ONE_MOVEMENT; }
        if (turnRight)  { rotatePlayer(Constants.PLAYER_ONE_MOVEMENT); }
        if (turnLeft)   { rotatePlayer(-Constants.PLAYER_ONE_MOVEMENT); }

        Point2D pt1 = rotatePlayer.deltaTransform(initialDirection.multiply(movementChangePlayer));
        movePlayer(pt1.getX(), pt1.getY());
    }

    /**
     * Rotates the player 360 degrees in either direction.
     * @param angle the angle, in degrees, of the player.
     */
    @Override
    public void rotatePlayer(float angle) {
        angle += rotatePlayer.getAngle();
        if (angle == Constants.PLAYER_ROTATION) {
            angle = 0;
        }
        rotatePlayer.setAngle(angle);
    }

    @Override
    public void fireProjectile() {
        ((GamePane)getParent()).getEngine().queueAddition(
                new Player2Projectile(
                        getPlayerCenterX(),
                        getPlayerCenterY(),
                        rotatePlayer.getAngle()
                )
        );
    }

    public void doTick() {
        doMovement();
    }
}
