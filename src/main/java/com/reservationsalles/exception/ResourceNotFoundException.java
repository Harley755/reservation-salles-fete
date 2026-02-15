package com.reservationsalles.exception;

/**
 * Exception levée lorsqu'une ressource demandée n'est pas trouvée
 * Utilisée pour les cas 404 Not Found
 * 
 * @author Projet Master 1
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s non trouvé(e) avec %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
