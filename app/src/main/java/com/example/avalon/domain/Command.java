package com.example.avalon.domain;

import java.util.Arrays;

public class Command {

    private String command;
    private String value;
    private String[] nominated;
    private Boolean[] votes;
    private boolean accepted;
    private int numberOfNegativeVotes;

    public Boolean[] getVotes() {
        return votes;
    }

    public void setVotes(Boolean[] votes) {
        this.votes = votes;
    }

    public Command(String command, String value, String[] nominated, int numberOfNegativeVotes) {
        this.command = command;
        this.value = value;
        this.nominated = nominated;
        this.numberOfNegativeVotes = numberOfNegativeVotes;
    }

    public Command(String command, String value, String[] nominated) {
        this.command = command;
        this.value = value;
        this.nominated = nominated;
    }

    public Command(String command, String value) {
        this.command = command;
        this.value = value;
    }

    public Command(String command, boolean accepted) {
        this.command = command;
        this.accepted = accepted;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getNominated() {
        return nominated;
    }

    public void setNominated(String[] nominated) {
        this.nominated = nominated;
    }

    public int getNumberOfNegativeVotes() {
        return numberOfNegativeVotes;
    }

    public void setNumberOfNegativeVotes(int numberOfNegativeVotes) {
        this.numberOfNegativeVotes = numberOfNegativeVotes;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }


    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                ", value='" + value + '\'' +
                ", nominated=" + Arrays.toString(nominated) +
                '}';
    }

}
