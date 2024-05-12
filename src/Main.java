import com.arigin.config.EntityCharacteristicsConfig;
import com.arigin.config.FieldSizeConfig;
import com.arigin.config.PossibilityOfEatingConfig;
import com.arigin.config.StartInitializationOfAnimalsInGame;
import com.arigin.constants.Constants;
import com.arigin.models.Island;
import com.arigin.models.service.AnyTimeLater;
import com.arigin.models.service.ChangePropertiesOfEntityInTheEndOfDay;
import com.arigin.models.service.Statistics;
import com.arigin.models.service.impl.ActionServiceImpl;
import com.arigin.models.service.impl.PrintAnimalToColsoleImp;
import com.arigin.models.service.jsonservice.CreateObjectFromJson;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Main {


    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Random random = new Random();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        CreateObjectFromJson createObjectFromJson = new CreateObjectFromJson(objectMapper, Constants.RESOURCES_CHARACTERISTICS_OF_ENTITY_JSON, jsonNode);
        EntityCharacteristicsConfig entityCharacteristicsConfig = new EntityCharacteristicsConfig(createObjectFromJson);
        PossibilityOfEatingConfig possibilityOfEatingConfig = new PossibilityOfEatingConfig(objectMapper, jsonNode, Constants.PATH_TOPOSSIBILLITY_OF_EATING, entityCharacteristicsConfig);
        FieldSizeConfig fieldSizeConfig = new FieldSizeConfig();//if you want change some parametrs you have to input values into properties.config
        StartInitializationOfAnimalsInGame startInitializfnionOfAnimalInGame =
                new StartInitializationOfAnimalsInGame(random, fieldSizeConfig, entityCharacteristicsConfig);
        Island island = new Island(startInitializfnionOfAnimalInGame, random, fieldSizeConfig);
        Statistics statistics = new Statistics(island);
        ActionServiceImpl actionService = new ActionServiceImpl(island, fieldSizeConfig, possibilityOfEatingConfig, random);
        ChangePropertiesOfEntityInTheEndOfDay changePropertiesOfEntityInTheEndOfDay = new ChangePropertiesOfEntityInTheEndOfDay(island);
        PrintAnimalToColsoleImp printAnimalToColsoleImp = new PrintAnimalToColsoleImp(island);

        AnyTimeLater anyTimeLater = new AnyTimeLater(island
                , startInitializfnionOfAnimalInGame
                , actionService
                , changePropertiesOfEntityInTheEndOfDay
                , possibilityOfEatingConfig
                , fieldSizeConfig
                , objectMapper
                , random
                , jsonNode
                , entityCharacteristicsConfig
                , statistics
                , printAnimalToColsoleImp);
        try {
            anyTimeLater.swichOnGame();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
