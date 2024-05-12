package arigin.models.enums;

import com.arigin.models.herbivores.*;
import com.arigin.models.plants.Plants;
import com.arigin.models.predators.*;


public enum EntityType {
    WOLF("Wolf", Wolf.class),
    FOX("Fox", Fox.class),
    BEAR("Bear", Bear.class),
    EAGLE("Eagle", Eagle.class),
    BOA_CONSTRICTOR("BoaConstrictor", BoaConstrictor.class),

    HORSE("Horse",Horse .class),
    DEER("Deer",Deer .class),
    RABBIT("Rabbit",Rabbit .class),
    MOUSE("Mouse",Mouse .class),
    GOAT("Goat",Goat .class),
    SHEEP("Sheep",Sheep .class),
    WILD_BOAR("WildBoar",WildBoar .class),
    BUFFALO("Buffalo",Buffalo .class),
    DUCK("Duck",Duck .class),
    CATERPILLAR("Caterpillar",Caterpillar .class),
    PLANTS("Plants", Plants.class);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    private String type;
    private Class clazz;


     EntityType(String type, Class clazz) {
        this.type = type;
        this.clazz = clazz;
    }
}
