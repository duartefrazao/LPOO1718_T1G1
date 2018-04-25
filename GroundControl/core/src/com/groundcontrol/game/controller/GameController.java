package com.groundcontrol.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.groundcontrol.game.controller.elements.BigPlanetController;
import com.groundcontrol.game.controller.elements.ElementController;
import com.groundcontrol.game.controller.elements.PlanetController;
import com.groundcontrol.game.controller.elements.PlayerController;
import com.groundcontrol.game.model.GameModel;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlanetModel;
import com.groundcontrol.game.model.elements.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class GameController implements ContactListener {

    public static final int ARENA_WIDTH = 100;

    public static final int ARENA_HEIGHT = 50;

    private static GameController controller;

    private final World world;

    private float accumulator;

    private static float VELOCITY_LIMIT = 10f;
    private final  PlayerController playerController;
    private ArrayList<ElementController> planetControllers = new ArrayList<ElementController>();


    private static float ANGULAR_LIMIT = 0.02f;

    private Vector2 planetForce = new Vector2(0, 0);

    public void setPlanetForce(float x, float y) {
        this.planetForce.x = x * 10;
        this.planetForce.y = y * 10;
    }

    private GameController(){
        world = new World(new Vector2(0, 0), true);

        List<PlanetModel> planets = GameModel.getInstance().getPlanets();

        ElementController planetC;

        playerController = new PlayerController(world,GameModel.getInstance().getPlayer());

        for (PlanetModel p : planets) {

            if (p.getSize() == PlanetModel.PlanetSize.MEDIUM) {

                planetC = new PlanetController(world, p);
                planetControllers.add(planetC);
            }
            else {
                planetC = new BigPlanetController(world, p);
                planetControllers.add(planetC);
            }
        }


        world.setContactListener(this);


    }


    private void applyGravityToPlanets() {
        Array<Body> bodies = new Array<Body>();

        world.getBodies(bodies);

        for (Body body : bodies) {
            body.applyForceToCenter(planetForce, true);
        }
    }

    private void limitVelocities(Body body) {


        float x = body.getLinearVelocity().x;

        if (x > VELOCITY_LIMIT)
            x = VELOCITY_LIMIT;
        else if (x < (-VELOCITY_LIMIT))
            x = -VELOCITY_LIMIT;

        float y = body.getLinearVelocity().y;

        if (y > VELOCITY_LIMIT)
            y = VELOCITY_LIMIT;
        else if (y < (-VELOCITY_LIMIT))
            y = -VELOCITY_LIMIT;


        float omega = body.getAngularVelocity();

        if (omega > ANGULAR_LIMIT)
            omega = ANGULAR_LIMIT;
        else if (omega < (-ANGULAR_LIMIT))
            omega = -ANGULAR_LIMIT;

        body.setLinearVelocity(x, y);
        body.setAngularVelocity(omega);
    }

    public static GameController getInstance() {
        if (controller == null)
            controller = new GameController();
        return controller;
    }

    public void update(float delta) {
        GameModel.getInstance().update(delta);

        float frameTime = Math.min(delta, 0.25f);

        accumulator += frameTime;

        applyGravityToPlanets();

        while (accumulator >= 1 / 60f) {
            world.step(1 / 60f, 6, 2);
            accumulator -= 1 / 60f;
        }

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);


        getPlayerRotation();

        for (Body body : bodies) {
            verifyBounds(body);
            limitVelocities(body);
            ((ElementModel) body.getUserData()).setX(body.getPosition().x);
            ((ElementModel) body.getUserData()).setY(body.getPosition().y);
            ((ElementModel) body.getUserData()).setRotation(body.getAngle());

        }


    }

    private void getPlayerRotation() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        Float distance=0f;

        Float angle=0f;
        for (ElementController planet:planetControllers){
            distance= abs(planet.getX()- playerController.getX());
            distance+=abs(planet.getY()- playerController.getY());

            if(distance <60){
                System.out.println(playerController.getAngle());
                ((PlayerModel) playerController.getUserData()).setRotation(20);
                //PlayerModel p =  ((PlayerModel) playerController.getUserData());
            }

          //  System.out.println(playerController.getAngle()-);

           // velocity.set(planet.getX() - playerController.getX(),planet.getY() - playerController.getY()).nor().scl(playerController.dst(planet.getX(), planet.getY())));
            //System.out.println(distance);

        }

    }



    private void verifyBounds(Body body) {
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

    public void moveLeft(float delta) {
        playerController.applyForceToCenter(-10,0,true);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);


    }

    public void moveRight(float delta) {
        playerController.applyForceToCenter(10,0,true);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);
    }


    public void moveUp(float delta) {
        playerController.applyForceToCenter(0,10,true);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);
    }

    public void moveDown(float delta) {
        playerController.applyForceToCenter(10,-10,true);
        ((PlayerModel) playerController.getUserData()).setAccelerating(true);
    }
}
