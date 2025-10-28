package service;

import model.Floor;
import model.Shop;
import model.MaintenanceTask;
import model.ElectricalAsset;
import repository.FloorRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FloorService extends AbstractService<Floor> {
    private FloorRepository floorRepository;

    public FloorService(FloorRepository repository) {
        super(repository);
        this.floorRepository = repository;
    }

    public Optional<Floor> findByNumber(int number) {
        return floorRepository.findByNumber(number);
    }

    public List<Floor> findFloorsWithShops() {
        return floorRepository.findFloorsWithShops();
    }

    public List<Floor> findFloorsWithPendingTasks() {
        return floorRepository.findFloorsWithPendingTasks();
    }

    public List<Floor> findFloorsWithMinShops(int minShops) {
        return floorRepository.findFloorsWithMinShops(minShops);
    }

    public List<Floor> findFloorsWithElectricalType(String electricalType) {
        return floorRepository.findFloorsWithElectricalType(electricalType);
    }

    public List<Floor> findFloorsWithDownElectricals() {
        return floorRepository.findFloorsWithDownElectricals();
    }

    public List<Integer> getAllFloorNumbers() {
        return floorRepository.getAllFloorNumbers();
    }

    public Map<Integer, Integer> getFloorShopCounts() {
        return floorRepository.getFloorShopCounts();
    }

    public List<Floor> findFloorsWithAssignments() {
        return floorRepository.findFloorsWithAssignments();
    }

    public boolean addShopToFloor(String floorId, Shop shop) {
        return floorRepository.addShopToFloor(floorId, shop);
    }

    public boolean addTaskToFloor(String floorId, MaintenanceTask task) {
        return floorRepository.addTaskToFloor(floorId, task);
    }

    public boolean addElectricalToFloor(String floorId, ElectricalAsset electrical) {
        return floorRepository.addElectricalToFloor(floorId, electrical);
    }

    public boolean removeShopFromFloor(String floorId, String shopId) {
        return floorRepository.removeShopFromFloor(floorId, shopId);
    }

    public void save(Floor entity) {
        floorRepository.save(entity);
    }
}