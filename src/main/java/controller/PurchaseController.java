package controller;

import jakarta.validation.Valid;
import model.Customer;
import model.Purchase;
import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repository.CustomerRepository;
import repository.PurchaseRepository;
import repository.ShopRepository;

import java.util.UUID;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public PurchaseController(PurchaseRepository purchaseRepository, ShopRepository shopRepository, CustomerRepository customerRepository) {
        this.purchaseRepository = purchaseRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;
    }

    // --- LIST & FILTER ---
    @GetMapping
    public String listAll(Model model, @RequestParam(value = "customerId", required = false) String customerId) {
        model.addAttribute("customers", customerRepository.findAll()); // Pentru filtrul din UI

        if (customerId != null && !customerId.isEmpty()) {
            model.addAttribute("purchases", purchaseRepository.findByCustomer_Id(customerId));
            model.addAttribute("selectedCustomerId", customerId);
        } else {
            model.addAttribute("purchases", purchaseRepository.findAll());
        }
        return "purchase/index";
    }

    // --- CREATE FORM ---
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("purchase", new Purchase());
        // Trimitem ambele liste pentru dropdown-uri
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("shops", shopRepository.findAll());
        return "purchase/form";
    }

    // --- EDIT FORM ---
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Purchase purchase = purchaseRepository.findById(id).orElse(null);
        if (purchase != null) {
            model.addAttribute("purchase", purchase);
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("shops", shopRepository.findAll());
            return "purchase/form";
        }
        return "redirect:/purchase";
    }

    // --- SAVE ---
    @PostMapping
    public String createOrUpdate(@Valid @ModelAttribute Purchase purchase,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "customerId", required = false) String customerId,
                                 @RequestParam(value = "shopId", required = false) String shopId,
                                 Model model) {

        // Validări manuale pentru relații
        if (customerId == null || customerId.isEmpty()) bindingResult.rejectValue("customer", "error.customer", "Required!");
        if (shopId == null || shopId.isEmpty()) bindingResult.rejectValue("shop", "error.shop", "Required!");

        if (bindingResult.hasErrors()) {
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("shops", shopRepository.findAll());
            return "purchase/form";
        }

        if (purchase.getId() == null || purchase.getId().isEmpty()) {
            purchase.setId(UUID.randomUUID().toString());
        }

        Customer c = customerRepository.findById(customerId).orElse(null);
        Shop s = shopRepository.findById(shopId).orElse(null);

        purchase.setCustomer(c);
        purchase.setShop(s);

        purchaseRepository.save(purchase);
        return "redirect:/purchase";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        purchaseRepository.deleteById(id);
        return "redirect:/purchase";
    }
}