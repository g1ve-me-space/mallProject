package controller;

import model.Customer;
import model.Floor;
import model.Purchase;
import model.Shop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.CustomerRepository;
import repository.FloorRepository;
import repository.ShopRepository;
import service.CustomerService;
import service.FloorService;
import service.ShopService;

import java.util.ArrayList;

/**
 * AppConfig creates beans for repositories/services and seeds sample data at startup
 * so your REST endpoints return something visible right away.
 */
@Configuration
public class AppConfig {
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public ShopRepository shopRepository() {
        return new ShopRepository();
    }

    @Bean
    public ShopService shopService() {
        return new ShopService(shopRepository());
    }

    @Bean
    public FloorRepository floorRepository() {
        return new FloorRepository();
    }

    @Bean
    public FloorService floorService() {
        return new FloorService(floorRepository());
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepository();
    }

    @Bean
    public CustomerService customerService() {
        return new CustomerService(customerRepository());
    }

    // Seed some data at startup so endpoints return something useful immediately
    @Bean
    public CommandLineRunner seedData(ShopService shopService, FloorService floorService, CustomerService customerService) {
        return args -> {
            log.info("Seeding sample data...");

            // Create a shop
            Shop shop = new Shop();
            shop.setId("shop-1");
            shop.setName("Demo Shop");
            shop.setOwnerName("Alice");
            shop.setAreaSqm(25.5);
            shop.setPurchases(new ArrayList<>());
            try {
                shopService.save(shop);
                log.info("Saved shop: {}", shop.getId());
            } catch (Exception e) {
                log.warn("Could not save shop: {}", e.getMessage());
            }

            // Create a floor
            Floor floor = new Floor();
            floor.setId("floor-1");
            floor.setNumber(1);
            floor.setShops(new ArrayList<>());
            floor.setTasks(new ArrayList<>());
            floor.setElectricals(new ArrayList<>());
            floor.setAssignments(new ArrayList<>());
            try {
                floorService.save(floor);
                log.info("Saved floor: {}", floor.getId());
            } catch (Exception e) {
                log.warn("Could not save floor: {}", e.getMessage());
            }

            // Create a customer
            Customer customer = new Customer();
            customer.setId("cust-1");
            customer.setName("Bob");
            customer.setCurrency("USD");
            customer.setPurchases(new ArrayList<>());
            try {
                customerService.save(customer);
                log.info("Saved customer: {}", customer.getId());
            } catch (Exception e) {
                log.warn("Could not save customer: {}", e.getMessage());
            }

            // Add a purchase to the shop (via ShopService so repository logic is used)
            Purchase purchase = new Purchase();
            purchase.setId("p-1");
            purchase.setCustomerId("cust-1");
            purchase.setShopId("shop-1");
            purchase.setAmount(12.5);
            try {
                boolean added = shopService.addPurchase("shop-1", purchase);
                log.info("Added purchase to shop-1: {}", added);
            } catch (Exception e) {
                log.warn("Could not add purchase: {}", e.getMessage());
            }

            log.info("Seeding finished.");
        };
    }
}