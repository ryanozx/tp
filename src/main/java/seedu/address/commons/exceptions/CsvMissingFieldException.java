package seedu.address.commons.exceptions;

/**
 * Represents an error where a requested column is not available in the CSV file
 */
public class CsvMissingFieldException extends Exception {
    private static final String MESSAGE_FORMAT = "Field %s is not found in the CSV file";

    public CsvMissingFieldException(String field) {
        super(String.format(MESSAGE_FORMAT, field));
    }
}
