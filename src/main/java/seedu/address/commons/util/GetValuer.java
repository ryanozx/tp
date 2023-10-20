package seedu.address.commons.util;

import seedu.address.commons.exceptions.CsvMissingFieldException;

/**
 * Interface for an object that returns a string value when provided with a field to query for
 */
public interface GetValuer {
    /**
     * Returns a string value associated with the queried field
     * @param field Name of field to query for
     * @return String value associated with queried field
     * @throws CsvMissingFieldException if no value is associated with the queried field
     */
    String getValue(String field) throws CsvMissingFieldException;
}
