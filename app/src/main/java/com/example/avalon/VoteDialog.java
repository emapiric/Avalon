package com.example.avalon;
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

import java.util.Arrays;


public class VoteDialog extends DialogFragment {

    TextView tvInfo;
    Button btnYes;
    Button btnNo;
    Command command;
    public static VoteDialog newInstance(Command command) {
        VoteDialog voteDialog = new VoteDialog();
        voteDialog.command = command;
        return voteDialog;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_vote, container);
        final PlayActivity parent = (PlayActivity)getActivity();
        findViews(view);
        tvInfo.setText(command.getValue()+" nominated " + Arrays.toString(command.getNominated())+"\nDo you agree to send them on a quest?");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.sendVoteToServer("YES");
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.sendVoteToServer("NO");
            }
        });
        return view;
    }

    private void findViews(View view) {
        tvInfo = view.findViewById(R.id.dialog_vote_info);
        btnYes = view.findViewById(R.id.btn_yes);
        btnNo = view.findViewById(R.id.btn_no);
    }

//    @Override
//    public void onClick(View v) {
//        PlayActivity parent = (PlayActivity)getActivity();
//        if (getView().getId() == R.id.btn_yes) {
//            parent.nomineesApproved("YES");
//            return;
//        }
//        parent.nomineesApproved("NO");
//    }
}
