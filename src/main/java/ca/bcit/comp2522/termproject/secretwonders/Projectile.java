package ca.bcit.comp2522.termproject.secretwonders;

import javafx.geometry.Point2D;
import javafx.scene.transform.Rotate;

public class Projectile extends Entity {

    protected double deltaX = 0.0;
    protected double deltaY = 0.0;
    protected int damage = 9999;
    Point2D initialDirection = new Point2D(0, -1);
    protected Rotate projectileRotation;
    protected Point2D pt1;


    public Projectile() {
        super("missingImage.png", 8, 8);
    }


    public Projectile(String spriteName, int damage) {
        super(spriteName, 8, 8);
        this.damage = damage;
    }

    public Projectile(String spriteName, double width, double height, int damage) {
        super(spriteName, width, height);
        this.damage = damage;
    }

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
    public int getDamage() {
        return damage;
    }

    protected void doMovement() {
        projectileRotation.setPivotX(getCenterX());
        projectileRotation.setPivotY(getCenterY());
        double x = pt1.getX() + getX();
        double y = pt1.getY() + getY();
        setX(x);
        setY(y);

    }

    @Override
    public void doTick() {
        doMovement();
    }

}