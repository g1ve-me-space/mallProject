package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Floor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FloorRepository extends InFileRepository<Floor> {

    public FloorRepository() {
        super("floor.json", new TypeReference<List<Floor>>() {});
    }

    /**
     * Finds a floor by its number.
     * This is a custom method required by FloorService.
     */
    public Optional<Floor> findByNumber(int number) {
        return findAll().stream()
                .filter(floor -> floor.getNumber() == number)
                .findFirst();
    }
}