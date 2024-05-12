package arigin.models.service.impl;

import com.arigin.config.FieldSizeConfig;
import com.arigin.config.PossibilityOfEatingConfig;
import com.arigin.models.Island;
import com.arigin.models.abstracts.Entity;
import com.arigin.models.enums.DirectionType;
import com.arigin.models.herbivores.*;
import com.arigin.models.island.Field;
import com.arigin.models.plants.Plant;
import com.arigin.models.predators.*;
import com.arigin.models.service.EatService;
import com.arigin.models.service.MoveService;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ActionServiceImpl implements MoveService, EatService, Runnable {
    private final Island island;
    private Random random;
    private FieldSizeConfig fieldSizeConfig;
    private PossibilityOfEatingConfig possibilityOfEatingConfig;

    public ActionServiceImpl(Island island, FieldSizeConfig fieldSizeConfig, PossibilityOfEatingConfig possibilityOfEatingConfig, Random random) {
        this.island = island;
        this.fieldSizeConfig = fieldSizeConfig;
        this.possibilityOfEatingConfig = possibilityOfEatingConfig;
        this.random=random;
    }

    @Override
    public synchronized void move(Random random) {
        var locationNextDay = island.getIsland();
        locationNextDay = sortMap(locationNextDay);
        var fields = new ArrayList<Field>();
        var entities = new ArrayList<Entity>();

        Entity arrField[][] = new Entity[fieldSizeConfig.getWidth()][fieldSizeConfig.getHeight()];


        for (Field field : locationNextDay.keySet()) {
            fields.add(field);
            entities.add(locationNextDay.get(field));
        }
        int k = 0;
        for (int i = 0; i < arrField.length; i++) {
            for (int i1 = 0; i1 < arrField[i].length; i1++) {
                arrField[i][i1] = entities.get(k);
                k++;
            }
        }
        int counter = 0;
        for (int i = 0; i < fieldSizeConfig.getWidth(); i++) {
            for (int i1 = 0; i1 < fieldSizeConfig.getHeight(); i1++) {
                mainMove(random, entities, counter, i, i1, fieldSizeConfig, arrField);
                counter++;

            }

        }

    }

    private void mainMove(Random random, ArrayList<Entity> entities, int counter, int i, int i1, FieldSizeConfig fieldSizeConfig, Entity[][] arrField) {
        if (entities.get(counter) != null && !(entities.get(counter) instanceof Plant)) {
            int moveY = i;
            int moveX = i1;
            int afterMoveX = i;
            int afterMoveY = i1;
            checkConditionOfMove(random, entities, counter, fieldSizeConfig, arrField, moveY, moveX, afterMoveX, afterMoveY);
        }
    }

    private void checkConditionOfMove(Random random, ArrayList<Entity> entities, int counter, FieldSizeConfig fieldSizeConfig, Entity[][] arrField, int moveY, int moveX, int afterMoveX, int afterMoveY) {
        for (int integer = 0; integer < entities.get(counter).getVelocity(); integer++) {
            DirectionType directionType = new ChooseDirectionServiceImpl().chooseDirection(random);
            if (!(((moveY + directionType.getMoveY() < 0) || (moveY + directionType.getMoveY() > fieldSizeConfig.getWidth() - 1))
                    || ((moveX + directionType.getMoveX() < 0) || (moveX + directionType.getMoveX() > fieldSizeConfig.getHeight() - 1)))) {
                moveY += directionType.getMoveY();
                moveX += directionType.getMoveX();
                if (island.getElementFromArraysOfEntities(moveY, moveX) == null) {
                    island.setArraysOfEntities(entities.get(counter), moveY, moveX);//put on array[][]
                    island.setIsland(new Field(moveY, moveX), island.getElementFromArraysOfEntities(moveY, moveX));
                    island.setArraysOfEntities(null, afterMoveX, afterMoveY);
                    island.setIsland(new Field(afterMoveX, afterMoveY), null);//put in map
                    arrField[moveY][moveX] = entities.get(counter);
                    afterMoveX = moveY;
                    afterMoveY = moveX;
                }
            }
        }
    }

    private static Map<Field, Entity> sortMap(Map<Field, Entity> locationNextDay) {
        locationNextDay = locationNextDay.entrySet().stream()
                .sorted(Comparator.comparing((x) -> x.getKey().getY()))
                .sorted(Comparator.comparing((x) -> x.getKey().getX()))
                .collect(LinkedHashMap::new,
                        (m, c) -> m.put(c.getKey(), c.getValue()),
                        LinkedHashMap::putAll);
        return locationNextDay;
    }


    @Override
    public synchronized void eat(Map<Map<Entity, Entity>, Integer> eatOrNo) {
        var locationNextDay = sortMap(island.getIsland());
        var forCheckHealthOfAnimls = sortMap(island.getIsland());
        FieldSizeConfig fieldSizeConfig = new FieldSizeConfig();
        Field[][] fields1 = new Field[fieldSizeConfig.getWidth()][fieldSizeConfig.getHeight()];
        createFieldArray(fields1);
        for (int i = 0; i < fields1.length; i++) {
            for (int i1 = 0; i1 < fields1[i].length; i1++) {
                int yUp = i;
                int xUp = i1 + 1;
                int yDown = i;
                int xDown = i1 - 1;
                int yLeft = i - 1;
                int xLeft = i1;
                int yRight = i + 1;
                int xRight = i1;
                boolean up = isFree(yUp, fields1, xUp);
                boolean down = isFree(yDown, fields1, xDown);
                boolean left = isFree(yLeft, fields1, xLeft);
                boolean right = isFree(yRight, fields1, xRight);
                var fieldList = new ArrayList<Field>();
                addTrueBooleanInList(fieldList, up, yUp, xUp, down, yDown, xDown, left, yLeft, xLeft, right, yRight, xRight);
                if (fieldList.size() != 0) {
                    Field ckechField = fieldList.get(rnd(fieldList) - 1);
                    if ((locationNextDay.get(fields1[i][i1]) instanceof Predator) && (locationNextDay.get(ckechField) instanceof Herbivore)) {
                        mainEatingMethod(eatOrNo, locationNextDay, fields1, i, i1, ckechField, forCheckHealthOfAnimls);

                    } else if ((locationNextDay.get(fields1[i][i1]) instanceof Herbivore) && (locationNextDay.get(ckechField) instanceof Plant)) {
                        mainEatingMethod(eatOrNo, locationNextDay, fields1, i, i1, ckechField, forCheckHealthOfAnimls);
                    }
                }


            }
        }



    }

    private void mainEatingMethod(Map<Map<Entity, Entity>, Integer> eatOrNo, Map<Field, Entity> locationNextDay, Field[][] fields1, int i, int i1, Field ckechField, Map<Field, Entity> forCheckHealthOfAnimls) {
        for (Map.Entry<Map<Entity, Entity>, Integer> mapIntegerEntry : eatOrNo.entrySet()) {
            for (Map.Entry<Entity, Entity> entityEntityEntry : mapIntegerEntry.getKey().entrySet()) {
                if (entityEntityEntry.getKey().equals(locationNextDay.get(fields1[i][i1])) &&
                        entityEntityEntry.getValue().equals(locationNextDay.get(ckechField))) {
                    if (mapIntegerEntry.getValue() >= ThreadLocalRandom.current().nextInt(100)) {
                        double health = locationNextDay.get(fields1[i][i1]).getWeight();
                        double etalonHealth = forCheckHealthOfAnimls.get(fields1[i][i1]).getWeight();
                        double checkHealth = ((locationNextDay.get(ckechField).getWeight() + health)>=etalonHealth ) ? etalonHealth : (locationNextDay.get(ckechField).getWeight() + health);
                        locationNextDay.get(fields1[i][i1]).setWeight(checkHealth);
                        locationNextDay.get(ckechField).setWeight(0.0);
                        island.getEntity(fields1[i][i1]).setWeight(checkHealth);
                        island.getEntity(ckechField).setWeight(0.0);
                    }
                }
            }
        }
    }

    private static int rnd(ArrayList<Field> fieldList) {
        if (fieldList.size() > 1) {
            int rnd = ThreadLocalRandom.current().nextInt(1, fieldList.size());
            return rnd;
        } else {
            return 1;
        }

    }

    private static void addTrueBooleanInList(List<Field> fieldList, boolean up, int yUp, int xUp, boolean down, int yDown, int xDown, boolean left, int yLeft, int xLeft, boolean right, int yRight, int xRight) {

        if (up) {
            fieldList.add(new Field(yUp, xUp));
        }
        if (down) {
            fieldList.add(new Field(yDown, xDown));
        }
        if (left) {
            fieldList.add(new Field(yLeft, xLeft));
        }
        if (right) {
            fieldList.add(new Field(yRight, xRight));
        }
    }

    private static void addToBooleanList(boolean up, ArrayList<Boolean> booleanList) {
        boolean uP = up == true ? booleanList.add(up) : booleanList.add(null);
    }

    private boolean isFree(int yUp, Field[][] fields1, int xUp) {
        if ((yUp >= 0 && yUp < fields1.length - 1) && (xUp >= 0 && xUp < fields1[0].length - 1)) {
            return true;
        }
        return false;
    }

    private static void createFieldArray(Field[][] fields1) {
        for (int i = 0; i < fields1.length; i++) {
            for (int i1 = 0; i1 < fields1[i].length; i1++) {
                fields1[i][i1] = new Field(i, i1);
            }
        }
    }



    @Override
    public void run() {
        move(random);
        sleep();
        eat(possibilityOfEatingConfig.getPossibilityOfEating());
        sleep();
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.err.println("Error in class ActionServiceImpl - thread time");
            throw new RuntimeException(e);
        }
    }
}
