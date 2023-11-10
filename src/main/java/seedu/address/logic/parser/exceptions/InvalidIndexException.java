package seedu.address.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser. Whereby the index is invalid.
 */
public class InvalidIndexException extends ParseException {
    public static final String MESSAGE_INVALID_INDEX = "Index is invalid!";
    public InvalidIndexException() {
        super(MESSAGE_INVALID_INDEX);
    }
}
