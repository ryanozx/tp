package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;

import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Status.StatusType;
import seedu.address.model.leave.Title;
import seedu.address.model.leave.exceptions.EndBeforeStartException;
import seedu.address.model.person.ComparablePerson;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Leave objects.
 */
public class LeaveBuilder {
    public static final Title DEFAULT_TITLE = new Title("Alice's Maternity Leave");
    public static final Description DEFAULT_DESCRIPTION = new Description("Alice's Maternity Leave Description");
    public static final Person DEFAULT_PERSON = ALICE;
    public static final Status DEFAULT_STATUS = Status.getDefault();
    public static final Date DEFAULT_START = Date.of("2020-02-01");
    public static final Date DEFAULT_END = Date.of("2020-02-02");

    private Title title;
    private Description description;
    private ComparablePerson employee;
    private Date start;
    private Date end;
    private Status status;
    /**
     * Creates a {@code LeaveBuilder} with the default details.
     */
    public LeaveBuilder() {
        title = DEFAULT_TITLE;
        description = DEFAULT_DESCRIPTION;
        employee = DEFAULT_PERSON;
        status = DEFAULT_STATUS;
        start = DEFAULT_START;
        end = DEFAULT_END;
    }

    /**
     * Initializes the LeaveBuilder with the data of {@code leaveToCopy}.
     */
    public LeaveBuilder(Leave leaveToCopy) {
        employee = leaveToCopy.getEmployee();
        title = leaveToCopy.getTitle();
        description = leaveToCopy.getDescription();
        start = leaveToCopy.getStart();
        end = leaveToCopy.getEnd();
        status = leaveToCopy.getStatus();
    }

    /**
     * Sets the {@code start} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withStart(Date start) {
        this.start = start;
        return this;
    }

    /**
     * Sets the {@code end} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withEnd(Date end) {
        this.end = end;
        return this;
    }

    /**
     * Sets the {@code employee} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withEmployee(ComparablePerson employee) {
        this.employee = employee;
        return this;
    }

    /**
     * Sets the {@code title} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code status} of the {@code Leave} that we are building.
     */
    public LeaveBuilder withStatus(StatusType status) {
        this.status = Status.of(status);
        return this;
    }

    /**
     * Builds Leave object based on attributes set previously
     * @return Leave object with given attributes
     * @throws EndBeforeStartException if end date is before start date
     * @throws NullPointerException if start or end date is null
     */
    public Leave build() throws EndBeforeStartException, NullPointerException {
        Range dateRange = Range.createNonNullRange(start, end);
        return new Leave(employee, title, dateRange, description, status);
    }
}
