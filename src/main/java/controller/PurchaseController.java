package controller;

import model.Customer;
import model.Purchase;
import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.CustomerRepository;
import repository.PurchaseRepository;
import repository.ShopRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;
    private final ShopRepository shopRepository;
    // CustomerRepository va fi roșu până îl creăm la pasul următor
    private final CustomerRepository customerRepository;

    @Autowired
    public PurchaseController(PurchaseRepository purchaseRepository,
                              ShopRepository shopRepository,
                              CustomerRepository customerRepository) {
        this.purchaseRepository = purchaseRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public String listAllPurchases(Model model) {
        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("purchases", purchases);
        return "purchase/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("purchase", new Purchase());

        // Trimitem listele către HTML pentru a popula dropdown-urile (<select>)
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("shops", shopRepository.findAll());

        return "purchase/form";
    }

    @PostMapping
    public String createPurchase(@ModelAttribute Purchase purchase,
                                 @RequestParam(value = "customerId", required = false) String customerId,
                                 @RequestParam(value = "shopId", required = false) String shopId) {

        // 1. Generăm ID pentru cumpărătură
        if (purchase.getId() == null || purchase.getId().isEmpty()) {
            purchase.setId(UUID.randomUUID().toString());
        }

        // 2. Găsim obiectele reale din baza de date pe baza ID-urilor venite din formular
        if (customerId != null) {
            Customer c = customerRepository.findById(customerId).orElse(null);
            purchase.setCustomer(c);
        }

        if (shopId != null) {
            Shop s = shopRepository.findById(shopId).orElse(null);
            purchase.setShop(s);
        }

        // 3. Salvăm
        purchaseRepository.save(purchase);
        return "redirect:/purchase";
    }

    @PostMapping("/{id}/delete")
    public String deletePurchase(@PathVariable String id) {
        purchaseRepository.deleteById(id);
        return "redirect:/purchase";
    }
}