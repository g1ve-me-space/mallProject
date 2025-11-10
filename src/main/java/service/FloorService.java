package service;

import model.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FloorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FloorService {

    private final FloorRepository floorRepository;

    @Autowired
    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    // --- Core Service Methods ---
    public List<Floor> findAll() {
        return floorRepository.findAll();
    }

    public Optional<Floor> findById(String id) {
        return floorRepository.findById(id);
    }

    public void deleteById(String id) {
        floorRepository.deleteById(id);
    }

    public void save(Floor floor) {
        if (floor.getId() == null || floor.getId().trim().isEmpty()) {
            floor.setId(UUID.randomUUID().toString());
        }
        floorRepository.save(floor);
    }

    // --- Custom Service Method ---
    // This now correctly calls the method on our new repository.
    public Optional<Floor> findByNumber(int number) {
        return floorRepository.findByNumber(number);
    }

    // WARNING: The rest of the custom methods from your old FloorService
    // (like findFloorsWithShops) are NOT included here because their logic
    // depends on other repositories. That logic should be moved INTO this service
    // in the future. For now, we are keeping it simple to ensure the app runs.
}