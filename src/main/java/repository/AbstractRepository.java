package repository;

import java.util.*;

public abstract class AbstractRepository<T> {
    protected Map<String, T> store = new HashMap<>();

    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public Optional<T> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public void save(String id, T entity) {
        store.put(id, entity);
    }

    public void deleteById(String id) {
        store.remove(id);
    }

    public boolean existsById(String id) {
        return store.containsKey(id);
    }

    public int count() {
        return store.size();
    }
}
