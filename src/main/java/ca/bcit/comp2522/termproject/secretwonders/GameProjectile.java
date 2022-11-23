package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

/**
 * Represents a moving projectile.
 * @author Olafson and Mahannah
 * @version November 2022
 */
public class GameProjectile extends GameEntity {

    private static final Point2D INITIAL_DIRECTION = new Point2D(0, -1);

    private final double angle;
    private final double originX;
    private final double originY;
    private final double deltaX;
    private final double deltaY;
    private final int damage;
    private Rotate projectileRotation;
    private Point2D point;

    /**
     * Creates an object of type GameProjectile.
     *
     * @param entityWidth
     * @param entityHeight
     * @param entitySpriteFileName
     * @param originX
     * @param originY
     * @param deltaX
     * @param deltaY
     * @param damage
     * @param angle
     */
    public GameProjectile(final double entityWidth, final double entityHeight, final String entitySpriteFileName,
                          final double originX, final double originY, final double deltaX, final double deltaY,
                          final int damage, final double angle) {
        super(entityWidth, entityHeight, entitySpriteFileName);

        this.originX = originX;
        this.originY = originY;
        setX(originX);
        setY(originY);

        this.projectileRotation = new Rotate();
        projectileRotation.setPivotX(originX);
        projectileRotation.setPivotY(originY);
        projectileRotation.setAngle(angle);
        getTransforms().addAll(projectileRotation);

        this.angle  = angle;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.damage = damage;

        point = projectileRotation.deltaTransform(INITIAL_DIRECTION.multiply(deltaY));
    }

    /**
     * Returns projectile damage.
     * @return projectile damage (int).
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Moves the projectile.
     */
    public void doMovement() {
        projectileRotation.setPivotX(getCenterX());
        projectileRotation.setPivotY(getCenterY());
        double x = point.getX() + getX();
        double y = point.getY() + getY();
        setX(x);
        setY(y);
    }
}
