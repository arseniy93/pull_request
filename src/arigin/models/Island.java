package arigin.models;

import com.arigin.config.FieldSizeConfig;
import com.arigin.config.StartInitializationOfAnimalsInGame;
import com.arigin.models.abstracts.Entity;
import com.arigin.models.herbivores.*;
import com.arigin.models.island.Field;
import com.arigin.models.plants.Plants;
import com.arigin.models.predators.*;
import com.arigin.models.service.PrintAnimalToColsole;

import java.util.*;
import java.util.stream.Collectors;

public class Island implements PrintAnimalToColsole {

    public Entity[][] getArraysOfEntities() {
        return arraysOfEntities;
    }

    public void setArraysOfEntities(Entity entity, int y, int x) {
        this.arraysOfEntities[y][x] = entity;
    }

    public Entity getElementFromArraysOfEntities(int y, int x) {
        return this.arraysOfEntities[y][x];
    }

    private volatile Entity[][] arraysOfEntities;

    public Island(StartInitializationOfAnimalsInGame startInitializationOfAnimalsInGame, Random random, FieldSizeConfig fieldSizeConfig) {
        arraysOfEntities = new Entity[fieldSizeConfig.getWidth()][fieldSizeConfig.getHeight()];
        this.island = startToFillIsland(startInitializationOfAnimalsInGame, random, fieldSizeConfig);

    }


    public void setIsland(Field field, Entity entity) {
        this.island.put(field, entity);
    }

    public void setIsland(Map<Field, Entity> island) {
        this.island = island;
    }

    public Map<Field, Entity> getIsland() {
        return island;
    }

    private volatile Map<Field, Entity> island = new HashMap<>();

    public synchronized boolean isNullMap() {
        List<Entity> list=island.entrySet()
                .stream()
                .filter(x->(x.getValue()!=null))
                .map((x)->x.getValue())
                .collect(Collectors.toList());

        return list.isEmpty();
    }

    private Map<Field, Entity> startToFillIsland(StartInitializationOfAnimalsInGame startInitializationOfAnimalsInGame, Random random, FieldSizeConfig fieldSizeConfig) {

        List<Entity> allEntity = startInitializationOfAnimalsInGame.getAllEntities();
        List<Entity> allEntityInField = setEntityOrNull(allEntity, fieldSizeConfig.getHeight(), fieldSizeConfig.getWidth());

        for (int i = 0; i < fieldSizeConfig.getWidth(); i++) {
            int counter = 0;
            for (int i1 = 0; i1 < fieldSizeConfig.getHeight(); i1++) {
                Entity entityOnField = getEntityOrNull(allEntityInField, random);
                Field field = new Field(i, i1);
                island.put(field, entityOnField);
                arraysOfEntities[i][i1] = entityOnField;
            }
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println("Error in class Island - thread sleep");
            throw new RuntimeException(e);
        }
        orderingHashMap();

        return island;
    }

    private void orderingHashMap() {
        island = island.entrySet().stream()
                .sorted(Comparator.comparing((x) -> x.getKey().getY()))
                .sorted(Comparator.comparing((x) -> x.getKey().getX()))
                .collect(LinkedHashMap::new,
                        (m, c) -> m.put(c.getKey(), c.getValue()),
                        LinkedHashMap::putAll);
    }

    private void printConsoleStartGame(int counter, FieldSizeConfig fieldSizeConfig, Entity entity) {
        String viewOfAniml = objectToView(entity);

        if (counter == fieldSizeConfig.getHeight() - 1) {
            System.out.print(viewOfAniml + "\n");
        } else {
            System.out.print(viewOfAniml + "\t");
        }
    }


    @Override
    public void printToConsole() {
        for (int i = 0; i < arraysOfEntities.length; i++) {
            for (int i1 = 0; i1 < arraysOfEntities[i].length; i1++) {
                System.out.print(objectToView(arraysOfEntities[i][i1]) + "\t");
            }
            System.out.println();
        }
    }

    public Entity getEntity(Field field) {
        return island.get(field);
    }


    private Entity getEntityOrNull(List<Entity> lists, Random random) {
        Entity entity;
        if (lists.size() == 1) {
            return entity = lists.remove(0);
        } else {
            return entity = lists.remove(random.nextInt(lists.size() - 1));
        }
    }


    private List<Entity> setEntityOrNull(List<Entity> entityList
            , int height
            , int weight) {
        var lists = new ArrayList<Entity>();
        lists.addAll(entityList);
        for (int i = 0; i < ((height * weight) - entityList.size()); i++) {
            lists.add(null);
        }
        // Collections.shuffle(lists);
        return lists;
    }

    private static String objectToView(Entity entity) {
        String viewOfAniml = "\uD83D\uDFE6";
        if (entity == null) {
            return viewOfAniml;
        }
        if (entity instanceof Wolf) {
            viewOfAniml = "🐺";
        } else if (entity instanceof BoaConstrictor) {
            viewOfAniml = "🐍";
        } else if (entity instanceof Fox) {
            viewOfAniml = "🦊";
        } else if (entity instanceof Bear) {
            viewOfAniml = "🐻";
        } else if (entity instanceof Eagle) {
            viewOfAniml = "🦅";
        } else if (entity instanceof Horse) {
            viewOfAniml = "🐎";
        } else if (entity instanceof Deer) {
            viewOfAniml = "🦌";
        } else if (entity instanceof Rabbit) {
            viewOfAniml = "🐇";
        } else if (entity instanceof Mouse) {
            viewOfAniml = "🐁";
        } else if (entity instanceof Goat) {
            viewOfAniml = "🐐";
        } else if (entity instanceof Sheep) {
            viewOfAniml = "🐑";
        } else if (entity instanceof WildBoar) {
            viewOfAniml = "🐗";
        } else if (entity instanceof Buffalo) {
            viewOfAniml = "🐃";
        } else if (entity instanceof Duck) {
            viewOfAniml = "🦆";
        } else if (entity instanceof Caterpillar) {
            viewOfAniml = "🐛";
        } else if (entity instanceof Plants) {
            viewOfAniml = "🌿";
        } else {
            viewOfAniml = "\uD83D\uDFE6";
        }
        return viewOfAniml;
    }


}
