package com.groundcontrol.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;

import java.util.ArrayList;
import java.util.List;

public class GameController implements ContactListener {

    public static final int ARENA_WIDTH = 100;

    public static final int ARENA_HEIGHT = 50;

    private static GameController controller;

    private final World world;

    private float accumulator;

    private GameController(){
        world = new World(new Vector2(0,-2), true);

        List<PlanetModel> planets = GameModel.getInstance().getPlanets();

        for(PlanetModel p : planets) {
            new PlanetController(world, p);
        }


        world.setContactListener(this);
    }
    
    public static GameController getInstance(){
        if(controller == null)
            controller = new GameController();
        return controller;
    }

    public void update(float delta){
        GameModel.getInstance().update(delta);

        float frameTime = Math.min(delta, 0.25f);

        accumulator += frameTime;

        while(accumulator >= 1/60f){
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);


        for(Body body : bodies){

            ((ElementModel) body.getUserData()).setX(body.getPosition().x);
            ((ElementModel) body.getUserData()).setY(body.getPosition().y);
            ((ElementModel) body.getUserData()).setRotation(body.getAngle());

        }


    }

    private void verifyBounds(Body body){
        if (body.getPosition().x < 0)
            body.setTransform(ARENA_WIDTH, body.getPosition().y, body.getAngle());

        if (body.getPosition().y < 0)
            body.setTransform(body.getPosition().x, ARENA_HEIGHT, body.getAngle());

        if (body.getPosition().x > ARENA_WIDTH)
            body.setTransform(0, body.getPosition().y, body.getAngle());

        if (body.getPosition().y > ARENA_HEIGHT)
            body.setTransform(body.getPosition().x, 0, body.getAngle());
    }

    @Override
    public void beginContact(Contact contact) {

        Body A = contact.getFixtureA().getBody();
        Body B = contact.getFixtureB().getBody();

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
