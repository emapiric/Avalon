package com.example.avalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.avalon.domain.Player;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    //Lista u koju ce se ucitati svi igraci kako bi se posle postavila imena u viewove
    List<Player> players;

    //TextView u koji se upisuju dodatne informacije o igracima (npr za Merlina ko su zlikovci)
    TextView tvInfo;
    //ImageView u koji se smesta slika lika
    ImageView ivCharacter;
    //Lista TextViewova koji sadrze imena igraca i slicice pijuna
    //prosto da nemamo 5 razlicitih TextView polja za sve nego ovako da prolazimo kroz njih
    ArrayList<TextView> tvPlayersList = new ArrayList<>();
    //NavigationBar za misije
    BottomNavigationView navbarMission;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        findViews();
        navbarMission.setOnNavigationItemSelectedListener(navBarMissionListener);


    }

    private void findViews() {
        tvInfo = findViewById(R.id.tv_info);
        ivCharacter = findViewById(R.id.iv_character);
        navbarMission = findViewById(R.id.navbar_mission);
        tvPlayersList.add((TextView) findViewById(R.id.tv_player1));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player2));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player3));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player4));
        tvPlayersList.add((TextView) findViewById(R.id.tv_player5));
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
}
