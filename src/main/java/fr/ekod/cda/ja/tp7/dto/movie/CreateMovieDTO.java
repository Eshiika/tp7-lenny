package fr.ekod.cda.ja.tp7.dto.movie;

import fr.ekod.cda.ja.tp7.validation.ValidReleaseYear;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMovieDTO(

        @NotBlank(message = "Title is required")
        String title,

        @ValidReleaseYear
        int releaseYear,

        @NotNull(message = "DurationMinutes is required")
        int durationMinutes,

        String genre,

        String synopsis,

        @NotNull(message = "Director id is required")
        Integer directorId

) {}