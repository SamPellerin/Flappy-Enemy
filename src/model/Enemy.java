package model;

public class Enemy extends GameObject {
    private int horizontalSpeed;
    private int verticalSpeed;
    private int verticalAcceleration;
    private int life;
    private int coins;
    public static final int r = 30;

    public Enemy() {
        super(GameObject.screenWidth / 2 - 30, GameObject.screenHeight / 2 - 30, r);
        this.coins = 0;
        this.horizontalSpeed = 120;
        this.verticalSpeed = 0;
        this.life = 100;
        this.verticalAcceleration = 500;
    }

    public int getHorizontalSpeed() {
        return this.horizontalSpeed;
    }

    public int getVerticalSpeed() {
        return this.verticalSpeed;
    }

    public int getVerticalAcceleration() {
        return this.verticalAcceleration;
    }

    public int getLife() {
        return this.life;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setHorizontalSpeed(int horizontalSpeed) {
        this.horizontalSpeed = horizontalSpeed;
    }

    public void setVerticalSpeed(int verticalSpeed) {
        this.verticalSpeed = verticalSpeed;
    }

    public void setVerticalAcceleration(int verticalAcceleration) {
        this.verticalAcceleration = verticalAcceleration;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
