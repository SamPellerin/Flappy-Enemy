// Authors: Samuel Pellerin 
// Date: April 26th, 2024
//
// This program is a "Flappy Bird" style game, where you control the enemy and fight heroes for coins.
// You can jump with the space key and shoot heroes with the e key. The goal of the game is to accumulate
// coins, without dying. When you die, your coin amount is written in a file named "Scores.txt" and the game stops.
// At any point, you can press on the pause button to pause the game, and you can then press on resume to resume it.
// As you gather coins, your speed is increased and escaping heroes is more difficult.

import controller.GameController;
import model.*;
import javafx.application.Application;
import javafx.stage.Stage;
import view.GameView;

public class FlappyEnemy extends Application {

    // When we run this file, the game will start and the game window will be shown.
    @Override
    public void start(Stage stage) {

        // MVC Components Instantiation
        GameModel gameModel = new GameModel();
        GameController gameController = new GameController(gameModel);
        GameView gameView = new GameView(gameModel, gameController);

        // Initialize the View and start the game
        gameView.initialize(stage);
        gameView.start();
    }
}