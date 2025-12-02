package bootstrap;

import enums.AssetStatus;
import enums.AssetType;
import model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MallRepository mallRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;
    // Poți adăuga și ElectricalAssetRepository dacă vrei să populezi și acolo

    public DataInitializer(MallRepository mallRepository,
                           ShopRepository shopRepository,
                           CustomerRepository customerRepository,
                           PurchaseRepository purchaseRepository) {
        this.mallRepository = mallRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verificăm dacă avem date. Dacă nu, populăm totul.
        if (mallRepository.count() == 0) {
            System.out.println("🚀 Initializare Bază de Date Completa...");

            // 1. Creare Mall-uri și Etaje
            List<Floor> allFloors = createMallsAndFloors();

            // 2. Creare Magazine (puse pe etaje)
            List<Shop> allShops = createShops(allFloors);

            // 3. Creare Clienți
            List<Customer> allCustomers = createCustomers();

            // 4. Creare Cumpărături (legate de Clienți și Magazine)
            createPurchases(allCustomers, allShops);

            System.out.println("✅ Initializare Finalizată cu Succes!");
        }
    }

    private List<Floor> createMallsAndFloors() {
        List<Floor> createdFloors = new ArrayList<>();
        for (int i = 1; i <= 3; i++) { // Facem 3 Mall-uri
            Mall mall = new Mall();
            mall.setId(UUID.randomUUID().toString());
            mall.setName("Mall " + i);
            mall.setCity("Oras " + i);
            mall.setAddress("Adresa " + i);

            List<Floor> floors = new ArrayList<>();
            for (int j = 1; j <= 4; j++) { // 4 Etaje per Mall
                Floor floor = new Floor();
                floor.setId(UUID.randomUUID().toString());
                floor.setNumber(j);
                floor.setMall(mall);
                floors.add(floor);
                createdFloors.add(floor);
            }
            mall.setFloors(floors);
            mallRepository.save(mall);
        }
        return createdFloors;
    }

    private List<Shop> createShops(List<Floor> floors) {
        List<Shop> shops = new ArrayList<>();
        Random rand = new Random();

        // Creăm 15 magazine
        for (int i = 1; i <= 15; i++) {
            Shop shop = new Shop();
            shop.setId(UUID.randomUUID().toString());
            shop.setName("Magazin " + i);
            shop.setCategory("Categorie " + (i % 5));

            // ⚠️ AICI ERA EROAREA: Înlocuiește setUserCode cu setPhoneNumber
            shop.setPhoneNumber("0700123" + i);

            // Alegem un etaj random pentru magazin
            Floor randomFloor = floors.get(rand.nextInt(floors.size()));
            shop.setFloor(randomFloor);

            shopRepository.save(shop);
            shops.add(shop);
        }
        return shops;
    }

    private List<Customer> createCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i <= 12; i++) { // 12 Clienți
            Customer c = new Customer();
            c.setId(UUID.randomUUID().toString());
            c.setName("Client " + i);
            c.setCurrency("EUR");
            c.setEmail("client" + i + "@example.com");

            customerRepository.save(c);
            customers.add(c);
        }
        return customers;
    }

    private void createPurchases(List<Customer> customers, List<Shop> shops) {
        Random rand = new Random();
        // Creăm 20 de cumpărături
        for (int i = 1; i <= 20; i++) {
            Purchase p = new Purchase();
            p.setId(UUID.randomUUID().toString());
            p.setAmount(10.0 + rand.nextInt(500)); // Preț între 10 și 510

            // Legăm cumpărătura de un client random și un magazin random
            p.setCustomer(customers.get(rand.nextInt(customers.size())));
            p.setShop(shops.get(rand.nextInt(shops.size())));

            purchaseRepository.save(p);
        }
    }
}