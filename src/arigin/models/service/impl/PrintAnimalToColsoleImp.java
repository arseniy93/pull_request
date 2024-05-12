package arigin.models.service.impl;

import com.arigin.models.Island;
import com.arigin.models.abstracts.Entity;
import com.arigin.models.herbivores.*;
import com.arigin.models.island.Field;
import com.arigin.models.plants.Plants;
import com.arigin.models.predators.*;
import com.arigin.models.service.PrintAnimalToColsole;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;


public class PrintAnimalToColsoleImp implements PrintAnimalToColsole, Runnable {
    public PrintAnimalToColsoleImp(Island island) {
        this.island = island;
    }

    private Island island;

    @Override
    public synchronized void printToConsole() {
        orderingHashMap();
        changeArray();


        for (int i = 0; i < island.getArraysOfEntities().length; i++) {
            for (int i1 = 0; i1 < island.getArraysOfEntities()[i].length; i1++) {
                System.out.print(objectToView(island.getArraysOfEntities()[i][i1]) + "\t");
            }
            System.out.println();
        }
    }

    private void orderingHashMap() {
        island.getIsland().entrySet().stream()
                .sorted(Comparator.comparing((x) -> x.getKey().getY()))
                .sorted(Comparator.comparing((x) -> x.getKey().getX()))
                .collect(LinkedHashMap::new,
                        (m, c) -> m.put(c.getKey(), c.getValue()),
                        LinkedHashMap::putAll);
    }

    private void changeArray() {
        for (Map.Entry<Field, Entity> fieldEntityEntry : island.getIsland().entrySet()) {
            island.setArraysOfEntities(fieldEntityEntry.getValue(), fieldEntityEntry.getKey().getY(), fieldEntityEntry.getKey().getX());
        }
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

    @Override
    public void run() {

        printToConsole();
        sleep();
    }
    private static void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.err.println("Error in class PrintAnimalToColsoleImp - thread time");
            throw new RuntimeException(e);
        }
    }
}
