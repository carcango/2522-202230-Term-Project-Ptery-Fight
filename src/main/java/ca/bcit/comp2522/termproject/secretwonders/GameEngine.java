package ca.bcit.comp2522.termproject.secretwonders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.ArrayList;

/**
 * GameEngine Class
 *
 * @author Olafson and Mahannah
 * @version 2022
 */
public class GameEngine {

    /**
     * Keyboard control directions.
     */
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    /**
     * Visible contents of Objects for the screen.
     */
    private GamePane pane;
    /**
     * the scene for the game.
     */
    private Scene scene;
    /**
     * the loop in which all game events happen.
     */
    private Timeline gameLoop;
    /**
     * Player one is the bee character who is controlled with the arrow keys and CTRL button.
     */
    private Player1 player1 = new Player1();
    /**
     * Player two is the dragonfly character who is controlled with the WASD keys and SPACE bar.
     */
    private Player2 player2 = new Player2();
    /**
     *Arraylist of Entities, not including projectiles. This keeps track of Entities are currently part of the game.
     */
    private ArrayList<Entity> entities = new ArrayList<>();
    /**
     * Arraylist of Projectiles, keeps track of what projectiles are currently part of the game.
     */
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    /**
     * Arraylist of entities to be removed from play at the end of the current game loop.
     */
    ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    /**
     * Arraylist of projectiles to be removed from play at the end of the current game loop.
     */
    ArrayList<Entity> entitiesToRemove = new ArrayList<>();
    /**
     * The last timestamp of player 2 attack (fireball).
     */
    long lastPlayerTwoShot = System.currentTimeMillis();

    /**
     * Constructor for GameEngine.
     */
    public GameEngine() {
        pane = new GamePane();
        scene = new Scene(pane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setupScene(pane);
        setupKeybindings();
        add(player1);
        add(player2);
        setupTimelines();
    }

    /**
     * sets up the attribute of pane to the passed pane, sets the root of the scene to watch the pane.
     * @param pane a GamePane pane.
     */
    private void setupScene(GamePane pane) {
        this.pane = pane;
        pane.setEngine(this);
        scene.setRoot(pane);
    }

    /**
     * watches key press, and if it is a valid move, then either starts moving a character or creates projectiles.
     * Keeps going as long as the keypress is held down.
     */
    private void setupKeybindings() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    player1.startMovement(Direction.LEFT); break;
                case A:
                    player2.startMovement(Direction.LEFT); break;
                case RIGHT:
                    player1.startMovement(Direction.RIGHT); break;
                case D:
                    player2.startMovement(Direction.RIGHT); break;
                case UP:
                    player1.startMovement(Direction.UP); break;
                case W:
                    player2.startMovement(Direction.UP); break;
                case DOWN:
                    player1.startMovement(Direction.DOWN); break;
                case S:
                    player2.startMovement(Direction.DOWN); break;
                case CONTROL:
                    player1.fireProjectile(); break;
                case SPACE:
                    if (System.currentTimeMillis() - lastPlayerTwoShot >= 500) {
                        player2.fireProjectile();
                        System.out.println(System.currentTimeMillis());
                        lastPlayerTwoShot = System.currentTimeMillis();
                        break;

                    }

            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    player1.stopMovement(Direction.LEFT); break;
                case A:
                    player2.stopMovement(Direction.LEFT); break;
                case RIGHT:
                    player1.stopMovement(Direction.RIGHT); break;
                case D:
                    player2.stopMovement(Direction.RIGHT); break;
                case UP:
                    player1.stopMovement(Direction.UP); break;
                case W:
                    player2.stopMovement(Direction.UP); break;
                case DOWN:
                    player1.stopMovement(Direction.DOWN); break;
                case S:
                    player2.stopMovement(Direction.DOWN); break;
            }
        });
    }

    /**
     * adds an entity to the game pane.
     * If the entity is a player, attachs a health bar to the entity.
     * @param entity an object that extends Entity.
     */
    private void add(Entity entity) {
        entities.add(entity);
        if (entity instanceof Player1) {
            player1 = (Player1)entity;
            pane.bindHealthOne(player1.healthPropertyUnmodifiable());
        }
        if (entity instanceof Player2) {
            player2 = (Player2)entity;
            pane.bindHealthTwo(player2.healthPropertyUnmodifiable());
        }
        if (entity instanceof Projectile) projectiles.add((Projectile)entity);
        pane.getChildren().add(entity);
    }

    /**
     * removes the entity from the ArrayLists.
     * @param entity an Entity of entities or projectile.
     */
    private void remove(Entity entity) {
        entities.remove(entity);
        projectiles.remove(entity);
        pane.getChildren().remove(entity);
    }

    /**
     * Adds Entity to queue to add on next game loop.
     * @param entity to be added.
     */
    public void queueAddition(Entity entity) {
        entitiesToAdd.add(entity);
    }

    /**
     * Adds entity to be removed on next game loop.
     * @param entity to be removed.
     */
    public void queueRemoval(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * returns player 1 X location.
     * @return return the X value of Player One.
     */
    public double getPlayer1X() {
        return player1.getX();
    }

    /**
     * returns player 2 Y location.
     * @return return the Y value of Player Two.
     */
    public double getPlayer1Y() {
        return player1.getY();
    }

    /**
     * returns player 1 Y location.
     * @return return the Y value of Player One.
     */
    public double getPlayer2X() {
        return player2.getX();
    }
    /**
     * returns player 2 Y location.
     * @return return the Y value of Player Two.
     */
    public double getPlayer2Y() {
        return player2.getY();
    }

    /**
     * returns instance of pane.
     * @return GamePane.
     */
    public GamePane getPane() {
        return pane;
    }

    /**
     * returns this scene.
     * @return Scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets up Timelines, and loops through gameLoop Timeline, watching for events. Handles game logic.
     */
    private void setupTimelines() {

        gameLoop = new Timeline(new KeyFrame(Duration.millis(Constants.TICK_LENGTH), e -> {
            //Check state of all entities every tick time duration.
            for (Entity entity : entities) {
                entity.doTick();
                if (entity instanceof Player1 || entity instanceof Player2) {
                    //removes player if dead
                    if (((Character) entity).getHealth() <= 0) {
                        queueRemoval(entity);
                    }
                }
            }
            //check if projectile hits something
            for (Projectile projectile : projectiles) {
                if (projectile instanceof Player1Projectile) {
                        if (projectile.intersects(player2.getX(), player2.getY(),
                                player2.getWidth(), player2.getHeight())) {
                            System.out.println(projectile.getX());
                            System.out.println(projectile.getY());
                            System.out.println(player2.getX());
                            System.out.println(player2.getY());
                            player2.subtractHealth(projectile.getDamage());
                            queueRemoval(projectile);
                    }
                }
                if (projectile instanceof Player2Projectile) {
                    if (projectile.intersects(player1.getX(), player1.getY(),
                            player1.getWidth(), player1.getHeight())) {
                        System.out.println("player two hit player one!");
                        player1.subtractHealth(projectile.getDamage());
                        queueRemoval(projectile);
                    }
                }
                if (projectile.getY() < -1000 || projectile.getY() > (Constants.SCREEN_HEIGHT * 2)) {
                    queueRemoval(projectile);
                }
            }
            //removes entities that have been flagged for removal earlier.
            for (Entity entity : entitiesToRemove) {
                remove(entity);
            }
            entitiesToRemove.clear();
            //adds entities that have been queued to add.
            for (Entity entity : entitiesToAdd) {
                add(entity);
            }
            entitiesToAdd.clear();
        }));
        //runs indefinitely
        //TODO make game loop end on win or loss
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

}