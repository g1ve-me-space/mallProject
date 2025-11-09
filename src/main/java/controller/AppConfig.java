package controller;

import model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.*;
import service.CustomerService;
import service.FloorService;
import service.ShopService;

import java.util.ArrayList;

@Configuration
public class AppConfig {
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    // Repositories
    @Bean public ShopRepository shopRepository() { return new ShopRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }
    @Bean public FloorRepository floorRepository() { return new FloorRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }
    @Bean public CustomerRepository customerRepository() { return new CustomerRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }
    @Bean public MallRepository mallRepository() { return new MallRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }
    @Bean public PurchaseRepository purchaseRepository() { return new PurchaseRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }
    @Bean public MaintenanceTaskRepository maintenanceTaskRepository() { return new MaintenanceTaskRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }

    // --- FIX #1: ADD DELETE OVERRIDE ---
    @Bean public ElectricalAssetRepository electricalAssetRepository() { return new ElectricalAssetRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }
    // ------------------------------------

    @Bean public StaffAssignmentRepository staffAssignmentRepository() { return new StaffAssignmentRepository(); }
    @Bean public StaffRepository staffRepository() { return new StaffRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }
    @Bean public SecurityStaffRepository securityStaffRepository() { return new SecurityStaffRepository() {
        @Override
        public void delete(String id) { store.remove(id); }
    }; }

    // Services
    @Bean public ShopService shopService() { return new ShopService(shopRepository()); }
    @Bean public FloorService floorService() { return new FloorService(floorRepository()); }
    @Bean public CustomerService customerService() { return new CustomerService(customerRepository()); }

    @Bean
    public CommandLineRunner seedData(
            ShopRepository shopRepo,
            CustomerRepository customerRepo,
            MallRepository mallRepo,
            FloorRepository floorRepo,
            PurchaseRepository purchaseRepo,
            MaintenanceTaskRepository taskRepo,
            ElectricalAssetRepository electricalRepo // <-- FIX #2: Inject repository
    ) {
        return args -> {
            log.info("Seeding sample data (AppConfig) ...");

            // Previous seed data... (omitted for brevity)
            Shop shop1 = new Shop(); shop1.setId("shop-1"); shop1.setName("Demo Electronics Store"); shop1.setCategory("Electronics"); shopRepo.save(shop1);
            Shop shop2 = new Shop(); shop2.setId("shop-2"); shop2.setName("The Book Nook"); shop2.setCategory("Books"); shopRepo.save(shop2);
            Customer customer1 = new Customer(); customer1.setId("cust-1"); customer1.setName("Charlie"); customer1.setCurrency("USD"); customerRepo.save(customer1);
            Customer customer2 = new Customer(); customer2.setId("cust-2"); customer2.setName("Diana"); customer2.setCurrency("EUR"); customerRepo.save(customer2);
            Mall mall1 = new Mall(); mall1.setId("mall-1"); mall1.setName("City Center Plaza"); mall1.setCity("Metropolis"); mallRepo.save(mall1);
            Mall mall2 = new Mall(); mall2.setId("mall-2"); mall2.setName("Lakeside Shopping"); mall2.setCity("Star City"); mallRepo.save(mall2);
            Floor floor1 = new Floor(); floor1.setId("floor-1"); floor1.setNumber(0); floorRepo.save(floor1);
            Floor floor2 = new Floor(); floor2.setId("floor-2"); floor2.setNumber(1); floorRepo.save(floor2);
            Purchase p1 = new Purchase("purchase-1", customer1.getId(), shop1.getId(), 1299.99); purchaseRepo.save(p1);
            Purchase p2 = new Purchase("purchase-2", customer2.getId(), shop2.getId(), 29.50); purchaseRepo.save(p2);
            Purchase p3 = new Purchase("purchase-3", customer1.getId(), shop2.getId(), 15.00); purchaseRepo.save(p3);
            MaintenanceTask task1 = new MaintenanceTask(); task1.setId("task-1"); task1.setDescription("Inspect HVAC system on Floor 1"); task1.setStatus("Planned"); taskRepo.save(task1);
            MaintenanceTask task2 = new MaintenanceTask(); task2.setId("task-2"); task2.setDescription("Replace light bulbs in main entrance"); task2.setStatus("Active"); taskRepo.save(task2);
            MaintenanceTask task3 = new MaintenanceTask(); task3.setId("task-3"); task3.setDescription("Clean central atrium windows"); task3.setStatus("Done"); taskRepo.save(task3);

            // --- FIX #2: Seed Electrical Assets ---
            ElectricalAsset asset1 = new ElectricalAsset("asset-1", floor1.getId(), "Escalator", "Working");
            electricalRepo.save(asset1);
            log.info("Saved sample asset: Type {}", asset1.getType());

            ElectricalAsset asset2 = new ElectricalAsset("asset-2", floor2.getId(), "Lift", "Down");
            electricalRepo.save(asset2);
            log.info("Saved sample asset: Type {}", asset2.getType());

            ElectricalAsset asset3 = new ElectricalAsset("asset-3", floor1.getId(), "AC", "Working");
            electricalRepo.save(asset3);
            log.info("Saved sample asset: Type {}", asset3.getType());
            // --------------------------------------

            log.info("Seeding finished.");
        };
    }
}