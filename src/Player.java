import Role.Role;

public class Player {
    private String name;
    private Role role;
    private static Player[] players;

    public Player(String name) {
        this.name = name;
    }

    public static void setPlayers(String names) {
        String[] temp = names.split(" ");
        players = new Player[temp.length];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(temp[i]);
        }
    }
}
