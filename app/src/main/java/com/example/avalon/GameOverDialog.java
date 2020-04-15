package com.example.avalon;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.avalon.domain.Command;



public class GameOverDialog extends DialogFragment {

    TextView tvInfo;
    Button btnPlayAgain;
    Button btnExit;
    Command command;

    public static GameOverDialog newInstance(Command command) {
        GameOverDialog gameOverDialog = new GameOverDialog();
        gameOverDialog.command = command;
        return gameOverDialog;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_vote, container);
        final PlayActivity parent = (PlayActivity)getActivity();
        findViews(view);
        if (command.getCommand().equals("gameOver"))
            tvInfo.setText(command.getValue() + " team has won the game.");
        //u suprotnom je komanda merlinGuessed i proveravamo da li je pogodjen ili ne
        else if (command.isAccepted())
            tvInfo.setText("Merlin guessed. Evil team has won the game.\nAssassin and Merlin were "+command.getNominated()[0]+command.getNominated()[1]);
        else
            tvInfo.setText("Merlin not guessed. Good team has won the game.\nAssassin and Merlin were "+command.getNominated()[0]+command.getNominated()[1]);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.playAgain();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.exit();
            }
        });
        return view;
    }

    private void findViews(View view) {
        tvInfo = view.findViewById(R.id.dialog_game_over_info);
        btnPlayAgain = view.findViewById(R.id.btn_play_again);
        btnExit = view.findViewById(R.id.btn_exit);
    }

}
