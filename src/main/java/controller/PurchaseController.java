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
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;
    private final ShopRepository shopRepository;
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
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("shops", shopRepository.findAll());
        return "purchase/form";
    }

    @PostMapping
    public String createPurchase(@ModelAttribute Purchase purchase,
                                 @RequestParam(value = "customerId", required = false) String customerId,
                                 @RequestParam(value = "shopId", required = false) String shopId) {

        if (purchase.getId() == null || purchase.getId().isEmpty()) {
            purchase.setId(UUID.randomUUID().toString());
        }

        if (customerId != null && !customerId.isEmpty()) {
            Customer c = customerRepository.findById(customerId).orElse(null);
            purchase.setCustomer(c);
        } else {
            purchase.setCustomer(null);
        }

        if (shopId != null && !shopId.isEmpty()) {
            Shop s = shopRepository.findById(shopId).orElse(null);
            purchase.setShop(s);
        } else {
            purchase.setShop(null);
        }

        purchaseRepository.save(purchase);
        return "redirect:/purchase";
    }

    @PostMapping("/{id}/delete")
    public String deletePurchase(@PathVariable String id) {
        purchaseRepository.deleteById(id);
        return "redirect:/purchase";
    }

    // ---------- ADDED: show edit form ----------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Purchase> opt = purchaseRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/purchase";
        }
        Purchase purchase = opt.get();
        model.addAttribute("purchase", purchase);
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("shops", shopRepository.findAll());
        return "purchase/form";
    }

    // ---------- ADDED: handle edit submit ----------
    @PostMapping("/{id}/edit")
    public String updatePurchase(@PathVariable String id,
                                 @ModelAttribute Purchase purchase,
                                 @RequestParam(value = "customerId", required = false) String customerId,
                                 @RequestParam(value = "shopId", required = false) String shopId) {

        // ensure path id is the source of truth
        purchase.setId(id);

        if (customerId != null && !customerId.isEmpty()) {
            Customer c = customerRepository.findById(customerId).orElse(null);
            purchase.setCustomer(c);
        } else {
            purchase.setCustomer(null);
        }

        if (shopId != null && !shopId.isEmpty()) {
            Shop s = shopRepository.findById(shopId).orElse(null);
            purchase.setShop(s);
        } else {
            purchase.setShop(null);
        }

        purchaseRepository.save(purchase);
        return "redirect:/purchase";
    }
}