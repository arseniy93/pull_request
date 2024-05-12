package arigin.models.service;

import com.arigin.models.abstracts.Entity;

import java.util.Map;

public interface EatService {
    void eat(Map<Map<Entity, Entity>, Integer> eatOrNo);
}
