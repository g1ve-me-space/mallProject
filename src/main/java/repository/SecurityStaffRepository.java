package repository;

import model.SecurityStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityStaffRepository extends JpaRepository<SecurityStaff, String> {
    // Totul este automat. Nu mai ai nevoie de codul vechi.
}