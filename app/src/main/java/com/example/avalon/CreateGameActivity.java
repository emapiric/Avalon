package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;
import tech.gusavila92.websocketclient.WebSocketClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avalon.domain.Player;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class CreateGameActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    EditText edName;
    TextView tvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        edName = findViewById(R.id.ed_creategame_name);
        tvCode = findViewById(R.id.tv_creategame_code);

        tvCode.setText(MainActivity.player.getRoomId());
    }

    public void createGame(View view) {
        try {
            String name = edName.getText().toString();
            if (name.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            MainActivity.player.setCommand("updateUsername");
            MainActivity.player.setUsername(name);
            MainActivity.webSocketClient.send(gson.toJson(MainActivity.player));
            Toast.makeText(getApplicationContext(), MainActivity.player.getUsername(), Toast.LENGTH_SHORT).show();

            startActivity(new Intent(CreateGameActivity.this,WaitActivity.class));

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
