package repository;

import com.fasterxml.jackson.core.type.TypeReference;
import model.MaintenanceTask;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaintenanceTaskRepository extends InFileRepository<MaintenanceTask> {

    public MaintenanceTaskRepository() {
        super("maintenanceTask.json", new TypeReference<List<MaintenanceTask>>() {});
    }
}