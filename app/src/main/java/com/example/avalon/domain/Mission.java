package com.example.avalon.domain;

import androidx.annotation.NonNull;

import com.example.avalon.service.MissionHandler;

import java.util.Arrays;

public class Mission {
    private int id;
    private int totalNumberOfVotes;
    private int minNegativeVotes;
    private int negativeVotes;
    private String result;
    private String[] nominated;

    public Mission(int id, int negativeVotes, String result, String[] nominated) {
        this.id = id;
        this.negativeVotes = negativeVotes;
        this.result = result;
        this.nominated = nominated;
        this.minNegativeVotes = 1;
    }

    public static Mission createMission(int numberOfPlayers, int id, int negativeVotes, String result, String[] nominated) {
        Mission mission = new Mission(id, negativeVotes, result, nominated);
        mission = MissionHandler.setNumberOfVotes(mission, numberOfPlayers,id);
        return mission;
    }

    public int getId() {
        return id;
    }

    public int getTotalNumberOfVotes() {
        return totalNumberOfVotes;
    }

    public int getMinNegativeVotes() {
        return minNegativeVotes;
    }

    public int getNegativeVotes() {
        return negativeVotes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTotalNumberOfVotes(int totalNumberOfVotes) {
        this.totalNumberOfVotes = totalNumberOfVotes;
    }

    public void setMinNegativeVotes(int minNegativeVotes) {
        this.minNegativeVotes = minNegativeVotes;
    }

    public void setNegativeVotes(int negativeVotes) {
        this.negativeVotes = negativeVotes;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String[] getNominated() {
        return nominated;
    }

    public void setNominated(String[] nominated) {
        this.nominated = nominated;
    }

    @NonNull
    @Override
    public String toString() {
        return "Mission number "+id+" "+result+".\nTotal number of votes: "+totalNumberOfVotes+"\nNumber of negative votes: "+negativeVotes+
                "\nPlayers that went on this mission:"+ Arrays.toString(nominated);
    }
}
