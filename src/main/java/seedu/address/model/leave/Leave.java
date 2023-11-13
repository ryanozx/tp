package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.leave.exceptions.EndBeforeStartException;
import seedu.address.model.person.ComparablePerson;
import seedu.address.model.person.Person;

/**
 * Represents a Leave request of an employee in the address book
 */
public class Leave {

    private final ComparablePerson employee;
    private final Title title;
    private final Description description;
    private final Date start;
    private final Date end;
    private final Status status;

    /**
     * Constructs a Leave object. Takes in a Person object, title and description, date range.
     * Requires all fields to be non-null.
     *
     * @param employee Employee that implements ComparablePerson
     * @param title Leave title
     * @param dateRange Range representing start and end dates of leaves, both start and end cannot be null
     * @param description Leave description
     */
    public Leave(ComparablePerson employee, Title title, Range dateRange, Description description)
            throws EndBeforeStartException {
        requireAllNonNull(employee, title, description, dateRange);
        requireNonNull(employee.getName());
        assert(dateRange.getStartDate().isPresent() && dateRange.getEndDate().isPresent());

        this.employee = employee;
        this.title = title;
        this.description = description;
        this.start = dateRange.getStartDate().get();
        this.end = dateRange.getEndDate().get();
        this.status = Status.getDefault();
    }

    /**
     * Constructs a Leave object without the optional description field.
     *
     * @param employee Employee that implements ComparablePerson
     * @param title Leave title
     * @param dateRange Range representing start and end dates of leaves, both start and end cannot be null
     */
    public Leave(ComparablePerson employee, Title title, Range dateRange)
            throws EndBeforeStartException {
        requireAllNonNull(employee, title, dateRange);
        requireNonNull(employee.getName());
        assert(dateRange.getStartDate().isPresent() && dateRange.getEndDate().isPresent());

        this.employee = employee;
        this.title = title;
        this.description = Description.getDefault();
        this.start = dateRange.getStartDate().get();
        this.end = dateRange.getEndDate().get();
        this.status = Status.getDefault();
    }

    /**
     * Constructs a Leave object with status.
     *
     * @param employee Employee that implements ComparablePerson
     * @param title Leave title
     * @param dateRange Range representing start and end dates of leaves, both start and end cannot be null
     * @param description Leave description
     * @param status Leave status
     */
    public Leave(ComparablePerson employee, Title title, Range dateRange, Description description, Status status)
            throws EndBeforeStartException {
        requireAllNonNull(employee, title, description, dateRange, status);
        requireNonNull(employee.getName());
        assert(dateRange.getStartDate().isPresent() && dateRange.getEndDate().isPresent());

        this.employee = employee;
        this.title = title;
        this.description = description;
        this.start = dateRange.getStartDate().get();
        this.end = dateRange.getEndDate().get();
        this.status = status;
    }

    public ComparablePerson getEmployee() {
        return employee;
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Status getStatus() {
        return status;
    }

    public boolean belongsTo(Person employee) {
        return this.employee.isSamePerson(employee);
    }

    /**
     * Creates a new Leave instance with all fields identical to the leave the method is called on,
     * except with a new person
     * @param p Person to replace the person field in the leave with
     * @return New Leave instance containing the person
     */
    public Leave copyWithNewPerson(Person p) {
        requireNonNull(p);
        return new Leave(p, title, Range.createNonNullRange(start, end), description, status);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Leave)) {
            return false;
        }

        Leave otherLeave = (Leave) o;
        return otherLeave.getEmployee().isSamePerson(getEmployee())
                && otherLeave.getTitle().equals(getTitle())
                && otherLeave.getDescription().equals(getDescription())
                && otherLeave.getStart().equals(getStart())
                && otherLeave.getEnd().equals(getEnd())
                && otherLeave.getStatus().equals(getStatus());
    }

    /**
     * Returns true if both leaves have the same identity and data fields.
     */
    public boolean isSameLeave(Leave otherLeave) {
        if (otherLeave == this) {
            return true;
        }

        return otherLeave != null
                && otherLeave.getEmployee().isSamePerson(getEmployee())
                && otherLeave.getStart().equals(getStart())
                && otherLeave.getEnd().equals(getEnd());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Employee: ")
                .append(getEmployee().getName())
                .append(" Title: ")
                .append(getTitle())
                .append(" Start: ")
                .append(getStart())
                .append(" End: ")
                .append(getEnd())
                .append(" Status: ")
                .append(getStatus());
        return builder.toString();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(employee, title, description, start, end);
    }
}
