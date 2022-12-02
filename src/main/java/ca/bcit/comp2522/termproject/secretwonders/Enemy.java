package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

import java.util.Random;

/**
 * Represents a game enemy.
 * @author Olafson & Mahannah
 * @version 26 November 2022
 */
public class Enemy extends Entity {

    private static final double MIN_DEGREE_ENEMY_FACES = 150;
    private static final double MAX_DEGREE_ENEMY_FACES = 220;
    private static final int    ENEMY_MOVEMENT_SPEED   = 2;
    private static final int    TOP_OF_SCREEN          = 0;
    private static final int    ENEMY_DAMAGE           = 20;

    private final Random random                        = new Random();
    private final Rotate rotateEnemy                   = new Rotate();
    private final Point2D initialDirection             = new Point2D(0, -1);

    private boolean isAlive;
    private boolean hasHitPlayer;

    /**
     * Creates an object of type Enemy.
     *
     * @param spriteName the name of file for the enemy's spite (String)
     * @param width the width of the enemy's sprite image (int)
     * @param height the height of the enemy's sprite image (int)
     */
    public Enemy(final String spriteName, final double width, final double height) {
        super(spriteName, width, height);
        isAlive = true;
        hasHitPlayer = false;
    }

    /**
     * Returns the amount of damage dealt by the enemy.
     *
     * @return the amount of damage dealt by the enemy (int).
     */
    public int getEnemyDamage() {
        return ENEMY_DAMAGE;
    }

    /**
     * Sets initial spawn location and sets rotate object to the player to allow rotation Transformations.
     */
    private void setInitialPosition() {
        setX(random.nextDouble(Constants.SCREEN_WIDTH));
        setY(TOP_OF_SCREEN);

        rotateEnemy.setPivotX(getCenterX());
        rotateEnemy.setPivotY(getCenterY());

        getTransforms().addAll(rotateEnemy);
    }

    /**
     * Sets an initial and permanent angle the enemy faces.
     */
    public void setEnemyAngle() {
        double enemyAngle = random.nextDouble(MIN_DEGREE_ENEMY_FACES, MAX_DEGREE_ENEMY_FACES);
        rotateEnemy.setAngle(enemyAngle);
    }

    public void makeEnemyAppear() {
        setInitialPosition();
        setEnemyAngle();
    }

    /*
     * Moves enemy across screen.
     */
    private void moveEnemy(final double changeInX, final double changeInY) {
        setX(changeInX + getX());
        setY(changeInY + getY());
    }

    public void setHasHitPlayer(final boolean hitStatus) {
        this.hasHitPlayer = hitStatus;
    }

    public boolean getHasHitPlayer() {
        return hasHitPlayer;
    }

    public void setIsAlive(final boolean newStatus) {
        this.isAlive = newStatus;
    }

    /**
     * Interprets instructions to move Player one.
     * Sets the pivot to centre of player, increments or rotates the movementChange based on what booleans are true,
     * and transforms the player in that direction.
     */
    @Override
    public void doMovement() {

        if (!isAlive) {
            return;
        }
        rotateEnemy.setPivotX(getCenterX());
        rotateEnemy.setPivotY(getCenterY());

        Point2D pt1 = rotateEnemy.deltaTransform(initialDirection.multiply(ENEMY_MOVEMENT_SPEED));
        moveEnemy(pt1.getX(), pt1.getY());
    }
}
