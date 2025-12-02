package controller;

import jakarta.validation.Valid;
import model.ElectricalAsset;
import model.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repository.ElectricalAssetRepository;
import repository.FloorRepository;
import service.ElectricalAssetService; // Asigură-te că service-ul există sau folosește repository direct

import java.util.UUID;

@Controller
@RequestMapping("/electrical")
public class ElectricalAssetController {

    private final ElectricalAssetRepository assetRepository; // Folosim repo direct pentru simplitate
    private final FloorRepository floorRepository;

    @Autowired
    public ElectricalAssetController(ElectricalAssetRepository assetRepository, FloorRepository floorRepository) {
        this.assetRepository = assetRepository;
        this.floorRepository = floorRepository;
    }

    @GetMapping
    public String listAll(Model model, @RequestParam(value = "floorId", required = false) String floorId) {
        model.addAttribute("floors", floorRepository.findAll()); // Pentru filtrul din UI

        if (floorId != null && !floorId.isEmpty()) {
            model.addAttribute("assets", assetRepository.findByFloor_Id(floorId));
            model.addAttribute("selectedFloorId", floorId);
        } else {
            model.addAttribute("assets", assetRepository.findAll());
        }
        return "electrical/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("electricalAsset", new ElectricalAsset());
        model.addAttribute("floors", floorRepository.findAll());
        return "electrical/form";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        ElectricalAsset asset = assetRepository.findById(id).orElse(null);
        if (asset != null) {
            model.addAttribute("electricalAsset", asset);
            model.addAttribute("floors", floorRepository.findAll());
            return "electrical/form";
        }
        return "redirect:/electrical";
    }

    @PostMapping
    public String createOrUpdate(@Valid @ModelAttribute ElectricalAsset electricalAsset,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "floorId", required = false) String floorId,
                                 Model model) {

        if (floorId == null || floorId.isEmpty()) {
            bindingResult.rejectValue("floor", "error.floor", "Trebuie să selectezi un etaj!");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("floors", floorRepository.findAll());
            return "electrical/form";
        }

        if (electricalAsset.getId() == null || electricalAsset.getId().isEmpty()) {
            electricalAsset.setId(UUID.randomUUID().toString());
        }

        Floor floor = floorRepository.findById(floorId).orElse(null);
        electricalAsset.setFloor(floor);

        assetRepository.save(electricalAsset);
        return "redirect:/electrical";
    }

    @PostMapping("/{id}/delete")
    public String deleteAsset(@PathVariable String id) {
        assetRepository.deleteById(id);
        return "redirect:/electrical";
    }
}