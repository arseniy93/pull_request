package arigin.models.service.jsonservice;

import com.arigin.constants.Constants;
import com.arigin.models.service.Translatable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SerializationToJson implements Translatable {
    private final List<String> toAnimals = new ArrayList<>();
    private final List<String> fromAnimals = new ArrayList<>();
    private final List<String> valuesOfPersentOfEating = new ArrayList<>();

    private final List<String> typeOfAnimals = new ArrayList<>();
    private final List<String> charactersOfAnimals = new ArrayList<>();
    private final List<String> valueOfCharacteristicsOfAnimal = new ArrayList<>();

    public void serializationToJson(ParsingFromSite parsingFromSite) throws IOException {
        getThreeArrays(parsingFromSite.getParsedArraysOfPossobillityOfEating(), fromAnimals, valuesOfPersentOfEating, toAnimals);
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode array = objectMapper.createArrayNode();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        translateArraysToEnglish(toAnimals);
        translateArraysToEnglish(fromAnimals);
        exportToJson(valuesOfPersentOfEating, toAnimals, fromAnimals, objectMapper, array, true);
        objectMapper.writeValue(new File("resources/possibiliteOfEating.json"), array);

        System.out.println("lines" + toAnimals);
        System.out.println("columnsOfAnimal = " + fromAnimals);
        System.out.println("valuesOfPersentOfEating = " + valuesOfPersentOfEating);

        ArrayNode arrayNode = objectMapper.createArrayNode();
        getThreeArrays(parsingFromSite.getParsedArraysOfCharacteristicsOfEntity(), typeOfAnimals, valueOfCharacteristicsOfAnimal, charactersOfAnimals);
        translateArraysToEnglish(typeOfAnimals);
        exportToJson(valueOfCharacteristicsOfAnimal, typeOfAnimals, charactersOfAnimals, objectMapper, arrayNode, false);
        objectMapper.writeValue(new File("resources/characteristicsOfEntity.json"), arrayNode);

        System.out.println("typeOfAnimals = " + typeOfAnimals);
        System.out.println("charactersOfAnimals = " + charactersOfAnimals);
        System.out.println("valueOfCharacteristicsOfAnimal = " + valueOfCharacteristicsOfAnimal);


    }


    private static void exportToJson(List<String> valuesOfPersentOfEating, List<String> lines, List<String> columnsOfAnimal, ObjectMapper objectMapper, ArrayNode array, boolean isFirstTable) {
        int sizeOfcolumnsOfAnimal = 0;
        int sizeOflines = 0;
        int counter = 0;
        if (isFirstTable) {
            for (int i = 0; i < valuesOfPersentOfEating.size(); i++) {
                if (sizeOflines == lines.size()) {
                    sizeOflines = 0;
                    sizeOfcolumnsOfAnimal++;
                }
                if (sizeOfcolumnsOfAnimal == columnsOfAnimal.size()) {
                    sizeOfcolumnsOfAnimal = 0;
                    sizeOflines++;
                }
                ObjectNode on = objectMapper.createObjectNode();
                on.put("from", columnsOfAnimal.get(sizeOfcolumnsOfAnimal));
                on.put("to", lines.get(sizeOflines));
                on.put("percent", (Integer) getNumberOrNULL(valuesOfPersentOfEating, i, false));
                sizeOflines++;
                array.add(on);
            }
        } else {
            for (int i = 0; i < lines.size(); i++) {
                ObjectNode on = objectMapper.createObjectNode();
                ObjectNode onInner = objectMapper.createObjectNode();
                ArrayNode array1 = new ObjectMapper().createArrayNode();
                boolean b = true;
                onInner.put(columnsOfAnimal.get(0), (Float) getNumberOrNULL(valuesOfPersentOfEating, counter++, b));
                onInner.put(columnsOfAnimal.get(1), (Float) getNumberOrNULL(valuesOfPersentOfEating, counter++, b));
                onInner.put(columnsOfAnimal.get(2), (Float) getNumberOrNULL(valuesOfPersentOfEating, counter++, b));
                onInner.put(columnsOfAnimal.get(3), (Float) getNumberOrNULL(valuesOfPersentOfEating, counter++, b));
                on.put(lines.get(sizeOflines), array1.add(onInner));
                array.add(on);
                sizeOflines++;

            }

        }
    }

    private static <T extends Number> T getNumberOrNULL(List<String> valuesOfPersentOfEating, int counter, boolean floatOrInteger) {
        Object number;
        String stringToNumber = valuesOfPersentOfEating.get(counter).replace(",", ".");
        if (floatOrInteger) {
            try {
                number = Float.parseFloat(stringToNumber);
                return (T) number;
            } catch (Exception e) {
                return null;
            }

        } else {
            try {
                number = Integer.parseInt(stringToNumber);
                return (T) number;

            } catch (Exception e) {
                return null;
            }
        }
    }


    private void getThreeArrays(String[][]
                                        arrays, List<String> columnsOfAnimal, List<String> valuesOfPersentOfEating, List<String> lines) {
        for (int i = 0; i < arrays.length; i++) {
            for (int i1 = 0; i1 < arrays[i].length; i1++) {
                sortArrays(arrays, columnsOfAnimal, valuesOfPersentOfEating, lines, i, i1);
            }
        }
    }


    private static void sortArrays(String[][] arrays, List<String> columnsOfAnimal
            , List<String> valuesOfPersentOfEating
            , List<String> lines
            , int i, int i1) {
        if (arrays[i][i1] != null)
          {
            if (i != 0) {
                if (i1 == 0) {
                    columnsOfAnimal.add(arrays[i][i1]);
                } else {
                    valuesOfPersentOfEating.add(arrays[i][i1]);
                }
            } else {
                lines.add(arrays[i][i1]);
            }
        }
    }

    @Override
    public void translateArraysToEnglish(List<String> lististWithRussiansWord) {
        for (int i = 0; i < lististWithRussiansWord.size(); i++) {
            for (String russianWord : Constants.russianToEnglish.keySet()) {
                if (russianWord.equals(lististWithRussiansWord.get(i))) {
                    lististWithRussiansWord.set(i, Constants.russianToEnglish.get(russianWord));
                }
            }
        }
    }
}
