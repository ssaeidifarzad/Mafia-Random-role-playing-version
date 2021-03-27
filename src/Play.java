import Role.Joker;

import java.util.Arrays;
import java.util.Scanner;

public class Play {
    static Player[] players = new Player[0];
    static int numOfPlayersWithRole = 0;
    static int day = 1;
    static int numOfPlayersHasRightToVote;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean gameCreated = false, gameStarted = false, gameIsRunning = true;
        boolean vote = false;
        String command;
        game:
        while (gameIsRunning) {
            command = scanner.next();
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
                                numOfPlayersWithRole++;
                            }
                            break;
                        }
                        if (i == players.length - 1) {
                            System.out.println("user not found");
                        }
                    }
                    break;
                case "start_game":
                    if (numOfPlayersWithRole != players.length) {
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
                System.out.println("Day " + day);
                int voters = 0;
                while (vote) {
                    String voter = scanner.next(), selected = scanner.next();
                    int voterIndex = -1, selectedIndex = -1;
                    for (int i = 0; i < players.length; i++) {
                        if (voter.equalsIgnoreCase(players[i].getName())) {
                            voterIndex = i;
                        }
                        if (selected.equalsIgnoreCase(players[i].getName())) {
                            selectedIndex = i;
                        }
                    }
                    if (voterIndex == -1 || selectedIndex == -1) {
                        System.out.println("user not found");
                        continue;
                    }
                    if (players[voterIndex].isSilenced()) {
                        System.out.println("voter is silenced");
                        continue;
                    }
                    if (!players[selectedIndex].isAlive()) {
                        System.out.println("selected player already dead");
                        continue;
                    }
                    players[selectedIndex].voteCount++;
                    voters++;
                    if (voters == numOfPlayersHasRightToVote) {
                        command = scanner.next();
                        while (!command.equals("end_vote")) {
                            System.out.println("everybody has voted, end election");
                            command = scanner.next();
                        }
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
                            clearVotes();
                            vote = false;
                            break;
                        }
                        if (players[max2].getRole().toString().equals("Joker")) {
                            System.out.println("Joker won!");
                            break game;
                        }
                        players[max2].setAlive(false);
                        System.out.println(players[max2].getName() + " died");
                        clearVotes();
                        numOfPlayersHasRightToVote--;
                        break;
                    }
                }
            }
        }
    }

    public static void setPlayers(String names) {
        String[] temp = names.split(" ");
        players = new Player[temp.length];
        numOfPlayersHasRightToVote = players.length;
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
