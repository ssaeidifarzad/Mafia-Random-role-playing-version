package Role;

public class Silencer implements Role {
    private boolean voting = false;

    @Override
    public String toString() {
        return "Silencer";
    }



    public boolean isVoting() {
        return voting;
    }

    public void voting(boolean voting) {
        this.voting = voting;
    }
}
