package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    // used when the employee index is out of bounds with respect to the Employee List
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX =
            "The employee index provided does not refer to any valid person."
            + " Please refer to the Employee list and verify that you have typed in the correct employee index.";
    // used when the employee index is < 1 or > 2^31 - 1
    public static final String MESSAGE_INVALID_PERSON_INDEX = "The employee index provided is not a value"
            + " between 1 and 2147483647";
    public static final String MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX =
            "The leave index provided does not refer to any valid leave."
            + " Please refer to the Leave list and verify that you have typed in the correct leave index.";
    public static final String MESSAGE_INVALID_LEAVE_INDEX = "The leave index provided is not a value"
            + " between 1 and 2147483647";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_LEAVES_LISTED_OVERVIEW = "%1$d leaves listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    public static final String EMPLOYEE_HEADER = "Employee: ";
    public static final String TITLE_HEADER = "; Title: ";
    public static final String START_HEADER = "; Start: ";
    public static final String END_HEADER = "; End: ";
    public static final String STATUS_HEADER = "; Status: ";
    public static final String DESCRIPTION_HEADER = "; Description: ";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ")
                .append(person.getPhone())
                .append("; Email: ")
                .append(person.getEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code leave} for display to the user.
     */
    public static String format(Leave leave) {
        final StringBuilder builder = new StringBuilder();
        builder.append(EMPLOYEE_HEADER)
                .append(leave.getEmployee().getName())
                .append(TITLE_HEADER)
                .append(leave.getTitle())
                .append(START_HEADER)
                .append(leave.getStart())
                .append(END_HEADER)
                .append(leave.getEnd())
                .append(STATUS_HEADER)
                .append(leave.getStatus());
        if (!leave.getDescription().isEmpty()) {
            builder.append(DESCRIPTION_HEADER)
                    .append(leave.getDescription());
        }
        return builder.toString();
    }

}
