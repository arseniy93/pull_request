package arigin.models.service.jsonservice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

public class ParsingFromSite {
    private final String[][] parsedArraysOfPossobillityOfEating = new String[16][17];
    private final String[][] parsedArrays = new String[17][29];
    private final String[][] parsedArraysOfCharacteristicsOfEntity = new String[17][5];
    private final String linkToSite;

    public ParsingFromSite(String linkToSite) {
        this.linkToSite = linkToSite;
        parsingOfPropabillityOfEntity();
        parsingOfCharacteristicsOfEntity();
    }

    public String[][] getParsedArraysOfPossobillityOfEating() {

        return parsedArraysOfPossobillityOfEating;
    }

    public String[][] getParsedArraysOfCharacteristicsOfEntity() {
        return parsedArraysOfCharacteristicsOfEntity;
    }


    private void parsingOfPropabillityOfEntity() {
        parsedArraysOfPossobillityOfEating[0][0] = null;
        Document docToConnetSite = inputsValuesFromSite();
        Element elementReadTable1;
        elementReadTable1 = docToConnetSite.select("table").first();
        Elements elementsOfTable1 = elementReadTable1.select("tr");
        getOptionsFromEatherTable1(elementsOfTable1, parsedArraysOfPossobillityOfEating);
    }

    private void parsingOfCharacteristicsOfEntity() {
        parsedArrays[0][0] = null;
        Document docToConnetSite = inputsValuesFromSite();
        Element elementReadTable2;
        elementReadTable2 = docToConnetSite.select("table").get(1);
        Elements elementsOfTable2 = elementReadTable2.select("tr");
        for (int i = 0; i < getOptionsFromEatherTable1(elementsOfTable2, parsedArrays).length; i++) {
            for (int i1 = 0; i1 < getOptionsFromEatherTable1(elementsOfTable2, parsedArrays)[i].length; i1++) {
                if (i == 0) {
                    if (i1 == 0) {
                        parsedArraysOfCharacteristicsOfEntity[i][i1] = null;
                    } else if (i1 == 1) {
                        parsedArraysOfCharacteristicsOfEntity[i][i1] = "weight";
                    } else if (i1 == 2) {
                        parsedArraysOfCharacteristicsOfEntity[i][i1] = "maxCoutnOfMovesOnField";
                    } else if (i1 == 3) {
                        parsedArraysOfCharacteristicsOfEntity[i][i1] = "velocity";
                    } else if (i1 == 4) {
                        parsedArraysOfCharacteristicsOfEntity[i][i1] = "eatToFull";
                    }
                } else {
                    parsedArraysOfCharacteristicsOfEntity[i][i1] = getOptionsFromEatherTable1(elementsOfTable2, parsedArrays)[i][i1];
                }
            }
        }
        printToScreen(parsedArraysOfCharacteristicsOfEntity);
    }


    private String[][] getOptionsFromEatherTable1(Elements elementsOfTable, String[][] arraysOfValues) {
        for (int j = 0; j < elementsOfTable.size(); j++) {
            Element elementOfTable1 = elementsOfTable.get(j);
            if (j == 0) {
                int counter = 1;
                for (String s : elementOfTable1.text().split(" ")) {
                    arraysOfValues[j][counter] = s;
                    counter++;
                }
            } else {
                arraysOfValues[j] = elementOfTable1.text().split(" ");
            }
        }
        return arraysOfValues;
    }

    private Document inputsValuesFromSite() {
        Document docToConnetSite;


        try {
            docToConnetSite = Jsoup.connect(linkToSite)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
                    .get();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return docToConnetSite;
    }


    protected void printToScreen(String[][] array) {
        Arrays.stream(array).map(Arrays::toString).forEach(System.out::println);
    }


}


