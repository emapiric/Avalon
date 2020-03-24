package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avalon.domain.Player;
import com.google.gson.Gson;

public class JoinGameActivity extends AppCompatActivity {
    EditText edName;
    EditText edCode;
    Player player;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
        edName = findViewById(R.id.ed_joingame_name);
        edCode = findViewById(R.id.ed_joingame_code);
        gson = new Gson();
    }

    public void joinGame(View view) {
        String name = edName.getText().toString();
        String code = edCode.getText().toString();
        player = new Player("enterRoom", name,null,code);
        if(MainActivity.webSocketClient == null){
            System.out.println("SOCKET NULL");
        }
        if(player == null){
            System.out.println("SOCKET NULL");
        }
        else{
            System.out.println(gson.toJson(player));
        }
        MainActivity.webSocketClient.send(gson.toJson(player));
        while(MainActivity.player.getRoomId() == null){

        }
        if (name.isEmpty() || code.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter your name and the code", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(getApplicationContext(), "Name: " + MainActivity.player.getRoomId(), Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
