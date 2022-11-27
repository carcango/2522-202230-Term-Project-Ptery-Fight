package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;
/**
 * Abstract Projectile Class.
 * @author Olafson and Mahannah.
 * @version 2022
 */
public class Projectile extends Entity {
    /**
     * movement along X coordinates.
     */
    protected double deltaX = 0.0;
    /**
     * movement along Y coordinates.
     */
    protected double deltaY = 0.0;
    /**
     * how much damage the projectile does.
     */
    protected int damage = 9999;
    /**
     * the default orientation of the projectile.
     */
    Point2D initialDirection = new Point2D(0, -1);
    /**
     * Rotate object for projectile.
     */
    protected Rotate projectileRotation;
    /**
     * Secondary orientation of the projectile for what it is when spawned.
     */
    protected Point2D pt1;

    /**
     * default projectile constructor.
     */
    public Projectile() {
        super("missingImage.png", 8, 8);
    }

    /**
     * Constructs projectile with image, width, height, and damage.
     * @param spriteName String file name.
     * @param width double width of image.
     * @param height double height of image.
     * @param damage int damage projectile does.
     */
    public Projectile(String spriteName, double width, double height, int damage) {
        super(spriteName, width, height);
        this.damage = damage;
    }

    /**
     * Constructs projectile with image, width, height, damage, and starting location and angle.
     * @param spriteName String file name.
     * @param width double width of image.
     * @param height double height of image.
     * @param damage int damage projectile does.
     * @param originX double X location of spawn.
     * @param originY double Y location of spawn.
     * @param deltaX Speed of projectile on X.
     * @param deltaY Speed of projectile on Y.
     * @param angle what direction it's facing.
     */
    public Projectile(String spriteName, double width, double height, int damage,
                      double originX, double originY, double deltaX, double deltaY, double angle) {
        super(spriteName, width, height);
        this.damage = damage;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        setX(originX);
        setY(originY);
        this.projectileRotation = new Rotate();
        projectileRotation.setPivotX(originX);
        projectileRotation.setPivotY(originY);
        getTransforms().addAll(projectileRotation);

        projectileRotation.setAngle(angle);

        pt1 = projectileRotation.deltaTransform(initialDirection.multiply(deltaY));
    }

    /**
     * gets damage.
     * @return int damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * moves the projectile on game loop.
     */
    @Override
    public void doMovement() {
        projectileRotation.setPivotX(getCenterX());
        projectileRotation.setPivotY(getCenterY());
        double x = pt1.getX() + getX();
        double y = pt1.getY() + getY();
        setX(x);
        setY(y);
    }
}
