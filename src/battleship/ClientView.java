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
    Player p = new Player();
    public ClientView(){}
    
    protected int startGame() throws IOException{
        //chon dang nhap hoac dang ki
        System.out.println("1. LOGIN");
        System.out.println("2. SIGN UP");
        System.out.println("Choose:");
        int choose1 = Integer.parseInt(inFromUser.readLine());
        while (true) {
            if (choose1 == 1 || choose1 == 2) {
                break;
            }
            System.out.println("Wrong choose!");
        }
        sent choose to server
        ...
        return choose1;
    }
    
    protected void  login() throws IOException{
        
        String username,password;
        while (true) {
                System.out.println("Username:");
                username = inFromUser.readLine();
                System.out.println("Password:");
                password = inFromUser.readLine();
                sent to server check
                ...
            if (response == true) {
                    break;
                }
                System.out.println("Wrong Username or Password!");
            }
        p.setName(username);
        p.setPassword(password);
    }
    
    protected void signUp(){
        while (true) {
                System.out.println("Username:");
                String username = inFromUser.readLine();
                System.out.println("Password:");
                String password = inFromUser.readLine();
                sent to server check
                ...
            if (response == true) {
                    break;
                }
                System.out.println("Username existed!");
            }
    }
    
    protected void beginGame(){
        
    }
    protected void showInfo(){
        
    }
    protected void logout(){
        
    }
    protected void showMenu(){
        
        //menu
        while (true) {
            int choose2 = 0;
            switch (choose2) {
                case 1:
                    //choi
                    beginGame();
                    break;
                case 2:
                    //xem thanh tich da dat
                    showInfo();
                    break;
                case 3:
                    //logout
                    logout();
                    break;
                default:
                    System.out.println("Wrong choice!");
            }
        }
    }
}
