package controller;

import model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.PurchaseRepository;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping
    public String listAllPurchases(Model model) {
        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("purchases", purchases);
        return "purchase/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        // This will now work because of the new constructor in Purchase.java
        model.addAttribute("purchase", new Purchase());
        return "purchase/form";
    }

    @PostMapping
    public String createPurchase(@ModelAttribute Purchase purchase) {
        purchaseRepository.save(purchase);
        return "redirect:/purchase";
    }

    @PostMapping("/{id}/delete")
    public String deletePurchase(@PathVariable String id) {
        // FIX: Call the new, correctly named method from our Repository interface.
        purchaseRepository.deleteById(id);
        return "redirect:/purchase";
    }
}