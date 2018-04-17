package com.groundcontrol.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.groundcontrol.game.GroundControl;

public class GameView extends ScreenAdapter {

    private final GroundControl game;

    public final static float PIXEL_TO_METER = 0.02f;



    private final OrthographicCamera camera;

    public GameView(GroundControl game){

        this.game = game;
        loadAssets();

        camera = createCamera();
    }

    private OrthographicCamera createCamera(){

        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        return camera;

    }

    private void loadAssets(){
        this.game.getAssetManager().load("aestroid_dark.png", Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        game.getBatch().begin();
        Texture bad = game.getAssetManager().get("aestroid_dark.png", Texture.class);
        game.getBatch().draw(bad, camera.position.x - (bad.getWidth() /2f), camera.position.y - (bad.getHeight() / 2f));
        game.getBatch().end();

    }

}
