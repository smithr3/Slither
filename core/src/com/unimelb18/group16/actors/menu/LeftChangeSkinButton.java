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

import com.badlogic.gdx.math.Rectangle;
import com.unimelb18.group16.enums.GameState;
import com.unimelb18.group16.utils.Constants;
import com.unimelb18.group16.utils.GameManager;

public class LeftChangeSkinButton extends GameButton {

    public interface LeftChangeSkinButtonButtonListener {
        public void onLeftChangeSkin();
    }

    private LeftChangeSkinButtonButtonListener listener;

    public LeftChangeSkinButton(Rectangle bounds, LeftChangeSkinButtonButtonListener listener) {
        super(bounds);
        this.listener = listener;
    }

    @Override
    protected String getRegionName() {
        return Constants.LEFT_CHANGE_SKIN_REGION_NAME;
    }

    @Override
    public void touched() {
        listener.onLeftChangeSkin();
    }

}
