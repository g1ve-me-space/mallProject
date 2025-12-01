package service;

import model.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FloorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID; // Import necesar pentru generarea ID-urilor

@Service
public class FloorService {

    private final FloorRepository floorRepository;

    @Autowired
    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }

    public List<Floor> findAll() {
        return floorRepository.findAll();
    }

    public Optional<Floor> findById(String id) {
        return floorRepository.findById(id);
    }

    public void deleteById(String id) {
        floorRepository.deleteById(id);
    }

    public Optional<Floor> findByNumber(int number) {
        // Această metodă funcționează pentru că am definit-o în FloorRepository
        return floorRepository.findByNumber(number);
    }

    public void save(Floor floor) {
        // ⚠️ MODIFICARE IMPORTANTĂ:
        // Dacă obiectul nu are ID (e nou creat din formular), îi generăm unul.
        if (floor.getId() == null || floor.getId().isEmpty()) {
            floor.setId(UUID.randomUUID().toString());
        }
        floorRepository.save(floor);
    }
}