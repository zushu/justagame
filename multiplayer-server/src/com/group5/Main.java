package com.group5;

import com.group5.game.Match;
import com.group5.game.MultiplayerMessage;
import com.group5.game.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.MULTIPLAYER_SERVER_PORT);

        while(true){
            System.out.println("Waiting for 2 players.......");
            Socket p1Socket = serverSocket.accept();
            System.out.println("Player1 connected.");
            Player player1 = new Player(p1Socket);
            MultiplayerMessage receivePlayerNames = player1.ReceiveMessage();
            player1.setUsername(receivePlayerNames.getName());
            receivePlayerNames.print();

            Socket p2Socket = serverSocket.accept();
            System.out.println("Player2 connected.");
            Player player2 = new Player(p2Socket);
            receivePlayerNames = player2.ReceiveMessage();
            player2.setUsername(receivePlayerNames.getName());
            receivePlayerNames.print();

            new Thread(new Match(player1,player2)).start();
        }
    }
}
