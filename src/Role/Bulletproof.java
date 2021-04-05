package Role;

public class Bulletproof implements Role {
private boolean shield;
    @Override
    public String toString() {
        return "Bulletproof";
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }
}
