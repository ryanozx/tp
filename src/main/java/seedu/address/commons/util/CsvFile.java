package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.CsvMismatchedColumnException;
import seedu.address.commons.exceptions.CsvMissingFieldException;

/**
 * Representation of the contents of a CSV file as an object.
 */
public class CsvFile {
    private final int numColumns;

    private final String delimiter;
    private final HashMap<String, Integer> columnIndices;
    private final String header;
    private final List<CsvRow> rows;

    /**
     * Represents a row of values in the CSV file. Encapsulating the row values
     * within this object allows one to extract the value in a column with just
     * the column's name, without having to know the column index.
     */
    public class CsvRow implements GetValuer {
        private final List<String> vals;

        /**
         * Constructs a CsvRow object. If the number of values supplied in the row is less
         * than the number of columns in the file, the values will be regarded as the values for
         * the first n columns, where n is the number of values in the row. The remaining columns
         * will be treated as empty values (empty string).
         * @param values String containing the values of the row delimited by the CSV delimiter
         * @throws CsvMismatchedColumnException Thrown if the number of values in the row is greater
         *      than the number of columns in the file
         */
        public CsvRow(String values) throws CsvMismatchedColumnException {
            String[] row = values.split(delimiter);
            boolean hasTooManyColumns = row.length > numColumns;
            if (hasTooManyColumns) {
                throw new CsvMismatchedColumnException(numColumns, row.length);
            }

            vals = new ArrayList<>(Arrays.asList(row));
            padRow();
        }

        /**
         * Constructs a CsvRow object. If the number of values supplied in the row is less
         * than the number of columns in the file, the values will be regarded as the values for
         * the first n columns, where n is the number of values in the row. The remaining columns
         * will be treated as empty values (empty string).
         * @param values List containing the values of the row delimited by the CSV delimiter
         * @throws CsvMismatchedColumnException if the number of values in the row is greater
         *      than the number of columns in the file
         */
        public CsvRow(List<?> values) throws CsvMismatchedColumnException {
            boolean hasTooManyColumns = values.size() > numColumns;
            if (hasTooManyColumns) {
                throw new CsvMismatchedColumnException(numColumns, values.size());
            }

            vals = values.stream().map(Object::toString).collect(Collectors.toList());
            padRow();
        }

        /**
         * Pads the end of the row with additional values (empty strings), in the event the number
         * of values in the row is less than the number of columns in the file. This
         * ensures that getValue() will always return a value, whether the value of the field
         * or an empty string. It is up to the user to handle the empty string case.
         */
        private void padRow() {
            boolean isDonePadding = vals.size() == numColumns;
            while (!isDonePadding) {
                vals.add("");
                isDonePadding = vals.size() == numColumns;
            }
        }

        /**
         * Returns the value in the row for a specified field.
         * @param field Name of field whose value should be returned
         * @return String representation of the value
         * @throws CsvMissingFieldException if the field does not exist in the file (no such
         *      column exists)
         */
        public String getValue(String field) throws CsvMissingFieldException {
            boolean isColumnPresent = columnIndices.containsKey(field);
            if (!isColumnPresent) {
                throw new CsvMissingFieldException(field);
            }

            int fieldIdx = columnIndices.get(field);
            return vals.get(fieldIdx);
        }

        /**
         * Returns the string representation of the row. This method is used to generate the
         * string representation of the row to write into the CSV file, since the values are
         * delimited by the delimiter.
         * @return String representation of row
         */
        public String printRow() {
            return String.join(delimiter, vals);
        }
    }

    /**
     * Constructs a CsvFile object. This constructor is called when constructing a CsvFile from a CSV file.
     * @param headers Headers in string representation delimited by specified delimiter.
     * @param delimiter The character used to delimit values in the CSV file. This delimiter should be used in both
     *                  header and rows.
     */
    public CsvFile(String headers, String delimiter) {
        requireNonNull(headers);
        requireNonNull(delimiter);

        this.delimiter = delimiter;
        rows = new ArrayList<>();
        header = headers.trim();

        String[] columnHeaders = headers.split(delimiter);
        numColumns = columnHeaders.length;

        columnIndices = new HashMap<>();
        for (int i = 0; i < columnHeaders.length; ++i) {
            columnIndices.put(columnHeaders[i], i);
        }
    }

    /**
     * Constructs a CsvFile object. This constructor is called when constructing a CsvFile from a list of objects
     * to be written into a CSV file.
     * @param headers List of header names
     * @param delimiter The character used to delimit values in the CSV file
     */
    public CsvFile(List<String> headers, String delimiter) {
        requireNonNull(headers);
        requireNonNull(delimiter);

        this.delimiter = delimiter;
        rows = new ArrayList<>();
        numColumns = headers.size();
        header = String.join(delimiter, headers);

        columnIndices = new HashMap<>();
        for (int i = 0; i < headers.size(); ++i) {
            columnIndices.put(headers.get(i), i);
        }
    }

    /**
     * Adds a row of values into the CSV file. This is useful if adding a row that is read from the CSV file.
     * @param row String representation of row to be added to the file, delimited by the delimiter
     */
    public void addRow(String row) {
        CsvRow newRow = new CsvRow(row);
        rows.add(newRow);
    }

    /**
     * Adds a row of values into the CSV file. This is useful if adding a row from a serialised object.
     * @param row List of values to be added to the file
     */
    public void addRow(List<?> row) {
        CsvRow newRow = new CsvRow(row);
        rows.add(newRow);
    }

    /**
     * Returns a stream of strings representing the header and rows of the CSV file. The first string in the
     * stream contains the header, while the rest of the stream consists of each row of values.
     * @return Stream of strings containing the headers and values
     */
    public Stream<String> getFileStream() {
        Stream<String> headerStream = Stream.of(header);
        Stream<String> valuesStream = rows.stream().map(CsvRow::printRow);

        return Stream.concat(headerStream, valuesStream);
    }

    /**
     * Returns a stream of rows
     * @return Steam of rows
     */
    public Stream<CsvRow> getRows() {
        return rows.stream();
    }
}

