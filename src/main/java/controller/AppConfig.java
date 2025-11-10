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

@Configuration
public class AppConfig {
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean public ShopRepository shopRepository() { return new ShopRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public FloorRepository floorRepository() { return new FloorRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public CustomerRepository customerRepository() { return new CustomerRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public MallRepository mallRepository() { return new MallRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public PurchaseRepository purchaseRepository() { return new PurchaseRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public MaintenanceTaskRepository maintenanceTaskRepository() { return new MaintenanceTaskRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public ElectricalAssetRepository electricalAssetRepository() { return new ElectricalAssetRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public MaintenanceStaffRepository maintenanceStaffRepository() { return new MaintenanceStaffRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public StaffAssignmentRepository staffAssignmentRepository() { return new StaffAssignmentRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public StaffRepository staffRepository() { return new StaffRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }
    @Bean public SecurityStaffRepository securityStaffRepository() { return new SecurityStaffRepository() {
        @Override public void delete(String id) { store.remove(id); }
    }; }

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
            ElectricalAssetRepository electricalRepo,
            MaintenanceStaffRepository maintenanceStaffRepo,
            SecurityStaffRepository securityStaffRepo,
            StaffAssignmentRepository assignmentRepo
    ) {
        return args -> {
            log.info("Seeding sample data (AppConfig) ...");

            Shop shop1 = new Shop(); shop1.setId("shop-1"); shop1.setName("Demo Electronics Store"); shop1.setCategory("Electronics");
            Shop shop2 = new Shop(); shop2.setId("shop-2"); shop2.setName("The Book Nook"); shop2.setCategory("Books");
            Customer customer1 = new Customer(); customer1.setId("cust-1"); customer1.setName("Charlie"); customer1.setCurrency("USD");
            Customer customer2 = new Customer(); customer2.setId("cust-2"); customer2.setName("Diana"); customer2.setCurrency("EUR");
            Mall mall1 = new Mall(); mall1.setId("mall-1"); mall1.setName("City Center Plaza"); mall1.setCity("Metropolis");
            Mall mall2 = new Mall(); mall2.setId("mall-2"); mall2.setName("Lakeside Shopping"); mall2.setCity("Star City");
            Floor floor1 = new Floor(); floor1.setId("floor-1"); floor1.setNumber(0);
            Floor floor2 = new Floor(); floor2.setId("floor-2"); floor2.setNumber(1);
            Purchase p1 = new Purchase("purchase-1", customer1.getId(), shop1.getId(), 1299.99);
            Purchase p2 = new Purchase("purchase-2", customer2.getId(), shop2.getId(), 29.50);
            Purchase p3 = new Purchase("purchase-3", customer1.getId(), shop2.getId(), 15.00);
            MaintenanceTask task1 = new MaintenanceTask(); task1.setId("task-1"); task1.setDescription("Inspect HVAC system on Floor 1"); task1.setStatus(TaskStatus.PLANNED);
            MaintenanceTask task2 = new MaintenanceTask(); task2.setId("task-2"); task2.setDescription("Replace light bulbs in main entrance"); task2.setStatus(TaskStatus.ACTIVE);
            MaintenanceTask task3 = new MaintenanceTask(); task3.setId("task-3"); task3.setDescription("Clean central atrium windows"); task3.setStatus(TaskStatus.DONE);
            ElectricalAsset asset1 = new ElectricalAsset("asset-1", floor1.getId(), AssetType.ESCALATOR, AssetStatus.WORKING);
            ElectricalAsset asset2 = new ElectricalAsset("asset-2", floor2.getId(), AssetType.LIFT, AssetStatus.DOWN);
            ElectricalAsset asset3 = new ElectricalAsset("asset-3", floor1.getId(), AssetType.AC, AssetStatus.WORKING);

            // --- FIX: Use the MaintenanceStaffType enum instead of strings ---
            MaintenanceStaff maint1 = new MaintenanceStaff(); maint1.setId("maint-staff-1"); maint1.setName("Eve"); maint1.setType(MaintenanceStaffType.ELECTRICAL);
            MaintenanceStaff maint2 = new MaintenanceStaff(); maint2.setId("maint-staff-2"); maint2.setName("Frank"); maint2.setType(MaintenanceStaffType.CLEANING);
            // -----------------------------------------------------------------

            SecurityStaff sec1 = new SecurityStaff(); sec1.setId("sec-staff-1"); sec1.setName("Grace"); sec1.setBadgeNo("S-101");
            SecurityStaff sec2 = new SecurityStaff(); sec2.setId("sec-staff-2"); sec2.setName("Heidi"); sec2.setBadgeNo("S-102");

            shopRepo.save(shop1); shopRepo.save(shop2);
            customerRepo.save(customer1); customerRepo.save(customer2);
            mallRepo.save(mall1); mallRepo.save(mall2);
            floorRepo.save(floor1); floorRepo.save(floor2);
            purchaseRepo.save(p1); purchaseRepo.save(p2); purchaseRepo.save(p3);
            taskRepo.save(task1); taskRepo.save(task2); taskRepo.save(task3);
            electricalRepo.save(asset1); electricalRepo.save(asset2); electricalRepo.save(asset3);
            maintenanceStaffRepo.save(maint1); maintenanceStaffRepo.save(maint2);
            securityStaffRepo.save(sec1); securityStaffRepo.save(sec2);

            StaffAssignment assign1 = new StaffAssignment("assign-1", maint1.getId(), floor1.getId(), Shift.MORNING);
            assignmentRepo.save(assign1);
            StaffAssignment assign2 = new StaffAssignment("assign-2", sec1.getId(), floor2.getId(), Shift.EVENING);
            assignmentRepo.save(assign2);
            StaffAssignment assign3 = new StaffAssignment("assign-3", maint2.getId(), floor1.getId(), Shift.NIGHT);
            assignmentRepo.save(assign3);

            log.info("Seeding finished.");
        };
    }
}