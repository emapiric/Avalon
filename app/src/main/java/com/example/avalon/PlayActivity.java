package com.example.avalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.avalon.domain.Mission;
import com.example.avalon.domain.Player;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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
    //dugme za slanje nominacije
    Button btnNominate;

    String SERVER;
    WebSocketClient webSocketClient;
    private Gson gson = new Gson();
    String username = MainActivity.player.getUsername();
    //hardkod za username
    //String username = "username";
    String role;
    ArrayList<String> nominatedPlayers;
    public VoteDialog voteDialog;
    MissionDialog missionDialog = new MissionDialog();
    GameOverDialog gameOverDialog;

    int missionID;
    int totalNumberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //ucitaj broj igraca
        totalNumberOfPlayers = WaitActivity.playerNames.size();
        missionID = 1;

        //hardkod za numberofplayers
        //totalNumberOfPlayers = 5;

        //INICIJALIZACIJA VIEW-OVA I BRISANJE NEPOTREBNIH PIJUNA
        //PARAMETAR - BROJ IGRACA
        findViews(totalNumberOfPlayers);
        setListeners();
        loadNamesToTextViews();

        navbarMission.setOnNavigationItemSelectedListener(navBarMissionListener);
        visibleInfo = true;

        SERVER = WaitActivity.SERVER + "/Game";
        createWebSocketClient();

    //    HARDKODOVANJE ZA TESTIRANJE SETIMAGEANDINFO
//        String[] info = {"zli1", "zli2"};
//        Command command = new Command("com","val",info);
//        setImageAndInfo("Merlin", command);

        //HARDKODOVANJE ZA TESTIRANJE NOMINACIJE
//        enablePlayerNomination();

        //HARDKODOVANJE ZA TESTIRANJE GLASANJA ZA NOMINOVANE
//        String[] nominated = {"Milos","Ema"};
//        Command command = new Command("nominated", "Marko", nominated);
//        openVoteDialog(command);

        //HARDKOD ZA MISSION DIALOG
//        String[] niz = {"Igrac1","Igrac2"};
//        Command command = new Command("komanda","passed", niz, 1);
//        String result = command.getValue();
//        Mission mission = Mission.createMission(totalNumberOfPlayers, missionID, command.getNegativeVotes(), result, command.getNominated());
//        addMissionToDialog(mission);
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
        btnNominate = findViewById(R.id.btn_nominate);
        //BRISANJE TJ. SAKRIVANJE VISKA PIJUNA
        deletePlayerViews(n);
    }

    private void setListeners() {
        for (TextView tv : tvPlayersList) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView clickedTextView = (TextView) v;
                    String nominatedPlayer = clickedTextView.getText().toString();
                    if (addToNominatedPlayers(nominatedPlayer)) {
                        clickedTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.player_nominated));
                    }
                    else{
                        clickedTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.player));
                    }
                }
            });
        }

        btnNominate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNominatedPlayersToServer();
                for (TextView tv : tvPlayersList) {
                    tv.setClickable(false);
                    tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.player));
                }
                v.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean addToNominatedPlayers(String nominatedPlayer) {
        if (nominatedPlayers.contains(nominatedPlayer)) {
            nominatedPlayers.remove(nominatedPlayer);
            return false;
        }
        if (nominatedPlayersListFull() || (missionID == 0 && nominatedPlayers.size()>0)) {
            Toast.makeText(getApplicationContext(), "You selected the maximum number of players.", Toast.LENGTH_SHORT).show();
            return false;
        }

        nominatedPlayers.add(nominatedPlayer);
        return true;
    }

    private boolean nominatedPlayersListFull() {
        if (missionID == 0)
            return false;
        Mission mission = Mission.createMission(missionID, totalNumberOfPlayers);
        if (nominatedPlayers.size() == mission.getTotalNumberOfVotes())
            return true;
        return false;
    }

    private void sendNominatedPlayersToServer() {
        String[] nominatedPlayersArray = new String[nominatedPlayers.size()];
        nominatedPlayers.toArray(nominatedPlayersArray);
        String commandForServer = setCommandForServer();
        Command command = new Command(commandForServer, username,nominatedPlayersArray);
        String message = gson.toJson(command);
        webSocketClient.send(message);
    }

    private String setCommandForServer() {
        if (missionID == 0)
            return "guessMerlin";
        else
            return "nominated";
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
                        Toast.makeText(getApplicationContext(), playerOnMove+" on move", Toast.LENGTH_SHORT).show();
                        if (playerOnMove.equals(username)) {
                            Toast.makeText(getApplicationContext(), "Select players for the quest", Toast.LENGTH_SHORT).show();
                            enablePlayerNomination();
                        }
                        break;
                    case "nominated" :
                        openVoteDialog(command);
                        break;
                    case "nominatedVote":
                        showVoteNextToPlayer(command);
                        break;
                    case "missionStarted":
                        Toast.makeText(getApplicationContext(), "Mission in progress", Toast.LENGTH_SHORT).show();
                        if (playerOnMission(command.getNominated())) {
                            openVoteDialog(command);
                        }
                        break;
                    case "missionFinished":
                        String result = command.getValue();
                        Toast.makeText(getApplicationContext(), "Mission has "+result, Toast.LENGTH_SHORT).show();
                        Mission mission = Mission.createMission(totalNumberOfPlayers, missionID, command.getNumberOfNegativeVotes(), result, command.getNominated());
                        addMissionToDialog(mission);
                        missionID++;
                        break;
                    case "gameOver":
                        if (command.getValue().equals("Good")) {
                            if (role.equals("Assassin")) {
                                Toast.makeText(getApplicationContext(), "Select Merlin", Toast.LENGTH_SHORT).show();
                                missionID = 0;
                                enablePlayerNomination();
                            }
                        }
                        else {
                            openGameOverDialog(command);
                        }
                        break;
                    case "merlinGuessed":
                        openGameOverDialog(command);
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
                tvInfo.setText("Evil players are: "+ Arrays.toString(command.getNominated()));
                break;
            case "Percival":
                imageDrawable = getResources().getDrawable(R.drawable.percival);
                tvInfo.setText("Merlin and Morgana are: " + Arrays.toString(command.getNominated()));
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
                tvInfo.setText(Arrays.toString(command.getNominated()) + " are also in the evil team");
                imageDrawable = getResources().getDrawable(R.drawable.morgana);
                break;
            case "Mordred":
                tvInfo.setText(Arrays.toString(command.getNominated()) + " are also in the evil team");
                imageDrawable = getResources().getDrawable(R.drawable.mordred);
                break;
            case "Assassin":
                tvInfo.setText(Arrays.toString(command.getNominated()) + " are also in the evil team");
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

    private void enablePlayerNomination() {
        nominatedPlayers = new ArrayList<>();
        for (TextView tv : tvPlayersList) {
            tv.setClickable(true);
        }
        btnNominate.setVisibility(View.VISIBLE);
    }

    public void openVoteDialog(Command command) {
        voteDialog = VoteDialog.newInstance(command);
        voteDialog.setCancelable(false);
        voteDialog.show(getSupportFragmentManager(), "VOTE_DIALOG");
    }

    public void sendVoteToServer(String commandForServer, boolean vote) {
        voteDialog.dismiss();
        Command command = new Command(commandForServer,vote);
        String message = gson.toJson(command);
        webSocketClient.send(message);
    }

    //server posalje niz tipa yes yes no no pa onda na osnovu toga ucitaj kruzice
    public void showVoteNextToPlayer(Command command) {
        String[] playerNames = command.getNominated();
        Boolean[] playerVotes = command.getVotes();
        for (int i = 0; i<playerNames.length; i++) {
            TextView tv = getTextViewByUsername(playerNames[i]);
            if (playerVotes[i])
                tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.circle_green),null,null,getResources().getDrawable(R.drawable.player));
            else
                tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.circle_red),null,null,getResources().getDrawable(R.drawable.player));
        }
    }

    private TextView getTextViewByUsername(String username) {
        for (TextView tv : tvPlayersList) {
            if (tv.getText().toString().equals(username))
                return tv;
        }
        return null;
    }
    private boolean playerOnMission(String[] nominated) {
        for (String player :  nominated) {
            if (player.equals(username))
                return true;
        }
        return false;
    }

    private void addMissionToDialog(Mission mission) {
        switch(missionID) {
            case 1:
                missionDialog.mission1 = mission;
                break;
            case 2:
                missionDialog.mission2 = mission;
                break;
            case 3:
                missionDialog.mission3 = mission;
                break;
            case 4:
                missionDialog.mission4 = mission;
                break;
            default:
                missionDialog.mission5 = mission;
                break;
        }
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navBarMissionListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    openMissionDialog(item);
                    return true;
                }
            };

    public void openMissionDialog(MenuItem item) {
        missionDialog.selectedItem = item;
        missionDialog.show(getSupportFragmentManager(),"MISSION_DIALOG");
    }

    public void openGameOverDialog(Command command) {
        gameOverDialog = GameOverDialog.newInstance(command);
        gameOverDialog.setCancelable(false);
        gameOverDialog.show(getSupportFragmentManager(), "VOTE_DIALOG");
    }
    public void playAgain() {
        startActivity(new Intent(PlayActivity.this,WaitActivity.class));
    }

    public void exit() {
        System.out.println("EXIT");
    }
}
