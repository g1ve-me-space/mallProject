package enums;

public enum AssetStatus {
    WORKING("Working"),
    DOWN("Down");

    private final String displayName;

    AssetStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}