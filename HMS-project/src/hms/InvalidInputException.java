package hms;

/**
 * Custom checked exception used across the Hospital Management System
 * to signal invalid user input (e.g. empty strings, out-of-range numbers,
 * malformed values) so it can be handled distinctly from generic
 * runtime errors.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }
}
