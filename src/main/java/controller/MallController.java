package controller;

import model.Mall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.MallRepository;
// No service needed yet for this controller, using repository directly

import java.util.List;
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

    @PostMapping
    public String createMall(@ModelAttribute Mall mall) {
        // A service would normally handle this logic
        if (mall.getId() == null || mall.getId().trim().isEmpty()) {
            mall.setId(UUID.randomUUID().toString());
        }
        mallRepository.save(mall);
        return "redirect:/mall";
    }

    @PostMapping("/{id}/delete")
    public String deleteMall(@PathVariable String id) {
        // FIX: Call the new, correctly named method from our Repository interface.
        mallRepository.deleteById(id);
        return "redirect:/mall";
    }
}