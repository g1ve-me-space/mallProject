package model;

public enum TaskStatus {
    PLANNED("Planned"),
    ACTIVE("Active"),
    DONE("Done");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}