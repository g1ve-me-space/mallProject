package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.SecurityStaff;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// Inherits all standard methods from our new InMemoryRepository
public class SecurityStaffRepository extends InFileRepository<SecurityStaff> {
    public SecurityStaffRepository() {super("securityStaff.json", new TypeReference<List<SecurityStaff>>() {});}
}