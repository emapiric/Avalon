package com.example.avalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.avalon.domain.Player;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //INICIJALIZACIJA VIEW-OVA I BRISANJE NEPOTREBNIH PIJUNA
        //PARAMETAR - BROJ IGRACA
        findViews(7);
        navbarMission.setOnNavigationItemSelectedListener(navBarMissionListener);
        visibleInfo = true;


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

}
