package model;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    Enemy enemy = new Enemy();
    List<Coin> coinList = new ArrayList<>();
    List<Hero> heroList = new ArrayList<>();
    List<Bullet> bulletList = new ArrayList<>();

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public List<Coin> getCoinList() {
        return coinList;
    }

    public void setCoinList(List<Coin> coinList) {
        this.coinList = coinList;
    }

    public List<Hero> getHeroList() {
        return heroList;
    }

    public void setHeroList(List<Hero> heroList) {
        this.heroList = heroList;
    }

    public List<Bullet> getBulletList() {
        return bulletList;
    }

    public void setBulletList(List<Bullet> bulletList) {
        this.bulletList = bulletList;
    }

}
