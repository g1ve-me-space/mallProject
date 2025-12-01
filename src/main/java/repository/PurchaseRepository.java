package repository;

import model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {
    // Metodele standard (save, findAll, delete) sunt incluse automat.
}