package service;

import java.util.List;
import java.util.Optional;

import repository.AbstractRepository;

public abstract class AbstractService<T> {

    protected AbstractRepository<T> repository;

    public AbstractService(AbstractRepository<T> repository) {
        this.repository = repository;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findById(String id) {
        return repository.findById(id);
    }

    public void save(String id, T entity) {
        repository.save(id, entity);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
