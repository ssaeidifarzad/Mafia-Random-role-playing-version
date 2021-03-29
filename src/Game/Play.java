package Game;

import Role.Detective;
import Role.Silencer;

import java.util.Arrays;
import java.util.Scanner;

public class Play {
    static Player[] players = new Player[0];

    static int day = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean gameCreated = false, gameStarted = false, gameIsRunning = true;
        boolean vote = false, night, getCommand = true;
        String command = "";
        game:
        while (gameIsRunning) {
            if (getCommand) {
                command = scanner.next();
            }
            switch (command) {
                case "create_game":
                    if (!gameCreated) {
                        String names = scanner.nextLine();
                        setPlayers(names.substring(1));
                        gameCreated = true;
                    } else {
                        System.out.println("game has already been created");
                    }
                    break;
                case "assign_role":
                    if (!gameCreated) {
                        System.out.println("no game created");
                        break;
                    }
                    String name = scanner.next();
                    for (int i = 0; i < players.length; i++) {
                        if (players[i].getName().equalsIgnoreCase(name)) {
                            boolean temp = players[i].setRole(scanner.next());
                            if (!temp) {
                                System.out.println("role not found");
                            } else {
                                Player.addNumOfPlayersWithRole();
                            }
                            break;
                        }
                        if (i == players.length - 1) {
                            System.out.println("user not found");
                        }
                    }
                    break;
                case "start_game":
                    if (Player.getNumOfPlayersWithRole() != players.length) {
                        System.out.println("one or more players do not have a role");
                        break;
                    }
                    if (!gameCreated) {
                        System.out.println("no game created");
                        break;
                    }
                    if (gameStarted) {
                        System.out.println("game has already been started");
                        break;
                    }
                    gameStarted = true;
                    for (Player player : players) {
                        System.out.println(player.getName() + ": " + player.getRole().toString());
                    }
                    vote = true;
            }
            if (gameStarted) {
                String survivor = "", mafiaTarget = "", silent = "", dead = "";
                boolean mafiaTargetDies = true;
                if (day == 1) {
                    System.out.println("Day " + day);
                } else {
                    System.out.println("Day " + day);
                    System.out.println("mafia tried to kill " + mafiaTarget);
                    if (mafiaTargetDies) {
                        System.out.println(dead + " was killed");
                    } else {
                        System.out.println(mafiaTarget + " survived");
                    }
                    System.out.println("Silenced " + silent);
                }
                while (vote) {
                    command = scanner.nextLine();
                    if (command.equals("end_vote")) {
                        vote = false;
                    }
                    if (vote) {
                        String[] names = command.split(" ");
                        if (names.length != 2) {
                            System.out.println("invalid command");
                            continue;
                        }
                        String firstPlayer = names[0], secondPlayer = names[1];
                        int voterIndex = -1, selectedIndex = -1;
                        for (int i = 0; i < players.length; i++) {
                            if (firstPlayer.equalsIgnoreCase(players[i].getName())) {
                                voterIndex = i;
                            }
                            if (secondPlayer.equalsIgnoreCase(players[i].getName())) {
                                selectedIndex = i;
                            }
                        }
                        if (voterIndex == -1 || selectedIndex == -1) {
                            System.out.println("user not found");
                            continue;
                        }
                        if (players[voterIndex].isSilent()) {
                            System.out.println("voter is silenced");
                            continue;
                        }
                        if (!players[selectedIndex].isAlive()) {
                            System.out.println("selected player already dead");
                            continue;
                        }
                        players[selectedIndex].voteCount++;
                    } else {
                        int[] voteCounts = new int[players.length];
                        for (int i = 0; i < players.length; i++) {
                            voteCounts[i] = players[i].voteCount;
                        }
                        int max1 = voteCounts[0], max2 = 0;
                        for (int i = 0; i < voteCounts.length; i++) {
                            if (max1 < voteCounts[i]) {
                                max1 = voteCounts[i];
                                max2 = i;
                            }
                        }
                        Arrays.sort(voteCounts);
                        if (voteCounts[voteCounts.length - 1] == voteCounts[voteCounts.length - 2]) {
                            System.out.println("nobody died");
                            vote = false;
                        } else if (players[max2].getRole().toString().equals("Joker")) {
                            System.out.println("Joker won!");
                            break game;
                        } else {
                            players[max2].setAlive(false);
                            System.out.println(players[max2].getName() + " died");
                        }
                        clearVotes();
                    }
                }
                night = true;
                System.out.println("Night " + day);
                day++;
                for (Player player : players) {
                    if (player.isPerformAtNight() && player.isAlive()) {
                        System.out.println(player.getName() + ": " + player.getRole().toString());
                    }
                }
                while (night) {
                    command = scanner.nextLine();
                    if (command.equals("end_night")) {
                        night = false;
                    }
                    if (night) {
                        String[] rolePerformingNames = command.split(" ");
                        if (rolePerformingNames.length != 2) {
                            System.out.println("invalid command");
                            continue;
                        }
                        String firstPlayer = rolePerformingNames[0], secondPlayer = rolePerformingNames[1];
                        int firstPlayerIndex = -1, secondPlayerIndex = -1;
                        for (int i = 0; i < players.length; i++) {
                            if (firstPlayer.equalsIgnoreCase(players[i].getName())) {
                                firstPlayerIndex = i;
                            }
                            if (secondPlayer.equalsIgnoreCase(players[i].getName())) {
                                secondPlayerIndex = i;
                            }
                        }
                        if (firstPlayerIndex == -1 || secondPlayerIndex == -1) {
                            System.out.println("user not found");
                            continue;
                        }
                        if (!players[firstPlayerIndex].isPerformAtNight()) {
                            System.out.println("user can not wake up during night");
                        }
                        if (!players[firstPlayerIndex].isAlive()) {
                            System.out.println("user is dead");
                        }
                        if (players[firstPlayerIndex].getRole().toString().equalsIgnoreCase("Silencer") && !((Silencer) players[firstPlayerIndex].getRole()).isVoting()) {
                            players[secondPlayerIndex].setSilent(true);
                            ((Silencer) players[firstPlayerIndex].getRole()).voting(true);
                            continue;
                        }
                        if (players[firstPlayerIndex].getRole().toString().equalsIgnoreCase("Detective")) {
                            if (((Detective) players[firstPlayerIndex].getRole()).isDetectionLimit()) {
                                System.out.println("detective has already asked");
                                continue;
                            }
                            if (!players[secondPlayerIndex].isAlive()) {
                                System.out.println("suspect is dead");
                                continue;
                            }
                            if (players[secondPlayerIndex].isMafia() && !players[secondPlayerIndex].getRole().toString().equalsIgnoreCase("Godfather")) {
                                System.out.println("Yes");
                            } else {
                                System.out.println("No");
                            }
                            ((Detective) players[firstPlayerIndex].getRole()).setDetectionLimit(true);
                            continue;
                        }
                        if (players[firstPlayerIndex].getRole().toString().equalsIgnoreCase("Doctor")) {
                            survivor = secondPlayer;
                        }
                        if (players[firstPlayerIndex].isMafia()) {
                            if (!players[secondPlayerIndex].isAlive()) {
                                System.out.println("player is already dead");
                                continue;
                            }
                            players[secondPlayerIndex].voteCount++;
                        }
                    } else {
                        int[] voteCounts = new int[Player.getNumOfPlayersWithRole() - Player.getMafiaCount()];
                        for (int i = 0, j = 0; i < players.length; i++) {
                            while (players[i].isMafia()) {
                                i++;
                            }
                            voteCounts[j] = players[i].voteCount;
                        }
                        int max1 = voteCounts[0], max2 = 0, max3 = voteCounts[0], max4 = 0;
                        for (int i = 0; i < voteCounts.length; i++) {
                            if (max1 < voteCounts[i]) {
                                max1 = voteCounts[i];
                                max2 = i;
                            }
                        }
                        for (int i = 0; i < voteCounts.length; i++) {
                            if (i == max2) {
                                continue;
                            }
                            if (max3 < voteCounts[i]) {
                                max3 = voteCounts[i];
                                max4 = i;
                            }
                        }
                        Arrays.sort(voteCounts);
                        if (voteCounts[voteCounts.length - 1] == voteCounts[voteCounts.length - 2]) {
                            mafiaTarget = players[max4].getName() + " and " + players[max2].getName();
                            if (voteCounts[voteCounts.length - 2] == voteCounts[voteCounts.length - 3]) {
                                mafiaTargetDies = false;
                            } else {
                                if (survivor.equalsIgnoreCase(players[max2].getName())) {
                                    dead = players[max4].getName();
                                    players[max4].setAlive(false);
                                    mafiaTargetDies = true;
                                } else if (survivor.equalsIgnoreCase(players[max4].getName())) {
                                    dead = players[max2].getName();
                                    players[max2].setAlive(false);
                                    mafiaTargetDies = true;
                                } else {
                                    mafiaTarget = "nobody";
                                    mafiaTargetDies = false;
                                }
                            }
                        } else {
                            mafiaTarget = players[max2].getName();
                            dead = players[max2].getName();
                            if (survivor.equalsIgnoreCase(players[max2].getName())) {
                                mafiaTargetDies = false;
                            } else {
                                mafiaTargetDies = true;
                                players[max2].setAlive(false);
                            }
                        }
                        getCommand = false;
                        clearVotes();
                    }
                }
            }
        }
    }

    public static void setPlayers(String names) {
        String[] temp = names.split(" ");
        players = new Player[temp.length];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(temp[i]);
        }
    }

    public static void clearVotes() {
        for (Player player : players) {
            player.voteCount = 0;
        }
    }
}
