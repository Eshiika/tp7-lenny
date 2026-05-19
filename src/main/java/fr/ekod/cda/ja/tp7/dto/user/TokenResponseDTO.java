package fr.ekod.cda.ja.tp7.dto.user;

public record TokenResponseDTO (

        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn

) {}
