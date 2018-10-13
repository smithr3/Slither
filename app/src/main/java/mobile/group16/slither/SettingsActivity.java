package mobile.group16.slither;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    SharedData sharedData;

    RadioButton radioArrow;
    RadioButton radioNormal;
    RadioButton radioJoystick;
    Button buttonSave;

    String currentGameMode = "arrow";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        radioArrow = findViewById(R.id.radioArrow);
        radioNormal = findViewById(R.id.radioNormal);
        radioJoystick = findViewById(R.id.radioJoystick);
        buttonSave = findViewById(R.id.buttonSave);

        radioArrow.setOnClickListener(this);
        radioNormal.setOnClickListener(this);
        radioJoystick.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        sharedData = new SharedData(getApplicationContext());

        String gameMode = sharedData.getKey("gameMode");

        if (gameMode != null && !gameMode.equals("")) {

            currentGameMode = gameMode;

            switch (gameMode) {
                case "arrow":
                    radioArrow.setChecked(true);
                    radioNormal.setChecked(false);
                    radioJoystick.setChecked(false);
                    break;
                case "normal":
                    radioArrow.setChecked(false);
                    radioNormal.setChecked(true);
                    radioJoystick.setChecked(false);
                    break;
                case "joystick":
                    radioArrow.setChecked(false);
                    radioNormal.setChecked(false);
                    radioJoystick.setChecked(true);
                    break;
            }
        } else {
            radioArrow.setChecked(true);
            radioNormal.setChecked(false);
            radioJoystick.setChecked(false);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioArrow:
                radioNormal.setChecked(false);
                radioJoystick.setChecked(false);
                currentGameMode = "arrow";
                break;
            case R.id.radioNormal:
                radioJoystick.setChecked(false);
                radioArrow.setChecked(false);
                currentGameMode = "normal";
                break;
            case R.id.radioJoystick:
                radioNormal.setChecked(false);
                radioArrow.setChecked(false);
                currentGameMode = "joystick";
                break;
            case R.id.buttonSave:
                sharedData.setValue("gameMode", currentGameMode);
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
