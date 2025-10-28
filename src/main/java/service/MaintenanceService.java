package service;

import model.MaintenanceTask;
import repository.MaintenanceRepository;

public class MaintenanceService extends AbstractService<MaintenanceTask> {
    public MaintenanceService(MaintenanceRepository repository) {
        super(repository);
    }

}
