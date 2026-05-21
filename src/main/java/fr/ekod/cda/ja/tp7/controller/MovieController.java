package fr.ekod.cda.ja.tp7.controller;

import fr.ekod.cda.ja.tp7.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieSummaryDTO;
import fr.ekod.cda.ja.tp7.dto.movie.UpdateMovieDTO;
import fr.ekod.cda.ja.tp7.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Film")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    @Operation(summary = "Retourne la liste des films")
    public ResponseEntity<List<MovieSummaryDTO>> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/movies/search")
    @Operation(summary = "Recherche un film par son titre, son année ou son genre")
    public ResponseEntity<List<MovieSummaryDTO>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre
    ) {
        return ResponseEntity.ok(movieService.searchMovies(title, year, genre));
    }

    @GetMapping("/movies/{id}")
    @Operation(summary = "Recherche un film par son id")
    public ResponseEntity<MovieDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @GetMapping("/movies/genre/{genre}")
    @Operation(summary = "Recherche un film par son genre")
    public ResponseEntity<List<MovieSummaryDTO>> findByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(movieService.findByGenre(genre));
    }

    @GetMapping("/movies/director/{directorId}")
    @Operation(summary = "Recherche un film avec l'id d'un réalisateur")
    public ResponseEntity<List<MovieSummaryDTO>> findByDirectorId(@PathVariable Integer directorId) {
        return ResponseEntity.ok(movieService.findByDirectorId(directorId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movies")
    @Operation(summary = "Créer un film")
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody CreateMovieDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieService.createMovie(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/movies/{id}")
    @Operation(summary = "Modifie le film")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Integer id, @RequestBody CreateMovieDTO dto) {
        return ResponseEntity.ok(movieService.updateMovie(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/movies/{id}")
    @Operation(summary = "Modifie partiellement le film")
    public ResponseEntity<MovieDTO> patchMovie(@PathVariable Integer id, @Valid @RequestBody UpdateMovieDTO dto) {
        return ResponseEntity.ok(movieService.patchMovie(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/movies/{id}")
    @Operation(summary = "Supprime le film")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
