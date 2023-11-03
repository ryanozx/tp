package seedu.address.storage;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.exceptions.CsvMissingFieldException;
import seedu.address.commons.util.CsvParsable;
import seedu.address.commons.util.GetValuer;
import seedu.address.model.leave.Leave;

/**
 * CSV-friendly version of {@link Leave}
 */
public class CsvAdaptedLeave extends AdaptedLeave implements CsvParsable {
    public static final String TITLE_HEADER = "Title";
    public static final String EMPLOYEE_HEADER = "Employee";
    public static final String START_HEADER = "Start";
    public static final String END_HEADER = "End";
    public static final String DESCRIPTION_HEADER = "Description";
    public static final String STATUS_HEADER = "Status";

    /**
     * Constructs a CsvAdaptedLeave
     * @param start Start date of leave
     * @param end End date of leave
     * @param title Title of leave
     * @param description Description of leave
     * @param status Status of leave
     * @param employee Name of employee
     */
    private CsvAdaptedLeave(String start, String end, String title, String description,
                            String status, String employee) {
        super(start, end, title, description, status, employee);
    }

    /**
     * Converts a given {@code Leave} into this class
     */
    public CsvAdaptedLeave(Leave source) {
        super(source);
    }

    @Override
    public List<String> getCsvValues() {
        String[] fieldValues = {title, employee.getName().toString(), start, end, description, status};
        return Arrays.asList(fieldValues);
    }

    /**
     * Returns a list of the column headers
     * @return List of column headers
     */
    public static List<String> getHeader() {
        String[] headers = {TITLE_HEADER, EMPLOYEE_HEADER, START_HEADER,
            END_HEADER, DESCRIPTION_HEADER, STATUS_HEADER};
        return Arrays.asList(headers);
    }

    /**
     * Constructs a CsvAdaptedLeave object from an object where values are queried using getValue()
     * @param row An object that contains values associated with a Leave, queried using getValue()
     * @return A CsvAdaptedLeave object with field values from the CsvRow
     * @throws CsvMissingFieldException if the CsvRow does not contain the field requested
     */
    public static CsvAdaptedLeave deserialiseLeave(GetValuer row) throws CsvMissingFieldException {
        String title = row.getValue(TITLE_HEADER);
        String employee = row.getValue(EMPLOYEE_HEADER);
        String start = row.getValue(START_HEADER);
        String end = row.getValue(END_HEADER);
        String description = row.getValue(DESCRIPTION_HEADER);
        String status = row.getValue(STATUS_HEADER);
        return new CsvAdaptedLeave(start, end, title, description, status, employee);
    }
}
