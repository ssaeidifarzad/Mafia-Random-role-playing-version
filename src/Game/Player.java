package Game;

import Role.*;

public class Player {
    private static int numOfPlayersWithRole = 0;
    private static int mafiaCount = 0;
    private final String name;
    private Role role = null;
    public byte voteCount;
    private boolean silent;
    private boolean alive;
    private boolean mafia;
    private boolean performAtNight;

    public Player(String name) {
        this.name = name;
        alive = true;
    }

    public static void addNumOfPlayersWithRole() {
        numOfPlayersWithRole++;
    }

    public static int getMafiaCount() {
        return mafiaCount;
    }

    public static int getNumOfPlayersWithRole() {
        return numOfPlayersWithRole;
    }

    public boolean setRole(String role) {
        if (role.equalsIgnoreCase("Villager")) {
            this.role = new Villager();
            mafia = false;
            performAtNight = false;
            return true;
        }
        if (role.equalsIgnoreCase("Joker")) {
            this.role = new Joker();
            mafia = false;
            performAtNight = false;
            return true;
        }
        if (role.equalsIgnoreCase("Doctor")) {
            this.role = new Doctor();
            mafia = false;
            performAtNight = true;
            return true;
        }
        if (role.equalsIgnoreCase("Detective")) {
            this.role = new Detective();
            mafia = false;
            performAtNight = true;
            return true;
        }
        if (role.equalsIgnoreCase("Mafia")) {
            this.role = new Mafia();
            mafia = true;
            performAtNight = true;
            mafiaCount++;
            return true;
        }
        if (role.equalsIgnoreCase("Godfather")) {
            this.role = new Godfather();
            mafia = true;
            performAtNight = true;
            mafiaCount++;
            return true;
        }
        if (role.equalsIgnoreCase("Silencer")) {
            this.role = new Silencer();
            mafia = true;
            performAtNight = true;
            mafiaCount++;
            return true;
        }
        if (role.equalsIgnoreCase("Bulletproof")) {
            this.role = new Bulletproof();
            mafia = false;
            performAtNight = false;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public boolean isMafia() {
        return mafia;
    }

    public boolean isPerformAtNight() {
        return performAtNight;
    }
}
