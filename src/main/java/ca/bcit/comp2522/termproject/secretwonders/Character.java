package ca.bcit.comp2522.termproject.secretwonders;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Character extends Entity {
    //this is an integer property because it needs to be observable in HealthMonitor
    private IntegerProperty healthProperty = new SimpleIntegerProperty(100);
    private int maxHealth;
    private HealthMonitor healthMonitor;

    public Character() { }

    public Character(int maxHealth) {
        this.maxHealth = maxHealth;
        healthProperty.set(maxHealth);
        healthMonitor = new HealthMonitor(this);
    }

    public Character(String spriteName, int maxHealth) {
        super(spriteName);
        this.maxHealth = maxHealth;
        healthProperty.set(maxHealth);
        healthMonitor = new HealthMonitor(this);
    }

    public Character(String spriteName, double width, double height, int maxHealth) {
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

    public void setHealth(int health) {
        healthProperty.set(health);
    }

    public void subtractHealth(int health) {
        healthProperty.set(healthProperty.get() - health);
    }

    public void addHealth(int health) {
        healthProperty.set(healthProperty.get() - health);
    }

    public ReadOnlyIntegerProperty healthPropertyUnmodifiable() {
        return healthProperty;
    }

    public HealthMonitor getHealthMonitor() {
        return healthMonitor;
    }
}
