package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.avalon.domain.Player;
import com.google.gson.Gson;

import tech.gusavila92.websocketclient.WebSocketClient;
import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private WebSocketClient webSocketClient;
    public static final String SERVER = "ws://192.168.1.58:9000/Avalon/Server";
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createWebSocketClient();
    }

    public void goToCreateGameActivity(View view) {
        startActivity(new Intent(MainActivity.this,CreateGameActivity.class));
    }
    public void goToJoinGameActivity(View view) {
        startActivity(new Intent(MainActivity.this,JoinGameActivity.class));
    }
    public void reconnect(View view) {

    }

    private void createWebSocketClient() {
        URI uri;
        try {
            uri = new URI(SERVER);
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                System.out.println("onOpen");
//                webSocketClient.send("Hello, World!");
                Player player = new Player("newPlayer", "Milos",null,null);
                webSocketClient.send(gson.toJson(player));
            }

            @Override
            public void onTextReceived(String message) {
                System.out.println(message);
            }

            @Override
            public void onBinaryReceived(byte[] data) {
                System.out.println("onBinaryReceived");
            }

            @Override
            public void onPingReceived(byte[] data) {
                System.out.println("onPingReceived");
            }

            @Override
            public void onPongReceived(byte[] data) {
                System.out.println("onPongReceived");
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                System.out.println("onCloseReceived");
            }
        };

        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.addHeader("Origin", "http://developer.example.com");
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }


}
