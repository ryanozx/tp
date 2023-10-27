package seedu.address.model.leave.exceptions;

/**
 * Signals that the end date is before the start date.
 */
public class EndBeforeStartException extends RuntimeException {
    public EndBeforeStartException() {
        super("End date cannot be before start date.");
    }
}
