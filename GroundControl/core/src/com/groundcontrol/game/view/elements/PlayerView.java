package com.groundcontrol.game.view.elements;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.groundcontrol.game.GroundControl;

public class PlayerView extends ElementView {


    public PlayerView(GroundControl game){
        super(game);
    }


    public Sprite createSprite(GroundControl game) {
        Texture texture = game.getAssetManager().get("player.png");

        return new Sprite(texture, texture.getWidth(), texture.getHeight());
    }

   /* public void update(ElementModel model){
        super.update(model);

        accelarating=((PlayerModel) model).isAccelerating();

        ((PlayerModel)model).setAccelerating(false);
    }

    @Override
    public void draw(SpriteBatch batch){
        stateTime+= Gdx.graphics.getDeltaTime();

        if(accelarating) {
            sprite.setRegion(acceleratingAnimation.getKeyFrame(stateTime, true));
        }else{
            sprite.setRegion(notAccelaratingRegion);
        }

        sprite.draw(batch);
    }*/
}
