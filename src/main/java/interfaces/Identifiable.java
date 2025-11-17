package interfaces;

/**
 * A simple interface for entities that have an ID.
 * This allows our generic repositories to access an entity's ID
 * and to set it when a new entity is created.
 *
 * @param <T> The type of the ID.
 */
public interface Identifiable<T> {
    T getId();
    void setId(T id); // <-- Add this line
}