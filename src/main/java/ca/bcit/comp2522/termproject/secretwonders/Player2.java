package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
/**
 * Player Two Class.
 * @author Olafson and Mahannah.
 * This is the Dragonfly character that is controlled with the WASD keys and Space Bar.
 */
public class Player2 extends Character {
    /**
     * how fast the character moves and turns.
     */
    final int movementFactor = Constants.PLAYER_TWO_MOVEMENT;
    /**
     * monitors if the flag for turning left is on.
     */
    protected boolean turnLeft = false;
    /**
     * monitors if the flag for turning right is on.
     */
    protected boolean turnRight = false;
    /**
     * monitors if the flag for going forward is on.
     */
    protected boolean goForward = false;
    /**
     * monitors if the flag for going backward is on.
     */
    protected boolean goBackward = false;
    /**
     * creates rotate object for player.
     */
    final Rotate rotatePlayer = new Rotate();
    /**
     * Creates initial Direction of directly up.
     */
    Point2D initialDirection = new Point2D(0, -1);
    /**
     * Constructs player two.
     */
    public Player2() {
        super("dragonfly.gif", Constants.PLAYER_TWO_WIDTH,
                Constants.PLAYER_TWO_HEIGHT, Constants.PLAYER_TWO_HEALTH);
        setInitialPosition();
    }
    /**
     * Sets initial spawn location and sets the rotate object to the player to allow rotation Transformations.
     */
    private void setInitialPosition() {
        setX(0);
        setY(Constants.SCREEN_HEIGHT - Constants.PLAYER_TWO_HEIGHT);
        rotatePlayer.setPivotX(getCenterX());
        rotatePlayer.setPivotY(getCenterY());
        getTransforms().addAll(rotatePlayer);
    }
    /**
     * sets the movement booleans to true.
     * @param dir Up, down, left or right.
     */
    public void startMovement(GameEngine.Direction dir) {
        switch (dir) {
            case UP:
                goForward = true; break;
            case DOWN:
                goBackward = true; break;
            case LEFT:
                turnLeft = true; break;
            case RIGHT:
                turnRight = true; break;
        }
    }
    /**
     * stops player movements when key press has been released.
     * @param dir UP, DOWN, LEFT, or RIGHT.
     */
    public void stopMovement(GameEngine.Direction dir) {
        switch (dir) {
            case UP -> goForward = false;
            case DOWN -> goBackward = false;
            case LEFT -> turnLeft = false;
            case RIGHT -> turnRight = false;
        }
    }
    /**
     * Moves the player two one step forward or backward.
     * @param changeInX how much the player is supposed to move along relative X coordinate (if turning).
     * @param changeInY how much the player is supposed to move along relative Y coordinate (forward or backwards).
     */
    private void movePlayer(final double changeInX, final double changeInY) {
        if (changeInX == 0 && changeInY == 0) {
            return;
        }
        double x = changeInX + getX();
        double y = changeInY + getY();
        setX(x);
        setY(y);
    }
    /**
     * Interprets instructions to move Player two.
     * Sets the pivot to centre of player, increments or rotates the movementChange based on what booleans are true,
     * and transforms the player in that direction.
     */
    @Override
    public void doMovement() {
        rotatePlayer.setPivotX(getCenterX());
        rotatePlayer.setPivotY(getCenterY());

        double movementChangePlayer = 0;

        if (goForward) {
            movementChangePlayer += movementFactor;
        }
        if (goBackward) {
            movementChangePlayer -= movementFactor;
        }
        if (turnRight) {
            rotatePlayer(movementFactor);
        }
        if (turnLeft) {
            rotatePlayer(-movementFactor);
        }

        Point2D pt1 = rotatePlayer.deltaTransform(initialDirection.multiply(movementChangePlayer));

        movePlayer(pt1.getX(), pt1.getY());

    }
    /**
     * rotates player one.
     * @param angle how much to rotate player one.
     * if angle is 360, set back to zero. (full rotation)
     */
    public void rotatePlayer(float angle) {
        angle += rotatePlayer.getAngle();
        if (angle == 360) {
            angle = 0;
        }
        rotatePlayer.setAngle(angle);
    }
    /**
     * creates Player two Projectile at characters location.
     */
    public void fireProjectile() {
        ((GamePane) getParent()).getEngine().queueAddition(
                new Player2Projectile(
                        getCenterX(),
                        getCenterY(),
                        rotatePlayer.getAngle()
                )
        );
    }
}
