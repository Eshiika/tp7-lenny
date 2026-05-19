package fr.ekod.cda.ja.tp7.exception;

public record ResourceNotFoundErrorResponse(
        String message,
        String resource,
        Object id
) {}
