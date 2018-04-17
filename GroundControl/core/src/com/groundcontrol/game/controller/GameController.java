package com.groundcontrol.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameController implements ContactListener {

    private static GameController controller;

    private final World world;

    private GameController(){
        world = new World(new Vector2(0,0), true);

        world.setContactListener(this);
    }
    
    public static GameController getInstance(){
        if(controller == null)
            controller = new GameController();
        return controller;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
