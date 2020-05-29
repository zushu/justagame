package com.group5;

import com.group5.Constants;
import com.group5.game.MultiplayerMessage;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Constants.MULTIPLAYER_SERVER_PORT);
        //while(true){
        System.out.println("Waiting for 2 players.......");
        Socket p1 = serverSocket.accept();
        System.out.println("Player1 connected.");
//            DataInputStream fromP1 = new DataInputStream(p1.getInputStream());
//            DataOutputStream toP1 = new DataOutputStream(p1.getOutputStream());
//
//            new DataOutputStream(toP1).writeUTF("response from SERVER SERVER SERVER SERVER");
//
//            String response = fromP1.readUTF();
//            String message = new String(response.getBytes(), "UTF-8");
//            System.out.println(message);

        ObjectOutputStream toP1Obj = new ObjectOutputStream(p1.getOutputStream());
        MultiplayerMessage msgToP1 = new MultiplayerMessage("server", 999, new Point(300,500), Constants.STATUS_CONTINUING);
        toP1Obj.writeObject(msgToP1);

        try {
            ObjectInputStream fromP1Obj = new ObjectInputStream(p1.getInputStream());
            MultiplayerMessage msgFromP1 = (MultiplayerMessage)fromP1Obj.readObject();

            System.out.println(msgFromP1.getName()+"  "+msgFromP1.getHealth()+"  "+msgFromP1.getPosition()+"  "+msgFromP1.getGameStatus());
        }catch (Exception e){
            e.printStackTrace();
        }



        //Socket p2 = serverSocket.accept();
        //System.out.println("Player2 connected.");
        //}
        serverSocket.close();
        System.exit(0);
    }
}
/*

    ObjectOutputStream os=new ObjectOutputStream(s.getOutputStream());
    student object1=new student(12,"Pankaj","M.tech");
    os.writeObject(object1);

    Socket s=new Socket("127.0.0.1",1700);
    ObjectInputStream is=new ObjectInputStream(s.getInputStream());
    student s=(student)is.readObject();
    s.showDetails();
    s.close();
*/


//    public static void main(String[] args) throws IOException {
//        final int port = PORT;
//        ServerMessage serverMessage;
//
//        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
//
//        ServerSocket serverSocket = new ServerSocket(port);
//        System.out.println("Server has been started at port " + port + ".");
//
//        while (true) {
//            System.out.println("Waiting for the players..");
//
//            Socket player1 = serverSocket.accept();
//            System.out.println("Player1 has joined the game.");
//
//            serverMessage = new ServerMessage(PLAYER1_JOINED, INIT_POSITION, INIT_HEALTH, INIT_SHOT, INIT_WON);
//            String messagePlayer1 = objectWriter.writeValueAsString(serverMessage);
//            serverMessage = new ServerMessage(PLAYER2_JOINED, INIT_POSITION, INIT_HEALTH, INIT_SHOT, INIT_WON);
//            String messagePlayer2 = objectWriter.writeValueAsString(serverMessage);
//
//            Socket player2 = serverSocket.accept();
//            System.out.println("Player2 has joined the game.");
//
//            new DataOutputStream(player1.getOutputStream()).writeUTF(messagePlayer1);
//            new DataOutputStream(player2.getOutputStream()).writeUTF(messagePlayer2);
//
//            new Thread(new Session(player1, player2)).start();
//        }
//    }