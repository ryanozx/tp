package seedu.address.model.leave;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Leave}'s {@code employee} matches the employee of the given index.
 */
public class LeaveContainsPersonPredicate implements Predicate<Leave> {
    private final Person employee;
    public LeaveContainsPersonPredicate(Person employee) {
        this.employee = employee;
    }

    @Override
    public boolean test(Leave leave) {
        return employee.equals(leave.getEmployee());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof seedu.address.model.leave.LeaveContainsPersonPredicate)) {
            return false;
        }

        seedu.address.model.leave.LeaveContainsPersonPredicate otherLeaveContainsPersonPredicate =
                (seedu.address.model.leave.LeaveContainsPersonPredicate) other;
        return employee.equals(otherLeaveContainsPersonPredicate.employee);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("employee", employee).toString();
    }
}

