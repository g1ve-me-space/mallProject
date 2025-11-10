package repository;

// Import the core interfaces from your 'interfaces' package
import interfaces.Identifiable;
import interfaces.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An abstract class providing a complete, generic in-memory implementation
 * of the Repository interface.
 *
 * It works with any entity type 'T' that implements the 'Identifiable' interface,
 * allowing it to reliably get an entity's ID for storage in a map.
 *
 * Specific repositories (like ShopRepository) will extend this class to inherit
 * all basic CRUD functionality.
 *
 * @param <T> The type of the entity, which must be Identifiable.
 * @param <ID> The type of the entity's ID (e.g., String).
 */
public abstract class InMemoryRepository<T extends Identifiable<ID>, ID> implements Repository<T, ID> {

    // This is the actual in-memory data store.
    // 'protected' means it can be accessed by any subclass (like ShopRepository).
    protected final Map<ID, T> store = new ConcurrentHashMap<>();

    /**
     * Saves an entity. This implementation is now written only ONCE.
     */
    @Override
    public void save(T entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("Entity ID cannot be null for saving.");
        }
        store.put(entity.getId(), entity);
    }

    /**
     * Finds an entity by its ID. Written only ONCE.
     */
    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(store.get(id));
    }

    /**
     * Finds all entities. Written only ONCE.
     */
    @Override
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    /**
     * Deletes an entity by its ID. Written only ONCE.
     */
    @Override
    public void deleteById(ID id) {
        store.remove(id);
    }

    /**
     * Counts all entities. Written only ONCE.
     */
    @Override
    public long count() {
        return store.size();
    }

    /**
     * Checks if an entity exists by its ID. Written only ONCE.
     */
    @Override
    public boolean existsById(ID id) {
        return store.containsKey(id);
    }
}