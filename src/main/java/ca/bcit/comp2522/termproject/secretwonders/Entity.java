package ca.bcit.comp2522.termproject.secretwonders;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Entity extends ImageView {

    protected double width = 16;
    protected double height = 16;
    protected Image sprite = new Image("missingImage.png",
            width, height, false, false);

    public Entity() {
        super.setImage(sprite);
        setFitHeight(height);
        setFitWidth(width);
    }


    public Entity(String spriteName) {
        sprite = new Image(spriteName, this.width, this.height, false, false);
        super.setImage(sprite);
        setFitHeight(height);
        setFitWidth(width);
    }


    public Entity(String spriteName, double width, double height) {
        this.width = width;
        this.height = height;
        sprite = new Image(spriteName, width, height, false, false);
        super.setImage(sprite);
        setFitHeight(height);
        setFitWidth(width);
    }


    public double getWidth() {
        return width;
    }


    public double getHeight() {
        return height;
    }

    public double getCenterX() {
        return getX() + (width / 2);
    }

    public double getCenterY() {
        return getY() + (height / 2);
    }

    public abstract void doTick();
}