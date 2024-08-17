package model;

public abstract class GameObject {
    private int positionX;
    private int positionY;
    private int r;
    public static final int screenWidth = 640;
    public static final int screenHeight = 400;

    public GameObject(int positionX, int positionY, int r) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.r = r;
    }

    public int getPositionX() {
        return this.positionX;
    }

    public int getPositionY() {
        return this.positionY;
    }

    public int getR() {
        return this.r;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    // The function intersects checks if the distance between two game objects
    // is smaller than their hitboxes. It returns true if they do.
    public static boolean intersects(GameObject object1, GameObject object2) {
        double dx = object1.getPositionX() - object2.getPositionX();
        double dy = object1.getPositionY() - object2.getPositionY();
        double dSquared = dx * dx + dy * dy;
        return dSquared < (object1.getR() + object2.getR()) * (object1.getR() + object2.getR());
    }
}
