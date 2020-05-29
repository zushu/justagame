package com.group5.game;

import com.group5.Constants;

import java.awt.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Match implements Runnable{
    private Player player1;
    private Player player2;
    private boolean is_finished = false;

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        System.out.println("Match thread started.");
        MultiplayerMessage initialSetupMsg = new MultiplayerMessage("test",1000,Constants.INITIAL_POSITION_1,Constants.STATUS_CONTINUING);
        player1.SendMessage(initialSetupMsg);
        initialSetupMsg.setPosition(Constants.INITIAL_POSITION_2);
        player1.SendMessage(initialSetupMsg);

        player2.SendMessage(initialSetupMsg);
        initialSetupMsg.setPosition(Constants.INITIAL_POSITION_1);
        player2.SendMessage(initialSetupMsg);

        new HandlePlayer(player1,player2).start();
        new HandlePlayer(player2,player1).start();
    }


    private class HandlePlayer extends Thread {
        private Player player;
        private Player rival;

        private MultiplayerMessage msgReceived;
        private MultiplayerMessage msgSent;

        HandlePlayer(Player player, Player rival){
            this.player = player;
            this.rival = rival;
        }

        @Override
        public void run() {
            super.run();
            System.out.println("HandlePlayer thread started.");
            while(!is_finished){
                msgReceived = player.ReceiveMessage();
                msgReceived.print();
                msgSent = msgReceived;
                msgSent.setHealth(msgSent.getHealth()-1);
                rival.SendMessage(msgSent);
            }
        }

    }

}


//            DataInputStream fromP1 = new DataInputStream(p1.getInputStream());
//            DataOutputStream toP1 = new DataOutputStream(p1.getOutputStream());
//
//            new DataOutputStream(toP1).writeUTF("response from SERVER SERVER SERVER SERVER");
//
//            String response = fromP1.readUTF();
//            String message = new String(response.getBytes(), "UTF-8");
//            System.out.println(message);
//


//    ObjectOutputStream toP1Obj = new ObjectOutputStream(p1.getOutputStream());
//    MultiplayerMessage msgToP1 = new MultiplayerMessage("server", 999, new Point(300,500), Constants.STATUS_CONTINUING);
//        toP1Obj.writeObject(msgToP1);
//
//                try {
//                ObjectInputStream fromP1Obj = new ObjectInputStream(p1.getInputStream());
//                MultiplayerMessage msgFromP1 = (MultiplayerMessage)fromP1Obj.readObject();
//
//                System.out.println(msgFromP1.getName()+"  "+msgFromP1.getHealth()+"  "+msgFromP1.getPosition()+"  "+msgFromP1.getGameStatus());
//                }catch (Exception e){
//                e.printStackTrace();
//                }