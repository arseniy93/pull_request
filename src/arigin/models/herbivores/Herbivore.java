package arigin.models.herbivores;

import com.arigin.models.abstracts.Entity;

public abstract class Herbivore extends Entity {
    protected Herbivore(Entity entity) {
        super(entity.getWeight(), entity.getMaxCoutnOfMovesOnField(), entity.getVelocity(), entity.getEatToFull());
    }

    public Herbivore() {
        super();
    }

}
