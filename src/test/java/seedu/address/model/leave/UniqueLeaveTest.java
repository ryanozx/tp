package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.BOB_LEAVE;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.LeaveNotFoundException;
import seedu.address.testutil.LeaveBuilder;

public class UniqueLeaveTest {

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

    @Test
    public void removePerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UniqueLeaveList().removePerson(null));
    }

    @Test
    public void removePerson_personInList_success() {
        Leave aliceLeave = new LeaveBuilder().withEmployee(
                new PersonEntry(ALICE.getName().toString())).build();
        Leave bobLeave = new LeaveBuilder().withEmployee(
                new PersonEntry(BOB.getName().toString())).build();

        // ensure we are not comparing based on same instance
        assertNotEquals(aliceLeave.getEmployee(), ALICE);
        assertNotEquals(bobLeave.getEmployee(), BOB);

        UniqueLeaveList ull = new UniqueLeaveList();
        ull.add(aliceLeave);
        ull.add(bobLeave);

        ull.removePerson(ALICE);
        assertTrue(ull.contains(bobLeave));
        assertFalse(ull.contains(aliceLeave));
    }

    @Test
    public void removePerson_personNotInList_noChange() {
        Leave aliceLeave = new LeaveBuilder().withEmployee(
                new PersonEntry(ALICE.getName().toString())).build();

        assertNotEquals(aliceLeave.getEmployee(), BOB);

        UniqueLeaveList ull = new UniqueLeaveList();
        ull.add(aliceLeave);
        ull.removePerson(BOB);
        assertTrue(ull.contains(aliceLeave));
    }

    @Test
    public void setPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UniqueLeaveList().setPerson(null, BOB));
        assertThrows(NullPointerException.class, () -> new UniqueLeaveList().setPerson(ALICE, null));
    }

    @Test
    public void setPerson_personInList_success() {
        Leave aliceLeave = new LeaveBuilder().withEmployee(
                new PersonEntry(ALICE.getName().toString())).build();
        Leave bobLeave = new LeaveBuilder().withEmployee(
                new PersonEntry(BOB.getName().toString())).build();

        // ensure we are not comparing based on same instance
        assertNotEquals(aliceLeave.getEmployee(), ALICE);
        assertNotEquals(bobLeave.getEmployee(), BOB);

        UniqueLeaveList ull = new UniqueLeaveList();
        ull.add(aliceLeave);
        ull.add(bobLeave);

        Leave expectedChangedLeave = new LeaveBuilder().withEmployee(
                new PersonEntry(AMY.getName().toString())).build();

        assertFalse(ull.contains(expectedChangedLeave));

        ull.setPerson(ALICE, AMY);
        assertTrue(ull.contains(bobLeave));
        assertFalse(ull.contains(aliceLeave));
        assertTrue(ull.contains(expectedChangedLeave));
    }

    @Test
    public void setPerson_personNotInList_noChange() {
        Leave aliceLeave = new LeaveBuilder().withEmployee(
                new PersonEntry(ALICE.getName().toString())).build();

        // ensure we are not comparing based on same instance
        assertNotEquals(aliceLeave.getEmployee(), ALICE);

        UniqueLeaveList ull = new UniqueLeaveList();
        ull.add(aliceLeave);

        ull.setPerson(BOB, AMY);
        assertTrue(ull.contains(aliceLeave));
    }
}
