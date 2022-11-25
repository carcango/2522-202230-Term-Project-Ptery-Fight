package ca.bcit.comp2522.termproject.secretwonders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameEngineUpdate {

    /**
     * Keyboard control directions.
     */
    public enum Direction {UP, DOWN, LEFT, RIGHT}

    private static final double PLAYER_WIDTH = 70;
    private static final double PLAYER_HEIGHT = 70;
    private static final int PLAYER_MAX_HEALTH = 100;
    private static final String PLAYER_1_SPRITE = "bee.gif";
    private static final String PLAYER_2_SPRITE = "dragonfly.gif";

    private GamePane gamePane;
    private Scene    gameScene;
    private Timeline gameLoop;

    private GameCharacter player1 = new GameCharacter(PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_1_SPRITE, PLAYER_MAX_HEALTH);
    private GameCharacter player2 = new GameCharacter(PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_2_SPRITE, PLAYER_MAX_HEALTH);

    private ArrayList<GameEntity> gameEntities = new ArrayList<>();
    private ArrayList<Projectile> projectiles  = new ArrayList<>();

    ArrayList<GameEntity> entitiesToAdd    = new ArrayList<>();
    ArrayList<GameEntity> entitiesToRemove = new ArrayList<>();

    public GameEngineUpdate() {
        gamePane = new GamePane();
        gameScene = new Scene(gamePane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setupKeybindings();
        add(player1);
        add(player2);
    }

    private void setupKeybindings() {
        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    player1.startMovement(GameEngine.Direction.LEFT); break;
                case A:
                    player2.startMovement(GameEngine.Direction.LEFT); break;
                case RIGHT:
                    player1.startMovement(GameEngine.Direction.RIGHT); break;
                case D:
                    player2.startMovement(GameEngine.Direction.RIGHT); break;
                case UP:
                    player1.startMovement(GameEngine.Direction.UP); break;
                case W:
                    player2.startMovement(GameEngine.Direction.UP); break;
                case DOWN:
                    player1.startMovement(GameEngine.Direction.DOWN); break;
                case S:
                    player2.startMovement(GameEngine.Direction.DOWN); break;
//                case CONTROL:
//                    player1.fireProjectile(); break;
//                case SPACE:
//                    player2.fireProjectile(); break;
            }
        });
        gameScene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    player1.stopMovement(GameEngine.Direction.LEFT); break;
                case A:
                    player2.stopMovement(GameEngine.Direction.LEFT); break;
                case RIGHT:
                    player1.stopMovement(GameEngine.Direction.RIGHT); break;
                case D:
                    player2.stopMovement(GameEngine.Direction.RIGHT); break;
                case UP:
                    player1.stopMovement(GameEngine.Direction.UP); break;
                case W:
                    player2.stopMovement(GameEngine.Direction.UP); break;
                case DOWN:
                    player1.stopMovement(GameEngine.Direction.DOWN); break;
                case S:
                    player2.stopMovement(GameEngine.Direction.DOWN); break;
            }
        });
    }

    private void add(final GameEntity gameEntity) {
        gameEntities.add(gameEntity);
        gamePane.bindHealthOne(player1.healthPropertyUnmodifiable());
        gamePane.bindHealthTwo(player2.healthPropertyUnmodifiable());
        gamePane.getChildren().add(gameEntity);
    }

    private void remove(final GameEntity gameEntity) {
        gameEntities.remove(gameEntity);
        projectiles.remove(gameEntity);
        gamePane.getChildren().remove(gameEntity);
    }

    public void queueAddition(final GameEntity gameEntity) {
        entitiesToAdd.add(gameEntity);
    }

    public void queueRemoval(final GameEntity gameEntity) {
        entitiesToRemove.add(gameEntity);
    }

    public Scene getScene() {
        return gameScene;
    }

    private void setupTimelines() {

        gameLoop = new Timeline(new KeyFrame(Duration.millis(Constants.TICK_LENGTH), e -> {
            //Check state of all entities every tick time duration.
            for (GameEntity gameEntity : gameEntities) {
                gameEntity.doTick();
                if (player1.getHealth() <= 0 || player2.getHealth() <= 0) {
                    queueRemoval(gameEntity);
                    }
                }
            }
        gameLoop.setCycleCount(Timeline.INDEFINITE)));
        gameLoop.play();
    }
}
