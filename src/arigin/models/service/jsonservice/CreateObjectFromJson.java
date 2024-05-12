package arigin.models.service.jsonservice;

import com.arigin.models.abstracts.Entity;
import com.arigin.models.enums.EntityType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateObjectFromJson {
    private final List<Entity> listOfEntity;

    public List<Entity> getListOfEntity() {
        return listOfEntity;
    }

    public CreateObjectFromJson(ObjectMapper objectMapper, String pathOfCharacteristics, JsonNode jsonNode) {
        listOfEntity= List.copyOf((createAnimals(objectMapper,pathOfCharacteristics,jsonNode)));

    }

    private List<Entity> createAnimals(ObjectMapper objectMapper, String pathOfCharacteristics, JsonNode jsonNode) {
        try {
            jsonNode = objectMapper.readTree(new File(pathOfCharacteristics));
        } catch (IOException e) {
            System.err.println("Error in CreateObjectFromJson class? file isn't found - 53");
            throw new RuntimeException(e);
        }
        EntityType[] entityTypes = EntityType.values();
        List<Entity> entities = new ArrayList<>();
        String parametrsOfAnimal="";
        for (EntityType entityType : entityTypes) {
            for (JsonNode node : jsonNode) {
                for (JsonNode jsonNode1 : node.path(entityType.getType())) {
                    parametrsOfAnimal=jsonNode1.toString();
                }
            }
            try {
                Entity entity =(Entity) objectMapper.readValue(parametrsOfAnimal,entityType.getClazz());
                entities.add(entity);
            } catch (JsonProcessingException e) {
                System.err.println("Error in CreateObjectFromJson class - 67");
                throw new RuntimeException(e);
            }

        }
        return entities;
    }
}
