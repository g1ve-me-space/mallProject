package repository;

import model.MaintenanceTask;
import org.springframework.stereotype.Repository;

@Repository
// Inherits all standard CRUD methods from InMemoryRepository.
// Its body is empty because it requires no custom business logic.
public class MaintenanceTaskRepository extends InMemoryRepository<MaintenanceTask, String> {
    // This body is intentionally left empty.
}