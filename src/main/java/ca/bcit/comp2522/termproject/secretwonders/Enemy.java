package ca.bcit.comp2522.termproject.secretwonders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Represents a game enemy.
 * @author Olafson & Mahannah
 * @version 26 November 2022
 */
public abstract class Enemy extends Entity {

    private static final int MAX_ENEMY_HEALTH = 60;

    private final IntegerProperty health = new SimpleIntegerProperty(MAX_ENEMY_HEALTH);
    private final int maxHealth;

    /**
     * Creates an object of type Enemy.
     *
     * @param spriteName the name of file for the enemy's spite (String)
     * @param width the width of the enemy's sprite image (int)
     * @param height the height of the enemy's sprite image (int)
     * @param maxHealth the maximum health value of the enemy (int)
     */
    public Enemy(final String spriteName, final double width, final double height, final int maxHealth) {
        super(spriteName, width, height); // Calls super constructor to instantiate variables.
        this.maxHealth = maxHealth;
        health.set(maxHealth);
    }

    /**
     * Returns the current health of the enemy.
     *
     * @return the enemy's current health as an int.
     */
    public int getHealth() {
        return health.get();
    }

    /**
     * Returns the maximum health of the enemy.
     *
     * @return the enemy's maximum health as an int.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Subtracts health from the enemy.
     *
     * @param subtractedHealth the amount of health (int) to be subtracted from the enemy's current health.
     */
    public void subtractHealth(final int subtractedHealth) {
           health.set(health.get() - subtractedHealth);
    }

    /**
     * Returns a read-only integer value representing the enemy's health.
     *
     * @return health as a read-only integer property.
     */
    public ReadOnlyIntegerProperty healthPropertyUnmodifiable() {
        return health;
    }
}
