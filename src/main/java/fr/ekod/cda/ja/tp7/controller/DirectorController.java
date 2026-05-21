package fr.ekod.cda.ja.tp7.controller;

import fr.ekod.cda.ja.tp7.dto.director.CreateDirectorDTO;
import fr.ekod.cda.ja.tp7.dto.director.DirectorDTO;
import fr.ekod.cda.ja.tp7.service.DirectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Réalisateur")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/directors")
    @Operation(summary = "Liste de réalisateurs")
    public ResponseEntity<List<DirectorDTO>> findAll() {
        return ResponseEntity.ok(directorService.findAll());
    }

    @GetMapping("/directors/{id}")
    @Operation(summary = "Recherche un réalisateur par son id")
    public ResponseEntity<DirectorDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(directorService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/directors")
    @Operation(summary = "Créer un réalisateur")
    public ResponseEntity<DirectorDTO> createDirector(@RequestBody CreateDirectorDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(directorService.createDirector(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/directors/{id}")
    @Operation(summary = "Modifie un réalisateur")
    public ResponseEntity<DirectorDTO> updateDirector(@PathVariable Integer id, @RequestBody CreateDirectorDTO dto) {
        return ResponseEntity.ok(directorService.updateDirector(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/directors/{id}")
    @Operation(summary = "Supprime un réalisateur")
    public ResponseEntity<Void> deleteDirector(@PathVariable Integer id) {
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }

}