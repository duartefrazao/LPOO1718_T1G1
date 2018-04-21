package com.groundcontrol.game.view.elements;

import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.model.elements.ElementModel;

import java.util.HashMap;
import java.util.Map;

public class ViewFactory {

    private static Map<ElementModel, ElementView> cache = new HashMap<ElementModel, ElementView>();

    public static ElementView makeView(GroundControl game, ElementModel model){

        if(!cache.containsKey(model)){
            cache.put(model, new PlanetView(game));
        }

        return cache.get(model);

    }

}
