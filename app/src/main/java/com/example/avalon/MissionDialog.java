package com.example.avalon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.avalon.domain.Mission;

public class MissionDialog extends AppCompatDialogFragment {
    public Mission mission1;
    public Mission mission2;
    public Mission mission3;
    public Mission mission4;
    public Mission mission5;

    public MenuItem selectedItem;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = generateMessage(selectedItem);

        builder.setTitle("Mission info")
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }

    private String generateMessage(MenuItem selectedItem) {
        Mission mission;
        switch(selectedItem.getItemId()) {
            case R.id.mission1:
                mission = mission1;
                break;
            case R.id.mission2:
                mission = mission2;
                break;
            case R.id.mission3:
                mission = mission3;
                break;
            case R.id.mission4:
                mission = mission4;
                break;
            default:
                mission = mission5;
                break;
        }
        if (mission == null)
            return "This mission is not over yet";
        return mission.toString();
    }
}
