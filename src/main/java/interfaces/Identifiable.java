package interfaces;

/**
 * A simple interface for entities that have an ID.
 * This allows our generic repositories to access an entity's ID
 * without knowing its specific type, enabling generic handling.
 *
 * @param <T> The type of the ID.
 */
public interface Identifiable<T> { // <--- This <T> is the crucial part that was missing.
    T getId();
}