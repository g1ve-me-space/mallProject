package repository;

import model.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FloorRepository extends JpaRepository<Floor, String> {

    // Doar scriind linia asta, Spring va sti sa faca "SELECT * FROM floor WHERE number = ?"
    Optional<Floor> findByNumber(int number);
}