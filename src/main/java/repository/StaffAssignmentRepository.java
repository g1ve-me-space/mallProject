package repository;

import model.StaffAssignment;
import org.springframework.stereotype.Repository;

@Repository
// Inherits all standard methods from our new InMemoryRepository
public class StaffAssignmentRepository extends InMemoryRepository<StaffAssignment, String> {
    // This body is intentionally left empty.
}