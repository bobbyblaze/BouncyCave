package com.e.bouncy.Menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.e.bouncy.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SkinMenu extends Activity {

    Button czech, slovak, smilie, arrow, miner, cat, yoda;

    public Button collections;

    public int selected;
    public int notSelected;

    public int whichPlayer = 0;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_skins);

        //ads
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        selected = R.drawable.outline_player_selected;
        notSelected = R.drawable.outline_player;

        arrow = (Button)findViewById(R.id.backArrow);
        smilie = (Button)findViewById(R.id.smilieButton);

        miner= (Button)findViewById(R.id.minerButton);
        czech = (Button)findViewById(R.id.czechButton);
        slovak = (Button)findViewById(R.id.skButton);
        cat = (Button)findViewById(R.id.catButton);
        yoda = (Button)findViewById(R.id.yodaButton);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent skinsBack = new Intent(SkinMenu.this, MainMenu.class);
                skinsBack.putExtra("WHICH_PLAYER", whichPlayer);
                startActivity(skinsBack);
            }
        });

        miner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miner.setBackgroundResource(selected);
                czech.setBackgroundResource(notSelected);
                smilie.setBackgroundResource(notSelected);
                cat.setBackgroundResource(notSelected);
                yoda.setBackgroundResource(notSelected);
                slovak.setBackgroundResource(notSelected);
                whichPlayer = 0;
                System.out.println("miner");
            }
        });

        smilie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smilie.setBackgroundResource(selected);
                miner.setBackgroundResource(notSelected);
                czech.setBackgroundResource(notSelected);
                cat.setBackgroundResource(notSelected);
                yoda.setBackgroundResource(notSelected);
                slovak.setBackgroundResource(notSelected);
                whichPlayer = 1;
                System.out.println("smilie");
            }
        });

        czech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                czech.setBackgroundResource(selected);
                smilie.setBackgroundResource(notSelected);
                miner.setBackgroundResource(notSelected);
                cat.setBackgroundResource(notSelected);
                yoda.setBackgroundResource(notSelected);
                slovak.setBackgroundResource(notSelected);
                whichPlayer = 2;
                System.out.println("czech");
            }
        });

        slovak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slovak.setBackgroundResource(selected);
                smilie.setBackgroundResource(notSelected);
                czech.setBackgroundResource(notSelected);
                yoda.setBackgroundResource(notSelected);
                miner.setBackgroundResource(notSelected);
                cat.setBackgroundResource(notSelected);
                whichPlayer = 3;
                System.out.println("slovak");
            }
        });

        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cat.setBackgroundResource(selected);
                czech.setBackgroundResource(notSelected);
                slovak.setBackgroundResource(notSelected);
                smilie.setBackgroundResource(notSelected);
                miner.setBackgroundResource(notSelected);
                yoda.setBackgroundResource(notSelected);
                whichPlayer = 4;
                System.out.println("cat");
            }
        });

        yoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yoda.setBackgroundResource(selected);
                czech.setBackgroundResource(notSelected);
                slovak.setBackgroundResource(notSelected);
                smilie.setBackgroundResource(notSelected);
                miner.setBackgroundResource(notSelected);
                cat.setBackgroundResource(notSelected);
                whichPlayer = 5;
                System.out.println("yoda");
            }
        });
    }

    public int getWhichPlayer(){
        return this.whichPlayer;
    }
}
