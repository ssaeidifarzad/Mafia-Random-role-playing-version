package Role;

public class Detective implements Role {
    private boolean detectionLimit = false;

    @Override
    public String toString() {
        return "Detective";
    }


    public boolean hasDetectionLimit() {
        return detectionLimit;
    }

    public void setDetectionLimit(boolean detectionLimit) {
        this.detectionLimit = detectionLimit;
    }
}
