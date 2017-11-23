/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author namch
 */
public class ClientView {

    BufferedReader inFromUser = new BufferedReader(
            new InputStreamReader(System.in));
    ClientController controller = new ClientController();
    Player p = new Player();

    public ClientView() {
    }

    protected void startGame() {
        //chon dang nhap hoac dang ki
        try {
            String choose1;
            while (true) {
                System.out.println("1. LOGIN");
                System.out.println("2. SIGN UP");
                System.out.println("Choose:");
                choose1 = inFromUser.readLine();
                if (choose1.equals("1") || choose1.equals("2")) {
                    break;
                }
                System.out.println("Wrong choose!");
            }
            // sent choose to server
            controller.sendToServer(choose1);
            if (choose1.equals("1")) {
                login();
            } else {
                signUp();
            }
            showMenu();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void login() throws IOException {

        String username, password;
        while (true) {
            System.out.println("Username:");
            username = inFromUser.readLine();
            System.out.println("Password:");
            password = inFromUser.readLine();
            Player p2 = new Player(username, password);
            boolean response = controller.sendToServer(p2);
            if (response == true) {
                break;
            }
            System.out.println("Wrong Username or Password!");
        }
        System.out.println("Wellcome back !");
        p.setName(username);
        p.setPassword(password);
    }

    protected void signUp() throws IOException {
        String username, password;
        while (true) {
            System.out.println("Username:");
            username = inFromUser.readLine();
            System.out.println("Password:");
            password = inFromUser.readLine();
            Player p = new Player(username, password);
            boolean response = controller.sendToServer(p);
            if (response == true) {
                break;
            }
            System.out.println("Username existed!");
        }
        System.out.println("Register success.");
        p.setName(username);
        p.setPassword(password);
    }

    protected void beginGame() throws IOException {
        //create the board
        board gameBoard = new board();
        //player is asked to add the ships to the board
        System.out.println("Below you can input where you want to"
                + " place your battleships.\n Please enter them in integers"
                + " starting with the row followed by columns\n (for example"
                + " start with the head as 11 for row 1,\n column 1 and "
                + " tail as 51 for row 5 and column 1)\nPlease input the data\n "
                + "left to right and top to bottom"
                + "Type q when done.");
        while (true) {
            System.out.println("Please enter head location:");
            String line1 = inFromUser.readLine();
            System.out.println("Please enter tail location:");
            String line2 = inFromUser.readLine();
            if (!line1.equals("q") || !line2.equals("q")) {
                int head = Integer.parseInt(line1);
                int tail = Integer.parseInt(line2);
                //we call the testPos method to verify that we can place
                //the battleship at the inputed locations
                gameBoard.testPos(head, tail);
                if (gameBoard.boatBool == true) {
                    gameBoard.createBoat(head, tail);
                    System.out.println("Creating boat at " + head + " and " + tail);
                } else {
                    System.out.println("Sorry, can't place the battleship using these locations.");
                }
            } else {
                break;
            }
        }

        gameBoard.printBoard();
        System.out.println("Wait P1...");
        controller.sendToServer("ready");
        while (true) {
            String state = controller.receiveFromServer();
            if (state == "ready") {
                break;
            }
        }

        //start game
        controller.play(p, gameBoard);
    }

    protected void showInfo() throws IOException {
        System.out.println("Enter ID of player you want to show info: ");
        String id = inFromUser.readLine();
        controller.sendToServer(id);
        Player p2 = controller.receivePlayerFromServer();
        if(p2 == null){
            System.out.println("Player is not exist.");
            return;
        }
            
        System.out.println("=====================================");
        System.out.println("Player " + id);
        System.out.println("Name : " + p2.getName());
        System.out.println("Game : " + p2.getGame());
        System.out.println("Win : " + p2.getWin());
        System.out.println("=====================================");
    }

    protected void logout() {
        p = null;
    }

    protected void showMenu() {
        try {

            //menu
            while (true) {
                System.out.println("===========Menu===========");
                System.out.println("1. Play");
                System.out.println("2. Search Player Info by Id");
                System.out.println("3. Logout");
                System.out.println("Please Choose:");
                int choose2;
                choose2 =Integer.parseInt(inFromUser.readLine());
                switch (choose2) {
                    case 1:
                        //choi
                        controller.sendToServer("1");
                        beginGame();
                        break;
                    case 2:
                        //xem thanh tich da dat
                        controller.sendToServer("2");
                        showInfo();
                        break;
                    case 3:
                        //logout
                        controller.sendToServer("3");
                        logout();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Wrong choice!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
