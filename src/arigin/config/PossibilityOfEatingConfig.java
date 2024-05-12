package arigin.config;

import com.arigin.models.abstracts.Entity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class PossibilityOfEatingConfig {
    public Map<Map<Entity, Entity>, Integer> getPossibilityOfEating() {
        return possibilityOfEating;
    }
    private volatile Map<Map<Entity, Entity>, Integer> possibilityOfEating;

    public PossibilityOfEatingConfig(ObjectMapper objectMapper
                                     , JsonNode jsonNode
            , String pathTopossibillityOfEating
            , EntityCharacteristicsConfig entityCharacteristicsConfig) {
        setInPossibilityMap(objectMapper, jsonNode, pathTopossibillityOfEating, entityCharacteristicsConfig);

        this.possibilityOfEating = possibilityOfEating;
    }
    private void setInPossibilityMap(ObjectMapper objectMapper, JsonNode jsonNode, String pathTopossibillityOfEating, EntityCharacteristicsConfig entityCharacteristicsConfig) {
        possibilityOfEating = new HashMap<>();
        //Map<Map<Entity, Entity>, Integer> copyOfpossibilityOfEating = Map.copyOf(possibilityOfEating);
        try {
            jsonNode = objectMapper.readTree(new File(pathTopossibillityOfEating));
        } catch (IOException e) {
            System.err.println("Error in class PossibilityOfEatingConfig ... ");
            throw new RuntimeException(e);
        }
        List<String> listFrom = new ArrayList<>();
        List<String> listTo = new ArrayList<>();
        List<Integer> listPercent = new ArrayList<>();
        for (JsonNode node : jsonNode) {
            listFrom.add((node.path("from")).asText());
            listTo.add((node.path("to")).asText());
            listPercent.add(node.path("percent").asInt());
        }
        setJsnode result = new setJsnode(listFrom, listTo, listPercent);

        forEachToExportPossibility(entityCharacteristicsConfig, result);
    }
    private void forEachToExportPossibility(EntityCharacteristicsConfig entityCharacteristicsConfig, setJsnode result) {
        entityCharacteristicsConfig.getEntityMapConfig().forEach((key, value1) -> {
            entityCharacteristicsConfig.getEntityMapConfig().forEach((key1, value) -> {
                for (int i = 0; i < result.listFrom().size(); i++) {
                    if (key.getType().equals(result.listFrom().get(i)) && key1.getType().equals(result.listTo().get(i))) {
                        Map<Entity, Entity> entityEntityMap = new HashMap<>();
                        entityEntityMap.put(value1, value);
                        Integer precent = checkInteger(result, i);
                        possibilityOfEating.put(entityEntityMap, precent);
                    }
                }
            });
        });
    }

    private static Integer checkInteger(setJsnode result, int i) {
        Integer precent = 0;
        try {
            precent = result.listPercent().get(i);
        } catch (Exception e) {
            precent = 0;
        }
        return precent;
    }

    private record setJsnode(List<String> listFrom, List<String> listTo, List<Integer> listPercent) {
    }
}
