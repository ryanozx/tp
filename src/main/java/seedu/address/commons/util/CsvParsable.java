package seedu.address.commons.util;

import java.util.List;

/**
 * Interface for objects that can be serialised into CSV rows
 */
public interface CsvParsable {
    /**
     * Returns a list of string values that can be serialized into a CSV row
     * @return List of string values
     */
    List<String> getCsvValues();
}
