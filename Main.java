/**
 * File: Modified Monopoly (Monopoly GO)
 * Authors: Ajai Jeyakaran & Andy Zhou
 * Date Created: May 29, 2023
 * Date Modified: June 19, 2023
 */

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int[] landed(String player, int index, int balance, String[] board, String[] ownership,
        int[] rentalfees) {
        Random ran = new Random();
        int dice1 = 0;
        int dice2 = 0;
        int rail = 0;
        while (dice1 == dice2) { // check dice number
            dice1 = ran.nextInt(6) + 1; // random dice 1
            dice2 = ran.nextInt(6) + 1; // random dice 2
            index += dice1;
            index += dice2;
            System.out.println(
                "Dice 1 rolled " + dice1 + "\n" + "Dice 2 rolled " + dice2 + "\nYou've rolled " + (dice1 + dice2)); // total
            // rolled
            if (dice1 == dice2) {
                System.out.println("\n" + player + " rolled a double! The dice will be rolled again.");
            }
            if (index >= 28) { // when on or passed GO TILE, awarded $200
                System.out.println("Passed GO!!!");
                balance += 200;
                index = index - 28;
            }
            System.out.println("\n" + player + " landed on " + board[index]);
            if (!(ownership[index].equals("bank")) && !(ownership[index].equals("na")) &&
                !(board[index].equals(player))) { // if a property is unowned
                if (index == 9 || index == 20) { // indexes if electric company and water works
                    rentalfees[index] = -(dice1 + dice2) * 5;
                } else if (index == 4 || index == 11 || index == 17 || index == 24) { // indexes of all the railroads
                    // each individual railroad for checking ownership
                    String owe = ownership[index];
                    if (ownership[4].equals(owe)) {
                        rail++;
                    }
                    if (ownership[11].equals(owe)) {
                        rail++;
                    }
                    if (ownership[17].equals(owe)) {
                        rail++;
                    }
                    if (ownership[24].equals(owe)) {
                        rail++;
                    }
                    rentalfees[index] = -rail * 50;
                }
            }
            ran.nextInt();
        }
        int[] arr = {
            index,
            balance,
            rentalfees[index]
        };
        return arr;
    }

    public static String validation1(String num) { // help validate the player input
        Scanner i = new Scanner(System.in);
        System.out.print("Player " + num + ": ");
        String player = i.nextLine();
        while (player.trim().equals("") || player.trim().equals("") || player.trim().length() < 2) {
            System.out.print("Invalid, Player " + num + ": ");
            player = i.nextLine();
        }
        return player;
    }

    public static String validation2(int balance, int[] price, int index) { // method for validating property input
        Scanner i = new Scanner(System.in);
        System.out.print("Type \"buy\" to purchase or \"auction\" to the market: ");
        String input = i.nextLine();
        while (!(input.equals("buy") || input.equals("auction")) || -price[index] > balance) {
            if (input.equals("buy") && -price[index] > balance) {
                System.out.print("Invalid, You can't afford this property with your balance of $" + balance + ": ");
                input = i.nextLine();
            } else {
                System.out.print("Invalid, enter \"buy\" to purchase or \"auction\" to the market: ");
                input = i.nextLine();
            }
        }
        return input;
    }

    public static int validation3(String num, int bidding, int balance) { // method for validating auction
        Scanner input = new Scanner(System.in);
        int counter = 0;
        boolean flag = true;
        String playerbid = "";
        while (flag) {
            System.out.print("Player " + num + "'s " + "bid: ");
            playerbid = input.nextLine();
            String[] arr = playerbid.split("");
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals("")) {
                    counter = 1;
                } else if (!((int) arr[i].charAt(0) >= 48 && (int) arr[i].charAt(0) <= 57)) { // checks if a number
                    counter = 1;
                }
            }
            if (counter == 0) {
                if (Integer.parseInt(playerbid) == 0) {
                    flag = false;
                } else if (Integer.parseInt(playerbid) > bidding && Integer.parseInt(playerbid) <= balance) {
                    flag = false;
                }
            }
            counter = 0;
        }
        return Integer.parseInt(playerbid);
    }

    public static int balance(String player, int index, int balance, String[] ownership, int[] price, int[] rentalfees,
        String[] board, int player1bal, int player2bal, int player3bal, int player4bal) {
        Scanner i = new Scanner(System.in);
        if (ownership[index].equals("bank")) {
            if (rentalfees[index] != 0) {
                System.out.println("\nYou can buy it for $" + -price[index] + ", you have $" + balance +
                    " and it has a rental fee of $" + -rentalfees[index]);
            } else {
                System.out.println("\nYou can buy it for $" + -price[index] + ", you have $" + balance);
            }
            String choice = validation2(balance, price, index);
            choice = choice.toLowerCase();
            if (choice.equals("buy")) {
                balance += price[index];
                ownership[index] = player;
                System.out.println(player + "'s Current Balance $" + balance);
            } else if (choice.equals("auction")) {
                System.out.println("\nEnter the amount you would like to bid for " + board[index] +
                    " if you wish to stop or not bid at all enter 0 when it's your turn.");
                int bidding = 0;
                boolean player1 = true;
                boolean player2 = true;
                boolean player3 = true;
                boolean player4 = true;
                String winner = "";
                int totalplay = 4;
                String[] arr = new String[1];

                while (totalplay != 1) {
                    int bid1 = 0;
                    int bid2 = 0;
                    int bid3 = 0;
                    int bid4 = 0;
                    if (player1 && totalplay != 1) {
                        bid1 = validation3("1", bidding, player1bal);
                        if (bid1 == 0) {
                            player1 = false;
                            totalplay--;
                        } else if (bid1 > bidding) {
                            bidding = bid1;
                        }
                    }
                    if (player2 && totalplay != 1) {
                        bid2 = validation3("2", bidding, player2bal);
                        if (bid2 == 0) {
                            player2 = false;
                            totalplay--;
                        } else if (bid2 > bidding) {
                            bidding = bid2;
                        }
                    }
                    if (player3 && totalplay != 1) {
                        bid3 = validation3("3", bidding, player3bal);
                        if (bid3 == 0) {
                            player3 = false;
                            totalplay--;
                        } else if (bid3 > bidding) {
                            bidding = bid3;
                        }
                    }
                    if (player4 && totalplay != 1) {
                        bid4 = validation3("4", bidding, player4bal);
                        if (bid4 == 0) {
                            player4 = false;
                            totalplay--;
                        } else if (bid4 > bidding) {
                            bidding = bid4;
                        }
                    }
                    if (bid1 == bidding) {
                        winner = "player1";
                    } else if (bid2 == bidding) {
                        winner = "player2";
                    } else if (bid3 == bidding) {
                        winner = "player3";
                    } else {
                        winner = "player4";
                    }
                }
                System.out.println(winner);
                arr = new String[] {
                    -bidding + "", winner
                };
            }

        } else if (!(ownership[index].equals("na")) && !(ownership[index].equals(player))) {
            System.out.println("You owe " + ownership[index] + " $" + -rentalfees[index]);
            balance += rentalfees[index];
        } else {
            if (board[index].equals("Jail Cell")) {
                System.out.println("nothing happens, you're just visiting!");
            } else if (board[index].equals("Free Parking")) {
                System.out.println("nothing happens here!");
            } else if (board[index].equals("GO TO JAIL")) {
                System.out.println(
                    "go directly to jail, you won't be able to roll the dice for the following three turns");
            } else if (board[index].equals("Luxury Tax")) {
                balance -= 100;
                System.out.println("You paid a total of $100 towards tax");
            } else if (board[index].equals("Income Tax")) {
                balance -= 200;
                System.out.println("You paid a total of $200 towards tax");
                System.out.println(player + "'s Balance: $" + balance);
            } else if (board[index].equals("GO")) {
                System.out.println("Current Balance is $" + balance);
            }
        }
        return balance;
    }

    public static int[] jail(int balance, int jail) { // the jail cell
        Scanner i = new Scanner(System.in);
        System.out.print("You can get out of jail right now by paying $50 would you like to? (\"yes\" or \"no\"): "); // pay $50 or wait 3 turns

        String choice = i.nextLine();
        while (!(choice.toLowerCase().equals("yes")) && !(choice.toLowerCase().equals("no"))) {
            System.out.print("invalid, would you like to pay $50 to get out jail + your current balance is $" + balance + ": ");
            choice = i.nextLine();
        }
        if (choice.equals("yes")) {
            balance -= 50;
            System.out.println("Current Balance is $" + balance);
            jail = 4;
        }
        int[] arr = new int[] {
            balance,
            jail
        };
        return arr;
    }

    public static String[] bankrupt(int balance, String[] ownership, String player) { // check for bankruptcy in players 
        System.out.println(player + " is now bankrupt they have a balance of $" + balance +
            " all their assets will now be unowned and free to be purchased if landed on");
        for (int i = 0; i < ownership.length; i++) {
            if (ownership[i].equals(player)) {
                ownership[i] = "bank";
            }
        }
        return ownership;
    }

    public static void announcement(int player1bal, int player2bal, int player3bal, int player4bal, String player1, String player2, String player3, String player4) { // checks who doesn''t have a balance less than 0
        if (player1bal > 0) {
            System.out.println("Congratulations " + player1 + " your are the winner of this game with $" + player1bal);
        } else if (player2bal > 0) {
            System.out.println("Congratulations " + player2 + " your are the winner of this game with $" + player2bal);
        } else if (player3bal > 0) {
            System.out.println("Congratulations " + player3 + " your are the winner of this game with $" + player3bal);
        } else if (player4bal > 0) {
            System.out.println("Congratulations " + player4 + " your are the winner of this game with $" + player4bal);
        }
    }

    public static void main(String[] args) {
        int totalplayer = 4; // 4 players default
        int player1bal = 1500; // balances of the start of game
        int player2bal = 1500;
        int player3bal = 1500;
        int player4bal = 1500;
        int index1 = 0; // assumes everyone starts at GO
        int index2 = 0;
        int index3 = 0;
        int index4 = 0;
        int jail1 = 0; // asssumes everyone not in jail
        int jail2 = 0;
        int jail3 = 0;
        int jail4 = 0;
        int[] price = { 200, -50, -60, -200, -200, -100, -100, 0, -140, -150, -140, -200, -180, -180, 0, -220, -220,
                -200, -260, -260, -150, 0, -300, -300, -200, -350, -100, -400 };// the prices of the properties
        String[] ownership = { "na", "bank", "bank", "na", "bank", "bank", "bank", "na", "bank", "bank", "bank", "bank",
                "bank", "bank", "na", "bank", "bank", "bank", "bank", "bank", "bank", "na", "bank", "bank", "bank",
                "bank", "na", "bank" }; // who owns the property
        int[] rentalfees = { 0, -77, -79, 0, 0, -81, -81, 0, -85, 0, -85, 0, -89, -89, 0, -93, -93, 0, -97,
                -97, 0, 0, -101, -101, 0, -110, 0, -125 }; // rental cost of properties
        String[] board = { "GO", "Mediterranean Avenue", "Baltic Avenue", "Income Tax", "Reading Railroad",
                "Oriental Avenue", "Vermont Avenue", "Jail Cell", "St. Charles Place", "Electric Company",
                "States Avenue", "Pennsylvania Railroad", "St. James Place", "Tennessee Avenue", "Free Parking",
                "Kentucky Avenue", "Indiana Avenue", "B. & O. Railroad", "Atlantic Avenue", "Ventnor Avenue",
                "Water Works", "GO TO JAIL", "Pacific Avenue", "North Carolina Avenue", "Short Line",
                "Park               Place", "Luxury Tax", "Boardwalk" }; // the board tiles

        Scanner i = new Scanner(System.in);
        System.out.println("Welcome to Monopoly GO created by Ajai Jeyakaran and Andy Zhou. Enjoy the game!!!\n");
        String player1 = validation1("1");
        String player2 = validation1("2");
        while (player2.toLowerCase().equals(player1.toLowerCase())) { //check to see if any if the player has the same name and ask for input again
            System.out.print("Invalid, ");
            player2 = validation1("2");
        }

        String player3 = validation1("3");
        while (player3.toLowerCase().equals(player1.toLowerCase()) ||
            player3.toLowerCase().equals(player2.toLowerCase())) {
            System.out.print("Invalid, ");
            player3 = validation1("3");
        }

        String player4 = validation1("4");
        while (player4.toLowerCase().equals(player1.toLowerCase()) ||
            player4.toLowerCase().equals(player2.toLowerCase()) ||
            player4.toLowerCase().equals(player3.toLowerCase())) {
            System.out.print("Invalid, ");
            player4 = validation1("4");
        }

        while (totalplayer != 1) {
            System.out.print("\n" + player1 + "'s turn:");
            i.nextLine();
            if (jail1 == 0) {
                int[] arr = landed(player1, index1, player1bal, board, ownership, rentalfees);
                index1 = arr[0];
                player1bal = arr[1];
                if (index1 == 4 || index1 == 11 || index1 == 17 || index1 == 24 || index1 == 9 || index1 == 20) {
                    rentalfees[index1] = arr[2]; //railroad checking
                }
                player1bal = balance(player1, index1, player1bal, ownership, price, rentalfees, board, player1bal,
                    player2bal, player3bal, player4bal);
                if (!(ownership[index1].equals("na")) && !(ownership[index1].equals("bank")) &&
                    !(ownership[index1].equals(player1))) {
                    if (ownership[index1].equals(player2)) {
                        player2bal += rentalfees[index1];
                        System.out.println(player2 + "'s current balance is $" + player2bal);
                    } else if (ownership[index1].equals(player3)) {
                        player3bal += rentalfees[index1];
                        System.out.println(player3 + "'s current balance is $" + player3bal);
                    } else if (ownership[index1].equals(player4)) {
                        player4bal += rentalfees[index1];
                        System.out.println(player4 + "'s current balance is $" + player4bal);
                    }
                    System.out.println(player1 + "'s current balance is $" + player1bal);
                }
                if (player1bal < 0) { //when balance less than 0
                    ownership = bankrupt(player1bal, ownership, player1);
                    totalplayer--;
                    jail1 = 10;
                }
                if (board[index1].equals("GO TO JAIL")) { //if land on GO TO JAIL TILE
                    jail1 = 1;
                }
            } else if (player1bal > 0) {
                int[] balancejail = jail(player1bal, jail1);
                player1bal = balancejail[0];
                jail1 = balancejail[1];
                if (jail1 == 4) {
                    jail1 = 0;
                } else {
                    System.out.println("You are in jail... you still have " + (4 - jail1 + "") + " turns left until you're out of jail");
                    jail1++;
                }
            } else {
                System.out.println("You are bankrupt.");
            }
            System.out.print("\n" + player2 + "'s turn:");
            i.nextLine();
            if (jail2 == 0) {
                int[] arr = landed(player2, index2, player2bal, board, ownership, rentalfees);
                index2 = arr[0];
                player2bal = arr[1];
                if (index2 == 4 || index2 == 11 || index2 == 17 || index2 == 24 || index2 == 9 || index2 == 20) {
                    rentalfees[index2] = arr[2];
                }
                player2bal = balance(player2, index2, player2bal, ownership, price, rentalfees, board, player1bal,
                    player2bal, player3bal, player4bal);
                if (!(ownership[index2].equals("na")) && !(ownership[index2].equals("bank"))) {
                    if (ownership[index2].equals(player1)) {
                        player1bal -= rentalfees[index2];
                        System.out.println(player1 + "'s current balance is $" + player1bal);
                    } else if (ownership[index2].equals(player3)) {
                        player3bal -= rentalfees[index2];
                        System.out.println(player3 + "'s current balance is $" + player3bal);
                    } else if (ownership[index2].equals(player4)) {
                        player4bal -= rentalfees[index2];
                        System.out.println(player4 + "'s current balance is $" + player4bal);
                    }
                    System.out.println(player2 + "'s current balance is $" + player2bal);
                }
                if (player2bal < 0) {
                    ownership = bankrupt(player2bal, ownership, player2);
                    totalplayer--;
                    jail2 = 10;
                }
                if (board[index2].equals("GO TO JAIL")) {
                    jail2 = 1;
                }
            } else if (player2bal > 0) {
                int[] balancejail = jail(player2bal, jail2);
                player2bal = balancejail[0];
                jail2 = balancejail[1];
                if (jail2 == 4) {
                    jail2 = 0;
                } else {
                    System.out.println("You are in jail... you still have " + (4 - jail2 + "") +
                        " turns left until you're out of jail");
                    jail2++;
                }
            } else {
                System.out.println("You are bankrupt.");
            }

            System.out.print("\n" + player3 + "'s turn:");
            i.nextLine();
            if (jail3 == 0) {
                int[] arr = landed(player3, index3, player3bal, board, ownership, rentalfees);
                index3 = arr[0];
                player3bal = arr[1];
                if (index3 == 4 || index3 == 11 || index3 == 17 || index3 == 24 || index3 == 9 || index3 == 20) {
                    rentalfees[index3] = arr[2];
                }
                player3bal = balance(player3, index3, player3bal, ownership, price, rentalfees, board, player1bal,
                    player2bal, player3bal, player4bal);
                if (!(ownership[index3].equals("na")) &&
                    !(ownership[index3].equals("bank"))) {
                    if (ownership[index3].equals(player1)) {
                        player1bal -= rentalfees[index3];
                        System.out.println(player1 + "'s current balance is $" + player1bal);
                    } else if (ownership[index3].equals(player2)) {
                        player2bal -= rentalfees[index3];
                        System.out.println(player2 + "'s current balance is $" + player2bal);
                    } else if (ownership[index3].equals(player4)) {
                        player4bal -= rentalfees[index3];
                        System.out.println(player4 + "'s current balance is $" + player4bal);
                    }
                    System.out.println(player3 + "'s current balance is $" + player3bal);
                }
                if (player3bal < 0) {
                    ownership = bankrupt(player3bal, ownership, player3);
                    totalplayer--;
                    jail3 = 10;
                }
                if (board[index3].equals("GO TO JAIL")) {
                    jail3 = 1;
                }
            } else if (player3bal > 0) {
                int[] balancejail = jail(player3bal, jail3);
                player3bal = balancejail[0];
                jail3 = balancejail[1];
                if (jail3 == 4) {
                    jail3 = 0;
                } else {
                    System.out.println("You are in jail... you still have " + (4 - jail2 + "") +
                        " turns left until you're out of jail");
                    jail2++;
                }
            } else {
                System.out.println("You are bankrupt.");
            }

            System.out.print("\n" + player4 + "'s turn:");
            i.nextLine();
            if (jail4 == 0) {
                int[] arr = landed(player4, index4, player4bal, board, ownership, rentalfees);
                index4 = arr[0];
                player4bal = arr[1];
                if (index4 == 4 || index4 == 11 || index4 == 17 || index4 == 24 || index4 == 9 || index4 == 20) {
                    rentalfees[index4] = arr[2];
                }
                player4bal = balance(player4, index4, player4bal, ownership, price, rentalfees, board, player1bal,
                    player2bal, player3bal, player4bal);
                if (!(ownership[index4].equals("na")) &&
                    !(ownership[index4].equals("bank"))) {
                    if (ownership[index4].equals(player1)) {
                        player1bal -= rentalfees[index4];
                        System.out.println(player1 + "'s current balance is $" + player1bal);
                    } else if (ownership[index4].equals(player2)) {
                        player2bal -= rentalfees[index4];
                        System.out.println(player2 + "'s current balance is $" + player2bal);
                    } else if (ownership[index4].equals(player3)) {
                        player3bal -= rentalfees[index4];
                        System.out.println(player3 + "'s current balance is $" + player3bal);
                    }
                    System.out.println(player4 + "'s current balance is $" + player4bal);
                }
                if (player4bal < 0) {
                    ownership = bankrupt(player4bal, ownership, player4);
                    totalplayer--;
                    jail4 = 10;
                }
                if (board[index4].equals("GO TO JAIL")) {
                    jail4 = 1;
                }
            } else if (player4bal > 0) {
                int[] balancejail = jail(player4bal, jail4);
                player4bal = balancejail[0];
                jail4 = balancejail[1];
                if (jail4 == 4) {
                    jail4 = 0;
                } else {
                    System.out.println("You are in jail... you still have " + (4 - jail2 + "") +
                        " turns left until you're out of jail");
                    jail2++;
                }
            } else {
                System.out.println("You are bankrupt.");
            }
        }
        announcement(player1bal, player2bal, player3bal, player4bal, player1, player2, player3, player4);
    }
}