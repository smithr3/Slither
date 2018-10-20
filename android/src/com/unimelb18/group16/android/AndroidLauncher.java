package com.unimelb18.group16.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.games.basegameutils.GameHelper;
import com.unimelb18.group16.BuildConfig;
import com.unimelb18.group16.MyGdxGame;
import com.unimelb18.group16.R;
import com.unimelb18.group16.utils.Constants;
import com.unimelb18.group16.utils.GameEventListener;

public class AndroidLauncher extends AndroidApplication implements
        GameEventListener {

    private static String SAVED_LEADERBOARD_REQUESTED = "SAVED_LEADERBOARD_REQUESTED";
    private static String SAVED_ACHIEVEMENTS_REQUESTED = "SAVED_ACHIEVEMENTS_REQUESTED";

    private GameHelper gameHelper;

    private AdView mAdView;
    private boolean mLeaderboardRequested;
    private boolean mAchievementsRequested;
    AdRequest request;
    private InterstitialAd mInterstitialAd;
    Button addButton;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the layout
        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

//        MobileAds.initialize(this, "ca-app-pub-1332753988296742~6854006287");


        // Game view
        View gameView = initializeForView(new MyGdxGame(this), config);
        layout.addView(gameView);


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

        addButton = new Button(getApplicationContext());
        addButton.setText("");
        addButton.setVisibility(View.INVISIBLE);


        mAdView = createAdView();
        mAdView.loadAd(new AdRequest.Builder().build());
        //  request = createAdRequest();
        Log.i("Test Device", request.isTestDevice(getContext()) + "");
        //   mAdView.loadAd(request);

        layout.addView(mAdView);

        //  layout.setBackgroundColor(Color.YELLOW);

        layout.addView(addButton);


        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 234:

                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                        break;
                }


            }
        };


        layout.setBackgroundColor(Color.YELLOW);
        setContentView(layout);


        //   mAdView.setVisibility(View.VISIBLE);

        //   gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        //   gameHelper.setup(this);
        //    gameHelper.setMaxAutoSignInAttempts(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //   gameHelper.onStart(this);
        //GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //     gameHelper.onStop();
        //  GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //   gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_LEADERBOARD_REQUESTED, mLeaderboardRequested);
        outState.putBoolean(SAVED_ACHIEVEMENTS_REQUESTED, mAchievementsRequested);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mLeaderboardRequested = savedInstanceState.getBoolean(SAVED_LEADERBOARD_REQUESTED, false);
        mAchievementsRequested = savedInstanceState.getBoolean(SAVED_ACHIEVEMENTS_REQUESTED, false);
    }

    private AdRequest createAdRequest() {
        return new AdRequest.Builder()
                .addTestDevice("F73318D6444F7D56A4B55176FC08F85B")  // An example device ID
                .build();
    }

    private AdView createAdView() {
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        return adView;
    }

    private RelativeLayout.LayoutParams getAdParams() {
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        return adParams;
    }

//    @Override
//    public void onSignInFailed() {
//        // handle sign-in failure (e.g. show Sign In button)
//        mLeaderboardRequested = false;
//        mAchievementsRequested = false;
//    }
//
//    @Override
//    public void onSignInSucceeded() {
//        // handle sign-in success
//        if (GameManager.getInstance().hasSavedMaxScore()) {
//            GameManager.getInstance().submitSavedMaxScore();
//        }
//
//        if (mLeaderboardRequested) {
//            displayLeaderboard();
//            mLeaderboardRequested = false;
//        }
//
//        if (mAchievementsRequested) {
//            displayAchievements();
//            mAchievementsRequested = false;
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void displayAd() {
        //addButton.callOnClick();

        // ------  For AdMob ------
        Message message = mHandler.obtainMessage(234, null);
        message.sendToTarget();

        //   mAdView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAd() {
        mAdView.setVisibility(View.GONE);
    }

    @Override
    public void submitScore(int score) {
//        if (gameHelper.isSignedIn()) {
//            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
//                    getString(R.string.leaderboard_high_scores), score);
//        } else {
//            GameManager.getInstance().saveScore(score);
//        }
    }

    @Override
    public void displayLeaderboard() {
//        if (gameHelper.isSignedIn()) {
//            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
//                    getString(R.string.leaderboard_high_scores)), 24);
//        } else {
//            gameHelper.beginUserInitiatedSignIn();
//            mLeaderboardRequested = true;
//        }
    }

    @Override
    public void displayAchievements() {
//        if (gameHelper.isSignedIn()) {
//            startActivityForResult(
//                    Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 25);
//        } else {
//            gameHelper.beginUserInitiatedSignIn();
//            mAchievementsRequested = true;
//        }
    }

    @Override
    public void share(String myScore) {
        String url = String.format("I scored " + myScore + " in : " + BuildConfig.APPLICATION_ID);
        String message = String.format(Constants.SHARE_MESSAGE_PREFIX, url);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, Constants.SHARE_TITLE));
    }

    @Override
    public void unlockAchievement(String id) {
//        if (gameHelper.isSignedIn()) {
//            Games.Achievements.unlock(gameHelper.getApiClient(), id);
//            GameManager.getInstance().setAchievementUnlocked(id);
//        }
    }

    @Override
    public void incrementAchievement(String id, int steps) {
//        if (gameHelper.isSignedIn()) {
//            Games.Achievements.increment(gameHelper.getApiClient(), id, steps);
//            GameManager.getInstance().incrementAchievementCount(id, steps);
//        }
    }

    @Override
    public String getGettingStartedAchievementId() {
        return getString(R.string.achievement_getting_started);
    }

    @Override
    public String getLikeARoverAchievementId() {
        return getString(R.string.achievement_like_a_rover);
    }

    @Override
    public String getSpiritAchievementId() {
        return getString(R.string.achievement_spirit);
    }

    @Override
    public String getCuriosityAchievementId() {
        return getString(R.string.achievement_curiosity);
    }

    @Override
    public String get5kClubAchievementId() {
        return getString(R.string.achievement_5k_club);
    }

    @Override
    public String get10kClubAchievementId() {
        return getString(R.string.achievement_10k_club);
    }

    @Override
    public String get25kClubAchievementId() {
        return getString(R.string.achievement_25k_club);
    }

    @Override
    public String get50kClubAchievementId() {
        return getString(R.string.achievement_50k_club);
    }

    @Override
    public String get10JumpStreetAchievementId() {
        return getString(R.string.achievement_10_jump_street);
    }

    @Override
    public String get100JumpStreetAchievementId() {
        return getString(R.string.achievement_100_jump_street);
    }

    @Override
    public String get500JumpStreetAchievementId() {
        return getString(R.string.achievement_500_jump_street);
    }

    @Override
    public String getSavedValue(String key) {
        return null;
    }

    @Override
    public void saveValue(String key, String value) {

    }

    private String getAdMobUnitId() {
        return getString(R.string.ad_unit_id);
    }

}