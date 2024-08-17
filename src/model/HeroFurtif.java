package model;

public class HeroFurtif extends Hero {
    int spawnYPoint;
    double lifetime;

    public HeroFurtif() {
        super("Furtif", 8);
        lifetime = 0;
        spawnYPoint = this.getPositionY();
    }

    public void update(Enemy enemy, double deltaTime) {
        this.lifetime += deltaTime;
        this.setPositionX((int) (this.getPositionX() - deltaTime * enemy.getHorizontalSpeed()));
        this.setPositionY((int) (spawnYPoint + (50 * Math.sin(2 * lifetime))));
        if (intersects(this, enemy)) {
            this.attack(enemy);
        }
    }

    // When the enemy touches the hero of type Furtif, we lose 10 coins.
    public void attack(Enemy enemy) {
        if (enemy.getCoins() > 10) {
            enemy.setCoins(enemy.getCoins() - 10);
        } else {
            enemy.setCoins(0);
        }
    }
}
