package model;

import java.util.Random;

public class HeroTank extends Hero {

    double lifetime;

    public HeroTank() {
        super("Tank", 7);
        lifetime = 0.5;
    }

    public void update(Enemy enemy, double deltaTime) {
        lifetime -= deltaTime;
        if (lifetime < 0) {
            Random random = new Random();
            int randX = random.nextInt(-30, 31);
            int randY = random.nextInt(-30, 31);
            setPositionX(getPositionX() + randX);
            setPositionY(getPositionY() + randY);
            lifetime = 0.5;
        }
        this.setPositionX((int) (this.getPositionX() - deltaTime * enemy.getHorizontalSpeed()));
        if (intersects(this, enemy)) {
            this.attack(enemy);
        }
    }

    // When the enemy touches the hero of type Tank, we lose half of our life.
    public void attack(Enemy enemy) {
        enemy.setLife(enemy.getLife() - 50);
    }
}
