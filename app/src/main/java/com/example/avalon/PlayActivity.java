package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.avalon.domain.Player;

import java.util.List;

public class PlayActivity extends AppCompatActivity {

    List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

    }
}
