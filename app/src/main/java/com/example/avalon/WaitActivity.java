package com.example.avalon;

import androidx.appcompat.app.AppCompatActivity;
import tech.gusavila92.websocketclient.WebSocketClient;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avalon.domain.Player;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class WaitActivity extends AppCompatActivity {

    TextView hostname;
    TextView  username;
    Button btnStart;
    ListView listView;
    ArrayAdapter<String> adapter;
    Player player;
    WebSocketClient webSocketClient = null;
    public static String SERVER;
    Handler playersHandler;

    public ArrayList<String> playerNames;
    private Logger logger =  Logger.getLogger(this.getClass().getName());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        player = MainActivity.player;
        System.out.println("KOMANDA JE" + player.getCommand());

        hostname = findViewById(R.id.tv_wait_hostname);
        username = findViewById(R.id.tv_wait_username);
        listView = findViewById(android.R.id.list);
        btnStart = findViewById(R.id.btn_wait_start);

        if(player.getCommand().equals("enterRoom"))
            btnStart.setVisibility(View.INVISIBLE);
        else
            btnStart.setVisibility(View.VISIBLE);

        SERVER = MainActivity.SERVER + "/" + player.getRoomId() + "/" + player.getPlayerId();
        createWebSocketClient();

        while(webSocketClient == null){

        }

        webSocketClient.send(player.getUsername());

        //HARDCODE ZA POPUNJAVANJE LISTE
        playerNames  = new ArrayList<String>();
//        playerNames.add("Player1");
//        playerNames.add("Player2");
//        playerNames.add("Player3");
//        playerNames.add("Player4");
//        playerNames.add("Player5");
        //ZATIM TREBA UCITATI HOSTNAME I USERNAME OD SERVERA ISTO
        hostname.setText(player.getRoomId());
        username.setText(player.getUsername());
        //OVU METODU TREBA POZIVATI DOKLE GOD STIZU PODACI OD SERVERA
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, playerNames);
        listView.setAdapter(adapter);

//        startUpdateing();

        //KAD SE UCITA SVE OD SERVERA PREKINE SE LOADING I ENABLEUJE SE DUGME I MENJA MU SE POZADINA MALO ESTETIKE I TI FAZONI
//        findViewById(R.id.loadingBar).setVisibility(View.GONE);

    }

    public void startGame(View view) {
        webSocketClient.send("startGame");
//        startActivity(new Intent(WaitActivity.this,PlayActivity.class));
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
                logger.info("Entered room!");

            }

            @Override
            public void onTextReceived(String message) {
                try {
                    if(message.equals("startGame")){
                        startActivity(new Intent(WaitActivity.this,PlayActivity.class));
                        return;
                    }
//                    player = gson.fromJson(message, Player.class);
                    System.out.println("SPISAK IGRACA: " + message);
                    String[] players = message.split(",");
                    playerNames.clear();
                    for (int i = 0; i < players.length; i++ ) {
                        if(!players[i].equals(player.getUsername())) {
                            playerNames.add(players[i]);
                            System.out.println(i + ". igrac je " + players[i]);
                        }
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() { // Tried new Handler(Looper.myLopper()) also
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });

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

//    private void startUpdateing() {
//        updateThread = new UpdateThread();
//        updateThread.start();
//    }

//    public class UpdateThread extends Thread {
//        private static final int DELAY = 10000;
//
//        @Override
//        public void run() {
//            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, playerNames);
//            listView.setAdapter(adapter);
//            while (true) {
//
//
//                adapter.notifyDataSetChanged();
//
//                try {
//                    Thread.sleep(DELAY);
//                } catch (InterruptedException e) {
//                    return;
//                }
//            }
//        }
//    }

}

