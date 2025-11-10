package repository;

import model.MaintenanceStaff;
import org.springframework.stereotype.Repository;

@Repository
// Inherits all standard CRUD methods from InMemoryRepository.
public class MaintenanceStaffRepository extends InMemoryRepository<MaintenanceStaff, String> {
    // This body is intentionally left empty.
}