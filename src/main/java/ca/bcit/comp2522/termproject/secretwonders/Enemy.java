package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
import java.util.Random;

/**
 * Represents an enemy character.
 *
 * @author Olafson & Mahannah
 * @version 26 November 2022
 */
public class Enemy extends AbstractEnemy {

    private static final double MIN_DEGREE_ENEMY_FACES = 20;
    private static final double MAX_DEGREE_ENEMY_FACES = 161;
    private static final int    ENEMY_MOVEMENT_SPEED   = 2;
    private static final int    ENEMY_WIDTH            = 50;
    private static final int    ENEMY_HEIGHT           = 50;
    private static final int    ENEMY_MAX_HEALTH       = 60;
    private static final String ENEMY_SPRITE           = "fireblast.gif";

    private static final Random RANDOM = new Random();

    private final Rotate rotateEnemy = new Rotate();
    private final double enemyStartPositionX = Constants.SCREEN_WIDTH / 2;
    private final double enemyStartPositionY = Constants.SCREEN_HEIGHT;

    private final Point2D initialDirection = new Point2D(
            enemyStartPositionX,
            enemyStartPositionY);

    /**
     * Create an object of type Enemy.
     */
    public Enemy() {
        super(ENEMY_SPRITE,
                ENEMY_WIDTH,
                ENEMY_HEIGHT,
                ENEMY_MAX_HEALTH);
        setInitialPosition();
        setEnemyAngle();
    }

    /**
     * Sets initial spawn location and sets rotate object to the player to allow rotation Transformations.
     */
    private void setInitialPosition() {
        setX((Constants.SCREEN_WIDTH - ENEMY_WIDTH));
        setY(Constants.SCREEN_HEIGHT - ENEMY_HEIGHT);

        rotateEnemy.setPivotX(getCenterX());
        rotateEnemy.setPivotY(getCenterY());

        getTransforms().addAll(rotateEnemy);
    }

    /**
     * Sets an initial and permanent angle the enemy faces.
     */
    public void setEnemyAngle() {
        double enemyAngle = RANDOM.nextDouble(MIN_DEGREE_ENEMY_FACES, MAX_DEGREE_ENEMY_FACES);
        rotateEnemy.setAngle(enemyAngle);
    }

    /**
     * Moves across the screen.
     *
     * @param changeInX how much the player is supposed to move along relative X coordinate (if turning).
     * @param changeInY how much the player is supposed to move along relative Y coordinate (forward or backwards).
     */
    private void moveEnemy(final double changeInX, final double changeInY) {
        setX(changeInX + getX());
        setY(changeInY + getY());
    }

    /**
     * Interprets instructions to move Player one.
     * Sets the pivot to centre of player, increments or rotates the movementChange based on what booleans are true,
     * and transforms the player in that direction.
     */
    @Override
    public void doMovement() {
        rotateEnemy.setPivotX(getCenterX());
        rotateEnemy.setPivotY(getCenterY());

        Point2D pt1 = rotateEnemy.deltaTransform(initialDirection.multiply(ENEMY_MOVEMENT_SPEED));
        moveEnemy(pt1.getX(), pt1.getY());
    }
}
