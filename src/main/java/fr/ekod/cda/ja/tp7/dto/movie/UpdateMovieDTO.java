package fr.ekod.cda.ja.tp7.dto.movie;

public record UpdateMovieDTO (
        String title,
        Integer releaseYear,
        Integer durationMinutes,
        String genre,
        String synopsis,
        Integer directorId
) {}
