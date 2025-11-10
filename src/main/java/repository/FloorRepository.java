package repository;

import model.Floor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FloorRepository extends InMemoryRepository<Floor, String> {

    /**
     * Finds a floor by its number.
     * This is a custom method required by FloorService.
     */
    public Optional<Floor> findByNumber(int number) {
        return store.values().stream()
                .filter(floor -> floor.getNumber() == number)
                .findFirst();
    }

    // TODO: We will need to implement all the other custom methods here
    // For now, we will leave them out to keep the focus on the service layer refactoring.
    // If the application fails to start because a method is missing, we will add it.
}