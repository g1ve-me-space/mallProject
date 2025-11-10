package repository;

import model.Purchase;
import org.springframework.stereotype.Repository;

@Repository
// Inherits all standard CRUD methods from InMemoryRepository.
// Its body is empty because it requires no custom business logic.
public class PurchaseRepository extends InMemoryRepository<Purchase, String> {
    // This body is intentionally left empty.
}