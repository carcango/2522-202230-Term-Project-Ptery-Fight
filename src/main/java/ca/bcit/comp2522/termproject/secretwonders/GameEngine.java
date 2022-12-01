package ca.bcit.comp2522.termproject.secretwonders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;
import java.util.ArrayList;

/**
 * GameEngine Class.
 *
 * @author Olafson and Mahannah
 * @version 2022
 */
public class GameEngine {

    /**
     * Keyboard control directions.
     */
    public enum Direction { UP, DOWN, LEFT, RIGHT }
    private final static int ENEMIES_IN_GAME = 5;

    /**
     * Visible contents of Objects for the screen.
     */
    private GamePane pane;
    /**
     * the scene for the game.
     */
    private final Scene scene;
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
    /*
    /**
     *Arraylist of Entities, not including projectiles. This keeps track of Entities are currently part of the game.
     */
    private final ArrayList<Entity> entities = new ArrayList<>();
    /**
     * Arraylist of Projectiles, keeps track of what projectiles are currently part of the game.
     */
    private final ArrayList<Projectile> projectiles = new ArrayList<>();
    /**
     * Arraylist of Enemies, keeps track of what projectiles are currently part of the game.
     */
    private final ArrayList<Enemy> enemies = new ArrayList<>();
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
    private long lastPlayerTwoShot = System.currentTimeMillis();

    private long lastEnemyAddedToGame = System.currentTimeMillis();

    private int playerOneScore = 0;
    private int playterTwoScore = 0;



    /**
     * Constructor for GameEngine.
     */
    public GameEngine() {
        pane = new GamePane();
        scene = new Scene(pane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setupScene(pane);
        setupKeybindings();

        // Add players to the game.
        add(player1);
        add(player2);

        // Main Game Loop
        setupTimelines();
    }

    /**
     * sets up the attribute of pane to the passed pane, sets the root of the scene to watch the pane.
     * @param gamePane a GamePane pane.
     */
    private void setupScene(final GamePane gamePane) {
        this.pane = gamePane;
        gamePane.setEngine(this);
        scene.setRoot(gamePane);
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
                case LEFT -> player1.stopMovement(Direction.LEFT);
                case A -> player2.stopMovement(Direction.LEFT);
                case RIGHT -> player1.stopMovement(Direction.RIGHT);
                case D -> player2.stopMovement(Direction.RIGHT);
                case UP -> player1.stopMovement(Direction.UP);
                case W -> player2.stopMovement(Direction.UP);
                case DOWN -> player1.stopMovement(Direction.DOWN);
                case S -> player2.stopMovement(Direction.DOWN);
            }
        });
    }

    /**
     * adds an entity to the game pane.
     * If the entity is a player, attaches a health bar to the entity.
     * @param entity an object that extends Entity.
     */
    private void add(final Entity entity) {
        entities.add(entity);
        if (entity instanceof Player1) {
            player1 = (Player1) entity;
            pane.bindHealthOne(player1.healthPropertyUnmodifiable());
            pane.getChildren().add(entity);
        }
        if (entity instanceof Player2) {
            player2 = (Player2) entity;
            pane.bindHealthTwo(player2.healthPropertyUnmodifiable());
            pane.getChildren().add(entity);
        }
        if (entity instanceof Projectile) {
            projectiles.add((Projectile) entity);
            pane.getChildren().add(entity);
        }
//        if (entity instanceof Enemy) {
//            enemies.add((Enemy) entity);
//            pane.getChildren().add(entity);
//            }
        }

    /**
     * removes the entity from the ArrayLists.
     * @param entity an Entity of entities or projectile.
     */
    private void remove(final Entity entity) {
        entities.remove(entity);
        projectiles.remove(entity);
        enemies.remove(entity);
        pane.getChildren().remove(entity);
    }

    /**
     * Adds Entity to queue to add on next game loop.
     * @param entity to be added.
     */
    public void queueAddition(final Entity entity) {
        entitiesToAdd.add(entity);
    }

    /**
     * Adds entity to be removed on next game loop.
     * @param entity to be removed.
     */
    public void queueRemoval(final Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * returns this scene.
     * @return Scene.
     */
    public Scene getScene() {
        return scene;
    }

    ////////////////////
    // MAIN GAME LOOP //
    ////////////////////
    /**
     * Sets up Timelines, and loops through gameLoop Timeline, watching for events. Handles game logic.
     */
    private void setupTimelines() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(Constants.TICK_LENGTH), e -> {

            //Check state of all entities every tick time duration.
            if (System.currentTimeMillis() - lastEnemyAddedToGame >= 1000) {
                Enemy enemy = new Enemy();

                enemies.add(enemy);
                entities.add(enemy);

                pane.getChildren().add(enemy);
                enemy.makeEnemyAppear();

                lastEnemyAddedToGame = System.currentTimeMillis();
            }

            for (Entity entity : entities) {
                //Moves all entities
                entity.doMovement();
                if (entity instanceof Player1 || entity instanceof Player2) {

                    // Remove player and their hit box if dead.
                    if (((Character) entity).getHealth() <= 0) {

                        // Remove player hit box
                        entity.setHeightToZero();
                        entity.setWidthToZero();
                        entity.setX(0);
                        entity.setY(0);

                        // Remove player
                        queueRemoval(entity);
                    }
                }
            }
            // Check if player projectiles hit player or enemy
            for (Projectile projectile : projectiles) {
                if (projectile instanceof Player1Projectile) {

                    // Check if Player 1 projectile hits Player 2
                    if (projectile.intersects(player2.getX(), player2.getY(),
                            player2.getWidth(), player2.getHeight())) {

                        System.out.println("Player 1 hit Player 2!");
                        player2.subtractHealth(projectile.getDamage());
                        playerOneScore++;
                        pane.playerOneScoreLabel.setText("Score: " + playerOneScore);
                        queueRemoval(projectile);
                    }
                }

                // Check if Player 2 projectile hits Player 1
                if (projectile instanceof Player2Projectile) {
                    // Check if Player 2 projectile hits Player 1
                    if (projectile.intersects(player1.getX(), player1.getY(),
                            player1.getWidth(), player1.getHeight())) {

                        System.out.println("Player 2 hit Player 1!");
                        player1.subtractHealth(projectile.getDamage());
                        playterTwoScore++;
                        pane.playerTwoScoreLabel.setText("Score: " + playterTwoScore);
                    }
                }
                //removes projectile if it goes much past screen bounds.
                if (projectile.getY() < -10 || projectile.getY() > (Constants.SCREEN_HEIGHT * 2)) {
                    queueRemoval(projectile);
                }
            }

            for (Enemy enemyUnit : enemies) {

                // Checks if enemy hits player 1; if so, character is damaged, and enemy disappears
                if (enemyUnit.intersects(player1.getX(), player1.getY(), player1.getWidth(), player1.getHeight())) {
                    System.out.println("Enemy hit Player 1!");
                    player1.subtractHealth(enemyUnit.getEnemyDamage());
                    queueRemoval(enemyUnit);

                }

                // Checks if enemy hits player 2; if so, character is damaged, and enemy disappears
                if (enemyUnit.intersects(player2.getX(), player2.getY(), player2.getWidth(), player2.getHeight())) {
                    System.out.println("Enemy hit Player 2!");
                    player2.subtractHealth(enemyUnit.getEnemyDamage());
                    queueRemoval(enemyUnit);
                }

                // Check if enemy hit by player 2 projectile
                for (Projectile projectile : projectiles) {
                    if (projectile instanceof Player1Projectile) {
                        if (projectile.intersects(enemyUnit.getX(), enemyUnit.getY(),
                                enemyUnit.getWidth(), enemyUnit.getHeight())) {
                            queueRemoval(enemyUnit);
                            queueRemoval(projectile);
                            playerOneScore++;
                            pane.playerOneScoreLabel.setText("Score: " + playerOneScore);
                        }
                    }
                    if (projectile instanceof Player2Projectile) {
                        if (projectile.intersects(enemyUnit.getX(), enemyUnit.getY(),
                                enemyUnit.getWidth(), enemyUnit.getHeight())) {
                            queueRemoval(enemyUnit);
                            playterTwoScore++;
                            pane.playerTwoScoreLabel.setText("Score: " + playterTwoScore);
                        }
                    }
                }

                // Checks if enemy is off screen; if so, removes enemy.
                if (enemyUnit.getCenterX() < -500 || enemyUnit.getCenterX() > Constants.SCREEN_WIDTH + 200) {
                    queueRemoval(enemyUnit);
                }
                if (enemyUnit.getCenterY() < -500 || enemyUnit.getCenterY() > Constants.SCREEN_HEIGHT + 200) {
                    queueRemoval(enemyUnit);
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
