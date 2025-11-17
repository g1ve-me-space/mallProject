package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.*;
import service.*;

@Configuration
public class AppConfig {
    // No changes are needed for the bean definitions.
    // Spring will correctly instantiate your new file-based repositories.

    // --- REPOSITORIES ---
    @Bean public ShopRepository shopRepository() { return new ShopRepository(); }
    @Bean public FloorRepository floorRepository() { return new FloorRepository(); }
    @Bean public MallRepository mallRepository() { return new MallRepository(); }
    @Bean public PurchaseRepository purchaseRepository() { return new PurchaseRepository(); }
    @Bean public MaintenanceTaskRepository maintenanceTaskRepository() { return new MaintenanceTaskRepository(); }
    @Bean public ElectricalAssetRepository electricalAssetRepository() { return new ElectricalAssetRepository(); }
    @Bean public CustomerRepository customerRepository() { return new CustomerRepository(); }
    @Bean public MaintenanceStaffRepository maintenanceStaffRepository() { return new MaintenanceStaffRepository(); }
    @Bean public StaffAssignmentRepository staffAssignmentRepository() { return new StaffAssignmentRepository(); }
    @Bean public SecurityStaffRepository securityStaffRepository() { return new SecurityStaffRepository(); }

    // --- SERVICES ---
    // These also remain unchanged, as they depend on the repository interfaces, not implementations.
    @Bean public ShopService shopService() { return new ShopService(shopRepository()); }
    @Bean public FloorService floorService() { return new FloorService(floorRepository()); }
    @Bean public ElectricalAssetService electricalAssetService() { return new ElectricalAssetService(electricalAssetRepository()); }
    @Bean public CustomerService customerService() { return new CustomerService(customerRepository()); }

    // --- COMMAND LINE RUNNER (DATA SEEDER) ---
    // The CommandLineRunner bean has been REMOVED.
    // The application should now rely exclusively on the data present in the JSON files
    // located in src/main/resources/data/.
    // Populating the database at runtime is no longer the desired behavior.
}