package arigin.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private Constants() {
    }

    public static final String RESOURCES_CHARACTERISTICS_OF_ENTITY_JSON = "resources/characteristicsOfEntity.json";
    public static final String LINK_1_OF_SITE = "https://javarush.com/quests/lectures/jru.module2.lecture44";
    public static final String PATH_TOPOSSIBILLITY_OF_EATING = "resources/possibiliteOfEating.json";
    public static final String SETTINGS_PROPERTIES = "resources/settings.properties";
    public static final Map<String, String> russianToEnglish = new HashMap<>();

    static {
        russianToEnglish.put("Волк", "Wolf");
        russianToEnglish.put("Удав", "BoaConstrictor");
        russianToEnglish.put("Лиса", "Fox");
        russianToEnglish.put("Медведь", "Bear");
        russianToEnglish.put("Орел", "Eagle");
        russianToEnglish.put("Лошадь", "Horse");
        russianToEnglish.put("Олень", "Deer");
        russianToEnglish.put("Кролик", "Rabbit");
        russianToEnglish.put("Мышь", "Mouse");
        russianToEnglish.put("Коза", "Goat");
        russianToEnglish.put("Овца", "Sheep");
        russianToEnglish.put("Кабан", "WildBoar");
        russianToEnglish.put("Буйвол", "Buffalo");
        russianToEnglish.put("Утка", "Duck");
        russianToEnglish.put("Гусеница", "Caterpillar");
        russianToEnglish.put("Растения", "Plants");
    }

}


