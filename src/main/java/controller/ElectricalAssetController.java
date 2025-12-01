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
import service.ElectricalAssetService;

import java.util.UUID;

@Controller
@RequestMapping("/electrical")
public class ElectricalAssetController {

    private final ElectricalAssetService electricalAssetService;
    private final FloorRepository floorRepository; // Pentru Dropdown

    @Autowired
    public ElectricalAssetController(ElectricalAssetService electricalAssetService, FloorRepository floorRepository) {
        this.electricalAssetService = electricalAssetService;
        this.floorRepository = floorRepository;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("assets", electricalAssetService.findAll());
        return "electrical/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("electricalAsset", new ElectricalAsset());
        // Trimitem lista de etaje către HTML
        model.addAttribute("floors", floorRepository.findAll());
        return "electrical/form";
    }

    @PostMapping
    public String createAsset(@Valid @ModelAttribute ElectricalAsset electricalAsset,
                              BindingResult bindingResult,
                              @RequestParam(value = "floorId", required = false) String floorId,
                              Model model) {

        // 1. VALIDARE MANUALĂ PENTRU DROPDOWN (ETAJ)
        if (floorId == null || floorId.isEmpty()) {
            // Dacă nu a ales nimic, băgăm o eroare manuală în "sacul" bindingResult
            // "floor" este numele câmpului, "error.floor" e un cod intern, iar ultimul e mesajul
            bindingResult.rejectValue("floor", "error.floor", "Trebuie să selectezi un etaj!");
        }

        // 2. VERIFICĂM DACĂ EXISTĂ ERORI (Fie de la @NotNull din Model, fie cea manuală de mai sus)
        if (bindingResult.hasErrors()) {
            // Reîncărcăm lista pentru dropdown, altfel dispare la eroare
            model.addAttribute("floors", floorRepository.findAll());
            return "electrical/form";
        }

        // 3. LOGICA DE SALVARE (Se execută doar dacă nu sunt erori)
        if (electricalAsset.getId() == null || electricalAsset.getId().isEmpty()) {
            electricalAsset.setId(UUID.randomUUID().toString());
        }

        // Găsim etajul și îl setăm
        Floor floor = floorRepository.findById(floorId).orElse(null);
        electricalAsset.setFloor(floor);

        electricalAssetService.save(electricalAsset);
        return "redirect:/electrical";
    }

    @PostMapping("/{id}/delete")
    public String deleteAsset(@PathVariable String id) {
        electricalAssetService.deleteById(id);
        return "redirect:/electrical";
    }
}