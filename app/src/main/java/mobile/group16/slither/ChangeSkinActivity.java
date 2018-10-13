package mobile.group16.slither;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChangeSkinActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton ibLeftChangeSkin;
    ImageButton ibRightChangeSkin;
    ChangeSkinView changeSkinView;

    SharedData sharedData;

    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_skin);

        ibLeftChangeSkin = (ImageButton) findViewById(R.id.ibLeftChangeSkin);
        ibRightChangeSkin = (ImageButton) findViewById(R.id.ibRightChangeSkin);
        buttonSave = findViewById(R.id.buttonSave);

        changeSkinView = findViewById(R.id.changeSkinView);

        ibLeftChangeSkin.setOnClickListener(this);
        ibRightChangeSkin.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sharedData = new SharedData(getApplicationContext());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ibLeftChangeSkin:
                changeSkinView.changeColor(Constants.LeftChangeSkin);
                changeSkinView.invalidate();
                break;
            case R.id.ibRightChangeSkin:
                changeSkinView.changeColor(Constants.RightChangeSkin);
                changeSkinView.invalidate();
                break;
            case R.id.buttonSave:
                sharedData.setValue("currentSkin", Integer.toString(changeSkinView.getCurrentColor()));
                startActivity(new Intent(this, MainActivity.class));
                break;

        }

        //starting game activity

    }
}
