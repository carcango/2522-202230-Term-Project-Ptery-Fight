package ca.bcit.comp2522.termproject.secretwonders;

import javafx.animation.FadeTransition;
import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Random;

/**
 * Represents a game enemy.
 * @author Olafson & Mahannah
 * @version December 2022
 */
public class Enemy extends Entity {

    private static final double MIN_DEGREE_ENEMY_FACES = 150;
    private static final double MAX_DEGREE_ENEMY_FACES = 220;
    private static final int    ENEMY_MOVEMENT_SPEED   = 2;
    private static final int    TOP_OF_SCREEN          = 0;
    private static final int    ATTACK_DAMAGE          = 20;

    private final Random        random                 = new Random();
    private final Rotate        rotateEnemy            = new Rotate();
    private final Point2D       initialDirection       = new Point2D(0, -1);

    private boolean             isAlive;
    private boolean             hasNotHitPlayer;

    /**
     * Creates an object of type Enemy.
     *
     * @param spriteName the name of file for the enemy's spite (String)
     * @param width the width of the enemy's sprite image (int)
     * @param height the height of the enemy's sprite image (int)
     */
    public Enemy(final String spriteName, final double width, final double height) {
        super(spriteName, width, height);
        isAlive         = true;
        hasNotHitPlayer = true;
    }

    /*
     * Sets initial spawn location at the top of the screen, and randomly at any point
     * along the X-axis from 0 (min) to the width of the screen (max). Also initializes
     * rotate pivot at the center of the enemy, to allow rotation transformations.
     */
    private void setInitialPosition() {
        setX(random.nextDouble(Constants.SCREEN_WIDTH));
        setY(TOP_OF_SCREEN);

        rotateEnemy.setPivotX(getCenterX());
        rotateEnemy.setPivotY(getCenterY());

        getTransforms().addAll(rotateEnemy);
    }

    /*
     * Sets the permanent angle, determined randomly between two values, along
     * which the enemy will fly across the screen.
     */
    private void setEnemyAngle() {
        rotateEnemy.setAngle(random.nextDouble(MIN_DEGREE_ENEMY_FACES, MAX_DEGREE_ENEMY_FACES));
    }

    /*
     * Moves enemy across screen.
     */
    private void moveEnemy(final double changeInX, final double changeInY) {
        setX(changeInX + getX());
        setY(changeInY + getY());
    }

    /**
     * Sets the initial position and flight angle of the enemy.
     */
    public void makeEnemyAppear() {
        setInitialPosition();
        setEnemyAngle();
    }

    /**
     * Fades out the enemy when enemy dies.
     */
    public void fadeOutEnemyWhenDead() {
        FadeTransition fadeOutEnemy = new FadeTransition(Duration.millis(5000), this);
        fadeOutEnemy.setByValue(1.0);
        fadeOutEnemy.setToValue(0);
        fadeOutEnemy.setByValue(0.2);
        fadeOutEnemy.play();
    }

    /**
     * Returns the amount of damage dealt by the enemy.
     *
     * @return the amount of damage dealt by the enemy (int).
     */
    public int getEnemyAttackDamage() {
        return ATTACK_DAMAGE;
    }

    /**
     * Sets a boolean value to based on if the enemy has hit the player (true) or not (false).
     */
    public void setHasHitPlayer() {
        this.hasNotHitPlayer = false;
    }

    /**
     * Returns true if the enemy has hit the player, else returns false.
     *
     * @return true if the enemy has hit the player, else false.
     */
    public boolean getHasNotHitPlayer() {
        return hasNotHitPlayer;
    }

    /**
     * Sets isAlive to false.
     */
    public void enemyIsDead() {
        this.isAlive = false;
    }

    /**
     * Moves the enemy, if the enemy is alive, else the enemy does not move.
     */
    @Override
    public void doMovement() {
        if (isAlive) {
            rotateEnemy.setPivotX(getCenterX());
            rotateEnemy.setPivotY(getCenterY());
            Point2D pt1 = rotateEnemy.deltaTransform(initialDirection.multiply(ENEMY_MOVEMENT_SPEED));
            moveEnemy(pt1.getX(), pt1.getY());
        }
    }
}
