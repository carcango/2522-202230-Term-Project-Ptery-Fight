package ca.bcit.comp2522.termproject.secretwonders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HealthMonitor extends Rectangle {
    public HealthMonitor(Character character) {
        super();
        setWidth(character.width);
        setHeight(2);
        setFill(Color.WHITE);
        xProperty().bind(character.xProperty());
        yProperty().bind(character.yProperty().subtract(2));
        widthProperty().bind(character.healthPropertyUnmodifiable().multiply(character.width).divide(character.getMaxHealth()));
    }
}