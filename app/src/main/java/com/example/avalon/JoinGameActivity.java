package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class JoinGameActivity extends AppCompatActivity {
    EditText edName;
    EditText edCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        edName = findViewById(R.id.ed_joingame_name);
        edCode = findViewById(R.id.ed_joingame_code);
    }

    public void joinGame(View view) {
        String name = edName.getText().toString();
        String code = edCode.getText().toString();
    }
}
