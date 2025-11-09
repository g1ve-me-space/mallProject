package controller;

import model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.PurchaseRepository;

import java.util.List;
import java.util.UUID;

@Controller // Changed from @RestController
@RequestMapping("/purchase") // Changed from /api/purchases
public class PurchaseController {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseController(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    /**
     * GET /purchase
     * Displays a list of all purchases.
     */
    @GetMapping
    public String listAllPurchases(Model model) {
        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("purchases", purchases);
        return "purchase/index"; // Renders templates/purchase/index.html
    }

    /**
     * GET /purchase/new
     * Displays the form to create a new purchase.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("purchase", new Purchase());
        return "purchase/form"; // Renders templates/purchase/form.html
    }

    /**
     * POST /purchase
     * Processes the form submission for creating a new purchase.
     */
    @PostMapping
    public String createPurchase(@ModelAttribute Purchase purchase) {
        purchase.setId(UUID.randomUUID().toString());
        purchaseRepository.save(purchase);
        return "redirect:/purchase"; // Redirects to the list page
    }

    /**
     * POST /purchase/{id}/delete
     * Deletes the specified purchase.
     */
    @PostMapping("/{id}/delete")
    public String deletePurchase(@PathVariable String id) {
        purchaseRepository.delete(id);
        return "redirect:/purchase";
    }
}