package controller;

import jakarta.validation.Valid;
import model.Floor;
import model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repository.FloorRepository;
import repository.ShopRepository;

import java.util.UUID;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopRepository shopRepository;
    private final FloorRepository floorRepository;

    @Autowired
    public ShopController(ShopRepository shopRepository, FloorRepository floorRepository) {
        this.shopRepository = shopRepository;
        this.floorRepository = floorRepository;
    }

    // --- LIST & FILTER ---
    @GetMapping
    public String listAll(Model model, @RequestParam(value = "floorId", required = false) String floorId) {
        // Trimitem lista de etaje pentru filtrul din UI
        model.addAttribute("floors", floorRepository.findAll());

        if (floorId != null && !floorId.isEmpty()) {
            model.addAttribute("shops", shopRepository.findByFloor_Id(floorId));
            model.addAttribute("selectedFloorId", floorId);
        } else {
            model.addAttribute("shops", shopRepository.findAll());
        }
        return "shop/index";
    }

    // --- CREATE FORM ---
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("shop", new Shop());
        model.addAttribute("floors", floorRepository.findAll()); // Dropdown
        return "shop/form";
    }

    // --- EDIT FORM ---
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Shop shop = shopRepository.findById(id).orElse(null);
        if (shop != null) {
            model.addAttribute("shop", shop);
            model.addAttribute("floors", floorRepository.findAll()); // Dropdown
            return "shop/form";
        }
        return "redirect:/shop";
    }

    // --- SAVE (CREATE OR UPDATE) ---
    @PostMapping
    public String createOrUpdate(@Valid @ModelAttribute Shop shop,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "floorId", required = false) String floorId,
                                 Model model) {

        // Validare manuală pentru Dropdown
        if (floorId == null || floorId.isEmpty()) {
            bindingResult.rejectValue("floor", "error.floor", "Trebuie să selectezi un etaj!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("floors", floorRepository.findAll());
            return "shop/form";
        }

        // Generare ID doar dacă e nou
        if (shop.getId() == null || shop.getId().isEmpty()) {
            shop.setId(UUID.randomUUID().toString());
        }

        Floor floor = floorRepository.findById(floorId).orElse(null);
        shop.setFloor(floor);

        shopRepository.save(shop);
        return "redirect:/shop";
    }

    @PostMapping("/{id}/delete")
    public String deleteShop(@PathVariable String id) {
        shopRepository.deleteById(id);
        return "redirect:/shop";
    }
}