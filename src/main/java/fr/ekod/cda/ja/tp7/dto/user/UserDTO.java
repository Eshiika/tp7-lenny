package fr.ekod.cda.ja.tp7.dto.user;

public record UserDTO(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String role
) {}