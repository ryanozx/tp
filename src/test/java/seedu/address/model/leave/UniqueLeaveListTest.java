package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.BOB_LEAVE;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.LeaveNotFoundException;
import seedu.address.testutil.LeaveBuilder;

public class UniqueLeaveListTest {

    private final UniqueLeaveList uniqueLeaveList = new UniqueLeaveList();

    @Test
    public void contains_nullLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLeaveList.contains(null));
    }

    @Test
    public void contains_leaveInList_returnsTrue() {
        uniqueLeaveList.add(ALICE_LEAVE);
        assertTrue(uniqueLeaveList.contains(ALICE_LEAVE));
    }

    @Test
    public void contains_leaveNotInList_returnsFalse() {
        assertFalse(uniqueLeaveList.contains(ALICE_LEAVE));
    }

    @Test
    public void contains_leaveWithSameIdentityFieldsInList_returnsTrue() {
        uniqueLeaveList.add(ALICE_LEAVE);
        Leave editedAliceLeave = new LeaveBuilder(ALICE_LEAVE).build();
        assertTrue(uniqueLeaveList.contains(editedAliceLeave));
    }

    @Test
    public void hasConcurrentLeave_nullLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLeaveList.hasConcurrentLeave(null));
    }

    @Test
    public void hasConcurrentLeave_leaveInList_returnsTrue() {
        uniqueLeaveList.add(ALICE_LEAVE);
        assertTrue(uniqueLeaveList.hasConcurrentLeave(ALICE_LEAVE));

        // Same employee name, different start date, same end date
        Leave editedAliceLeave = new LeaveBuilder(ALICE_LEAVE).withTitle("Alice's Maternity Leave 2")
                .withDescription("Alice's Maternity Leave 2 Description")
                .withStart(Date.of(ALICE_LEAVE.getStart().getDate().plusDays(1))).build();
        assertTrue(uniqueLeaveList.hasConcurrentLeave(editedAliceLeave));

        // Same employee name, same start date, different end date
        Leave editedAliceLeave2 = new LeaveBuilder(ALICE_LEAVE).withTitle("Alice's Maternity Leave 3")
                .withDescription("Alice's Maternity Leave 3 Description")
                .withEnd(Date.of(ALICE_LEAVE.getEnd().getDate().plusDays(1))).build();
        assertTrue(uniqueLeaveList.hasConcurrentLeave(editedAliceLeave2));

        // Same employee name, different start date, different end date
        Leave editedAliceLeave3 = new LeaveBuilder(ALICE_LEAVE).withTitle("Alice's Maternity Leave 4")
                .withDescription("Alice's Maternity Leave 4 Description")
                .withStart(Date.of(ALICE_LEAVE.getStart().getDate().plusDays(1)))
                .withEnd(Date.of(ALICE_LEAVE.getEnd().getDate().plusDays(1))).build();
        assertTrue(uniqueLeaveList.hasConcurrentLeave(editedAliceLeave3));
    }

    @Test
    public void hasConcurrentLeave_concurrentLeaveAbsent_returnsFalse() {
        uniqueLeaveList.add(ALICE_LEAVE);

        // different employee name
        assertFalse(uniqueLeaveList.hasConcurrentLeave(BOB_LEAVE));

        // different start date, different end date but not overlapping
        Leave editedAliceLeave = new LeaveBuilder(ALICE_LEAVE).withTitle("Alice's Maternity Leave 2")
                .withDescription("Alice's Maternity Leave 2 Description")
                .withStart(Date.of(ALICE_LEAVE.getStart().getDate().plusDays(10)))
                .withEnd(Date.of(ALICE_LEAVE.getEnd().getDate().plusDays(10))).build();
        assertFalse(uniqueLeaveList.hasConcurrentLeave(editedAliceLeave));
    }

    @Test
    public void add_nullLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLeaveList.add(null));
    }

    @Test
    public void add_duplicateLeave_throwsDuplicateLeaveException() {
        uniqueLeaveList.add(ALICE_LEAVE);
        assertThrows(DuplicateLeaveException.class, () -> uniqueLeaveList.add(ALICE_LEAVE));
    }

    @Test
    public void setLeave_nullTargetLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLeaveList.setLeave(null, ALICE_LEAVE));
    }

    @Test
    public void setLeave_nullEditedLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLeaveList.setLeave(ALICE_LEAVE, null));
    }

    @Test
    public void setLeave_targetLeaveNotInList_throwsLeaveNotFoundException() {
        assertThrows(LeaveNotFoundException.class, () -> uniqueLeaveList.setLeave(ALICE_LEAVE, ALICE_LEAVE));
    }

    @Test
    public void setLeave_setDuplicateLeave_throwsDuplicateLeaveException() {
        uniqueLeaveList.add(ALICE_LEAVE);
        uniqueLeaveList.add(BOB_LEAVE);
        assertThrows(DuplicateLeaveException.class, () -> uniqueLeaveList.setLeave(ALICE_LEAVE, BOB_LEAVE));
    }

    @Test
    public void setLeave_setEditedLeave_success() {
        uniqueLeaveList.add(ALICE_LEAVE);
        uniqueLeaveList.setLeave(ALICE_LEAVE, BOB_LEAVE);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        expectedUniqueLeaveList.add(BOB_LEAVE);
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void remove_nullLeave_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLeaveList.remove(null));
    }

    @Test
    public void remove_leaveDoesNotExist_throwsLeaveNotFoundException() {
        assertThrows(LeaveNotFoundException.class, () -> uniqueLeaveList.remove(ALICE_LEAVE));
    }

    @Test
    public void remove_existingLeave_removesLeave() {
        uniqueLeaveList.add(ALICE_LEAVE);
        uniqueLeaveList.remove(ALICE_LEAVE);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void setLeaves_nullUniqueLeaveList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLeaveList.setLeaves((UniqueLeaveList) null));
    }

    @Test
    public void setLeaves_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueLeaveList.setLeaves((List<Leave>) null));
    }

    @Test
    public void setLeaves_leaveListNotUnique_throwsDuplicateLeaveException() {
        List<Leave> listWithDuplicateLeaves = Arrays.asList(ALICE_LEAVE, ALICE_LEAVE);
        assertThrows(DuplicateLeaveException.class, () -> uniqueLeaveList.setLeaves(listWithDuplicateLeaves));
    }

    @Test
    public void setLeaves_uniqueLeaveList_replacesOwnListWithProvidedUniqueLeaveList() {
        uniqueLeaveList.add(ALICE_LEAVE);
        UniqueLeaveList expectedUniqueLeaveList = new UniqueLeaveList();
        expectedUniqueLeaveList.add(BOB_LEAVE);
        uniqueLeaveList.setLeaves(expectedUniqueLeaveList);
        assertEquals(expectedUniqueLeaveList, uniqueLeaveList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueLeaveList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueLeaveList.toString(), uniqueLeaveList.asUnmodifiableObservableList().toString());
    }

    @Test
    public void hashCodeMethod() {
        assertEquals(uniqueLeaveList.hashCode(), uniqueLeaveList.asUnmodifiableObservableList().hashCode());
    }

    @Test
    public void equalsMethod() {
        // same object -> returns true
        assertTrue(uniqueLeaveList.equals(uniqueLeaveList));

        // null -> returns false
        assertFalse(uniqueLeaveList.equals(null));

        // different type -> returns false
        assertFalse(uniqueLeaveList.equals(5));

        // different internal list -> returns false
        UniqueLeaveList uniqueLeaveListCopy = new UniqueLeaveList();
        uniqueLeaveListCopy.add(ALICE_LEAVE);
        assertFalse(uniqueLeaveList.equals(uniqueLeaveListCopy));

        // same internal list -> returns true
        uniqueLeaveList.add(ALICE_LEAVE);
        assertTrue(uniqueLeaveList.equals(uniqueLeaveListCopy));
    }
}
