package com.example.avalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avalon.domain.Command;
import com.example.avalon.domain.Player;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import tech.gusavila92.websocketclient.WebSocketClient;

public class PlayActivity extends AppCompatActivity {

    //Lista u koju ce se ucitati svi igraci kako bi se posle postavila imena u viewove
    List<Player> players;
    //vidljivost slike i informacija
    boolean visibleInfo;

    //TextView za You Are
    TextView tvYouAre;
    //TextView u koji se upisuju dodatne informacije o igracima (npr za Merlina ko su zlikovci)
    TextView tvInfo;
    //ImageView u koji se smesta slika lika
    ImageView ivCharacter;
    //Lista TextViewova koji sadrze imena igraca i slicice pijuna
    //prosto da nemamo 5 razlicitih TextView polja za sve nego ovako da prolazimo kroz njih
    ArrayList<TextView> tvPlayersList = new ArrayList<>();
    //NavigationBar za misije
    BottomNavigationView navbarMission;
    //dugme za hide info
    Button btnHideInfo;

    String SERVER;
    WebSocketClient webSocketClient;
    private Gson gson = new Gson();

    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //INICIJALIZACIJA VIEW-OVA I BRISANJE NEPOTREBNIH PIJUNA
        //PARAMETAR - BROJ IGRACA
        findViews(5);
        loadNamesToTextViews();

        navbarMission.setOnNavigationItemSelectedListener(navBarMissionListener);
        visibleInfo = true;

        //HARDKODOVANJE ZA TESTIRANJE SETIMAGEANDINFO
//        String[] info = {"zli1", "zli2"};
//        Command command = new Command("com","val",info);
//        setImageAndInfo("Merlin", command);

        SERVER = WaitActivity.SERVER + "/Game";

    }



    private void findViews(int n) {
        tvYouAre = findViewById(R.id.tv_you_are);
        tvInfo = findViewById(R.id.tv_info);
        ivCharacter = findViewById(R.id.iv_character);
        navbarMission = findViewById(R.id.navbar_mission);
        tvPlayersList.add((TextView) findViewById(R.id.tv_player1));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player2));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player3));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player4));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player5));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player6));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player7));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player8));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player9));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player10));
        btnHideInfo = findViewById(R.id.btn_hide_info);
        //BRISANJE TJ. SAKRIVANJE VISKA PIJUNA
        deletePlayerViews(n);
    }

    public void deletePlayerViews(int n) {
        if (n<9)  {
            LinearLayout extraLinearLayout = findViewById(R.id.extraLinearLayout);
            extraLinearLayout.setVisibility(View.GONE);
        }
        if (n==9) {
            TextView tvToHide = tvPlayersList.get(6);
            tvPlayersList.set(6,tvPlayersList.get(9));
            tvPlayersList.set(9,tvToHide);
        }
        if (n==6 || n == 7) {
            TextView tvToHide = tvPlayersList.get(0);
            tvPlayersList.set(0,tvPlayersList.get(7));
            tvPlayersList.set(7,tvToHide);
        }
        for (int i = n; i < 10; i++) {
            tvPlayersList.get(i).setVisibility(View.INVISIBLE);
        }
        //BRISANJE VISKA IGRACA IZ LISTE
        for (int i = tvPlayersList.size()-1; i >= n; i--) {
            tvPlayersList.remove(i);
        }
    }

    private void loadNamesToTextViews() {
        for (int i = 0; i < tvPlayersList.size(); i++) {
            tvPlayersList.get(i).setText(WaitActivity.playerNames.get(i));
        }
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navBarMissionListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    openDialog();
                    return true;
                }
    };

    public void openDialog() {
        MissionDialog missionDialog = new MissionDialog();
        missionDialog.show(getSupportFragmentManager(),"example dialog");
    }

    public void hideInfo(View view) {
        if (visibleInfo) {
            tvYouAre.setVisibility(View.INVISIBLE);
            ivCharacter.setVisibility(View.INVISIBLE);
            tvInfo.setVisibility(View.INVISIBLE);
            btnHideInfo.setText("SHOW INFO");
            visibleInfo = false;
        }
        else {
            tvYouAre.setVisibility(View.VISIBLE);
            ivCharacter.setVisibility(View.VISIBLE);
            ivCharacter.setVisibility(View.VISIBLE);
            btnHideInfo.setText("HIDE INFO");
            visibleInfo = true;
        }
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
                //prima se poruka s likom od servera

            }

            @Override
            public void onTextReceived(String message) {
                Command command = gson.fromJson(message, Command.class);
                switch (command.getCommand()) {
                    case "role" :
                        role = command.getValue();
                        setImageAndInfo(role, command);
                        break;
                    case "onMove" :
                        String playerOnMove = command.getValue();
                        //ova dole linija je za to da se promeni boja pijuna u zuto
                        //videcemo da li ce da bude smaranja oko vracanja u belo tj kad to da se uradi
                        //getTextViewByPlayersName(command.getValue()).setCompoundDrawablesWithIntrinsicBounds(null,null,null,getResources().getDrawable(R.drawable.player_on_move));
                        Toast.makeText(getApplicationContext(), playerOnMove+" on move", Toast.LENGTH_SHORT).show();
                        if (playerOnMove.equals(MainActivity.player.getUsername())) {
                            Toast.makeText(getApplicationContext(), "Select players for the quest", Toast.LENGTH_SHORT).show();
                            selectPlayersForQuest();
                        }
                        break;
                    default:
                        break;
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

        webSocketClient.connect();
    }


    private void setImageAndInfo(String role, Command command) {
        Drawable imageDrawable = null;

        switch(role) {
            case "Merlin":
                imageDrawable = getResources().getDrawable(R.drawable.merlin);
                tvInfo.setText("Evil players are: "+ command.nominatedToString());
                break;
            case "Percival":
                imageDrawable = getResources().getDrawable(R.drawable.percival);
                tvInfo.setText("Merlin and Morgana are: " + command.nominatedToString());
                break;
            case "Lancelot":
                imageDrawable = getResources().getDrawable(R.drawable.lancelot);
                break;
            case "Pleb1":
                imageDrawable = getResources().getDrawable(R.drawable.pleb1);
                    break;
            case "Pleb2":
                imageDrawable = getResources().getDrawable(R.drawable.pleb2);
                break;
            case "Pleb3":
                imageDrawable = getResources().getDrawable(R.drawable.pleb3);
                break;
            case "Morgana":
                tvInfo.setText("Mordred and Assassin are: " + command.nominatedToString());
                imageDrawable = getResources().getDrawable(R.drawable.morgana);
                break;
            case "Mordred":
                tvInfo.setText("Morgana and Assassin are: " + command.nominatedToString());
                imageDrawable = getResources().getDrawable(R.drawable.mordred);
                break;
            case "Assassin":
                tvInfo.setText("Morgana and Mordred are: " + command.nominatedToString());
                imageDrawable = getResources().getDrawable(R.drawable.assassin);
                break;
            case "Oberon":
                imageDrawable = getResources().getDrawable(R.drawable.oberon);
                break;
            default:
                break;
        }

        if (imageDrawable != null) {
            ivCharacter.setImageDrawable(imageDrawable);
            //ivCharacter.setImageDrawable(Drawable.createFromPath("src\\main\\res\\drawable\\merlin.JPG"));
        }
    }

    private TextView getTextViewByPlayersName(String value) {
        for (TextView tv : tvPlayersList) {
            if(tv.getText().toString().equals(value))
                return tv;
        }
        return null;
    }


    private void selectPlayersForQuest() {

    }

}
