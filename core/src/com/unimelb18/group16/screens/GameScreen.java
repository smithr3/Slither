/*
 * Copyright (c) 2014. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.unimelb18.group16.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.unimelb18.group16.stages.GameStage;
import com.unimelb18.group16.utils.Constants;
import com.unimelb18.group16.utils.ParallaxBackground;
import com.unimelb18.group16.utils.ParallaxLayer;


public class GameScreen implements Screen {

    private GameStage stage;

    private Texture imgTexture;

    private ParallaxBackground parallaxBackground;

    private TextureRegion textureRegion;

    public GameScreen() {
        stage = new GameStage();

        imgTexture = new Texture(Gdx.files.internal(Constants.BACKGROUND_IMAGE_PATH));
        imgTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        textureRegion = new TextureRegion(imgTexture);

        parallaxBackground = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(textureRegion,new Vector2(),new Vector2(0, 0)),
        }, 1920, 1080,new Vector2(150,0));
    }

    @Override
    public void render(float delta) {
        //Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        parallaxBackground.render(delta);


        //Update the stage
        stage.draw();
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
