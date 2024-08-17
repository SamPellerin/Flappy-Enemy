package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class GameController {
    GameModel gameModel;
    double deltaTime;

    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    // Update the game model (various game objects) after the given delay
    public void updateAll(double deltaTime) {
        this.deltaTime = deltaTime;
        updateEnemy();
        updateCoins();
        updateBullets();
        updateHeroes();
    }

    private void updateEnemy() {

        Enemy enemy = gameModel.getEnemy();

        // Update Speed
        enemy.setVerticalSpeed(min((int) (enemy.getVerticalSpeed() + enemy.getVerticalAcceleration()
                * deltaTime), 300));

        // Calculate new position
        double newPosY = enemy.getPositionY() + enemy.getVerticalSpeed() * deltaTime - 0.5 *
                enemy.getVerticalAcceleration() * Math.pow(deltaTime, 2);

        // Check boundaries and apply change of direction
        if (newPosY >= (GameObject.screenHeight - 2 * enemy.getR())) {
            enemy.setPositionY(GameObject.screenHeight - 2 * enemy.getR());
            enemy.setVerticalSpeed(-300);
        } else if (newPosY <= 0) {
            enemy.setPositionY(0);
            enemy.setVerticalSpeed(300);
        } else {
            enemy.setPositionY((int) newPosY);
        }
    }

    private void updateCoins() {
        Enemy enemy = gameModel.getEnemy();
        List<Coin> toRemove = new ArrayList<>();
        // Move the coin
        for (Coin coin : gameModel.getCoinList()) {
            coin.setPositionX((int) (coin.getPositionX() - deltaTime * enemy.getHorizontalSpeed()));
            // Remove if the coin is off screen
            if (coin.getPositionX() <= -2 * coin.getR()) {
                toRemove.add(coin);
                // Update the enemy stats if he picks a coin
            } else if (GameObject.intersects(coin, enemy)) {
                toRemove.add(coin);
                enemy.setHorizontalSpeed(enemy.getHorizontalSpeed() + 10);
                enemy.setVerticalAcceleration(enemy.getVerticalAcceleration() + 15);
                enemy.setCoins(enemy.getCoins() + 1);
            }
        }
        gameModel.getCoinList().removeAll(toRemove);
    }

    private void updateBullets() {
        List<Bullet> toRemove = new ArrayList<>();
        // Move all bullets
        for (Bullet bullet : gameModel.getBulletList()) {
            // Change the bullet position based on the enemy speed
            bullet.setPositionX((int) (bullet.getPositionX() + deltaTime * gameModel.getEnemy().getHorizontalSpeed() * 2));
            // Remove if the bullet is off screen
            if (bullet.getPositionX() >= GameObject.screenWidth) {
                toRemove.add(bullet);
            }
        }
        gameModel.getBulletList().removeAll(toRemove);
    }

    private void updateHeroes() {
        Enemy enemy = gameModel.getEnemy();
        List<Hero> toRemoveHeros = new ArrayList<>();
        // Move all heroes
        for (Hero hero : gameModel.getHeroList()) {
            hero.update(enemy, deltaTime);

            // Remove if hero is off screen
            if (hero.getPositionX() <= -2 * hero.getR()) {
                toRemoveHeros.add(hero);
                // Case where the hero touches the enemy
            } else if (GameObject.intersects(hero, enemy)) {
                toRemoveHeros.add(hero);
            }

            // Checks if the hero is killed by the enemy
            List<Bullet> toRemoveBullets = new ArrayList<>();
            for (Bullet bullet : gameModel.getBulletList()) {
                if (GameObject.intersects(bullet, hero)) {
                    enemy.setCoins(enemy.getCoins() + hero.getCoins());
                    toRemoveHeros.add(hero);
                    toRemoveBullets.add(bullet);
                }
            }
            gameModel.getBulletList().removeAll(toRemoveBullets);
        }
        gameModel.getHeroList().removeAll(toRemoveHeros);
    }
}