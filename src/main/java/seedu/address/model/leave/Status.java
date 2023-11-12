package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

/**
 * Represents the status of a leave request.
 */
public class Status {

    /**
     * Represents all possible status types of a leave request.
     */
    public enum StatusType {
        PENDING, APPROVED, REJECTED
    }

    public static final String MESSAGE_CONSTRAINTS = "Status should be one of the following: "
            + "PENDING, APPROVED, REJECTED";
    private static final String REGEX = "PENDING|APPROVED|REJECTED";
    private final StatusType status;

    /**
     * Constructs a Status object from a StatusType enum
     * @param status One of PENDING, APPROVED, or REJECTED
     */
    private Status(StatusType status) {
        requireNonNull(status);
        this.status = status;
    }

    /**
     * Returns a {@code Status} object given a {@code String} status.
     *
     * @param status String containing status
     * @return Status object
     * @throws IllegalArgumentException if string does not match known status types
     */
    public static Status of(String status) throws IllegalArgumentException {
        requireNonNull(status);
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return new Status(StatusType.valueOf(status));
    }

    /**
     * Returns a {@code Status} object given a {@code StatusType} status.
     *
     * @param status StatusType value
     * @return Status object
     * @throws NullPointerException if no status supplied
     */
    public static Status of(StatusType status) throws NullPointerException {
        requireNonNull(status);
        return new Status(status);
    }

    /**
     * Returns whether the string matches the value of a valid StatusType
     * @param status String to check
     * @return True if string matches a StatusType value, False otherwise
     */
    public static boolean isValidStatus(String status) {
        return status.matches(REGEX);
    }

    /**
     * Returns a {@code Status} object with a default PENDING status
     *
     * @return Status object
     */
    public static Status getDefault() {
        return new Status(StatusType.PENDING);
    }

    public StatusType getStatusType() {
        return status;
    }

    @Override
    public String toString() {
        return status.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Status
                && status.equals(((Status) other).status));
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
