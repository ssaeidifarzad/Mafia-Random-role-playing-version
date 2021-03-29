package Role;

public class Detective extends Role {
    private boolean detectionLimit = false;

    @Override
    public String toString() {
        return "Detective";
    }


    public boolean isDetectionLimit() {
        return detectionLimit;
    }

    public void setDetectionLimit(boolean detectionLimit) {
        this.detectionLimit = detectionLimit;
    }
}
