package model;

public enum AssetType {
    LIFT("Lift"),
    AC("AC"),
    LIGHT("Light"),
    ESCALATOR("Escalator");

    private final String displayName;

    AssetType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}