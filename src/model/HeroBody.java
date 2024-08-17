package model;

public class HeroBody extends Hero {

    public HeroBody() {
        super("Body", 5);
    }

    public void update(Enemy enemy, double deltaTime) {
        this.setPositionX((int) (this.getPositionX() - deltaTime * enemy.getHorizontalSpeed()));
        if (intersects(this, enemy)) {
            this.attack(enemy);
        }
    }

    // When the enemy touches the hero of type Body, the enemy dies.
    public void attack(Enemy enemy) {
        enemy.setLife(0);
    }

}
