package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.MaintenanceStaff;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaintenanceStaffRepository extends InFileRepository<MaintenanceStaff> {
    public MaintenanceStaffRepository() {
        super("maintenanceStaff.json", new TypeReference<List<MaintenanceStaff>>() {});
    }
}