package fr.ekod.cda.ja.tp7.repository;

import fr.ekod.cda.ja.tp7.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByDirectorId(Integer directorId);

    List<Movie> findByGenreIgnoreCase(String genre);

    @Query("""
        SELECT m
        FROM Movie m
        WHERE (:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:year IS NULL OR m.releaseYear = :year)
        AND (:genre IS NULL OR LOWER(m.genre) = LOWER(:genre))
    """)
    List<Movie> searchMovies(
            @Param("title") String title,
            @Param("year") Integer year,
            @Param("genre") String genre
    );

}
