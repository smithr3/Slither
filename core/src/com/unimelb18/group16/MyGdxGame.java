package com.unimelb18.group16;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.unimelb18.group16.screens.GameScreen;
import com.unimelb18.group16.utils.AssetsManager;
import com.unimelb18.group16.utils.AudioUtils;
import com.unimelb18.group16.utils.GameEventListener;
import com.unimelb18.group16.utils.GameManager;

public class MyGdxGame extends Game {

	public MyGdxGame(GameEventListener listener) {
		GameManager.getInstance().setGameEventListener(listener);
	}

	@Override
	public void create() {
		AssetsManager.loadAssets();
		setScreen(new GameScreen());
	}

	@Override
	public void dispose() {
		super.dispose();
		AudioUtils.dispose();
		AssetsManager.dispose();
	}

}
