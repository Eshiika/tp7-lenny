package fr.ekod.cda.ja.tp7.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

        @NotBlank(message = "FirstName is required")
        String firstName,

        @NotBlank(message = "LastName is required")
        String lastName,

        @Email(message = "The email address must be syntactically correct")
        String email,

        @Size(min = 8)
        String password

) {}