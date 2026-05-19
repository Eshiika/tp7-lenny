package fr.ekod.cda.ja.tp7.service;

import fr.ekod.cda.ja.tp7.dto.movie.CreateMovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieDTO;
import fr.ekod.cda.ja.tp7.dto.movie.MovieSummaryDTO;
import fr.ekod.cda.ja.tp7.dto.movie.UpdateMovieDTO;
import fr.ekod.cda.ja.tp7.entity.Director;
import fr.ekod.cda.ja.tp7.entity.Movie;
import fr.ekod.cda.ja.tp7.exception.ResourceNotFoundException;
import fr.ekod.cda.ja.tp7.mapper.DirectorMapper;
import fr.ekod.cda.ja.tp7.mapper.MovieMapper;
import fr.ekod.cda.ja.tp7.repository.DirectorRepository;
import fr.ekod.cda.ja.tp7.repository.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final DirectorRepository directorRepository;

    public MovieService(
            MovieRepository movieRepository,
            MovieMapper movieMapper,
            DirectorRepository directorRepository
    ) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.directorRepository = directorRepository;
    }

    public List<MovieSummaryDTO> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapper::toSummaryDto)
                .toList();
    }

    public MovieDTO findById(Integer id) {
        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));
    }

    public List<MovieSummaryDTO> findByGenre(String genre) {
        return movieRepository.findByGenreIgnoreCase(genre)
                .stream()
                .map(movieMapper::toSummaryDto)
                .toList();
    }

    public List<MovieSummaryDTO> findByDirectorId(Integer directorId) {
        return movieRepository.findByDirectorId(directorId)
                .stream()
                .map(movieMapper::toSummaryDto)
                .toList();
    }

    public List<MovieSummaryDTO> searchMovies(String title, Integer year, String genre) {
        title = (title == null || title.isBlank()) ? null : title;
        genre = (genre == null || genre.isBlank()) ? null : genre;
        return movieRepository.searchMovies(title, year, genre)
                .stream()
                .map(movieMapper::toSummaryDto)
                .toList();
    }

    @Transactional
    public MovieDTO createMovie(CreateMovieDTO dto) {
        Director director = directorRepository.findById(dto.directorId())
                .orElseThrow(() -> new ResourceNotFoundException("Director", dto.directorId()));
        Movie movie = movieMapper.toEntity(dto);
        movie.setDirector(director);
        Movie saveMovie = movieRepository.save(movie);
        return movieMapper.toDto(saveMovie);
    }

    @Transactional
    public MovieDTO updateMovie(Integer id, CreateMovieDTO dto) {
        return movieRepository.findById(id)
                .map(movieMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));
    }

    @Transactional
    public MovieDTO patchMovie(Integer id, UpdateMovieDTO dto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", id));

        if (dto.title() != null) {
            movie.setTitle(dto.title());
        }

        if (dto.releaseYear() != null) {
            movie.setReleaseYear(dto.releaseYear());
        }

        if (dto.durationMinutes() != null) {
            movie.setDurationMinutes(dto.durationMinutes());
        }

        if (dto.genre() != null) {
            movie.setGenre(dto.genre());
        }

        if (dto.synopsis() != null) {
            movie.setSynopsis(dto.synopsis());
        }

        if (dto.directorId() != null) {
            Director director = directorRepository.findById(dto.directorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Director", dto.directorId()));

            movie.setDirector(director);
        }

        return movieMapper.toDto(movie);
    }

    @Transactional
    public void deleteMovie(Integer id) {
        movieRepository.deleteById(id);
    }

}
