package repository;

import model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, String> {

    // Spring va crea automat interogarea SQL:
    // "SELECT * FROM shop WHERE LOWER(name) = LOWER(?)"
    Optional<Shop> findByNameIgnoreCase(String name);
}