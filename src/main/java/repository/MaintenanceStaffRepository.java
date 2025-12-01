package repository;

import model.MaintenanceStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceStaffRepository extends JpaRepository<MaintenanceStaff, String> {
    // Totul este automat. Nu mai ai nevoie de constructor sau logică de fișiere.
}