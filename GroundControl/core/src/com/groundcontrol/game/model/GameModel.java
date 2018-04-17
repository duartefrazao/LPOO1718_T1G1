package com.groundcontrol.game.model;


import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;

import java.util.List;

public class GameModel {

    /**
     * Singleton instance of game model
     */
    private static GameModel instance;

    /**
     * Player character
     */
    private PlayerModel player;

    /**
     * Returns singleton
     */
    public static GameModel getInstance(){
        if (instance == null)  instance = new GameModel();
        return instance;
    }

    /**
     * Planets
     */
    private List<PlanetModel> planets;

    private GameModel(){
        
    }
}
