package ca.bcit.comp2522.termproject.secretwonders;

/**
 * Represents a game character entity.
 * @author Olafson and Mahannah
 * @version November 2022
 */
public class GameCharacter extends GameEntity {

    private double entityWidth;
    private double entityHeight;
    private String entitySpriteFileName;
    private final int maxHealth;
    private final int health;
    private final HealthMonitor healthMonitor;

    /**
     * Creates an object of type GameCharacter.
     *
     * @param entityWidth the width of the game character (double).
     * @param entityHeight the height of the game character (double).
     * @param entitySpriteFileName the filename of the game character's sprite (String).
     * @param maxHealth the maximum health of the game character (int).
     * @param health the current health of the game character (int).
     * @param healthMonitor the health monitor for the game character (HealthMonitor).
     */
    public GameCharacter(final double entityWidth, final double entityHeight, final String entitySpriteFileName,
                         final int maxHealth, final int health, final HealthMonitor healthMonitor) {
        super(entityWidth, entityHeight, entitySpriteFileName);
        this.maxHealth = maxHealth;
        this.health = health;
        this.healthMonitor = healthMonitor;
    }

    /**
     * Returns the game character's max health.
     * @return game character's max health (int).
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns the game character's current health.
     * @return game character's current health (int).
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the game character's health monitor.
     * @return game character's health monitor (HealthMonitor)
     */
    public HealthMonitor getHealthMonitor() {
        return healthMonitor;
    }

    /**
     * Subtracts from the character's current health.
     *
     * @param characterHealth the game character's current health.
     */
    public void subtractHealth(int characterHealth) {
        characterHealth = health;
        // Code needed.
    }
}
