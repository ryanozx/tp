package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

/**
 * Represents the status of a leave request.
 */
public class Status {

    /**
     * Represents all possible status types of a leave request.
     */
    public static enum StatusType {
        PENDING, APPROVED, REJECTED
    }

    public static final String MESSAGE_CONSTRAINTS = "Status should be one of the following: "
            + "PENDING, APPROVED, REJECTED";
    private StatusType status;

    private Status(StatusType status) {
        this.status = status;
    }

    /**
     * Returns a {@code Status} object given a {@code String} status.
     *
     * @param status
     * @return Status object
     * @throws IllegalArgumentException
     */
    public static Status of(String status) throws IllegalArgumentException {
        requireNonNull(status);
        if (!status.matches("PENDING|APPROVED|REJECTED")) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return new Status(StatusType.valueOf(status));
    }

    /**
     * Returns a {@code Status} object with a default PENDING status
     *
     * @return Status object
     */
    public static Status getDefault() {
        return new Status(StatusType.PENDING);
    }

    public StatusType getStatus() {
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
