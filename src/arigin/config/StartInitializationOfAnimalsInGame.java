package arigin.config;

import com.arigin.models.abstracts.Entity;
import com.arigin.models.enums.EntityType;
import com.arigin.models.herbivores.*;
import com.arigin.models.plants.Plant;
import com.arigin.models.predators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StartInitializationOfAnimalsInGame {
    public StartInitializationOfAnimalsInGame(Random random
            , FieldSizeConfig fieldSizeConfig
            , EntityCharacteristicsConfig entityCharacteristicsConfig) {
        startListOfHerbivors = generate(random, entityCharacteristicsConfig, fieldSizeConfig.getMaxStartQualityOfHerbivores(), "H");
        startListOfPredator = generate(random, entityCharacteristicsConfig, fieldSizeConfig.getMaxStartQualityOfPredators(), "P");
        startListOfPlants = generate(random, entityCharacteristicsConfig, fieldSizeConfig.getMaxQualityOfPlant(), "PL");
        allEntities = summOfEntities(startListOfPredator, startListOfPlants, startListOfHerbivors);
    }

    public List<Entity> getStartListOfHerbivors() {
        return startListOfHerbivors;
    }

    public List<Entity> getStartListOfPredator() {
        return startListOfPredator;
    }

    public List<Entity> getStartListOfPlants() {
        return startListOfPlants;
    }

    private final List<Entity> startListOfHerbivors;
    private final List<Entity> startListOfPredator;
    private final List<Entity> startListOfPlants;

    public List<Entity> getAllEntities() {
        return allEntities;
    }

    private final List<Entity> allEntities;

    private List<Entity> summOfEntities(List<Entity> predators
            , List<Entity> plants
            , List<Entity> herbervorces
            ) {
        var lists=new ArrayList<Entity>();
        addEntitiesElements(predators, lists);
        addEntitiesElements(plants,lists);
        addEntitiesElements(herbervorces,lists);
        //Collections.shuffle(lists);
        return lists;

    }

    private void addEntitiesElements(List<Entity> entities,List<Entity> allEntitiess ) {
        for (int i = 0; i < entities.size(); i++) {
            allEntitiess.add(entities.get(i));
        }
    }

    private List<Entity> generate(Random random
            , EntityCharacteristicsConfig entityCharacteristicsConfig
            , int quality
            , String typeOfAnomals) {
        var lists = new ArrayList<Entity>();
        int k = 0;
        k = separatedOfAnimals(entityCharacteristicsConfig, typeOfAnomals, lists, k);

        var fillLists = new ArrayList<Entity>();
        for (int i = 0; i < quality; i++) {
            boolean j = (k == 1) ? fillLists.add(lists.get(random.nextInt(0, lists.size()))) : fillLists.add(lists.get(0));
        }
        return fillLists;
    }

    private static int separatedOfAnimals(EntityCharacteristicsConfig entityCharacteristicsConfig, String typeOfAnomals, ArrayList<Entity> lists, int k) {
        for (Map.Entry<EntityType, Entity> entityTypeEntityEntry : entityCharacteristicsConfig.getEntityMapConfig().entrySet()) {
            if (entityTypeEntityEntry.getValue() instanceof Predator && typeOfAnomals.equals("P")) {
                lists.add(entityTypeEntityEntry.getValue());
                k = 1;
            } else if (entityTypeEntityEntry.getValue() instanceof Herbivore && typeOfAnomals.equals("H")) {
                lists.add(entityTypeEntityEntry.getValue());
                k = 1;
            } else if (entityTypeEntityEntry.getValue() instanceof Plant && typeOfAnomals.equals("PL")) {
                lists.add(entityTypeEntityEntry.getValue());
                k = 0;
            }
        }
        return k;
    }
}

