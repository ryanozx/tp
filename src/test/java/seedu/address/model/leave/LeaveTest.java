package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.BOB_LEAVE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.leave.exceptions.EndBeforeStartException;
import seedu.address.testutil.LeaveBuilder;

public class LeaveTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Leave(null, null, null, null));
        assertThrows(NullPointerException.class, () -> new Leave(null, null, null, null, null));
    }

    @Test
    public void constructor_invalidLeave_throwsEndBeforeStartException() {
        assertThrows(EndBeforeStartException.class, () -> new Leave(ALICE, "Alice's Maternity Leave",
                ALICE_LEAVE.getEnd(), ALICE_LEAVE.getStart()));
        assertThrows(EndBeforeStartException.class, () -> new Leave(BOB, "Bob's Paternity Leave",
                BOB_LEAVE.getEnd(), BOB_LEAVE.getStart(), "Bob's Paternity Leave Description"));
    }

    @Test
    public void equalsMethod() {
        // same values -> returns true
        Leave aliceCopy = new LeaveBuilder(ALICE_LEAVE).build();
        assertTrue(ALICE_LEAVE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_LEAVE.equals(ALICE_LEAVE));

        // null -> returns false
        assertFalse(ALICE_LEAVE.equals(null));

        // different type -> returns false
        assertFalse(ALICE_LEAVE.equals(5));

        // different leave -> returns false
        assertFalse(ALICE_LEAVE.equals(BOB_LEAVE));

        // different employee -> returns false
        Leave editedAliceLeave = new LeaveBuilder(ALICE_LEAVE).withEmployee(BOB).build();
        assertFalse(ALICE_LEAVE.equals(editedAliceLeave));

        // different title -> returns false
        editedAliceLeave = new LeaveBuilder(ALICE_LEAVE).withTitle("Bob's Paternity Leave").build();
        assertFalse(ALICE_LEAVE.equals(editedAliceLeave));

        // different description -> returns false
        editedAliceLeave = new LeaveBuilder(ALICE_LEAVE).withDescription("Bob's Paternity Leave Description").build();
        assertFalse(ALICE_LEAVE.equals(editedAliceLeave));

        // different start date -> returns false
        editedAliceLeave = new LeaveBuilder(ALICE_LEAVE).withStart(ALICE_LEAVE.getStart().plusDays(1)).build();
        assertFalse(ALICE_LEAVE.equals(editedAliceLeave));

        // different end date -> returns false
        editedAliceLeave = new LeaveBuilder(ALICE_LEAVE).withEnd(ALICE_LEAVE.getEnd().plusDays(1)).build();
        assertFalse(ALICE_LEAVE.equals(editedAliceLeave));
    }

    @Test
    public void isSameLeaveMethod() {
        // same object -> returns true
        assertTrue(ALICE_LEAVE.isSameLeave(ALICE_LEAVE));

        // null -> returns false
        assertFalse(ALICE_LEAVE.isSameLeave(null));

        // different leave -> returns false
        assertFalse(ALICE_LEAVE.isSameLeave(BOB_LEAVE));

        // different employee -> returns false
        assertFalse(ALICE_LEAVE.isSameLeave(new LeaveBuilder(ALICE_LEAVE).withEmployee(BOB).build()));

        // different start date -> returns false
        assertFalse(ALICE_LEAVE.isSameLeave(new LeaveBuilder(ALICE_LEAVE).withStart(ALICE_LEAVE.getStart().plusDays(1))
                .build()));

        // different end date -> returns false
        assertFalse(ALICE_LEAVE.isSameLeave(new LeaveBuilder(ALICE_LEAVE).withEnd(ALICE_LEAVE.getEnd().plusDays(1))
                .build()));

        // same employee, same start date, same end date -> returns true
        assertTrue(ALICE_LEAVE.isSameLeave(new LeaveBuilder(ALICE_LEAVE).withTitle("Alice's Maternity Leave 2")
                .withDescription("Alice's Maternity Leave 2 Description").build()));

        // different status -> return true
        assertTrue(ALICE_LEAVE.isSameLeave(new LeaveBuilder(ALICE_LEAVE).withStatus("Approved").build()));
    }

    @Test
    public void toStringMethod() {
        assertEquals(ALICE_LEAVE.toString(), "Employee: " + ALICE.getName()
                + " Title: " + ALICE_LEAVE.getTitle()
                + " Start: " + ALICE_LEAVE.getStart()
                + " End: " + ALICE_LEAVE.getEnd()
                + " Status: " + ALICE_LEAVE.getStatus());
    }

    @Test
    public void belongsToMethod() {
        // same employee -> returns true
        assertTrue(ALICE_LEAVE.belongsTo(ALICE));

        // different employee -> returns false
        assertFalse(ALICE_LEAVE.belongsTo(BOB));
    }

    @Test
    public void hashCodeMethod() {
        // same object -> returns same hashcode
        assertEquals(ALICE_LEAVE.hashCode(), ALICE_LEAVE.hashCode());

        // different leave -> returns different hashcode
        assertFalse(ALICE_LEAVE.hashCode() == BOB_LEAVE.hashCode());
    }
}
