package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

/**
 * Predicate to test if a leave has a given status
 */
public class LeaveHasStatusPredicate implements Predicate<Leave> {
    private final Status status;

    /**
     * Constructs a LeaveHasStatusPredicate object.
     * @param status Status to match against
     */
    public LeaveHasStatusPredicate(Status status) {
        requireNonNull(status);
        this.status = status;
    }

    @Override
    public boolean test(Leave leave) {
        return leave.getStatus().equals(status);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LeaveHasStatusPredicate)) {
            return false;
        }

        LeaveHasStatusPredicate otherPred = (LeaveHasStatusPredicate) other;
        return this.status.equals(otherPred.status);
    }

}
