package ca.bcit.comp2522.termproject.secretwonders;

public interface MovableCharacter {
    void startMovement(GameEngine.Direction direction);
    void stopMovement(GameEngine.Direction direction);
    void doMovement();
    void rotatePlayer(float angle);
}
