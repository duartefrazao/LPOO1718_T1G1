package com.groundcontrol.game.view.elements;

import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.model.elements.ElementModel;

import java.util.HashMap;
import java.util.Map;

import static com.groundcontrol.game.model.elements.ElementModel.ModelType.PLANET;
import static com.groundcontrol.game.model.elements.ElementModel.ModelType.PLAYER;

public class ViewFactory {

    private static Map<ElementModel, ElementView> cache = new HashMap<ElementModel, ElementView>();

    public static ElementView makeView(GroundControl game, ElementModel model){

        if(!cache.containsKey(model)){
            if(model.getType()== PLANET)
                cache.put(model, new PlanetView(game));
            if(model.getType()==PLAYER)
                cache.put(model, new PlayerView(game));
        }

        return cache.get(model);

    }

}
