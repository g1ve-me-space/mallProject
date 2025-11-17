package service;

import model.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.FloorRepository;

import java.util.List;
import java.util.Optional;

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
        return floorRepository.findByNumber(number);
    }

    public void save(Floor floor) {
        // Let the repository set the ID if necessary.
        floorRepository.save(floor);
    }
}