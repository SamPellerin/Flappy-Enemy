package model;

public class Bullet extends GameObject {
    public static final int r = 10;

    public Bullet(Enemy enemy) {
        super(enemy.getPositionX() + 2 * enemy.getR(), enemy.getPositionY(), r);
    }
}