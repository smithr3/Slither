package com.unimelb18.group16.utils;

import com.badlogic.gdx.math.Vector2;

public class Constants {

    public static final String GAME_NAME = "Slither IO";

    public static final int APP_WIDTH = 2732;
    ;
    public static final int APP_HEIGHT = 2048;
    public static final int CAMERA_DEFAULT_WIDTH = 1080;
    public static final int CAMERA_DEFAULT_HEIGHT = 800;

    public static final float WORLD_TO_SCREEN = 32;

    public static final Vector2 WORLD_GRAVITY = new Vector2(0, -10);

    public static final float GROUND_X = 0;
    public static final float GROUND_Y = 0;
    public static final float GROUND_WIDTH = 25f;
    public static final float GROUND_HEIGHT = 2f;
    public static final float GROUND_DENSITY = 0f;

    public static final float RUNNER_X = 2;
    public static final float RUNNER_Y = GROUND_Y + GROUND_HEIGHT;
    public static final float RUNNER_WIDTH = 1f;
    public static final float RUNNER_HEIGHT = 2f;
    public static final float RUNNER_GRAVITY_SCALE = 3f;
    public static final float RUNNER_DENSITY = 0.5f;
    public static final float RUNNER_DODGE_X = 2f;
    public static final float RUNNER_DODGE_Y = 1.5f;
    public static final Vector2 RUNNER_JUMPING_LINEAR_IMPULSE = new Vector2(0, 13f);
    public static final float RUNNER_HIT_ANGULAR_IMPULSE = 10f;

    public static final float ENEMY_X = 25f;
    public static final float ENEMY_DENSITY = RUNNER_DENSITY;
    public static final float RUNNING_SHORT_ENEMY_Y = 1.5f;
    public static final float RUNNING_LONG_ENEMY_Y = 2f;
    public static final float FLYING_ENEMY_Y = 3f;
    public static final Vector2 ENEMY_LINEAR_VELOCITY = new Vector2(-10f, 0);

    public static final String BACKGROUND_ASSETS_ID = "background";
    public static final String CHANGE_SKIN_ASSETS_ID = "change_skin";

    public static final String GROUND_ASSETS_ID = "ground";
    public static final String RUNNER_RUNNING_ASSETS_ID = "change_skin";
    public static final String RUNNER_DODGING_ASSETS_ID = "change_skin";
    public static final String RUNNER_HIT_ASSETS_ID = "change_skin";
    public static final String SAVE_BUTTON = "save";
    public static final String RUNNING_SMALL_ENEMY_ASSETS_ID = "change_skin";
    public static final String RUNNING_LONG_ENEMY_ASSETS_ID = "change_skin";
    public static final String RUNNING_BIG_ENEMY_ASSETS_ID = "change_skin";
    public static final String RUNNING_WIDE_ENEMY_ASSETS_ID = "change_skin";
    public static final String FLYING_SMALL_ENEMY_ASSETS_ID = "change_skin";
    public static final String FLYING_WIDE_ENEMY_ASSETS_ID = "change_skin";

    public static final String BACKGROUND_IMAGE_PATH = "tile.png";
    public static final String CHANGE_SKIN_IMAGE_PATH = "change_skin.png";
    public static final String GROUND_IMAGE_PATH = "ground.png";
    public static final String SPRITES_ATLAS_PATH = "slither.pack";
    public static final String[] RUNNER_RUNNING_REGION_NAMES = new String[]{"change_skin", "change_skin"};
    public static final String RUNNER_DODGING_REGION_NAME = "ads";
    public static final String RUNNER_HIT_REGION_NAME = "ads";
    public static final String RUNNER_JUMPING_REGION_NAME = "ads";

    public static final String[] RUNNING_SMALL_ENEMY_REGION_NAMES = new String[]{"change_skin", "change_skin"};
    public static final String[] RUNNING_LONG_ENEMY_REGION_NAMES = new String[]{"change_skin", "change_skin"};
    public static final String[] RUNNING_BIG_ENEMY_REGION_NAMES = new String[]{"change_skin", "change_skin"};
    public static final String[] RUNNING_WIDE_ENEMY_REGION_NAMES = new String[]{"change_skin", "change_skin"};
    public static final String[] FLYING_SMALL_ENEMY_REGION_NAMES = new String[]{"change_skin", "change_skin"};
    public static final String[] FLYING_WIDE_ENEMY_REGION_NAMES = new String[]{"change_skin", "change_skin"};

    public static final String SOUND_ON_REGION_NAME = "sound";
    public static final String SOUND_OFF_REGION_NAME = "sound";
    public static final String MUSIC_ON_REGION_NAME = "music";
    public static final String MUSIC_OFF_REGION_NAME = "music";
    public static final String PAUSE_REGION_NAME = "about";
    public static final String PLAY_REGION_NAME = "about";
    public static final String BIG_PLAY_REGION_NAME = "play_ai";
    public static final String MULTI_PLAYER_REGION_NAME = "play_online";
    public static final String ABOUT_REGION_NAME = "about";
    public static final String CLOSE_REGION_NAME = "about";
    public static final String SHARE_REGION_NAME = "share";
    public static final String SETTINGS_REGION_NAME = "settings";

    public static final String LEFT_CHANGE_SKIN_REGION_NAME = "left_arrow";
    public static final String RIGHT_CHANGE_SKIN_REGION_NAME = "right_arrow";

    public static final String TUTORIAL_LEFT_REGION_NAME = "ads";
    public static final String TUTORIAL_RIGHT_REGION_NAME = "ads";
    public static final String TUTORIAL_LEFT_TEXT = "\nTap left to dodge";
    public static final String TUTORIAL_RIGHT_TEXT = "\nTap right to jump";

    public static final String RUNNER_JUMPING_SOUND = "jump.wav";
    public static final String RUNNER_HIT_SOUND = "hit.wav";
    public static final String GAME_MUSIC = "fun_in_a_bottle.mp3";

    public static final String FONT_NAME = "roboto_bold.ttf";

    public static final String ABOUT_TEXT = "Developed by: Anup Nepal, Robert Smith\nSupport Staff: " +
            "Nelson Chen, Abhirup Khanna & Kang Tai";
    public static final String SHARE_MESSAGE_PREFIX = "Check out " + GAME_NAME + " %s";
    public static final String SHARE_TITLE = "Share!";
    public static final String PAUSED_LABEL = "Paused";


    public static final int SPEED_POWER = 1;

    public static final int LONG_POWER = 0;

}