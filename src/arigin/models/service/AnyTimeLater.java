package arigin.models.service;

import com.arigin.config.EntityCharacteristicsConfig;
import com.arigin.config.FieldSizeConfig;
import com.arigin.config.PossibilityOfEatingConfig;
import com.arigin.config.StartInitializationOfAnimalsInGame;
import com.arigin.models.Island;
import com.arigin.models.service.impl.ActionServiceImpl;
import com.arigin.models.service.impl.PrintAnimalToColsoleImp;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AnyTimeLater {
    private Island island;
    private ActionServiceImpl actionService;
    private ChangePropertiesOfEntityInTheEndOfDay changePropertiesOfEntityInTheEndOfDay;
    private PossibilityOfEatingConfig possibilityOfEatingConfig;
    private FieldSizeConfig fieldSizeConfig;
    private StartInitializationOfAnimalsInGame startInitializationOfAnimalsInGame;
    private ObjectMapper objectMapper;
    private Random random;
    private JsonNode jsonNode;
    private EntityCharacteristicsConfig entityCharacteristicsConfig;
    private Statistics statistics;
    private PrintAnimalToColsoleImp printAnimalToColsoleImp;

    public AnyTimeLater(Island island
            , StartInitializationOfAnimalsInGame startInitializationOfAnimalsInGame
            , ActionServiceImpl actionService
            , ChangePropertiesOfEntityInTheEndOfDay changePropertiesOfEntityInTheEndOfDay
            , PossibilityOfEatingConfig possibilityOfEatingConfig
            , FieldSizeConfig fieldSizeConfig
            , ObjectMapper objectMapper
            , Random random
            , JsonNode jsonNode
            , EntityCharacteristicsConfig entityCharacteristicsConfig
            , Statistics statistics
            , PrintAnimalToColsoleImp printAnimalToColsoleImp
    ) {
        this.island = island;
        this.actionService = actionService;
        this.changePropertiesOfEntityInTheEndOfDay = changePropertiesOfEntityInTheEndOfDay;
        this.possibilityOfEatingConfig = possibilityOfEatingConfig;
        this.fieldSizeConfig = fieldSizeConfig;
        this.startInitializationOfAnimalsInGame = startInitializationOfAnimalsInGame;
        this.objectMapper = objectMapper;
        this.random = random;
        this.jsonNode = jsonNode;
        this.entityCharacteristicsConfig = entityCharacteristicsConfig;
        this.statistics = statistics;
        this.printAnimalToColsoleImp = printAnimalToColsoleImp;

    }

    public void swichOnGame() throws InterruptedException {
            do {
                ExecutorService executorService=Executors.newScheduledThreadPool(3);
                executorService.submit(changePropertiesOfEntityInTheEndOfDay);
                executorService.awaitTermination(150, TimeUnit.MILLISECONDS);
                executorService.submit(actionService);
                executorService.awaitTermination(150, TimeUnit.MILLISECONDS);
                executorService.submit(printAnimalToColsoleImp);
                executorService.awaitTermination(150, TimeUnit.MILLISECONDS);
                executorService.submit(statistics);
                executorService.awaitTermination(150, TimeUnit.MILLISECONDS);
                Thread.sleep(200);
                if(island.isNullMap()) {
                    executorService.shutdown();
                }
                executorService.close();
            } while (!island.isNullMap());

    }
}
