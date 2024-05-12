package arigin.models.enums;

public enum DirectionType {
    LEFT(0,-1),
    RIGHT(0,1),
    UP(1,0),
    DOWN(-1,0),
    STAND(0,0);

    public int getMoveX() {
        return moveX;
    }

    public int getMoveY() {
        return moveY;
    }

    DirectionType(int moveX, int moveY) {
        this.moveX = moveX;
        this.moveY = moveY;
    }

    private int moveX;
    private int moveY;


}
