package arigin.models.service;

import com.arigin.models.Island;
import com.arigin.models.abstracts.Entity;
import com.arigin.models.herbivores.*;
import com.arigin.models.plants.Plants;
import com.arigin.models.predators.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics implements PrintAnimalToColsole, Runnable {
    private Island island;

    public Statistics(Island island) {
        this.island = island;
    }

    private synchronized Map<Entity, Integer> calculateStatistitcs() {
        List<Entity> entityList = island.getIsland()
                .entrySet()
                .stream()
                .filter(x -> (x.getValue() != null))
                .map(x -> x.getValue())
                .collect(Collectors.toList());
        var quantity = new HashMap<Entity, Integer>();
        var quantity1 = new HashMap<Entity, Integer>();
        for (int i = 0; i < entityList.size(); i++) {
            int counter = 0;
            for (int i1 = 0; i1 < entityList.size(); i1++) {
                if (entityList.get(i) == entityList.get(i1)) {
                    counter++;
                }
            }
            if (i == 0) {
                quantity.put(entityList.get(i), counter);
                quantity1.put(entityList.get(i), counter);
            } else {
                quantity1.put(entityList.get(i), counter);
                for (Map.Entry<Entity, Integer> entityIntegerEntry : quantity1.entrySet()) {
                    if (!(entityIntegerEntry.getKey().equals(entityList.get(i)))) {
                        quantity.put(entityList.get(i), counter);
                    }
                }
            }

        }

        return quantity;
    }


    @Override
    public synchronized void printToConsole() {
        calculateStatistitcs().forEach((x, y) ->
                System.out.println(x.getClass().getName().toString().split("\\.", 5)[4]
                        + " : " + objectToView(x) + "  quantity of animals species = " + y));
        System.out.println();
    }

    @Override
    public void run() {
        printToConsole();
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
