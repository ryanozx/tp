package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.BOB_LEAVE;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.LeaveNotFoundException;
import seedu.address.testutil.LeaveBuilder;

public class LeavesBookTest {

    private final LeavesBook leavesBook = new LeavesBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), leavesBook.getLeaveList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> leavesBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyLeavesBook_replacesData() {
        LeavesBook newData = getTypicalLeavesBook();
        leavesBook.resetData(newData);
        assertEquals(newData, leavesBook);
    }

    @Test
    public void resetData_withDuplicateLeaves_throwsDuplicateLeaveException() {
        // Two leaves with the same identity fields
        Leave editedAlice = new LeaveBuilder(ALICE_LEAVE).withDescription(
                "Alice's Maternity Leave Description").build();
        List<Leave> newLeaves = Arrays.asList(ALICE_LEAVE, editedAlice);
        LeavesBookStub newData = new LeavesBookStub(newLeaves);

        assertThrows(DuplicateLeaveException.class, () -> leavesBook.resetData(newData));
    }

    @Test
    public void hasLeave_nullLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> leavesBook.hasLeave(null));
    }

    @Test
    public void hasLeave_leaveNotInLeavesBook_returnsFalse() {
        assertFalse(leavesBook.hasLeave(ALICE_LEAVE));
    }

    @Test
    public void hasLeave_leaveInLeavesBook_returnsTrue() {
        leavesBook.addLeave(ALICE_LEAVE);
        assertTrue(leavesBook.hasLeave(ALICE_LEAVE));
    }

    @Test
    public void hasLeave_leaveWithSameIdentityFieldsInLeavesBook_returnsTrue() {
        leavesBook.addLeave(ALICE_LEAVE);
        Leave editedAlice = new LeaveBuilder(ALICE_LEAVE).withDescription(
                "Alice's Maternity Leave Description").build();
        assertTrue(leavesBook.hasLeave(editedAlice));
    }

    @Test
    public void addLeave_nullLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> leavesBook.addLeave(null));
    }

    @Test
    public void addLeave_duplicateLeave_throwsDuplicateLeaveException() {
        leavesBook.addLeave(ALICE_LEAVE);
        assertThrows(DuplicateLeaveException.class, () -> leavesBook.addLeave(ALICE_LEAVE));
    }

    @Test
    public void setLeave_nullTargetLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> leavesBook.setLeave(null, ALICE_LEAVE));
    }

    @Test
    public void setLeave_targetNotInBook_throwsLeaveNotFoundException() {
        assertThrows(LeaveNotFoundException.class, () -> leavesBook.setLeave(ALICE_LEAVE, ALICE_LEAVE));
    }

    @Test
    public void setLeave_targetAlreadyExistInBook_throwsDuplicateLeaveException() {
        leavesBook.addLeave(ALICE_LEAVE);
        leavesBook.addLeave(BOB_LEAVE);
        assertThrows(DuplicateLeaveException.class, () -> leavesBook.setLeave(ALICE_LEAVE, BOB_LEAVE));
    }

    @Test
    public void setLeave_editedLeaveIsSameLeave_success() {
        leavesBook.addLeave(ALICE_LEAVE);
        leavesBook.setLeave(ALICE_LEAVE, ALICE_LEAVE);
        LeavesBook expectedLeavesBook = new LeavesBook();
        expectedLeavesBook.addLeave(ALICE_LEAVE);
        assertEquals(expectedLeavesBook, leavesBook);
    }

    @Test
    public void removeLeave_nullLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> leavesBook.removeLeave(null));
    }

    @Test
    public void removeLeave_leaveDoesNotExist_throwsLeaveNotFoundException() {
        assertThrows(LeaveNotFoundException.class, () -> leavesBook.removeLeave(ALICE_LEAVE));
    }

    @Test
    public void removeLeave_existingLeave_removesLeave() {
        leavesBook.addLeave(ALICE_LEAVE);
        leavesBook.removeLeave(ALICE_LEAVE);
        LeavesBook expectedLeavesBook = new LeavesBook();
        assertEquals(expectedLeavesBook, leavesBook);
    }

    @Test
    public void getLeaveList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> leavesBook.getLeaveList().remove(0));
    }

    @Test
    public void equalsMethod() {
        // same object -> returns true
        assertTrue(leavesBook.equals(leavesBook));

        // null -> returns false
        assertFalse(leavesBook.equals(null));

        // different types -> returns false
        assertFalse(leavesBook.equals(5));

        // different leaves book -> returns false
        LeavesBook differentLeavesBook = new LeavesBook();
        differentLeavesBook.addLeave(ALICE_LEAVE);
        assertFalse(leavesBook.equals(differentLeavesBook));

        // same unique leave list -> return true
        LeavesBook differentLeavesBook2 = new LeavesBook();
        differentLeavesBook2.addLeave(ALICE_LEAVE);
        assertTrue(differentLeavesBook.equals(differentLeavesBook2));
    }

    @Test
    public void toStringMethod() {
        String expected = LeavesBook.class.getCanonicalName() + "{leaves=" + leavesBook.getLeaveList() + "}";
        assertEquals(expected, leavesBook.toString());
    }

    /**
     * A stub ReadOnlyLeavesBook whose leaves list can violate interface constraints.
     */
    private static class LeavesBookStub implements ReadOnlyLeavesBook {
        private final ObservableList<Leave> leaves = FXCollections.observableArrayList();

        LeavesBookStub(Collection<Leave> leaves) {
            this.leaves.setAll(leaves);
        }

        @Override
        public ObservableList<Leave> getLeaveList() {
            return leaves;
        }
    }

}
