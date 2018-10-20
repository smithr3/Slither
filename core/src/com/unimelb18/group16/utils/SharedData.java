package com.unimelb18.group16.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class SharedData {
    public static String getKey(String key) {
        return Gdx.app.getPreferences("SlitherSettings").getString(key, "");
    }

    public static void setValue(String key, String value) {

        Preferences preferences = Gdx.app.getPreferences("SlitherSettings");
        preferences.putString(key, value);
        preferences.flush();
    }

}