package battleship;

//Author: Andrei Ghenoiu
//Fall 2011 Networks class Vermont Technical College
//if you have any questions contact me at andrei_stefang@yahoo.com
import java.io.*;
import java.net.*;

public class ClientController {

    private String ip = "127.0.0.1";
    private String sentence;
    private String modifiedSentence;
    private Socket clientSocket;
    private Socket hitOrMiss;
    private String miss = "MISSSSSSSSSSSSS";
    private String hit = "HITTTTTTTTT";
    private int serverPort = 1995;
    private DataInputStream inFromServer;
    private DataOutputStream outToServer;
    private BufferedReader inFromUser = new BufferedReader(
            new InputStreamReader(System.in));

    public ClientController() {
        try {
            clientSocket = new Socket(ip, serverPort);
            inFromServer = new DataInputStream(
                    clientSocket.getInputStream());

            //out to server the hit or miss message
            outToServer = new DataOutputStream(
                    clientSocket.getOutputStream());
        } catch (Exception e) {
        }
    }

    public void sendToServer(String s) throws IOException {
        outToServer.writeUTF(s);
        outToServer.flush();
    }

    public String receiveFromServer() throws IOException {
        return inFromServer.readLine();
    }

    public Player receivePlayerFromServer() {
        try {
            ObjectInputStream ois = new ObjectInputStream(inFromServer);
            return (Player) ois.readObject();
        } catch (Exception e) {
        }
        return null;
    }

    public boolean sendToServer(Player p) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(outToServer);
        oos.writeObject(p);
        return inFromServer.readBoolean();
    }

    public void play(Player p, board gameBoard) throws IOException {
        System.out.println("BEGIN");
        ////////////////////////////////////
        //game starts
        ////////////////////////////////////
        int round = 1;
        while (true) {
            int hitRow;
            int hitCol;
            System.out.println("Round " + round);
            round++;
            //the client sends a hit
            System.out.println("Please send hit:");
            sentence = inFromUser.readLine();
            System.out.println(" sent hit:" + sentence);
            sendToServer(sentence + "\n");
            //the client receives a hit from the server
            modifiedSentence = receiveFromServer();
            System.out.println("Result from P1: " + modifiedSentence);
            if (modifiedSentence.equals("You won!")) {
                break;
            }
            System.out.println("Turn P1");
            modifiedSentence = receiveFromServer();
            System.out.println("Hit from P1: " + modifiedSentence);
            int clientInt = Integer.parseInt(modifiedSentence);
            hitRow = Math.abs(clientInt / 10) - 1;
            hitCol = clientInt % 10 - 1;

            if (gameBoard.testHit(hitRow, hitCol)) {
                gameBoard.testLoss();
                if (gameBoard.testLoss() == false) {
                    hit = miss = "You won!";
                    sendToServer(hit + "\n");
                    System.out.println("Sorry, you lost!");
                    break;
                }
                sendToServer(hit + "\n");
            } else {
                sendToServer(miss + "\n");
            }
        }
    }
}
