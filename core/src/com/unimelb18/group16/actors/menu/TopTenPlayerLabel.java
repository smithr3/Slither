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
import com.unimelb18.group16.utils.Constants;

public class TopTenPlayerLabel extends Actor {

    private Rectangle bounds;
    private BitmapFont font;

    public void setTopTenPeople(String topTenPeople) {
        this.topTenPeople = topTenPeople;
    }

    private String topTenPeople = "";

    public TopTenPlayerLabel() {

        font = AssetsManager.getSmallestFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        font.draw(batch, topTenPeople, Constants.APP_WIDTH - 200, Constants.APP_HEIGHT - 100, 100, Align.left, false);


    }

}
