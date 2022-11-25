package ca.bcit.comp2522.termproject.secretwonders;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.util.ArrayList;


public class GameEngine {

    /**
     * Keyboard control directions.
     */
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    private GamePane pane;
    private final Scene scene;

    private Player1 player1 = new Player1();
    private Player2 player2 = new Player2();

    private ArrayList<Entity> entities = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    ArrayList<Entity> entitiesToAdd = new ArrayList<>();
    ArrayList<Entity> entitiesToRemove = new ArrayList<>();


    public GameEngine() {
        pane = new GamePane();
        scene = new Scene(pane, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setupScene(pane);
        setupKeybindings();
        add(player1);
        add(player2);
        setupTimelines();
    }

    private void setupScene(final GamePane pane) {
        this.pane = pane;
        pane.setEngine(this);
        scene.setRoot(pane);
    }

    private void setupKeybindings() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT -> player1.startMovement(Direction.LEFT);
                case A -> player2.startMovement(Direction.LEFT);
                case RIGHT -> player1.startMovement(Direction.RIGHT);
                case D -> player2.startMovement(Direction.RIGHT);
                case UP -> player1.startMovement(Direction.UP);
                case W -> player2.startMovement(Direction.UP);
                case DOWN -> player1.startMovement(Direction.DOWN);
                case S -> player2.startMovement(Direction.DOWN);
                case CONTROL -> player1.fireProjectile();
                case SPACE -> player2.fireProjectile();
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

    private void add(final Entity entity) {
        entities.add(entity);
        if (entity instanceof Player1) {
            player1 = (Player1) entity;
            pane.bindHealthOne(player1.healthPropertyUnmodifiable());
        }
        if (entity instanceof Player2) {
            player2 = (Player2) entity;
            pane.bindHealthTwo(player2.healthPropertyUnmodifiable());
        }
        if (entity instanceof Projectile) projectiles.add((Projectile)entity);
        pane.getChildren().add(entity);
    }


    private void remove(final Entity entity) {

        entities.remove(entity);

        projectiles.remove(entity);
        pane.getChildren().remove(entity);
    }



    public void queueAddition(Entity entity) {
        entitiesToAdd.add(entity);
    }


    public void queueRemoval(Entity entity) {
        entitiesToRemove.add(entity);
    }

    public double getPlayer1X() {
        return player1.getX();
    }

    public double getPlayer1Y() {
        return player1.getY();
    }

    public double getPlayer2X() {
        return player2.getX();
    }

    public double getPlayer2Y() {
        return player2.getY();
    }

    public GamePane getPane() {
        return pane;
    }


    public Scene getScene() {
        return scene;
    }


    private void setupTimelines() {

        //Check state of all entities every tick time duration.
        //removes player if dead
        //check if projectile hits something
        Timeline gameLoop = new Timeline(new KeyFrame(Duration.millis(Constants.TICK_LENGTH), e -> {
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
                if (projectile.getY() < -1000
                        || projectile.getY() > (Constants.SCREEN_HEIGHT * 2))
                    queueRemoval(projectile);
            }
            for (Entity entity : entitiesToRemove) {
                remove(entity);
            }
            entitiesToRemove.clear();
            for (Entity entity : entitiesToAdd) {
                add(entity);
            }
            entitiesToAdd.clear();
        }));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

}