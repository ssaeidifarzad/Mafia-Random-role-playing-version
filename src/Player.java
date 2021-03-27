import Role.*;

public class Player {
    private final String name;
    private Role role = null;
    public byte voteCount;
    private boolean silenced;
    private boolean alive;
    private boolean mafia;

    public Player(String name) {
        this.name = name;
        alive = true;
    }

    public boolean setRole(String role) {
        if (role.equalsIgnoreCase("Villager")) {
            this.role = new Villager();
            mafia = false;
            return true;
        }
        if (role.equalsIgnoreCase("Joker")) {
            this.role = new Joker();
            mafia = false;
            return true;
        }
        if (role.equalsIgnoreCase("Doctor")) {
            this.role = new Doctor();
            mafia = false;
            return true;
        }
        if (role.equalsIgnoreCase("Detective")) {
            this.role = new Detective();
            mafia = false;
            return true;
        }
        if (role.equalsIgnoreCase("Mafia")) {
            this.role = new Mafia();
            mafia = true;
            return true;
        }
        if (role.equalsIgnoreCase("Godfather")) {
            this.role = new Godfather();
            mafia = true;
            return true;
        }
        if (role.equalsIgnoreCase("Silencer")) {
            this.role = new Silencer();
            mafia = true;
            return true;
        }
        if (role.equalsIgnoreCase("Bulletproof")) {
            this.role = new Bulletproof();
            mafia = false;
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

    public boolean isSilenced() {
        return silenced;
    }
}
