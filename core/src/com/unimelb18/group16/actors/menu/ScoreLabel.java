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

package com.unimelb18.group16.actors.menu;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.unimelb18.group16.utils.AssetsManager;

public class ScoreLabel extends Actor {

    private Rectangle bounds;
    private BitmapFont font;

    private String scoreLength;
    private String scoreRank;

    public void setScoreLength(String scoreLength) {
        this.scoreLength = scoreLength;
    }

    public void setScoreRank(String scoreRank) {
        this.scoreRank = scoreRank;
    }

    public ScoreLabel() {

        scoreLength = "";
        scoreRank = "";
        font = AssetsManager.getSmallestFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        font.draw(batch, "Your Score: " + scoreLength, 50, 70, 100, Align.left, false);
        font.draw(batch, "Your Rank: " + scoreRank, 50, 40, 100, Align.left, false);


    }

}
