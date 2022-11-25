package ca.bcit.comp2522.termproject.secretwonders;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GHealthMonitor extends Rectangle {
    public GHealthMonitor(final GameCharacter gameCharacter) {
        super();
        setWidth(gameCharacter.getWidth());
        setHeight(2);
        setFill(Color.WHITE);
        xProperty().bind(gameCharacter.xProperty());
        yProperty().bind(gameCharacter.yProperty().subtract(2));
        widthProperty().bind(gameCharacter.healthPropertyUnmodifiable().multiply(gameCharacter.getWidth()).divide(gameCharacter.getMaxHealth()));
    }
}
