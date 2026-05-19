package fr.ekod.cda.ja.tp7.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final Object id;

    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " with id " + id + " not found");
        this.resource = resource;
        this.id = id;
    }
}
