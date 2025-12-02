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
import java.util.Optional;
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

        // Generare ID dacă lipsește (ensures entity has id before saving)
        if (shop.getId() == null || shop.getId().trim().isEmpty()) {
            shop.setId(UUID.randomUUID().toString());
        }

        // Legăm magazinul de etajul ales din Dropdown
        if (floorId != null && !floorId.isEmpty()) {
            Floor floor = floorRepository.findById(floorId).orElse(null);
            shop.setFloor(floor);
        } else {
            shop.setFloor(null);
        }

        shopService.save(shop);
        return "redirect:/shop";
    }

    @PostMapping("/{id}/delete")
    public String deleteShop(@PathVariable String id) {
        shopService.deleteById(id);
        return "redirect:/shop";
    }

    // ---------- ADDED: show edit form ----------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Shop> opt = shopService.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/shop";
        }
        model.addAttribute("shop", opt.get());
        model.addAttribute("floors", floorRepository.findAll());
        return "shop/form";
    }

    // ---------- ADDED: handle edit submit ----------
    @PostMapping("/{id}/edit")
    public String updateShop(@PathVariable String id,
                             @ModelAttribute Shop shop,
                             @RequestParam(value = "floorId", required = false) String floorId) {

        // ensure path id is the source of truth
        shop.setId(id);

        if (floorId != null && !floorId.isEmpty()) {
            Floor floor = floorRepository.findById(floorId).orElse(null);
            shop.setFloor(floor);
        } else {
            shop.setFloor(null);
        }

        shopService.save(shop);
        return "redirect:/shop";
    }
}