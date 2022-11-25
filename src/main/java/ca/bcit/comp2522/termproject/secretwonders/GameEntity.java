package ca.bcit.comp2522.termproject.secretwonders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a game entity.
 * @author Olafson and Mahannah
 * @version November 2022
 */
public abstract class GameEntity extends ImageView {

    private static final boolean IMAGE_PRESERVE_RATIO_APPLIED = false;
    private static final boolean IMAGE_SMOOTH_FILTER_APPLIED  = false;

    /**
     * Width of the entity.
     */
    protected final double entityWidth;

    /**
     * Height of the entity.
     */
    protected final double entityHeight;

    /**
     * Filename of the entity sprite.
     */
    protected final String entitySpriteFileName;

    /**
     * Creates an object of type GameEntity when called by an extended subclass.
     *
     * @param entityWidth the width of the entity (double).
     * @param entityHeight the height of the entity (double).
     * @param entitySpriteFileName the filename of the entity sprite (String).
     */
    public GameEntity(final double entityWidth, final double entityHeight, final String entitySpriteFileName) {
        this.entityWidth = entityWidth;
        this.entityHeight = entityHeight;
        this.entitySpriteFileName = entitySpriteFileName;

        // Create sprite image
        Image entityImage = new Image(entitySpriteFileName, entityWidth, entityHeight,
                IMAGE_PRESERVE_RATIO_APPLIED, IMAGE_SMOOTH_FILTER_APPLIED);

        // Set sprite image and dimensions
        setImage(entityImage);
        setFitHeight(entityHeight);
        setFitWidth(entityWidth);
    }

    /**
     * Returns the width of the entity.
     * @return the width of the entity (double).
     */
    public double getWidth() {
        return entityWidth;
    }

    /**
     * Returns the height of the entity.
     * @return the height of the entity (double).
     */
    public double getHeight() {
        return entityHeight;
    }

    /**
     * Returns the horizontal (x-axis) center of the entity.
     * @return the horizontal (x-axis) center of the entity (double).
     */
    public double getCenterX() {
        return getX() + (entityWidth / 2);
    }

    /**
     * Returns the vertical (y-axis) center of the entity.
     * @return the vertical (y-axis) center of the entity (double).
     */
    public double getCenterY() {
        return getY() + (entityHeight / 2);
    }

    public abstract void doTick();
}
