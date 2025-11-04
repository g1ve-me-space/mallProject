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
        public void delete(String id) {

        }
    }; }
    @Bean public FloorRepository floorRepository() { return new FloorRepository(); }
    @Bean public CustomerRepository customerRepository() { return new CustomerRepository(); }
    @Bean public MallRepository mallRepository() { return new MallRepository() {
        @Override
        public void delete(String id) {

        }
    }; }
    @Bean public PurchaseRepository purchaseRepository() { return new PurchaseRepository() {
        @Override
        public void delete(String id) {

        }
    }; }
    @Bean public MaintenanceTaskRepository maintenanceTaskRepository() { return new MaintenanceTaskRepository() {
        @Override
        public void delete(String id) {

        }
    }; }
    @Bean public ElectricalAssetRepository electricalAssetRepository() { return new ElectricalAssetRepository(); }
    @Bean public StaffAssignmentRepository staffAssignmentRepository() { return new StaffAssignmentRepository(); }
    @Bean public StaffRepository staffRepository() { return new StaffRepository() {
        @Override
        public void delete(String id) {

        }
    }; }
    @Bean public SecurityStaffRepository securityStaffRepository() { return new SecurityStaffRepository() {
        @Override
        public void delete(String id) {

        }
    }; }

    @Bean public ShopService shopService() { return new ShopService(shopRepository()); }
    @Bean public FloorService floorService() { return new FloorService(floorRepository()); }
    @Bean public CustomerService customerService() { return new CustomerService(customerRepository()); }

    @Bean
    public CommandLineRunner seedData(
            ShopRepository shopRepo,
            FloorRepository floorRepo,
            CustomerRepository customerRepo,
            MallRepository mallRepo,
            PurchaseRepository purchaseRepo,
            MaintenanceTaskRepository taskRepo,
            ElectricalAssetRepository electricalRepo,
            StaffAssignmentRepository assignmentRepo,
            StaffRepository staffRepo,
            SecurityStaffRepository securityRepo
    ) {
        return args -> {
            log.info("Seeding sample data (AppConfig) ...");

            // --- Mall
            Mall mall = new Mall();
            mall.setId("mall-1");
            mall.setName("Central Mall");
            mall.setCity("ExampleCity");
            mall.setFloors(new ArrayList<>());
            try {
                mallRepo.save(mall);
                log.info("Saved mall: {}", mall.getId());
            } catch (Exception e) {
                log.warn("Could not save mall: {}", e.getMessage());
            }

            // --- Floor
            Floor floor = new Floor();
            floor.setId("floor-1");
            floor.setNumber(1);
            floor.setShops(new ArrayList<>());
            floor.setTasks(new ArrayList<>());
            floor.setElectricals(new ArrayList<>());
            floor.setAssignments(new ArrayList<>());
            try {
                floorRepo.save(floor);
                // Associate floor to mall (use mallRepo.addFloor if available)
                boolean addedFloor = mallRepo.addFloor(mall.getId(), floor);
                log.info("Saved floor: {} and added to mall: {} -> {}", floor.getId(), mall.getId(), addedFloor);
            } catch (Exception e) {
                log.warn("Could not save floor: {}", e.getMessage());
            }

            // --- Shop
            Shop shop = new Shop();
            shop.setId("shop-1");
            shop.setName("Demo Shop");
            shop.setOwnerName("Alice");
            shop.setAreaSqm(25.5);
            shop.setPurchases(new ArrayList<>());
            try {
                shopRepo.save(shop);
                // Optionally add to floor
                boolean addedToFloor = floorRepo.addShopToFloor(floor.getId(), shop);
                log.info("Saved shop: {}, added to floor {} -> {}", shop.getId(), floor.getId(), addedToFloor);
            } catch (Exception e) {
                log.warn("Could not save shop: {}", e.getMessage());
            }

            // --- Customer
            Customer customer = new Customer();
            customer.setId("cust-1");
            customer.setName("Bob");
            customer.setCurrency("USD");
            customer.setPurchases(new ArrayList<>());
            try {
                customerRepo.save(customer);
                log.info("Saved customer: {}", customer.getId());
            } catch (Exception e) {
                log.warn("Could not save customer: {}", e.getMessage());
            }

            // --- Purchase
            Purchase purchase = new Purchase();
            purchase.setId("p-1");
            purchase.setCustomerId(customer.getId());
            purchase.setShopId(shop.getId());
            purchase.setAmount(12.5);
            try {
                purchaseRepo.save(purchase);
                // Also add to shop purchases (some controllers/services expect purchases stored inside shop)
                boolean added = shopRepo.addPurchase(shop.getId(), purchase);
                log.info("Saved purchase: {}, shopRepo.addPurchase -> {}", purchase.getId(), added);
            } catch (Exception e) {
                log.warn("Could not save purchase: {}", e.getMessage());
            }

            // --- Maintenance Task
            MaintenanceTask task = new MaintenanceTask();
            task.setId("task-1");
            task.setDescription("Replace light bulbs");
            task.setStatus("Planned");
            task.setAssignmentId(null);
            try {
                taskRepo.save(task);
                log.info("Saved maintenance task: {}", task.getId());
            } catch (Exception e) {
                log.warn("Could not save maintenance task: {}", e.getMessage());
            }

            // --- Electrical Asset
            ElectricalAsset ea = new ElectricalAsset();
            ea.setId("elec-1");
            ea.setType("HVAC");
            ea.setStatus("OK");
            try {
                electricalRepo.save(ea);
                // optionally attach to floor
                boolean added = floorRepo.addElectricalToFloor(floor.getId(), ea);
                log.info("Saved electrical asset: {}, added to floor -> {}", ea.getId(), added);
            } catch (Exception e) {
                log.warn("Could not save electrical asset: {}", e.getMessage());
            }

            // --- Staff (security / generic)
            Staff staff = new Staff();
            staff.setId("staff-1");
            staff.setName("John Security");
            staff.setRole("Security");
            try {
                staffRepo.save(staff);
                log.info("Saved staff: {}", staff.getId());
            } catch (Exception e) {
                log.warn("Could not save staff: {}", e.getMessage());
            }

            SecurityStaff sec = new SecurityStaff();
            sec.setId("sec-1");
            sec.setName("Securus");
            try {
                securityRepo.save(sec);
                log.info("Saved security staff: {}", sec.getId());
            } catch (Exception e) {
                log.warn("Could not save security staff: {}", e.getMessage());
            }

            // --- Staff assignment
            StaffAssignment assignment = new StaffAssignment();
            assignment.setId("assign-1");
            assignment.setFloorId(floor.getId());
            assignment.setStaffId(staff.getId());
            assignment.setShift("Morning");
            try {
                assignmentRepo.save(assignment);
                log.info("Saved assignment: {}", assignment.getId());
            } catch (Exception e) {
                log.warn("Could not save assignment: {}", e.getMessage());
            }

            log.info("Seeding finished.");
        };
    }
}