package seedu.address.commons.exceptions;

/**
 * Represents an error where the number of columns of a row not matching the number of columns in the header
 */
public class CsvMismatchedColumnException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Incorrect number of columns when parsing CSV row,"
            + " expected %d columns, encountered %d columns.";
    public CsvMismatchedColumnException(int expectedColumnCount, int actualColumnCount) {
        super(String.format(ERROR_MESSAGE, expectedColumnCount, actualColumnCount));
    }
}
