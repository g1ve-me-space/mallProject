package service;

import model.Mall;
import repository.MallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MallService {
    private final MallRepository repository;

    public MallService(MallRepository repository) {
        this.repository = repository;
    }

    public List<Mall> findAll() {
        return repository.findAll();
    }

    public Optional<Mall> findById(String id) {
        return repository.findById(id);
    }

    public void save(Mall mall) {
        // AppConfig seed code uses repository.save(mall) in many places; repository provides save methods.
        // Here we delegate to repository; if repo expects save(String id, Mall) that method also exists.
        try {
            // try save(mall) if available
            repository.getClass().getMethod("save", Mall.class).invoke(repository, mall);
        } catch (Exception e) {
            // fallback to save by id if the above isn't available
            repository.save(mall.getId(), mall);
        }
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}