package controller;

import model.Floor;
import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.FloorRepository;
import repository.ShopRepository;
import service.ShopService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final FloorRepository floorRepository; // Avem nevoie de asta pentru Dropdown

    @Autowired
    public ShopController(ShopService shopService, FloorRepository floorRepository) {
        this.shopService = shopService;
        this.floorRepository = floorRepository;
    }

    @GetMapping
    public String listAllShops(Model model) {
        model.addAttribute("shops", shopService.findAll());
        return "shop/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("shop", new Shop());
        // ⚠️ TRIMITEM LISTA DE ETAJE CĂTRE HTML
        model.addAttribute("floors", floorRepository.findAll());
        return "shop/form";
    }

    @PostMapping
    public String createShop(@ModelAttribute Shop shop,
                             @RequestParam(value = "floorId", required = false) String floorId) {

        // Legăm magazinul de etajul ales din Dropdown
        if (floorId != null && !floorId.isEmpty()) {
            Floor floor = floorRepository.findById(floorId).orElse(null);
            shop.setFloor(floor);
        }

        shopService.save(shop);
        return "redirect:/shop";
    }

    @PostMapping("/{id}/delete")
    public String deleteShop(@PathVariable String id) {
        shopService.deleteById(id);
        return "redirect:/shop";
    }
}