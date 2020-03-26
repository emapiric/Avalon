package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WaitActivity extends AppCompatActivity {

    TextView hostname;
    TextView  username;
    Button btnStart;
    ListView listView;
    ArrayList<String> playerNames;
    ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        hostname = findViewById(R.id.tv_wait_hostname);
        username = findViewById(R.id.tv_wait_username);
        listView = findViewById(android.R.id.list);
        btnStart = findViewById(R.id.btn_wait_start);
        btnStart.setVisibility(View.INVISIBLE);


        //HARDCODE ZA POPUNJAVANJE LISTE
        playerNames  = new ArrayList<String>();
        playerNames.add("Player1");
        playerNames.add("Player1");
        playerNames.add("Player1");
        playerNames.add("Player1");
        playerNames.add("Player1");
        //ZATIM TREBA UCITATI HOSTNAME I USERNAME OD SERVERA ISTO
        hostname.setText("HOSTNAME");
        username.setText("USERNAME");
        //OVU METODU TREBA POZIVATI DOKLE GOD STIZU PODACI OD SERVERA
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerNames);
        listView.setAdapter(adapter);

        //KAD SE UCITA SVE OD SERVERA PREKINE SE LOADING I ENABLEUJE SE DUGME I MENJA MU SE POZADINA MALO ESTETIKE I TI FAZONI
        //findViewById(R.id.loadingBar).setVisibility(View.GONE);

    }

    public void startGame(View view) {
        startActivity(new Intent(WaitActivity.this,PlayActivity.class));
    }
}
