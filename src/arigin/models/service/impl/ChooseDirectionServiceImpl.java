package arigin.models.service.impl;

import com.arigin.models.enums.DirectionType;

import java.util.Random;

public class ChooseDirectionServiceImpl {



    public DirectionType chooseDirection(Random random){
        return DirectionType.values()[random.nextInt(1,DirectionType.values().length)];
    }

}
