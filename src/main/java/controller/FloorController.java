package controller;

import jakarta.validation.Valid;
import model.Floor;
import model.Mall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import repository.FloorRepository;
import repository.MallRepository;
import service.FloorService;

@Controller
@RequestMapping("/floor")
public class FloorController {

    private final FloorService floorService;
    private final MallRepository mallRepository; // Pentru Dropdown

    @Autowired
    public FloorController(FloorService floorService, MallRepository mallRepository) {
        this.floorService = floorService;
        this.mallRepository = mallRepository;
    }

    @GetMapping
    public String listAll(Model model) {
        model.addAttribute("floors", floorService.findAll());
        return "floor/index";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("floor", new Floor());
        // Trimitem lista de Mall-uri către HTML
        model.addAttribute("malls", mallRepository.findAll());
        return "floor/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Floor floor,
                         BindingResult bindingResult,
                         @RequestParam(value = "mallId", required = false) String mallId,
                         Model model) {

        if (bindingResult.hasErrors()) {
            // Dacă avem erori, trebuie să retrimitem lista de mall-uri
            model.addAttribute("malls", mallRepository.findAll());
            return "floor/form";
        }

        if (mallId != null && !mallId.isEmpty()) {
            Mall mall = mallRepository.findById(mallId).orElse(null);
            floor.setMall(mall);
        }

        floorService.save(floor);
        return "redirect:/floor";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        floorService.deleteById(id);
        return "redirect:/floor";
    }

    // Add these methods inside controller class (FloorController)

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Floor floor = floorService.findById(id).orElse(null);
        if (floor == null) {
            return "redirect:/floor";
        }
        model.addAttribute("floor", floor);
        model.addAttribute("malls", mallRepository.findAll());
        return "floor/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute Floor floor,
                         BindingResult bindingResult,
                         @RequestParam(value = "mallId", required = false) String mallId,
                         Model model) {

        if (bindingResult.hasErrors()) {
            // re-send malls list for the form
            model.addAttribute("malls", mallRepository.findAll());
            return "floor/form";
        }

        // ensure path id is used
        floor.setId(id);

        if (mallId != null && !mallId.isEmpty()) {
            Mall mall = mallRepository.findById(mallId).orElse(null);
            floor.setMall(mall);
        } else {
            floor.setMall(null);
        }

        floorService.save(floor);
        return "redirect:/floor";
    }
}