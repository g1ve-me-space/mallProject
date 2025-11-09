package controller;

import model.ElectricalAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.ElectricalAssetRepository;

import java.util.List;
import java.util.UUID;

@Controller // Changed from @RestController
@RequestMapping("/electrical") // Changed from /api/electricals
public class ElectricalAssetController {

    private final ElectricalAssetRepository electricalRepository;

    @Autowired
    public ElectricalAssetController(ElectricalAssetRepository electricalRepository) {
        this.electricalRepository = electricalRepository;
    }

    /**
     * GET /electrical
     * Displays a list of all electrical assets.
     */
    @GetMapping
    public String listAllAssets(Model model) {
        List<ElectricalAsset> assets = electricalRepository.findAll();
        model.addAttribute("assets", assets);
        return "electrical/index"; // Renders templates/electrical/index.html
    }

    /**
     * GET /electrical/new
     * Displays the form to create a new asset.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        ElectricalAsset newAsset = new ElectricalAsset();
        newAsset.setStatus("Working"); // Set a helpful default
        model.addAttribute("asset", newAsset);
        return "electrical/form"; // Renders templates/electrical/form.html
    }

    /**
     * POST /electrical
     * Processes the form submission for creating a new asset.
     */
    @PostMapping
    public String createAsset(@ModelAttribute ElectricalAsset asset) {
        asset.setId(UUID.randomUUID().toString());
        electricalRepository.save(asset);
        return "redirect:/electrical"; // Redirects to the list page
    }

    /**
     * POST /electrical/{id}/delete
     * Deletes the specified asset.
     */
    @PostMapping("/{id}/delete")
    public String deleteAsset(@PathVariable String id) {
        electricalRepository.delete(id);
        return "redirect:/electrical";
    }
}