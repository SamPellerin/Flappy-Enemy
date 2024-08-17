package model;

import java.util.Random;

public abstract class Hero extends GameObject {
    private final String type;
    private final int coins;
    private static final int minR = 10;
    private static final int maxR = 45;

    public Hero(String type, int coins) {
        super(screenWidth, 0, new Random().nextInt(minR, maxR + 1));
        this.setPositionY(new Random().nextInt(screenHeight + 1 - this.getR() * 2));
        this.type = type;
        this.coins = coins;
    }

    public abstract void update(Enemy enemy, double deltaTime);

    public abstract void attack(Enemy enemy);

    public String getType() {
        return this.type;
    }

    public int getCoins() {
        return coins;
    }
}
