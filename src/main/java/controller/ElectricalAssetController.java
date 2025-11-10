package controller;

import model.AssetStatus;
import model.AssetType;
import model.ElectricalAsset;
import model.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.ElectricalAssetRepository;
import repository.FloorRepository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/electrical")
public class ElectricalAssetController {

    private final ElectricalAssetRepository electricalRepository;
    private final FloorRepository floorRepository;

    @Autowired
    public ElectricalAssetController(ElectricalAssetRepository electricalRepository, FloorRepository floorRepository) {
        this.electricalRepository = electricalRepository;
        this.floorRepository = floorRepository;
    }

    @GetMapping
    public String listAllAssets(Model model) {
        List<ElectricalAsset> assets = electricalRepository.findAll();
        model.addAttribute("assets", assets);
        return "electrical/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        ElectricalAsset newAsset = new ElectricalAsset();
        newAsset.setStatus(AssetStatus.WORKING);

        List<Floor> allFloors = floorRepository.findAll();
        allFloors.sort(Comparator.comparing(Floor::getNumber));

        model.addAttribute("asset", newAsset);
        model.addAttribute("allFloors", allFloors);
        model.addAttribute("allTypes", AssetType.values());
        model.addAttribute("allStatuses", AssetStatus.values());

        return "electrical/form";
    }

    @PostMapping
    public String createAsset(@ModelAttribute ElectricalAsset asset) {
        asset.setId(UUID.randomUUID().toString());
        electricalRepository.save(asset);
        return "redirect:/electrical";
    }

    @PostMapping("/{id}/delete")
    public String deleteAsset(@PathVariable String id) {
        electricalRepository.delete(id);
        return "redirect:/electrical";
    }
}