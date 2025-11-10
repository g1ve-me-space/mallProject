package enums;

public enum Shift {
    MORNING("Morning"),
    EVENING("Evening"),
    NIGHT("Night");

    private final String displayName;

    Shift(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}