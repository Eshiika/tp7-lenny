package fr.ekod.cda.ja.tp7.controller;

import fr.ekod.cda.ja.tp7.dto.director.CreateDirectorDTO;
import fr.ekod.cda.ja.tp7.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.tp7.service.DirectorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DirectorWebController {

    private final DirectorService directorService;

    public DirectorWebController(DirectorService directorService) {
        this.directorService = directorService;
    }

    // Page des réalisateurs
    @GetMapping("/directors")
    public String directors(Model model) {
        model.addAttribute(
                "directors",
                directorService.findAll()
        );
        return "directors/list";
    }

    // Page détail d'un réalisateur
    @GetMapping("/directors/{id}")
    public String directorDetail(@PathVariable Integer id, Model model) {
        model.addAttribute(
                "director",
                directorService.findById(id)
        );
        return "directors/detail";
    }

    // Ajout d'un réalisateur
    @PostMapping("/directors/new")
    public String createMovie(
            @Valid @ModelAttribute("movieForm") CreateDirectorDTO dto,
            BindingResult result,
            Model model
    ) {
        directorService.createDirector(dto);
        return "redirect:/directors";
    }
}
