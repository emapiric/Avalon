package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.avalon.domain.Player;
import com.example.avalon.service.Service;
import com.example.avalon.service.impl.ServiceImpl;
import com.example.sharedPreferences.Base;
import com.google.gson.Gson;

import tech.gusavila92.websocketclient.WebSocketClient;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/*
    UNOS USER NAME - A PRE PRELASKA NA NEKI DRUGI ACTIVITY
 */
public class MainActivity extends AppCompatActivity {

    public static Player player;
    public static WebSocketClient webSocketClient;
    public static final String SERVER = "ws://localhost:9000/Avalon/Server";
    private Gson gson = new Gson();
    private Service service = new ServiceImpl();
    private Logger logger =  Logger.getLogger(this.getClass().getName());
    public static Base base;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        base=new Base();
        Base.sharedPreferences=getSharedPreferences(base.getPreferencesKey(),MODE_PRIVATE);

        String playerId, roomId;

        playerId = base.getPlayerId();
        roomId = base.getRoomId();

        System.out.println("PLAYER I ROOM ID: "+ playerId + " "+ roomId);

        player = new Player("null","null","null","null");
        logger.info("OPEN");
        createWebSocketClient();

        while(webSocketClient == null){

        }
    }

    public void goToCreateGameActivity(View view) {
        player.setCommand("newPlayer");
        try{
        String message = gson.toJson(player);
        System.out.println("JSON " + message);
        webSocketClient.send(message);
        while(player.getRoomId().equals("null")){

        }

        base.savePlayerId(player.getPlayerId());
        base.saveRoomId(player.getRoomId());

        startActivity(new Intent(MainActivity.this,CreateGameActivity.class));

        }catch (Exception e){
            System.out.println("PRVI PEDER");
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
     //  startActivity(new Intent(MainActivity.this,PlayActivity.class));
    }

    public void goToJoinGameActivity(View view) {
        startActivity(new Intent(MainActivity.this,JoinGameActivity.class));
    }

    public void reconnect(View view) {

        // ucitati iz baze player i roomid
        //samo ako nije null i ako hoce reconnect
//        webSocketClient.send(gson.toJson(player));
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
                logger.info("Connected to server!");

            }

            @Override
            public void onTextReceived(String message) {
                try {
                    player = gson.fromJson(message, Player.class);
                    System.out.println("RECEVED FROM SERVER: " + message);
                }catch (Exception e){
                    System.out.println("DRUGI PEDER");
                    System.out.println(e.getMessage());
                    System.out.println(e.getStackTrace());
                }
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

//        webSocketClient.setConnectTimeout(10000);
//        webSocketClient.setReadTimeout(60000);
//        webSocketClient.addHeader("Origin", "http://developer.example.com");
//        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

}
