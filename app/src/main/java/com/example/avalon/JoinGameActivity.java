package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.avalon.domain.Player;
import com.google.gson.Gson;

public class JoinGameActivity extends AppCompatActivity {
    EditText edName;
    EditText edCode;
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

        if (name.isEmpty() || code.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter your name and the code", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!validCode(code)){
            Toast.makeText(getApplicationContext(), "Invalid code", Toast.LENGTH_SHORT).show();
            return;
        }

        MainActivity.player.setCommand("enterRoom");
        MainActivity.player.setUsername(name);
        MainActivity.player.setRoomId(code);

        if(MainActivity.webSocketClient == null){
            System.out.println("SOCKET NULL");
        }

        MainActivity.webSocketClient.send(gson.toJson(MainActivity.player));
        while(MainActivity.player.getRoomId() == null){

        }
        if(MainActivity.player.getRoomId().equals("Room does not exist") ||
                MainActivity.player.getRoomId().equals("Room is full")){
            Toast.makeText(getApplicationContext(), MainActivity.player.getRoomId(), Toast.LENGTH_SHORT).show();
        }
        else{
            MainActivity.base.savePlayerId(MainActivity.player.getPlayerId());
            MainActivity.base.saveRoomId(MainActivity.player.getRoomId());

            startActivity(new Intent(JoinGameActivity.this,WaitActivity.class));
        }

    }

    public boolean validCode(String code){
        try{
            int number = Integer.parseInt(code);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
