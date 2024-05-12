package arigin.models.abstracts;

public abstract class Entity {
    protected Entity(Double weight, Integer maxCoutnOfMovesOnField, Integer velocity, Double eatToFull) {
        this.weight = weight;
        this.maxCoutnOfMovesOnField = maxCoutnOfMovesOnField;
        this.velocity = velocity;
        this.eatToFull = eatToFull;
    }


    public Entity() {


    }

    public Double getWeight() {
        return weight;
    }

    public Integer getMaxCoutnOfMovesOnField() {
        return maxCoutnOfMovesOnField;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public Double getEatToFull() {
        return eatToFull;
    }

    private Double weight;
    private Integer maxCoutnOfMovesOnField;
    private Integer velocity;
    private  Double eatToFull;
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setMaxCoutnOfMovesOnField(Integer maxCoutnOfMovesOnField) {
        this.maxCoutnOfMovesOnField = maxCoutnOfMovesOnField;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "weight=" + weight +
                ", maxCoutnOfMovesOnField=" + maxCoutnOfMovesOnField +
                ", velocity=" + velocity +
                ", eatToFull=" + eatToFull +
                '}';
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    public void setEatToFull(Double eatToFull) {
        this.eatToFull = eatToFull;
    }



}
