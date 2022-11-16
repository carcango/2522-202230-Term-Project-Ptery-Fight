package ca.bcit.comp2522.termproject.secretwonders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * Demonstrates the handling of keyboard events.
 */
public class Game extends Application {

    private ImageView player1;

    private ImageView player2;

    boolean goNorth, goSouth, goWest, goEast, goNorth2, goSouth2, goWest2, goEast2;
    Point2D initialDirection = new Point2D(0, -1);

    final Rotate rotatePlayer1 = new Rotate();
    final Rotate rotatePlayer2 = new Rotate();
    final Translate translatePlayer1 = new Translate();
    final Translate translatePlayer2 = new Translate();

    /**
     * Displays an image that can be moved using the arrow keys.
     *
     * @param primaryStage a Stage
     */
    public void start(final Stage primaryStage) {

        Image Knight = new Image("Knight.png", true);
        player1 = new ImageView(Knight);

        Image Wizard = new Image("Wizard.png", true);
        player2 = new ImageView(Wizard);

        final int Player1StartCoordinate = 20;
        player1.setX(Player1StartCoordinate);
        player1.setY(Player1StartCoordinate);

        final int Player2StartCoordinate = 200;
        player2.setX(Player2StartCoordinate);
        player2.setY(Player2StartCoordinate);




        rotatePlayer1.setPivotX(player1.getX() + 16);
        rotatePlayer1.setPivotY(player1.getY() + 16);
        player1.getTransforms().addAll(translatePlayer1, rotatePlayer1);

        rotatePlayer2.setPivotX(player2.getX() + 16);
        rotatePlayer2.setPivotY(player2.getY() + 16);
        player2.getTransforms().addAll(translatePlayer2, rotatePlayer2);

        Group root = new Group(player1, player2);

        final int appWidth = 600;
        final int appHeight = 600;
        Scene scene = new Scene(root, appWidth, appHeight, Color.WHITE);

        // Register the key listener here
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP -> goNorth = true;
                    case DOWN -> goSouth = true;
                    case LEFT -> goWest = true;
                    case RIGHT -> goEast = true;
                    case W -> goNorth2 = true;
                    case S -> goSouth2 = true;
                    case A -> goWest2 = true;
                    case D -> goEast2 = true;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP -> goNorth = false;
                    case DOWN -> goSouth = false;
                    case LEFT -> goWest = false;
                    case RIGHT -> goEast = false;
                    case W -> goNorth2 = false;
                    case S -> goSouth2 = false;
                    case A -> goWest2 = false;
                    case D -> goEast2 = false;
                }
            }
        });

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double delta = 0;
                double delta2 = 0;

                if (goNorth) {
                    delta += 5;
                }
                if (goSouth) {
                    delta -= 5;
                }
                if (goEast) {
                    rotatePlayer1(3);
                }
                if (goWest) {
                    rotatePlayer1(-3);
                }
                if (goNorth2) {
                    delta2 += 5;
                }
                if (goSouth2) {
                    delta2 -= 5;
                }
                if (goEast2) {
                    rotatePlayer2(3);
                }
                if (goWest2) {
                    rotatePlayer2(-3);
                }

                Point2D pt1 = rotatePlayer1.deltaTransform(initialDirection.multiply(delta));
                Point2D pt2 = rotatePlayer2.deltaTransform(initialDirection.multiply(delta2));

                movePlayer1(pt1.getX(), pt1.getY());
                movePlayer2(pt2.getX(), pt2.getY());
            }
        };
        timer.start();
    }



    private void movePlayer1(double dx, double dy) {
        if (dx == 0 && dy == 0) {
            return;
        }
        double x = dx + translatePlayer1.getX();
        double y = dy + translatePlayer1.getY();
        translatePlayer1.setX(x);
        translatePlayer1.setY(y);
    }
    private void movePlayer2(double dx, double dy) {
        if (dx == 0 && dy == 0) {
            return;
        }
        double x = dx + translatePlayer2.getX();
        double y = dy + translatePlayer2.getY();
        translatePlayer2.setX(x);
        translatePlayer2.setY(y);
    }

    public void rotatePlayer1(float angle) {
        angle += rotatePlayer1.getAngle();
        if (angle == 360) {
            angle = 0;
        }
        rotatePlayer1.setAngle(angle);
    }
    public void rotatePlayer2(float angle) {
        angle += rotatePlayer2.getAngle();
        if (angle == 360) {
            angle = 0;
        }
        rotatePlayer2.setAngle(angle);
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }
}

