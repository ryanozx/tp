package seedu.address.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser. Whereby the index is invalid.
 */
public class InvalidIndexException extends ParseException {
    public InvalidIndexException(String message) {
        super(message);
    }
}
