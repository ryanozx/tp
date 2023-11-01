package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Predicate to test if a leave's period intersects with the
 * queried period.
 */
public class LeaveInPeriodPredicate implements Predicate<Leave> {
    private final Date start;
    private final Date end;

    /**
     * Constructs a LeaveInPeriodPredicate object. There are 4 possible inputs for the range
     * 1) Both start and end dates are supplied - return only leaves that have periods
     * intersecting with this period
     * 2) Only the start date is supplied - return only leaves whose end date lies on or
     * is after the provided start date
     * 3) Only the end date is supplied - return only leaves whose start date lies on or
     * is after the provided end date
     * 4) No start date or end date is supplied - return all leaves
     * @param dateRange Range object representing query range
     */
    public LeaveInPeriodPredicate(Range dateRange) {
        requireNonNull(dateRange);
        this.start = dateRange.getStartDate();
        this.end = dateRange.getEndDate();
    }
    @Override
    public boolean test(Leave leave) {
        boolean hasStartDate = start != null;
        boolean hasEndDate = end != null;

        boolean isLeaveEndBeforeQueryStart = hasStartDate && leave.getEnd().isBefore(start);
        boolean isLeaveStartAfterQueryEnd = hasEndDate && leave.getStart().isAfter(end);

        return !isLeaveEndBeforeQueryStart && !isLeaveStartAfterQueryEnd;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LeaveInPeriodPredicate)) {
            return false;
        }

        LeaveInPeriodPredicate otherPredicate = (LeaveInPeriodPredicate) other;
        boolean hasMatchingStarts = (
                this.start != null && otherPredicate.start != null && this.start.equals(otherPredicate.start))
                || (this.start == null && otherPredicate.start == null);
        boolean hasMatchingEnds = (
                this.end != null && otherPredicate.end != null && this.end.equals(otherPredicate.end))
                || (this.end == null && otherPredicate.end == null);
        return hasMatchingStarts && hasMatchingEnds;
    }
}
