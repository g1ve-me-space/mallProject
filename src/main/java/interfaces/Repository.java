package interfaces;

import java.util.List;
import java.util.Optional;

/**
 * A generic interface defining the standard contract for a repository.
 * It provides common data access methods like CRUD (Create, Read, Update, Delete).
 *
 * @param <T> The type of the entity managed by the repository.
 * @param <ID> The type of the entity's primary key (e.g., String, Long).
 */
public interface Repository<T, ID> {

    /**
     * Retrieves all entities of type T.
     * @return a list of all entities.
     */
    List<T> findAll();

    /**
     * Retrieves an entity by its ID.
     * @param id The ID of the entity to retrieve.
     * @return an Optional containing the entity if found, or an empty Optional if not.
     */
    Optional<T> findById(ID id);

    /**
     * Saves a given entity. Use this for both creating new entities and updating existing ones.
     * @param entity The entity to save.
     */
    void save(T entity);

    /**
     * Deletes the entity with the given ID.
     * @param id The ID of the entity to delete.
     */
    void deleteById(ID id);

    /**
     * Returns the total number of entities available.
     * @return the total number of entities.
     */
    long count();

    /**
     * Checks if an entity with the given ID exists.
     * @param id The ID to check.
     * @return true if an entity with the given ID exists, false otherwise.
     */
    boolean existsById(ID id);
}