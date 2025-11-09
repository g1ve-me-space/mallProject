package controller;

import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.ShopService;

import java.util.List;
import java.util.UUID; // A better way to generate unique string IDs

@Controller // Changed from @RestController
@RequestMapping("/shop") // Changed from /api/shops to match the UI path
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    /**
     * GET /shop
     * Displays a list of all shops. This method now returns a template name.
     */
    @GetMapping
    public String listAllShops(Model model) {
        List<Shop> shops = shopService.findAll();
        model.addAttribute("shops", shops);
        return "shop/index"; // Renders templates/shop/index.html
    }

    /**
     * GET /shop/new
     * Displays the form to create a new shop. This is a new method.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("shop", new Shop());
        return "shop/form"; // Renders templates/shop/form.html
    }

    /**
     * POST /shop
     * Processes the form submission for creating a new shop.
     * Changed from @RequestBody to @ModelAttribute for web forms.
     */
    @PostMapping
    public String createShop(@ModelAttribute Shop shop) {
        // Since we are using String IDs, we must generate a unique one before saving.
        shop.setId(UUID.randomUUID().toString());
        shopService.save(shop);
        return "redirect:/shop"; // Redirects to the list page
    }

    /**
     * POST /shop/{id}/delete
     * Deletes the specified shop. This is a new method for the UI.
     */
    @PostMapping("/{id}/delete")
    public String deleteShop(@PathVariable String id) {
        shopService.deleteById(id);
        return "redirect:/shop";
    }

    // The other methods for the REST API have been removed to keep this controller focused on the UI.
}