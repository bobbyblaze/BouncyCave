package com.e.bouncy.Menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.e.bouncy.GamePanel;
import com.e.bouncy.R;
public class GameOver extends Activity {

    GamePanel gamePanel;

    public Button restart;

    public int scrHeight;
    public int scrWidth;
    public int whichPlayer;

    public int Score;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        // get a display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        //load the resolution into a Point object
        Point resolution = new Point();
        display.getSize(resolution);

        // resolutions of the screen
        this.scrHeight = resolution.y;
        this.scrWidth = resolution.x;

        gamePanel = new GamePanel(this, scrWidth, scrHeight, whichPlayer);

        score = gamePanel.Score;

        Score = getResources().getString(R.strings.Score);
        StringBuilder sb = new StringBuilder(Score);
        sb.replace(0, 1, score.toString());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            whichPlayer = extras.getInt("WHICH_PLAYER");
        }

        restart = (Button)findViewById(R.id.restart);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(gamePanel);
            }
        });
    }
}
