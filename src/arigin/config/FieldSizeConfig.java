package arigin.config;

import com.arigin.constants.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FieldSizeConfig {

    public int getDaysOfLine() {
        return daysOfLine;
    }

    public void setDaysOfLine(int daysOfLine) {
        this.daysOfLine = daysOfLine;
    }

    public int getMaxStartQualityOfPredators() {
        return maxStartQualityOfPredators;
    }

    public void setMaxStartQualityOfPredators(int maxStartQualityOfPredators) {
        this.maxStartQualityOfPredators = maxStartQualityOfPredators;
    }

    public int getMaxStartQualityOfHerbivores() {
        return maxStartQualityOfHerbivores;
    }

    public void setMaxStartQualityOfHerbivores(int maxStartQualityOfHerbivores) {
        this.maxStartQualityOfHerbivores = maxStartQualityOfHerbivores;
    }

    public int getMaxQualityOfEntity() {
        return maxQualityOfEntity;
    }

    public void setMaxQualityOfEntity(int maxQualityOfEntity) {
        this.maxQualityOfEntity = maxQualityOfEntity;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int daysOfLine;
    private int maxStartQualityOfPredators;
    private int maxStartQualityOfHerbivores;
    private  int maxQualityOfEntity;

    public int getMaxQualityOfPlant() {
        return maxStartQualityOfPlants;
    }

    private  int maxStartQualityOfPlants;




    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    private Map<String, Integer> coordinatesXY() {
        Map<String, Integer> xy = new HashMap<>();
        try {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(Constants.SETTINGS_PROPERTIES)))) {
                bufferedReader.lines().forEach((br) -> xy.put(
                        br.substring(br.indexOf(".") + 1, br.indexOf("="))
                        , Integer.parseInt(br.substring(br.indexOf("=") + 1))));
            }
        } catch (IOException e) {
            System.err.println("The file isn't found, class FieldSizeConfig ");
            throw new RuntimeException(e);
        }
        return xy;
    }

    public FieldSizeConfig() {
        this.width = coordinatesXY().get("wight");
        this.height = coordinatesXY().get("hight");
        this.daysOfLine=coordinatesXY().get("daysOfLine");
        this.maxQualityOfEntity=coordinatesXY().get("maxQualityOfEntity");
        this.maxStartQualityOfPredators=coordinatesXY().get("maxStartQualityOfPredators");
        this.maxStartQualityOfHerbivores=coordinatesXY().get("maxStartQualityOfHerbivores");
        this.maxQualityOfEntity=coordinatesXY().get("maxQualityOfEntity");
        this.maxStartQualityOfPlants=coordinatesXY().get("maxStartQualityOfPlants");
    }

    private int width;
    private int height;
}
