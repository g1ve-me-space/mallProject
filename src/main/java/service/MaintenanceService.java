package service;

import model.MaintenanceTask;
import repository.MaintenanceTaskRepository;

public class MaintenanceService extends AbstractService<MaintenanceTask> {
    public MaintenanceService(MaintenanceTaskRepository repository) {
        super(repository);
    }

}
