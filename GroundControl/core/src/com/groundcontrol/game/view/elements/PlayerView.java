package com.groundcontrol.game.view.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groundcontrol.game.GroundControl;
import com.groundcontrol.game.model.elements.ElementModel;
import com.groundcontrol.game.model.elements.PlayerModel;

import static com.groundcontrol.game.view.GameView.PIXEL_TO_METER;

public class PlayerView extends ElementView {

    private boolean accelarating;

    private float stateTime=0;

    public PlayerView(GroundControl game){
        super(game);
    }


    public Sprite createSprite(GroundControl game) {
        Texture texture = game.getAssetManager().get("player.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

    public void update(ElementModel model){
        super.update(model);

        accelarating=((PlayerModel) model).isAccelerating();

        ((PlayerModel)model).setAccelerating(false);
    }

    @Override
    public void draw(SpriteBatch batch){
        stateTime+= Gdx.graphics.getDeltaTime();

        /*if(accelarating) {
            sprite.setRegion(acceleratingAnimation.getKeyFrame(stateTime, true));
        }else{
            sprite.setRegion(notAccelaratingRegion);
        }*/

        sprite.draw(batch);
    }
}
