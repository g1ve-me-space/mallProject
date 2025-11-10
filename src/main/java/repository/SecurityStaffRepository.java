package repository;

import model.SecurityStaff;
import org.springframework.stereotype.Repository;

@Repository
// Inherits all standard methods from our new InMemoryRepository
public class SecurityStaffRepository extends InMemoryRepository<SecurityStaff, String> {
    // This body is intentionally left empty.
}