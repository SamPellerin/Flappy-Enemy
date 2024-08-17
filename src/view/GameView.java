package view;

import controller.GameController;
import model.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.ScoreFileHandler;

import java.util.Random;

public class GameView extends AnimationTimer {

    GameModel gameModel;
    GameController gameController;
    GraphicsContext context;
    Scene scene;
    Random random = new Random();
    private long lastTime;

    private double xBackground = 0;
    private double timeCoin = 2;
    private double timeHero = 3;
    private double timeBullet = 1;

    Text lifeText = new Text();
    Text coinText = new Text();
    HBox menu;

    Image backgroundImg = new Image("/assets/bg.png");
    Image enemyImg = new Image("/assets/enemy.png", Enemy.r * 2, Enemy.r * 2, true, true);
    Image coinImg = new Image("/assets/coin.png", Coin.r * 2, Coin.r * 2, true, true);
    Image bulletImg = new Image("/assets/bullet.png", Bullet.r * 2, Bullet.r * 2, true, true);

    String bodyPath = "/assets/heroBody.png";
    String furtifPath = "/assets/heroFurtif.png";
    String tankPath = "/assets/heroTank.png";

    public GameView(GameModel gameModel, GameController gameController) {
        this.gameModel = gameModel;
        this.gameController = gameController;
    }

    public void initialize(Stage stage) {
        // Setting up the window.
        VBox root = new VBox();
        scene = new Scene(root, GameObject.screenWidth, GameObject.screenHeight + 40);
        Canvas canvas = new Canvas(GameObject.screenWidth, GameObject.screenHeight);
        root.getChildren().add(canvas);
        context = canvas.getGraphicsContext2D();

        // Links to all the images and paths.
        context.drawImage(backgroundImg, 0, 0);

        // Setting the menu in an horizontal box.
        refreshMenuText();
        menu = new HBox();
        Button but = new Button("Pause");
        menu.setSpacing(20);
        menu.setMaxHeight(40);
        menu.setMinHeight(40);
        menu.getChildren().add(but);
        menu.getChildren().add(new Separator(Orientation.VERTICAL));
        menu.getChildren().add(lifeText);
        menu.getChildren().add(new Separator(Orientation.VERTICAL));
        menu.getChildren().add(coinText);
        menu.setAlignment(Pos.CENTER);
        root.getChildren().add(menu);

        // Setting the button action to stop the timer or start it if the game is running or in pause.
        but.setFocusTraversable(false);
        but.setOnAction(event -> {
            if (but.getText().equals("Pause")) {
                // Pause the timer when the button is clicked
                stop();
                but.setText("Resume");
            } else {
                but.setText("Pause");
                start();
            }
        });

        // Showing the game window.
        stage.setTitle("Flappy Enemy");
        stage.setScene(scene);
        stage.show();

    }

    // Starting the timer. We initiate the lastTime of the animation to the system time.
    // We define what the different keys will do when they are pressed.
    @Override
    public void start() {
        lastTime = System.nanoTime();
        scene.setOnKeyPressed((value) -> {
            if (value.getCode() == KeyCode.SPACE) {
                gameModel.getEnemy().setVerticalSpeed(-300);
            }
            if (value.getCode() == KeyCode.E) {
                if (timeBullet < 0) {
                    Bullet bullet = new Bullet(gameModel.getEnemy());
                    gameModel.getBulletList().add(bullet);
                    timeBullet = 1;
                }
            }
        });
        super.start(); // Starting the handle method.
    }

    // Disconnecting "spacebar" and "e" when the timer is stopped so that all inputs are ignored.
    @Override
    public void stop() {
        scene.setOnKeyPressed(null);
        super.stop();
    }

    // This method is called by the timer multiple times per second. It updates the position of the
    // game objects according to the time that has passed since the last handle call. It then draws the
    // game objects on the game window at their updated position.
    @Override
    public void handle(long now) {
        // Updating time variables.
        double deltaTime = (now - lastTime) * 1e-9;
        timeCoin -= deltaTime;
        timeHero -= deltaTime;
        timeBullet -= deltaTime;

        // The controller is used to handle the movement of all coins, heroes and bullets as well as the enemy,
        // and the interactions between all of those objects (Business logic + data model update).
        gameController.updateAll(deltaTime);

        // Refresh the UI
        adjustBackground(deltaTime);
        drawGameObjects();

        // Let's add a new hero or coin if required.
        addCoinAndHero();

        // Updating the menu.
        refreshMenuText();

        // Special handling when game is over
        checkForEndOfGame();

        // Updating the lastTime variable for the next handle call.
        lastTime = now;
    }

    private void checkForEndOfGame() {
        // Handling the death of the enemy. We stop the game, remove the resume button and write the
        // coin amount to the score file.
        if (gameModel.getEnemy().getLife() == 0) {
            stop();
            menu.getChildren().removeFirst();
            menu.getChildren().addFirst(new Text("Merci Robin"));
            new ScoreFileHandler().updateFile(gameModel.getEnemy().getCoins());
        }
    }

    private void drawGameObjects() {
        //-----------------------------------------------------------------------------------------------------
        // Now, we only need to draw those objects to their updated positions, and add a new coin or hero is
        // required.

        // Drawing the enemy.
        context.drawImage(enemyImg, gameModel.getEnemy().getPositionX(), gameModel.getEnemy().getPositionY());

        // Drawing all the coins.
        for (Coin coin : gameModel.getCoinList()) {
            context.drawImage(coinImg, coin.getPositionX(), coin.getPositionY());
        }

        // Drawing all the bullets.
        for (Bullet bullet : gameModel.getBulletList()) {
            context.drawImage(bulletImg, bullet.getPositionX(), bullet.getPositionY());
        }

        // Drawing all heroes.
        for (Hero hero : gameModel.getHeroList()) {
            context.drawImage(new Image(
                    switch (hero.getType()) {
                        case "Body" -> bodyPath;
                        case "Furtif" -> furtifPath;
                        default -> tankPath;
                    }, hero.getR() * 2, hero.getR() * 2, true, true), hero.getPositionX(),
                    hero.getPositionY());
        }
    }

    private void adjustBackground(double deltaTime) {
        // First, lets adjust the background to its new position.
        if (xBackground <= -GameObject.screenWidth) {
            xBackground = 0;
        } else {
            xBackground -= deltaTime * gameModel.getEnemy().getHorizontalSpeed();
        }

        // Draws two consecutives backgrounds so the background is moving constantly to the left.
        double yBackground = 0;
        context.drawImage(backgroundImg, xBackground, yBackground);
        context.drawImage(backgroundImg, xBackground + GameObject.screenWidth, yBackground);
    }

    private void addCoinAndHero() {
        if (timeCoin < 0) {
            timeCoin = 2;
            gameModel.getCoinList().add(new Coin());
        }

        if (timeHero < 0) {
            timeHero = 3;
            gameModel.getHeroList().add(
                    switch (random.nextInt(0, 3)) {
                        case 0 -> new HeroBody();
                        case 1 -> new HeroFurtif();
                        default -> new HeroTank();
                    }
            );
        }
    }

    private void refreshMenuText() {
        lifeText.setText("Life: " + gameModel.getEnemy().getLife());
        coinText.setText("Coins: " + gameModel.getEnemy().getCoins());
    }

}
