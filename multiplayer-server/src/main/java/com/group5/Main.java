package main.java.com.group5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        //while(true){
            System.out.println("Waiting for 2 players.......");
            Socket p1 = serverSocket.accept();
            System.out.println("Player1 connected.");
            DataInputStream fromP1 = new DataInputStream(p1.getInputStream());
            DataOutputStream toP1 = new DataOutputStream(p1.getOutputStream());

            new DataOutputStream(toP1).writeUTF("response from SERVER SERVER SERVER SERVER");


            String response = fromP1.readUTF();
            String message = new String(response.getBytes(), "UTF-8");
            System.out.println(message);


            //Socket p2 = serverSocket.accept();
            //System.out.println("Player2 connected.");
        //}
        System.exit(0);
    }
}
