package fr.ekod.cda.ja.tp7.controller;

import fr.ekod.cda.ja.tp7.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieSummaryDTO;
import fr.ekod.cda.ja.tp7.dto.movie.UpdateMovieDTO;
import fr.ekod.cda.ja.tp7.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieSummaryDTO>> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/movies/search")
    public ResponseEntity<List<MovieSummaryDTO>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre
    ) {
        return ResponseEntity.ok(movieService.searchMovies(title, year, genre));
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @GetMapping("/movies/genre/{genre}")
    public ResponseEntity<List<MovieSummaryDTO>> findByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(movieService.findByGenre(genre));
    }

    @GetMapping("/movies/director/{directorId}")
    public ResponseEntity<List<MovieSummaryDTO>> findByDirectorId(@PathVariable Integer directorId) {
        return ResponseEntity.ok(movieService.findByDirectorId(directorId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody CreateMovieDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieService.createMovie(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Integer id, @RequestBody CreateMovieDTO dto) {
        return ResponseEntity.ok(movieService.updateMovie(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/movies/{id}")
    public ResponseEntity<MovieDTO> patchMovie(@PathVariable Integer id, @Valid @RequestBody UpdateMovieDTO dto) {
        return ResponseEntity.ok(movieService.patchMovie(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
