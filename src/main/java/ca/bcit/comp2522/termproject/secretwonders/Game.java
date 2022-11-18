package ca.bcit.comp2522.termproject.secretwonders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates the handling of keyboard events.
 * @author Carson Olafson and Rhys Mahannah
 * @version 2022
 */
public class Game extends Application {


    final int BOARD_WIDTH = 600;
    final int BOARD_HEIGHT = 600;

    final int MOVEMENT_FACTOR_BEE = 5;
    final int MOVEMENT_FACTOR_DRAGONFLY = 7;
    final int SIZE_DRAGONFLY = 80;
    final int SIZE_BEE = 70;
    /**
     * signal for player one to go forward.
     */
    boolean goForward;
    /**
     * signal for player one to go backward.
     */
    boolean goBackward;
    /**
     * signal for player one to turn left.
     */
    boolean turnLeft;
    /**
     * signal for player one to turn right.
     */
    boolean turnRight;
    /**
     * signal for player one to attack.
     */
    boolean attackSignal;
    /**
     * signal for player two to go forward.
     */

    boolean goForward2;
    /**
     * signal for player two to go backward.
     */
    boolean goBackward2;
    /**
     * signal for player two to turn left.
     */
    boolean turnLeft2;
    /**
     * signal for player two to turn right.
     */
    boolean turnRight2;
    /**
     * signal for player one to attack.
     */
    boolean attackSignal2;

    Point2D initialDirection = new Point2D(0, -1);

    final Rotate rotatePlayer1 = new Rotate();
    final Rotate rotatePlayer2 = new Rotate();
    final Translate translatePlayer1 = new Translate();
    final Translate translatePlayer2 = new Translate();
    private ImageView flyImage;
    private ImageView player1;
    private ImageView player2;
    private Group root;

    /**
     * Displays an image that can be moved using the arrow keys.
     *
     * @param primaryStage a Stage
     */
    public void start(final Stage primaryStage) {

        // Creates player images
        Image dragonfly = new Image("dragonfly.gif", true);
        player1 = new ImageView(dragonfly);
        player1.setFitHeight(SIZE_DRAGONFLY);
        player1.setFitWidth(SIZE_DRAGONFLY);

        Image bee = new Image("bee.gif", true);
        player2 = new ImageView(bee);
        player2.setFitHeight(SIZE_BEE);
        player2.setFitWidth(SIZE_BEE);

        Image fly = new Image("fly.gif", true);
        flyImage = new ImageView(fly);
        flyImage.setFitHeight(SIZE_BEE);
        flyImage.setFitWidth(SIZE_BEE);

        final int playerOneStartCoordinateX = 125;
        final int playerOneStartCoordinateY = 500;
        player1.setX(playerOneStartCoordinateX);
        player1.setY(playerOneStartCoordinateY);

        final int playerTwoStartCoordinateX = 475;
        final int playerTwoStartCoordinateY = 500;
        player2.setX(playerTwoStartCoordinateX);
        player2.setY(playerTwoStartCoordinateY);

        rotatePlayer1.setPivotX(player1.getX() + 40);
        rotatePlayer1.setPivotY(player1.getY() + 40);
        player1.getTransforms().addAll(translatePlayer1, rotatePlayer1);

        rotatePlayer2.setPivotX(player2.getX() + 35);
        rotatePlayer2.setPivotY(player2.getY() + 35);
        player2.getTransforms().addAll(translatePlayer2, rotatePlayer2);

        root = new Group(player1, player2);


        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT, Color.WHITE);

        // Register the key listener here
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP -> goForward = true;
                case DOWN -> goBackward = true;
                case LEFT -> turnLeft = true;
                case RIGHT -> turnRight = true;
                case CONTROL -> {
                    try {
                        player1Attack();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                case W -> goForward2 = true;
                case S -> goBackward2 = true;
                case A -> turnLeft2 = true;
                case D -> turnRight2 = true;
                case SPACE -> player2Attack();
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP -> goForward = false;
                case DOWN -> goBackward = false;
                case LEFT -> turnLeft = false;
                case RIGHT -> turnRight = false;
//                case CONTROL -> attackSignal = false;
                case W -> goForward2 = false;
                case S -> goBackward2 = false;
                case A -> turnLeft2 = false;
                case D -> turnRight2 = false;
//                case SPACE -> attackSignal2 = false;
            }
        });

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {
                double movementChangePlayerOne = 0;
                double movementChangePlayerTwo = 0;

                if (goForward) {
                    movementChangePlayerOne += MOVEMENT_FACTOR_DRAGONFLY;
                }
                if (goBackward) {
                    movementChangePlayerOne -= MOVEMENT_FACTOR_DRAGONFLY;
                }
                if (turnRight) {
                    rotatePlayer1(MOVEMENT_FACTOR_DRAGONFLY);
                }
                if (turnLeft) {
                    rotatePlayer1(-MOVEMENT_FACTOR_DRAGONFLY);
                }
                if (goForward2) {
                    movementChangePlayerTwo += MOVEMENT_FACTOR_BEE;
                }
                if (goBackward2) {
                    movementChangePlayerTwo -= MOVEMENT_FACTOR_BEE;
                }
                if (turnRight2) {
                    rotatePlayer2(MOVEMENT_FACTOR_BEE);
                }
                if (turnLeft2) {
                    rotatePlayer2(-MOVEMENT_FACTOR_BEE);
                }

                Point2D pt1 = rotatePlayer1.deltaTransform(initialDirection.multiply(movementChangePlayerOne));
                Point2D pt2 = rotatePlayer2.deltaTransform(initialDirection.multiply(movementChangePlayerTwo));

                movePlayer1(pt1.getX(), pt1.getY());
                movePlayer2(pt2.getX(), pt2.getY());
            }
        };
        timer.start();
    }



    private void movePlayer1(final double changeInX, final double changeInY) {
        if (changeInX == 0 && changeInY == 0) {
            return;
        }
        double x = changeInX + translatePlayer1.getX();
        double y = changeInY + translatePlayer1.getY();
        translatePlayer1.setX(x);
        translatePlayer1.setY(y);

    }
    private void movePlayer2(final double changeInX, final double changeInY) {
        if (changeInX == 0 && changeInY == 0) {
            return;
        }
        double x = changeInX + translatePlayer2.getX();
        double y = changeInY + translatePlayer2.getY();
        translatePlayer2.setX(x);
        translatePlayer2.setY(y);
    }
    private void moveBullet(final double changeInX, final double changeInY, final ImageView bullet) {
        if (changeInX == 0 && changeInY == 0) {
            return;
        }
        double x = changeInX + bullet.getX();
        double y = changeInY + bullet.getY();
        bullet.setX(x);
        bullet.setY(y);
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


    public void player1Attack() throws InterruptedException {
        Image fireBlast = new Image("fireblast.gif", true);
        ImageView tempFire = new ImageView(fireBlast);
        tempFire.setFitHeight(120);
        tempFire.setFitWidth(60);
        double flyStartCoordinateX = translatePlayer1.getX() + 125 + (SIZE_DRAGONFLY / 8);
        double flyStartCoordinateY = translatePlayer1.getY() + 500 + (SIZE_DRAGONFLY / 12);
        tempFire.setX(flyStartCoordinateX);
        tempFire.setY(flyStartCoordinateY);

        final Rotate rotateFire = new Rotate();
        System.out.println(rotatePlayer1.getAngle());
//        System.out.println(translatePlayer1.getY());
        rotateFire.setPivotX(translatePlayer1.getX() + 125 + (SIZE_DRAGONFLY / 2));
        rotateFire.setPivotY(translatePlayer1.getY() + 500 + (SIZE_DRAGONFLY / 2));
        tempFire.getTransforms().addAll(rotateFire);

        rotateFire.setAngle(rotatePlayer1.getAngle());
        int movingForward = 0;
        if (goForward){
            movingForward = 5;
        }

        root.getChildren().add(tempFire);

        int finalMovingForward = movingForward;
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {

                moveBullet(0, (-5 - finalMovingForward), tempFire);
            }
        };
        timer.start();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                root.getChildren().remove(tempFire);

                            }
                        });
                    }
                },
                800
        );

    }

    public void player2Attack() {
        Image fly = new Image("short_spear.png", true);
        ImageView tempSpear = new ImageView(fly);
        tempSpear.setFitHeight(29);
        tempSpear.setFitWidth(6);
        double spearStartCoordinateX = translatePlayer2.getX() + 475 + (SIZE_BEE / 2);
        double spearStartCoordinateY = translatePlayer2.getY() + 500  + (SIZE_BEE / 2);
        tempSpear.setX(spearStartCoordinateX);
        tempSpear.setY(spearStartCoordinateY);

        final Rotate rotateSpear = new Rotate();
        final Translate translateSpear = new Translate();
        rotateSpear.setPivotX(translatePlayer2.getX() + 475 + (SIZE_BEE / 2));
        rotateSpear.setPivotY(translatePlayer2.getY() + 500  + (SIZE_BEE / 2));
        tempSpear.getTransforms().addAll(rotateSpear, translateSpear);

        rotateSpear.setAngle(rotatePlayer2.getAngle());
        root.getChildren().add(tempSpear);
        System.out.println(initialDirection);
        System.out.println(rotatePlayer2.getAngle());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(final long now) {

                moveBullet(0, -10, tempSpear);
            }
        };
        timer.start();


        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {

                        Platform.runLater(new Runnable() {
                            @Override public void run() {
                                root.getChildren().remove(tempSpear);

                            }
                        });
                    }
                },
                5000
        );
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

