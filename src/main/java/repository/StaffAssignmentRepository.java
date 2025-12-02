package repository;
import model.StaffAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StaffAssignmentRepository extends JpaRepository<StaffAssignment, String> {
    List<StaffAssignment> findByFloor_Id(String floorId);
    List<StaffAssignment> findByStaff_Id(String staffId);
    List<StaffAssignment> findByFloor_IdAndStaff_Id(String floorId, String staffId);
}