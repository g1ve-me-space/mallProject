package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.StaffAssignment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// Inherits all standard methods from our new InMemoryRepository
public class StaffAssignmentRepository extends InFileRepository<StaffAssignment> {

    public StaffAssignmentRepository() {
        super("staffAssignment.json", new TypeReference<List<StaffAssignment>>() {});
    }
}