package com.example.avalon.domain;

import java.util.Arrays;

public class Command {

    private String command;
    private String value;
    private String[] nominated;
    private int negativeVotes;

    public Command(String command, String value, String[] nominated, int negativeVotes) {
        this.command = command;
        this.value = value;
        this.nominated = nominated;
        this.negativeVotes = negativeVotes;
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

    public int getNegativeVotes() {
        return negativeVotes;
    }

    public void setNegativeVotes(int negativeVotes) {
        this.negativeVotes = negativeVotes;
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
