package mobile.group16.slither;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button buttonPlay;
    private Button buttonScore;

    //code added:
    private ImageButton buttonSettingPic;

    private ImageButton changeSkin;
    private ImageButton ibSetting;
    private Button buttonShareSocialMedia;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        /* getting the button */
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonScore = findViewById(R.id.buttonScore);
        buttonShareSocialMedia = findViewById(R.id.buttonShareSocialMedia);
        changeSkin = findViewById(R.id.changeSkin);
        ibSetting = findViewById(R.id.ibSetting);


        buttonPlay.setOnClickListener(this);
        changeSkin.setOnClickListener(this);
        ibSetting.setOnClickListener(this);
        buttonScore.setOnClickListener(this);
        buttonShareSocialMedia.setOnClickListener(this);

        MobileAds.initialize(this, "ca-app-pub-1332753988296742~6854006287");
        AdRequest request = new AdRequest.Builder()
                .addTestDevice("F73318D6444F7D56A4B55176FC08F85B")  // An example device ID
                .build();

        Log.i("Test Device =>", request.isTestDevice(getApplicationContext()) + "");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonPlay:
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.changeSkin:
                startActivity(new Intent(this, ChangeSkinActivity.class));
                break;
            case R.id.ibSetting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.buttonShareSocialMedia:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);

                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "I have achieved a high score in game : 2000");
                startActivity(Intent.createChooser(intent, "Share"));
                break;
            case R.id.buttonScore:
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
        }

        //starting game activity

    }
}
