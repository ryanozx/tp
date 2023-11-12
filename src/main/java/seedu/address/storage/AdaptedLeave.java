package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.PersonEntry;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Title;
import seedu.address.model.leave.exceptions.EndBeforeStartException;
import seedu.address.model.person.ComparablePerson;

/**
 * Base class of AdaptedLeave used to serialise and deserialise the Leave class into different formats
 */
abstract class AdaptedLeave implements ToModelTyper<Leave> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Leave's %s field is missing!";

    protected final String start;
    protected final String end;
    protected final String title;
    protected final String description;
    protected final String status;
    protected final ComparablePerson employee;

    /**
     * Initialises fields in the AdaptedLeave base class
     * @param start Start date of leave
     * @param end End date of leave
     * @param title Title of leave
     * @param description Description of leave
     * @param status Status of leave
     * @param employee Name of employee
     * @throws IllegalArgumentException if name of employee violates naming constraints
     * @throws NullPointerException if null is passed in for employee
     */
    public AdaptedLeave(String start, String end, String title, String description,
                        String status, String employee)
            throws IllegalArgumentException, NullPointerException {
        this.start = start;
        this.end = end;
        this.title = title;
        this.description = description;
        this.status = status;
        this.employee = new PersonEntry(employee);
    }

    /**
     * Constructs an AdaptedLeave instance from a Leave object
     * @param leave Leave to extract fields from
     */
    public AdaptedLeave(Leave leave) {
        requireNonNull(leave);
        this.start = leave.getStart().toFormattedString();
        this.end = leave.getEnd().toFormattedString();
        this.title = leave.getTitle().toString();
        this.description = leave.getDescription().toString();
        this.status = leave.getStatus().toString();
        this.employee = leave.getEmployee();
    }

    /**
     * Converts this Jackson-friendly adapted leave object into the model's {@code Leave} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted leave.
     */
    @Override
    public Leave toModelType() throws IllegalValueException {
        final Title modelTitle = createTitle();
        final Description modelDescription = createDescription();
        final Range dateRange = createRange();
        final Status modelStatus = createStatus();
        final ComparablePerson modelEmployee = validateEmployee();
        return new Leave(modelEmployee, modelTitle, dateRange, modelDescription, modelStatus);
    }

    /**
     * Checks validity of title field and creates a Title object
     * @return Title object containing the title field
     * @throws IllegalValueException if the field does not satisfy Title's data constraints
     */
    private Title createTitle() throws IllegalValueException {
        checkNullField(title, "title");
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        return new Title(title);
    }

    /**
     * Checks if the field is null
     * @param field Field to check
     * @param fieldName Name of field
     * @throws IllegalValueException if field is null
     */
    private void checkNullField(Object field, String fieldName) throws IllegalValueException {
        if (field == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldName));
        }
    }

    /**
     * Checks validity of description field and creates a Description object
     * @return Description object containing the description field
     * @throws IllegalValueException if the field does not satisfy Description's data constraints
     */
    private Description createDescription() throws IllegalValueException {
        checkNullField(description, "description");
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(description);
    }

    /**
     * Creates a Range object from the start and end fields
     * @return Range representing the range of dates from start to end
     * @throws IllegalValueException if the start and end dates are not in the correct format or the
     *      end date is before the start date
     */
    private Range createRange() throws IllegalValueException {
        checkNullField(start, "start");
        checkNullField(end, "end");

        try {
            final Date modelStart = Date.of(start);
            final Date modelEnd = Date.of(end);
            return Range.createNonNullRange(modelStart, modelEnd);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        } catch (EndBeforeStartException e) {
            throw new IllegalValueException(Range.MESSAGE_END_BEFORE_START_ERROR);
        }
    }

    /**
     * Checks validity of status field and creates a Status object
     * @return Status object containing the status field
     * @throws IllegalValueException if the field does not satisfy Status's data constraints
     */
    private Status createStatus() throws IllegalValueException {
        checkNullField(status, "status");
        if (!Status.isValidStatus(status)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return Status.of(status);
    }

    /**
     * Checks that employee is non-null and employee has a name. It is not necessary to
     * test for name validity, or whether the full name in Name is non-null, as these checks
     * have already been performed during the initialisation of the Name object.
     * @return the same ComparablePerson instance
     * @throws IllegalValueException if either the employee field is null or the employee's name
     *      field is null
     */
    private ComparablePerson validateEmployee() throws IllegalValueException {
        checkNullField(employee, "employee");
        checkNullField(employee.getName(), "employee name");
        return employee;
    }
}
