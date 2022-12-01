package ca.bcit.comp2522.termproject.secretwonders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Character Class.
 * @author Olafson and Mahhanah
 * @version 2022.
 */
public abstract class Character extends Entity {
    /**
     * the current health of the character.
     * this is an integer property because it needs to implement observable in HealthMonitor
     */
    private final IntegerProperty healthProperty = new SimpleIntegerProperty(100);
    /**
     * Max health of the Character.
     */
    private int maxHealth;
    /**
     * A HealthMonitor object that graphically shows the characters current health.
     */
    private HealthMonitor healthMonitor;

    public Character() { }

    public Character(final String spriteName, final double width, final double height, final int maxHealth) {
        super(spriteName, width, height);
        this.maxHealth = maxHealth;
        healthProperty.set(maxHealth);
        healthMonitor = new HealthMonitor(this);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return healthProperty.get();
    }

    public void subtractHealth(int health) {
        healthProperty.set(healthProperty.get() - health);
    }

    public ReadOnlyIntegerProperty healthPropertyUnmodifiable() {
        return healthProperty;
    }
}
