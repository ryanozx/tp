package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.CsvMismatchedColumnException;
import seedu.address.commons.exceptions.CsvMissingFieldException;

public class CsvFileTest {
    private static final String TEST_DELIMITER = ";";
    private static final List<String> TEST_HEADER_LIST = Arrays.asList("first", "second", "third");
    private static final String TEST_HEADER = String.join(TEST_DELIMITER, TEST_HEADER_LIST);
    private static final String NON_EXISTENT_HEADER = "fourth";
    private static final List<String> FIRST_ROW_VALS = Arrays.asList("firstVal", "secondVal", "thirdVal");

    private static final String FIRST_ROW = String.join(TEST_DELIMITER, FIRST_ROW_VALS);

    @Test
    public void constructor_stringHeader_returnsCsvFile() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        // Only way to retrieve header for now is to obtain the file stream and
        // extract the second string from the stream (since the first string contains the delimiter)
        List<String> lines = getLines(file);

        assert(lines.size() == 2);
        assertDelimiter(lines, TEST_DELIMITER);
        assertEquals(lines.get(1), TEST_HEADER);
    }

    private List<String> getLines(CsvFile file) {
        return file.getFileStream().collect(Collectors.toList());
    }

    private void assertDelimiter(List<String> lines, String delimiter) {
        assert(!lines.isEmpty());
        assertEquals(lines.get(0), String.format(CsvFile.DELIMITER_SPECIFIER, delimiter));
    }
    @Test
    public void constructor_listHeader_returnsCsvFile() {
        CsvFile file = new CsvFile(TEST_HEADER_LIST, TEST_DELIMITER);
        List<String> lines = getLines(file);

        assert(lines.size() == 2);
        assertDelimiter(lines, TEST_DELIMITER);
        String expectedResult = String.join(TEST_DELIMITER, TEST_HEADER_LIST);
        assertEquals(lines.get(1), expectedResult);
    }

    @Test
    public void addRow_stringRow() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);

        file.addRow(FIRST_ROW);
        List<String> lines = getLines(file);
        // 1 line for header, 1 line for row
        assert(lines.size() == 3);
        assertEquals(lines.get(2), FIRST_ROW);
    }

    @Test
    public void addRow_listRow() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);

        file.addRow(new MockCsvParsable(FIRST_ROW_VALS));
        List<String> lines = getLines(file);
        assert(lines.size() == 3);
        assertEquals(lines.get(2), FIRST_ROW);
    }

    private static class MockCsvParsable implements CsvParsable {
        private final List<String> values;

        public MockCsvParsable(List<String> values) {
            this.values = values;
        }
        public List<String> getCsvValues() {
            return values;
        }
    }

    @Test
    public void getFileStream() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        List<String> secondRowVals = Arrays.asList("fourthVal", "fifthVal", "sixthVal");
        String secondRow = String.join(TEST_DELIMITER, secondRowVals);

        file.addRow(FIRST_ROW);
        file.addRow(secondRow);
        List<String> lines = getLines(file);

        assert(lines.size() == 4);
        assertDelimiter(lines, TEST_DELIMITER);
        assertEquals(lines.get(1), TEST_HEADER);
        assertEquals(lines.get(2), FIRST_ROW);
        assertEquals(lines.get(3), secondRow);
    }

    @Test
    public void getRows_success() {
        // Also doubles as the test for CsvRow constructor via string, where the number
        // of values supplied equals to the number of columns in the header

        // Additionally, this can be taken as the test for CsvRow printRow()
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        file.addRow(FIRST_ROW);

        List<CsvFile.CsvRow> rows = getRows(file);
        assert(rows.size() == 1);
        assertEquals(rows.get(0).printRow(), FIRST_ROW);
    }

    private List<CsvFile.CsvRow> getRows(CsvFile file) {
        return file.getRows().collect(Collectors.toList());
    }

    @Test
    public void csvRowAddRow_nullRow_throwsException() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        assertThrows(NullPointerException.class, () -> file.addRow((CsvParsable) null));
    }
    @Test
    public void csvRowStringConstructor_fewerColsThanHeader_success() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        List<String> vals = Arrays.asList("firstVal", "secondVal");
        String row = String.join(TEST_DELIMITER, vals);

        file.addRow(row);
        List<CsvFile.CsvRow> rows = getRows(file);
        assert(rows.size() == 1);
        List<String> expectedVals = Arrays.asList("firstVal", "secondVal", "");
        String expectedRow = String.join(TEST_DELIMITER, expectedVals);
        assertEquals(rows.get(0).printRow(), expectedRow);
    }

    @Test
    public void csvRowStringConstructor_moreColsThanHeader_throwsException() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        List<String> vals = Arrays.asList("firstVal", "secondVal", "thirdVal", "fourthVal");
        String row = String.join(TEST_DELIMITER, vals);

        assertThrows(CsvMismatchedColumnException.class, () -> file.addRow(row));
    }

    @Test
    public void csvRowListConstructor_sameColsAsHeader_success() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        file.addRow(new MockCsvParsable(FIRST_ROW_VALS));

        List<CsvFile.CsvRow> rows = getRows(file);
        assert(rows.size() == 1);
        assertEquals(rows.get(0).printRow(), FIRST_ROW);
    }

    @Test
    public void csvRowListConstructor_fewerColsThanHeader_success() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        List<String> vals = Arrays.asList("firstVal", "secondVal");

        file.addRow(new MockCsvParsable(vals));
        List<CsvFile.CsvRow> rows = getRows(file);
        assert(rows.size() == 1);
        List<String> expectedVals = Arrays.asList("firstVal", "secondVal", "");
        String expectedRow = String.join(TEST_DELIMITER, expectedVals);
        assertEquals(rows.get(0).printRow(), expectedRow);
    }

    @Test
    public void csvRowListConstructor_moreColsThanHeader_throwsException() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        List<String> vals = Arrays.asList("firstVal", "secondVal", "thirdVal", "fourthVal");

        assertThrows(CsvMismatchedColumnException.class, () -> file.addRow(new MockCsvParsable(vals)));
    }

    @Test
    public void csvRowGetValue_allValuesPresent_success() throws Exception {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        file.addRow(new MockCsvParsable(FIRST_ROW_VALS));

        List<CsvFile.CsvRow> rows = getRows(file);
        assert(rows.size() == 1);

        CsvFile.CsvRow firstCsvRow = rows.get(0);
        for (int i = 0; i < TEST_HEADER_LIST.size(); ++i) {
            assertEquals(firstCsvRow.getValue(TEST_HEADER_LIST.get(i)),
                    FIRST_ROW_VALS.get(i));
        }
    }

    @Test
    public void csvRowGetValue_missingField_throwsException() {
        CsvFile file = new CsvFile(TEST_HEADER, TEST_DELIMITER);
        file.addRow(new MockCsvParsable(FIRST_ROW_VALS));

        List<CsvFile.CsvRow> rows = getRows(file);
        assert(rows.size() == 1);
        CsvFile.CsvRow firstCsvRow = rows.get(0);
        assertThrows(CsvMissingFieldException.class, () ->
                firstCsvRow.getValue(NON_EXISTENT_HEADER));
    }
}
