package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import interfaces.Identifiable;
import interfaces.Repository;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A generic repository implementation that persists data in a JSON file.
 * It implements the Repository interface and is designed to be thread-safe.
 *
 * @param <T> The type of the entity, which must be Identifiable.
 */
public class InFileRepository<T extends Identifiable<String>> implements Repository<T, String> {

    private final Path filePath;
    private final ObjectMapper objectMapper;
    private final List<T> items;
    private final TypeReference<List<T>> typeReference;

    /**
     * Constructs a new InFileRepository.
     *
     * @param jsonFileName The name of the JSON file (e.g., "bus.json").
     * @param typeReference The TypeReference needed for deserializing a list of generic type T.
     */
    public InFileRepository(String jsonFileName, TypeReference<List<T>> typeReference) {
        // Correct path for Maven/Gradle structure
        this.filePath = Paths.get("src", "main", "resources", "data", jsonFileName);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Makes the JSON file readable
        this.typeReference = typeReference;
        this.items = new CopyOnWriteArrayList<>(); // Thread-safe list
        loadData();
    }

    /**
     * Loads data from the JSON file into the in-memory list.
     * If the file or directory does not exist, it creates them.
     */
    private void loadData() {
        try {
            if (Files.notExists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            if (Files.notExists(filePath)) {
                // If the file does not exist, create it with an empty list.
                Files.write(filePath, "[]".getBytes());
            }

            byte[] jsonData = Files.readAllBytes(filePath);
            if (jsonData.length > 0) {
                List<T> loadedItems = objectMapper.readValue(jsonData, typeReference);
                if (loadedItems != null) {
                    items.addAll(loadedItems);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load data from file: " + filePath, e);
        }
    }

    /**
     * Saves the current in-memory list of items to the JSON file.
     */
    private synchronized void saveData() {
        try {
            byte[] jsonData = objectMapper.writeValueAsBytes(items);
            Files.write(filePath, jsonData);
        } catch (IOException e) {
            throw new RuntimeException("Could not save data to file: " + filePath, e);
        }
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(items);
    }

    @Override
    public Optional<T> findById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(T entity) {
        if (entity.getId() == null || entity.getId().isEmpty() || !existsById(entity.getId())) {
            // New entity: generate ID if missing and add to list.
            if (entity.getId() == null || entity.getId().isEmpty()) {
                entity.setId(UUID.randomUUID().toString());
            }
            items.add(entity);
        } else {
            // Existing entity: remove the old one, add the updated one.
            items.removeIf(item -> item.getId().equals(entity.getId()));
            items.add(entity);
        }
        saveData();
    }

    @Override
    public void deleteById(String id) {
        items.removeIf(item -> item.getId().equals(id));
        saveData();
    }

    @Override
    public long count() {
        return items.size();
    }

    @Override
    public boolean existsById(String id) {
        return items.stream().anyMatch(item -> item.getId().equals(id));
    }
}