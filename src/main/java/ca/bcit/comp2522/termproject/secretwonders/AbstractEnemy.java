package ca.bcit.comp2522.termproject.secretwonders;

/**
 * Represents a game enemy.
 * @author Olafson & Mahannah
 * @version 26 November 2022
 */
public abstract class AbstractEnemy extends Entity {

    private static final int ENEMY_DAMAGE = 20;

    /**
     * Creates an object of type Enemy.
     *
     * @param spriteName the name of file for the enemy's spite (String)
     * @param width the width of the enemy's sprite image (int)
     * @param height the height of the enemy's sprite image (int)
     */
    public AbstractEnemy(final String spriteName, final double width, final double height) {
        super(spriteName, width, height);
    }

    /**
     * Returns the amount of enemy dealt by the enemy.
     *
     * @return the amount of damage dealt by the enemy (int).
     */
    public int getEnemyDamage() {
        return ENEMY_DAMAGE;
    }
}
