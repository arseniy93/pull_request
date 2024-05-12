package arigin.models.abstracts;


public abstract class Animal extends Entity {
    protected Animal(Double weight, Integer maxCoutnOfMovesOnField, Integer velocity, Double eatToFull) {
        super(weight, maxCoutnOfMovesOnField, velocity, eatToFull);
    }

    public Animal() {
        super();
    }

}
