package mobile.group16.slither;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        // save display size in point ojbect
        Point size = new Point();
        display.getSize(size);

        gameView = new GameView(this, size.x, size.y);

        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        // pausing the game when activity is paused
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        // running the game when activity is resumed
        super.onResume();
        gameView.resume();
    }
}