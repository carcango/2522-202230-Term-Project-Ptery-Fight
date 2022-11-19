package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;


public class Player2 extends Character {


    private static Image Sprite = new Image("dragonfly.gif",
            Constants.PLAYER_TWO_WIDTH, Constants.PLAYER_TWO_HEIGHT, false, false);
    final int movementFactor = Constants.PLAYER_TWO_MOVEMENT;
    protected boolean turnLeft = false;
    protected boolean turnRight = false;
    protected boolean goForward = false;
    protected boolean goBackward = false;
    final Rotate rotatePlayer = new Rotate();
    Point2D initialDirection = new Point2D(0, -1);

    public Player2() {
        super("dragonfly.gif", Constants.PLAYER_TWO_WIDTH,
                Constants.PLAYER_TWO_WIDTH, Constants.PLAYER_TWO_HEALTH);
        setInitialPosition();
    }

    private void setInitialPosition() {
        setX(0);
        setY(Constants.SCREEN_HEIGHT - Constants.PLAYER_ONE_HEIGHT);
        rotatePlayer.setPivotX(getCenterX());
        rotatePlayer.setPivotY(getCenterY());
        getTransforms().addAll(rotatePlayer);
    }

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

    public void stopMovement(GameEngine.Direction dir) {
        switch (dir) {
            case UP -> goForward = false;
            case DOWN -> goBackward = false;
            case LEFT -> turnLeft = false;
            case RIGHT -> turnRight = false;
        }
    }
    private void movePlayer1(final double changeInX, final double changeInY) {
        if (changeInX == 0 && changeInY == 0) {
            return;
        }
        double x = changeInX + getX();
        double y = changeInY + getY();
        setX(x);
        setY(y);
    }

    public void doMovement() {
        rotatePlayer.setPivotX(getCenterX());
        rotatePlayer.setPivotY(getCenterY());

        double movementChangePlayerOne = 0;

        if (goForward) {
            movementChangePlayerOne += movementFactor;
        }
        if (goBackward) {
            movementChangePlayerOne -= movementFactor;
        }
        if (turnRight) {
            rotatePlayer(movementFactor);
        }
        if (turnLeft) {
            rotatePlayer(-movementFactor);
        }

        Point2D pt1 = rotatePlayer.deltaTransform(initialDirection.multiply(movementChangePlayerOne));

        movePlayer1(pt1.getX(), pt1.getY());

    }
    public void rotatePlayer(float angle) {
        angle += rotatePlayer.getAngle();
        if (angle == 360) {
            angle = 0;
        }
        rotatePlayer.setAngle(angle);
    }

    public void fireProjectile() {
        ((GamePane)getParent()).getEngine().queueAddition(
                new Player2Projectile(
                        getCenterX() - (Constants.PROJECTILE_PLAYER_ONE_WIDTH / 2),
                        getY() - Constants.PROJECTILE_PLAYER_ONE_HEIGHT,
                        rotatePlayer.getAngle()
                )
        );
    }
    @Override
    public void doTick() {
        doMovement();
    }
}