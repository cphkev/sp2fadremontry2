package dat.lyngby.security.exceptions;

/**
 * Purpose: To handle validation exceptions in the API
 * Author: Thomas Hartmann
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
