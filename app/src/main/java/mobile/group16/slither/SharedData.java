package mobile.group16.slither;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedData {

    private final SharedPreferences mPrefs;

    public SharedData(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getKey(String key) {
        String str = mPrefs.getString(key,"");
        return str;
    }

    public void setValue(String key,String value) {
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(key, value);
        mEditor.apply();
    }

}