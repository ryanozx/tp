package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.leave.Status.StatusType.APPROVED;
import static seedu.address.model.leave.Status.StatusType.PENDING;
import static seedu.address.model.leave.Status.StatusType.REJECTED;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LeaveBuilder;

public class LeaveHasStatusPredicateTest {
    private static final LeaveHasStatusPredicate PENDING_PRED = new LeaveHasStatusPredicate(Status.of(PENDING));
    private static final LeaveHasStatusPredicate APPROVED_PRED = new LeaveHasStatusPredicate(Status.of(APPROVED));
    private static final LeaveHasStatusPredicate REJECTED_PRED = new LeaveHasStatusPredicate(Status.of(REJECTED));

    @Test
    public void constructor_nullRange_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LeaveHasStatusPredicate(null));
    }

    @Test
    public void equals() {
        // same predicate
        assertEquals(PENDING_PRED, PENDING_PRED);
        // diff type
        assertFalse(PENDING_PRED.equals("1"));

        // same status
        LeaveHasStatusPredicate pendingPredCopy = new LeaveHasStatusPredicate(Status.of(PENDING));
        assertEquals(PENDING_PRED, pendingPredCopy);

        // diff status
        assertNotEquals(PENDING_PRED, APPROVED_PRED);
        assertNotEquals(PENDING_PRED, REJECTED_PRED);
        assertNotEquals(APPROVED_PRED, REJECTED_PRED);
    }

    @Test
    public void test() {
        Leave approvedLeave = new LeaveBuilder().withStatus(APPROVED).build();
        Leave pendingLeave = new LeaveBuilder().withStatus(PENDING).build();
        Leave rejectedLeave = new LeaveBuilder().withStatus(REJECTED).build();

        assertTrue(APPROVED_PRED.test(approvedLeave));
        assertFalse(APPROVED_PRED.test(pendingLeave));
        assertFalse(APPROVED_PRED.test(rejectedLeave));

        assertTrue(PENDING_PRED.test(pendingLeave));
        assertFalse(PENDING_PRED.test(approvedLeave));
        assertFalse(PENDING_PRED.test(rejectedLeave));

        assertTrue(REJECTED_PRED.test(rejectedLeave));
        assertFalse(REJECTED_PRED.test(approvedLeave));
        assertFalse(REJECTED_PRED.test(pendingLeave));
    }
}
