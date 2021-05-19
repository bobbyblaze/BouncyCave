package com.e.bouncy.Menu;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.e.bouncy.GamePanel;
import com.e.bouncy.Object.Player;
import com.e.bouncy.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainMenu extends Activity {

    private Player player;
    public GamePanel gamePanel;

    private AdView mAdView;

    public int scrHeight;
    public int scrWidth;
    public int whichPlayer;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main_menu);

        //ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // get a display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();

        //load the resolution into a Point object
        Point resolution = new Point();
        display.getSize(resolution);

        // resolutions of the screen
        this.scrHeight = resolution.y;
        this.scrWidth = resolution.x;

        Button playButton = (Button) findViewById(R.id.playButton);
        Button skins = (Button) findViewById(R.id.skins);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            whichPlayer = extras.getInt("WHICH_PLAYER");
        }

        gamePanel = new GamePanel(this, scrWidth, scrHeight, whichPlayer);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                setContentView(gamePanel);
            }
        });

        skins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skins = new Intent(MainMenu.this, SkinMenu.class);
                startActivity(skins);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePanel.pause();
    }

    // If the Activity is resumed make sure to resume our thread
    @Override
    protected void onResume() {
        super.onResume();
        gamePanel.resume();
    }

}
