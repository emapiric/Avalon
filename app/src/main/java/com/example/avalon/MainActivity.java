package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToCreateGameActivity(View view) {
        startActivity(new Intent(MainActivity.this,CreateGameActivity.class));
    }
    public void goToJoinGameActivity(View view) {
        startActivity(new Intent(MainActivity.this,JoinGameActivity.class));
    }
}
