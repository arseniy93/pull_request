package arigin.models.service;

import com.arigin.models.Island;
import com.arigin.models.abstracts.Entity;
import com.arigin.models.herbivores.*;
import com.arigin.models.island.Field;
import com.arigin.models.plants.Plants;
import com.arigin.models.predators.*;

import java.util.Map;

public class ChangePropertiesOfEntityInTheEndOfDay implements Runnable {
    private Island island;

    public ChangePropertiesOfEntityInTheEndOfDay(Island island) {
        this.island = island;
    }

    public synchronized void changePropertiesOfEntityAfterDay() {
        island.getIsland().values().forEach(entity -> reduceWeight(entity));
        for (Map.Entry<Field, Entity> fieldEntityEntry : island.getIsland().entrySet()) {
            if (fieldEntityEntry.getValue()!=null) {
                if(fieldEntityEntry.getValue().getWeight()<=0){
                    fieldEntityEntry.setValue(null);
                }
            }
        }

    }

    private void reduceWeight(Entity entity) {
        if (entity instanceof Predator) {
            decreaseHealthPercent((Predator) entity);
        } else if (entity instanceof Herbivore) {
            decreaseHealthPercent((Herbivore) entity);
        } else if (entity instanceof Plants) {
            decreaseHealthPercent((Plants) entity);
        }

    }

    private void decreaseHealthPercent(Plants plant) {
        if (plant instanceof Plants) {
            plant.setWeight(plant.getWeight() - 0.01);
        }
    }

    private void decreaseHealthPercent(Predator animal) {
        if (animal instanceof Bear) {
            animal.setWeight(animal.getWeight() - 50);
        } else if (animal instanceof BoaConstrictor) {
            animal.setWeight(animal.getWeight() - 1);
        } else if (animal instanceof Fox) {
            animal.setWeight(animal.getWeight() - 0.5);
        } else if (animal instanceof Eagle) {
            animal.setWeight(animal.getWeight() - 0.5);
        } else if (animal instanceof Wolf) {
            animal.setWeight(animal.getWeight() - 5);
        }
    }

    private void decreaseHealthPercent(Herbivore animal) {
        if (animal instanceof Horse) {
            animal.setWeight(animal.getWeight() - 50);
        } else if (animal instanceof Deer) {
            animal.setWeight(animal.getWeight() - 40);
        } else if (animal instanceof Rabbit) {
            animal.setWeight(animal.getWeight() - 0.1);
        } else if (animal instanceof Mouse) {
            animal.setWeight(animal.getWeight() - 0.001);
        } else if (animal instanceof Goat) {
            animal.setWeight(animal.getWeight() - 4);
        } else if (animal instanceof Sheep) {
            animal.setWeight(animal.getWeight() - 5);
        } else if (animal instanceof WildBoar) {
            animal.setWeight(animal.getWeight() - 40);
        } else if (animal instanceof Buffalo) {
            animal.setWeight(animal.getWeight() - 70);
        } else if (animal instanceof Duck) {
            animal.setWeight(animal.getWeight() - 0.1);
        } else if (animal instanceof Caterpillar) {
            animal.setWeight(animal.getWeight() - 0.001);
        }
    }


    @Override
    public void run() {
        changePropertiesOfEntityAfterDay();
        sleep();
    }
    private static void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.err.println("Error in class ChangePropertiesOfEntityInTheEndOfDay - thread time");
            throw new RuntimeException(e);
        }
    }
}
