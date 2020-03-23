package com.example.avalon.domain;

public class Player {

    private String command;
    private String username;
    private String playerId;
    private String roomId;

    public Player(String command, String username, String playerId, String roomId) {
        this.command = command;
        this.username = username;
        this.playerId = playerId;
        this.roomId = roomId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", playerId='" + playerId + '\'' +
                ", roomId='" + roomId + '\'' +
                '}';
    }

}

