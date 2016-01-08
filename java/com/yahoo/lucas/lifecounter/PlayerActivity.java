package com.yahoo.lucas.lifecounter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PlayerActivity extends Activity {
    int currentPlayer;
    int p1h;
    int p2h;
    int p3h;
    int p4h;
    String player1;
    String player2;
    String player3;
    String player4;
    int[] players;
    Chronometer chron;
    boolean time;
    boolean timerOn;
    long runningTime;
    Button timer;
    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //initializing
        chron = (Chronometer) findViewById(R.id.chronometer);
        Intent data = getIntent();
        player1 = data.getStringExtra("player1");
        final int plus = data.getIntExtra("plus",2);
        final int numPlayers = data.getIntExtra("numPlayers",1);
        final int health = data.getIntExtra("health",20);
        players = new int[numPlayers];
        for (int i=0;i<numPlayers;i++){
            players[i] = i+1;
        }
        p1h = health;
        currentPlayer = 1;
        if (numPlayers>1){
            player2 = data.getStringExtra("player2");
            p2h = health;
        }
        if (numPlayers>2){
            player3 = data.getStringExtra("player3");
            p3h = health;
        }
        if (numPlayers>3){
            player4 = data.getStringExtra("player4");
            p4h = health;
        }
        time = false;
        timerOn = false;
        runningTime = 0;
        final TextView t = (TextView) findViewById(R.id.textView4);
        final TextView name = (TextView) findViewById(R.id.textView5);
        Button plusbutton = (Button) findViewById(R.id.button3);
        Button minusbutton = (Button) findViewById(R.id.button4);
        Button bigplus = (Button) findViewById(R.id.buttonplus);
        Button bigminus = (Button) findViewById(R.id.buttonminus);
        Button right = (Button) findViewById(R.id.buttonright);
        Button left = (Button) findViewById(R.id.buttonleft);
        timer = (Button) findViewById(R.id.buttontimer);
        reset = (Button) findViewById(R.id.buttonreset);
        //set visibilities
        chron.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);
        if (numPlayers==1){
            right.setVisibility(View.INVISIBLE);
            left.setVisibility(View.INVISIBLE);
        }
        //set fonts
        String fontPath = "fonts/BLKCHCRY.TTF";
        Typeface tf = Typeface.createFromAsset(getAssets(),fontPath);
        t.setTypeface(tf);
        chron.setTypeface(tf);
        reset.setTypeface(tf);
        name.setTypeface(tf);
        timer.setTypeface(tf);
        plusbutton.setTypeface(tf);
        minusbutton.setTypeface(tf);
        right.setTypeface(tf);
        left.setTypeface(tf);
        bigplus.setTypeface(tf);
        bigminus.setTypeface(tf);
        //set texts
        name.setText(player1);
        changeText(t, p1h);
        reset.setText("Reset");
        timer.setText("Timer");
        bigplus.setText("+"+Integer.toString(plus));
        bigminus.setText("-" + Integer.toString(plus));
        //set button clicks
        plusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHealth(currentPlayer,1);
            }
        });
        minusbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHealth(currentPlayer, -1);
            }
        });
        bigplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHealth(currentPlayer, plus);
            }
        });
        bigminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddHealth(currentPlayer,plus*-1);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = currentPlayer;
                if (cur!=numPlayers){
                    currentPlayer = players[cur];
                }
                else{
                    currentPlayer = players[0];
                }
                UpdatePlayer(currentPlayer);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur = currentPlayer;
                if (cur!=1) {
                    currentPlayer = players[cur-2];
                }
                else{
                    currentPlayer = players[numPlayers-1];
                }
                UpdatePlayer(currentPlayer);
            }
        });
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!timerOn){
                    chron.setVisibility(View.VISIBLE);
                    reset.setVisibility(View.VISIBLE);
                }
                if(!time) {
                    chron.setBase(SystemClock.elapsedRealtime() + runningTime);
                    chron.start();
                    timer.setText("Pause");
                    time = true;
                }else{
                    runningTime = chron.getBase() - SystemClock.elapsedRealtime();
                    chron.stop();
                    timer.setText("Resume");
                    time = false;
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerOn = false;
                time = false;
                chron.stop();
                runningTime = 0;
                timer.setText("Timer");
                chron.setVisibility(View.GONE);
                reset.setVisibility(View.GONE);
            }
        });
        //ads
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest testRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(testRequest);
    }

    public void AddHealth(int player, int val){
        final TextView t = (TextView) findViewById(R.id.textView4);
        if (player==1) {
            p1h+=val;
            changeText(t, p1h);
        }
        else if (player==2) {
            p2h+=val;
            changeText(t, p2h);
        }
        else if (player==3) {
            p3h+=val;
            changeText(t, p3h);
        }
        else if (player==4) {
            p4h+=val;
            changeText(t,p4h);
        }
    }

    public void UpdatePlayer(int player){
        final TextView t = (TextView) findViewById(R.id.textView4);
        final TextView name = (TextView) findViewById(R.id.textView5);
        if (player==1) {
            changeText(t,p1h);
            name.setText(player1);
        }
        else if (player==2) {
            changeText(t,p2h);
            name.setText(player2);
        }
        else if (player==3) {
            changeText(t,p3h);
            name.setText(player3);
        }
        else if (player==4) {
            changeText(t,p4h);
            name.setText(player4);
        }
    }

    public void changeText(TextView t, int val){
        float factor = (float) Math.floor(Math.log10(Math.abs(val)));
        float size;
        if (val!=0) {
            size = 90 - factor * 10;
        }
        else{
            size = 90;
        }
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        t.setText(Integer.toString(val));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
