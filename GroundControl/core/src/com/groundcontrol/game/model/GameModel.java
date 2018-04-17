package com.groundcontrol.game.model;


import com.groundcontrol.game.controller.GameController;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;

import java.util.ArrayList;
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
        planets = new ArrayList<PlanetModel>();
        player = new PlayerModel(350,350,0);

        for(PlanetModel p:planets){

        }
    }

    public static void setInstance(GameModel instance) {
        GameModel.instance = instance;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }

    public List<PlanetModel> getPlanets() {
        return planets;
    }

    public void setPlanets(List<PlanetModel> planets) {
        this.planets = planets;
    }
}
