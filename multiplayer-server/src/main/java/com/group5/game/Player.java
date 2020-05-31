package com.group5.game;

import com.group5.Constants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Player {
    private Socket socket;
    private String username;
    private double playerTime = 0.0d;

    private ObjectInputStream receiveStream;
    private ObjectOutputStream sendStream;

    private MultiplayerMessage msgReceived;
    private MultiplayerMessage msgSent;

    public Player(Socket socket){
        this.socket = socket;
        try {
            this.receiveStream = new ObjectInputStream(socket.getInputStream());
            this.sendStream = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public MultiplayerMessage ReceiveMessage(){
        try {
            this.msgReceived = (MultiplayerMessage)((this.receiveStream).readObject());
            return this.msgReceived;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void SendMessage(MultiplayerMessage message){
        try {
            this.msgSent = message;
            sendStream.writeObject(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public void incrementPlayerTime(){
        this.playerTime += Constants.FINAL_TICK_INTERVAL;
        if(this.playerTime >= 24000){
            this.playerTime = 0;
        }
    }
    public double getPlayerTime(){
        return this.playerTime;
    }
}
