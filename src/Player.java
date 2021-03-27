import Role.*;

public class Player {
    private final String name;
    private Role role = null;
    public byte voteCount;
    private boolean silenced;
    private boolean alive;

    public Player(String name) {
        this.name = name;
        alive = true;
    }

    public boolean setRole(String role) {
        if (role.equalsIgnoreCase("Villager")) {
            this.role = new Villager();
            return true;
        }
        if (role.equalsIgnoreCase("Joker")) {
            this.role = new Joker();
            return true;
        }
        if (role.equalsIgnoreCase("Doctor")) {
            this.role = new Doctor();
            return true;
        }
        if (role.equalsIgnoreCase("Detective")) {
            this.role = new Detective();
            return true;
        }
        if (role.equalsIgnoreCase("Mafia")) {
            this.role = new Mafia();
            return true;
        }
        if (role.equalsIgnoreCase("Godfather")) {
            this.role = new Godfather();
            return true;
        }
        if (role.equalsIgnoreCase("Silencer")) {
            this.role = new Silencer();
            return true;
        }
        if (role.equalsIgnoreCase("Bulletproof")) {
            this.role = new Bulletproof();
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
