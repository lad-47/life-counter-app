package com.yahoo.lucas.lifecounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class StartActivity extends Activity {
    String player1 = "Player 1";
    String player2 = "Player 2";
    String player3 = "Player 3";
    String player4 = "Player 4";
    int plus = 2;
    int health = 20;
    Integer[] players = {1,2,3,4};
    int numPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        final EditText nameone = (EditText) findViewById(R.id.editText);
        final EditText def = (EditText) findViewById(R.id.editText8);
        final EditText plusval = (EditText) findViewById(R.id.editText2);
        final EditText nametwo = (EditText) findViewById(R.id.editText3);
        final EditText namethree = (EditText) findViewById(R.id.editText4);
        final EditText namefour = (EditText) findViewById(R.id.editText5);
        final TextView two = (TextView) findViewById(R.id.textView4);
        final TextView three = (TextView) findViewById(R.id.textView5);
        final TextView four = (TextView) findViewById(R.id.textView6);
        two.setVisibility(View.GONE);
        nametwo.setVisibility(View.GONE);
        three.setVisibility(View.GONE);
        namethree.setVisibility(View.GONE);
        four.setVisibility(View.GONE);
        namefour.setVisibility(View.GONE);
        Spinner mSpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>
                (this,android.R.layout.simple_spinner_item,players);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numPlayers = (Integer) parent.getItemAtPosition(position);
                if (numPlayers>3){
                    four.setVisibility(View.VISIBLE);
                    namefour.setVisibility(View.VISIBLE);
                }
                if (numPlayers>2) {
                    three.setVisibility(View.VISIBLE);
                    namethree.setVisibility(View.VISIBLE);
                }
                if (numPlayers>1) {
                    two.setVisibility(View.VISIBLE);
                    nametwo.setVisibility(View.VISIBLE);
                }
                if (numPlayers<2){
                    two.setVisibility(View.GONE);
                    nametwo.setVisibility(View.GONE);
                }
                if (numPlayers<3){
                    three.setVisibility(View.GONE);
                    namethree.setVisibility(View.GONE);
                }
                if (numPlayers<4){
                    four.setVisibility(View.GONE);
                    namefour.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numPlayers = 1;
            }
        });
        Button b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameone.getText().length()!=0) {
                    player1 = nameone.getText().toString();
                }
                if (nametwo.getText().length()!=0) {
                    player2 = nametwo.getText().toString();
                }
                if (namethree.getText().length()!=0) {
                    player3 = namethree.getText().toString();
                }
                if (namefour.getText().length()!=0) {
                    player4 = namefour.getText().toString();
                }
                if (plusval.getText().length()!=0) {
                    plus = Integer.parseInt(plusval.getText().toString());
                }
                if (def.getText().length()!=0) {
                    health = Integer.parseInt(def.getText().toString());
                }
                Intent start = new Intent(StartActivity.this,PlayerActivity.class);
                start.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (numPlayers>1){
                    start.putExtra("player2",player2);
                }
                if (numPlayers>2){
                    start.putExtra("player3",player3);
                }
                if (numPlayers>3){
                   start.putExtra("player4",player4);
                }
                start.putExtra("player1",player1);
                start.putExtra("numPlayers",numPlayers);
                start.putExtra("plus",plus);
                start.putExtra("health",health);
                startActivity(start);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
