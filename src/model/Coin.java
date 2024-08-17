package model;

import java.util.Random;

public class Coin extends GameObject {

    public static final int r = 20;

    public Coin() {
        super(screenWidth, new Random().nextInt(GameObject.screenHeight + 1 - 40), r);
    }
}
