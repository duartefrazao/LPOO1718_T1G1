package com.groundcontrol.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.groundcontrol.game.view.GameView;

public class GroundControl extends Game {
	private SpriteBatch batch;
	private AssetManager assetManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		startGame();
	}

	private void startGame(){
		setScreen(new GameView(this));
	}

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

}
