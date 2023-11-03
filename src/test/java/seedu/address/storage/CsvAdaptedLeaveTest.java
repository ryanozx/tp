package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.CsvAdaptedLeave.DESCRIPTION_HEADER;
import static seedu.address.storage.CsvAdaptedLeave.EMPLOYEE_HEADER;
import static seedu.address.storage.CsvAdaptedLeave.END_HEADER;
import static seedu.address.storage.CsvAdaptedLeave.START_HEADER;
import static seedu.address.storage.CsvAdaptedLeave.STATUS_HEADER;
import static seedu.address.storage.CsvAdaptedLeave.TITLE_HEADER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.CsvMissingFieldException;
import seedu.address.commons.util.GetValuer;

public class CsvAdaptedLeaveTest {
    private static final String VALID_TITLE = ALICE_LEAVE.getTitle().toString();
    private static final String VALID_EMPLOYEE = ALICE_LEAVE.getEmployee().getName().toString();
    private static final String VALID_START = ALICE_LEAVE.getStart().toFormattedString();
    private static final String VALID_END = ALICE_LEAVE.getEnd().toFormattedString();
    private static final String VALID_DESCRIPTION = ALICE_LEAVE.getDescription().toString();
    private static final String VALID_STATUS = ALICE_LEAVE.getStatus().toString();

    @Test
    public void getCsvValues_validFields() {
        String[] expectedValues = {VALID_TITLE, VALID_EMPLOYEE, VALID_START,
            VALID_END, VALID_DESCRIPTION, VALID_STATUS};

        CsvAdaptedLeave leave = new CsvAdaptedLeave(ALICE_LEAVE);
        assertEquals(leave.getCsvValues(), Arrays.asList(expectedValues));
    }

    @Test
    public void getHeader() {
        String[] expectedHeaders = {TITLE_HEADER, EMPLOYEE_HEADER, START_HEADER,
            END_HEADER, DESCRIPTION_HEADER, STATUS_HEADER};
        assertEquals(CsvAdaptedLeave.getHeader(), Arrays.asList(expectedHeaders));
    }

    /**
     * Mock CsvRow that returns ALICE_LEAVE's values when the corresponding headers are queried.
     * All headers' values are present.
     */
    class MockCsvRow implements GetValuer {
        private final boolean hasTitle;
        private final boolean hasEmployee;
        private final boolean hasStart;
        private final boolean hasEnd;
        private final boolean hasDescription;
        private final boolean hasStatus;

        /**
         * Constructs a MockCsvRow object with all headers' values present.
         */
        public MockCsvRow() {
            this.hasTitle = true;
            this.hasEmployee = true;
            this.hasStart = true;
            this.hasEnd = true;
            this.hasDescription = true;
            this.hasStatus = true;
        }

        /**
         * Constructs a MockCsvRow object. Boolean values are used to control whether the corresponding
         * fields' values are present in the row
         * @param hasTitle If the row contains the title
         * @param hasEmployee If the row contains the employee name
         * @param hasStart If the row contains the start date
         * @param hasEnd If the row contains the end date
         * @param hasDescription If the row contains the description
         * @param hasStatus If the row contains the status
         */
        public MockCsvRow(boolean hasTitle, boolean hasEmployee, boolean hasStart,
                          boolean hasEnd, boolean hasDescription, boolean hasStatus) {
            this.hasTitle = hasTitle;
            this.hasEmployee = hasEmployee;
            this.hasStart = hasStart;
            this.hasEnd = hasEnd;
            this.hasDescription = hasDescription;
            this.hasStatus = hasStatus;
        }

        /**
         * Returns values associated with the corresponding column.
         *
         * @param field Name of column to query for
         * @return String value associated with column
         * @throws CsvMissingFieldException if queried field has no associated value
         */
        public String getValue(String field) throws CsvMissingFieldException {
            if (hasTitle && field.equals(TITLE_HEADER)) {
                return VALID_TITLE;
            }
            if (hasEmployee && field.equals(EMPLOYEE_HEADER)) {
                return VALID_EMPLOYEE;
            }
            if (hasStart && field.equals(START_HEADER)) {
                return VALID_START;
            }
            if (hasEnd && field.equals(END_HEADER)) {
                return VALID_END;
            }
            if (hasDescription && field.equals(DESCRIPTION_HEADER)) {
                return VALID_DESCRIPTION;
            }
            if (hasStatus && field.equals(STATUS_HEADER)) {
                return VALID_STATUS;
            }
            throw new CsvMissingFieldException(field);
        }
    }

    @Test
    public void deserialiseLeave_allFieldsPresent_returnsCsvAdaptedLeave() throws Exception {
        GetValuer mockCsvRow = new CsvAdaptedLeaveTest.MockCsvRow();
        CsvAdaptedLeave leave = CsvAdaptedLeave.deserialiseLeave(mockCsvRow);
        assertEquals(leave.toModelType(), ALICE_LEAVE);
    }

    @Test
    public void deserialiseLeave_missingTitle_throwsException() {
        GetValuer mockCsvRow = new CsvAdaptedLeaveTest.MockCsvRow(
                false, true, true, true, true, true);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(TITLE_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedLeave.deserialiseLeave(mockCsvRow));
    }

    @Test
    public void deserialiseLeave_missingEmployee_throwsException() {
        GetValuer mockCsvRow = new CsvAdaptedLeaveTest.MockCsvRow(
                true, false, true, true, true, true);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(EMPLOYEE_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedLeave.deserialiseLeave(mockCsvRow));
    }

    @Test
    public void deserialiseLeave_missingStart_throwsException() {
        GetValuer mockCsvRow = new CsvAdaptedLeaveTest.MockCsvRow(
                true, true, false, true, true, true);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(START_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedLeave.deserialiseLeave(mockCsvRow));
    }

    @Test
    public void deserialiseLeave_missingEnd_throwsException() {
        GetValuer mockCsvRow = new CsvAdaptedLeaveTest.MockCsvRow(
                true, true, true, false, true, true);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(END_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedLeave.deserialiseLeave(mockCsvRow));
    }

    @Test
    public void deserialiseLeave_missingDescription_throwsException() {
        GetValuer mockCsvRow = new CsvAdaptedLeaveTest.MockCsvRow(
                true, true, true, true, false, true);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(DESCRIPTION_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedLeave.deserialiseLeave(mockCsvRow));
    }

    @Test
    public void deserialiseLeave_missingStatus_throwsException() {
        GetValuer mockCsvRow = new CsvAdaptedLeaveTest.MockCsvRow(
                true, true, true, true, true, false);
        CsvMissingFieldException expectedError = new CsvMissingFieldException(STATUS_HEADER);
        assertThrows(CsvMissingFieldException.class, expectedError.getMessage(), () ->
                CsvAdaptedLeave.deserialiseLeave(mockCsvRow));
    }
}
