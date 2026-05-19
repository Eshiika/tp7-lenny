package fr.ekod.cda.ja.tp7.dto.director;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateDirectorDTO(

        @NotBlank(message = "FirstName is required")
        String firstName,

        @NotBlank(message = "LastName is required")
        String lastName,

        String nationality,

        LocalDate birthDate

) {}