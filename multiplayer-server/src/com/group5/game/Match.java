package com.group5.game;

import com.group5.Constants;

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

        MultiplayerMessage initialSetupMsg = new MultiplayerMessage(player2.getUsername(),1000,Constants.INITIAL_POSITION_1,Constants.STATUS_CONTINUING,0);
        player1.SendMessage(initialSetupMsg);
        initialSetupMsg.setPosition(Constants.INITIAL_POSITION_2);
        player1.SendMessage(initialSetupMsg);

        initialSetupMsg.setName(player1.getUsername());
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
                //msgReceived.print();
                msgSent = msgReceived;
                rival.SendMessage(msgSent);
            }
        }

    }

}
