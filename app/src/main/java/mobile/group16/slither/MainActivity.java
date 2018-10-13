package mobile.group16.slither;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button buttonPlay;

    //code added:
    private ImageButton buttonSettingPic;

    private ImageButton changeSkin;
    private ImageButton ibSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //getting the button
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        changeSkin = (ImageButton) findViewById(R.id.changeSkin);
        ibSetting = (ImageButton) findViewById(R.id.ibSetting);
        buttonPlay.setOnClickListener(this);
        changeSkin.setOnClickListener(this);
        ibSetting.setOnClickListener(this);

        // can't find buttonSettingPic
//        buttonSettingPic = (ImageButton) findViewById(R.id.buttonSettingPic);


        //go to settings menu once pressed
//        buttonSettingPic.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
//                startActivity(intent);
//            }
//        });

        //go to games once pressed
//        buttonPlay.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                Intent intent = new Intent(MainActivity.this,GameActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.buttonPlay:
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.changeSkin:
                startActivity(new Intent(this, ChangeSkinActivity.class));
                break;
            case R.id.ibSetting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }

        //starting game activity

    }
}
