package controller;

import jakarta.validation.Valid; // Import pentru validare
import model.Mall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Import pentru erori
import org.springframework.web.bind.annotation.*;
import repository.MallRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/mall")
public class MallController {

    private final MallRepository mallRepository;

    @Autowired
    public MallController(MallRepository mallRepository) {
        this.mallRepository = mallRepository;
    }

    @GetMapping
    public String listAllMalls(Model model) {
        List<Mall> malls = mallRepository.findAll();
        model.addAttribute("malls", malls);
        return "mall/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("mall", new Mall());
        return "mall/form";
    }

    // ⚠️ Aici am adăugat validarea cerută de temă
    @PostMapping
    public String createMall(@Valid @ModelAttribute Mall mall, BindingResult bindingResult) {

        // Dacă există erori (ex: nume gol), ne întoarcem la formular
        if (bindingResult.hasErrors()) {
            return "mall/form";
        }

        // Generare ID dacă nu există
        if (mall.getId() == null || mall.getId().trim().isEmpty()) {
            mall.setId(UUID.randomUUID().toString());
        }

        mallRepository.save(mall);
        return "redirect:/mall";
    }

    @PostMapping("/{id}/delete")
    public String deleteMall(@PathVariable String id) {
        mallRepository.deleteById(id);
        return "redirect:/mall";
    }

    // ---------- ADDED: show edit form ----------
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        Optional<Mall> opt = mallRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/mall";
        }
        model.addAttribute("mall", opt.get());
        return "mall/form";
    }

    // ---------- ADDED: handle edit submit ----------
    @PostMapping("/{id}/edit")
    public String updateMall(@PathVariable String id,
                             @Valid @ModelAttribute Mall mall,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "mall/form";
        }

        // ensure path id is the source of truth
        mall.setId(id);

        mallRepository.save(mall);
        return "redirect:/mall";
    }
}