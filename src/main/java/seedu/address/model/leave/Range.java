package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.model.leave.exceptions.EndBeforeStartException;


/**
 * Represents a range of dates
 * Guarantees: End date will not be before start date, if both are present
 */
public class Range {
    public static final String MESSAGE_END_BEFORE_START_ERROR =
            "The end date is earlier than the start date!";
    private final Date startDate;
    private final Date endDate;

    /**
     * Constructs a Range object. Since construction is only possible via the static methods,
     * it is guaranteed that the end date will not be before the start date, if both are present
     * @param startDate Start date of range/null
     * @param endDate End date of range/null
     */
    private Range(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructs a Range object with a non-null start date and a non-null end date
     * @param startDate Start date of range
     * @param endDate End date of range
     * @return Range object representing a range of dates from start date to end date inclusive
     * @throws EndBeforeStartException if end date is before start date
     * @throws NullPointerException if start or end date is null
     */
    public static Range createNonNullRange(Date startDate, Date endDate) throws EndBeforeStartException,
            NullPointerException {
        requireNonNull(startDate);
        requireNonNull(endDate);

        if (endDate.isBefore(startDate)) {
            throw new EndBeforeStartException();
        }

        return new Range(startDate, endDate);
    }

    /**
     * Constructs a Range object with nullable start date and nullable end date
     * @param startDate Start date of range
     * @param endDate End date of range
     * @return Range object representing a range of dates from start date (if present) to end date (if present)
     *      inclusive
     * @throws EndBeforeStartException if end date is before start date
     */
    public static Range createNullableRange(Date startDate, Date endDate) throws EndBeforeStartException {
        boolean hasStartDate = startDate != null;
        boolean hasEndDate = endDate != null;

        if (hasStartDate && hasEndDate && endDate.isBefore(startDate)) {
            throw new EndBeforeStartException();
        }

        return new Range(startDate, endDate);
    }

    /**
     * Returns the start date of the range as an Optional
     * @return Start date of range. Can be an empty Optional if no start date was provided.
     */
    public Optional<Date> getStartDate() {
        return Optional.ofNullable(startDate);
    }

    /**
     * Returns the end date of the range as an Optional
     * @return End date of range. Can be an empty Optional if no end date was provided.
     */
    public Optional<Date> getEndDate() {
        return Optional.ofNullable(endDate);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Range)) {
            return false;
        }

        Range otherRange = (Range) other;
        boolean hasMatchingStart = (this.startDate != null && otherRange.startDate != null
                && this.startDate.equals(otherRange.startDate))
                || (this.startDate == null && otherRange.startDate == null);
        boolean hasMatchingEnd = (this.endDate != null && otherRange.endDate != null
                && this.endDate.equals(otherRange.endDate))
                || (this.endDate == null && otherRange.endDate == null);
        return hasMatchingStart && hasMatchingEnd;
    }
}
