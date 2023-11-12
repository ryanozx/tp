package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_LEAVES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.LeaveContainsPersonPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.TestUtil;

public class FindLeaveCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
    @Test
    public void execute_findLeave_success() {
        Index index = Index.fromOneBased(1);
        FindLeaveCommand findLeaveCommand = new FindLeaveCommand(index);
        Person employee = TestUtil.getPerson(model, index);
        LeaveContainsPersonPredicate predicate = new LeaveContainsPersonPredicate(employee);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
        expectedModel.updateFilteredLeaveList(predicate);
        String expectedMessage = String.format(MESSAGE_LEAVES_LISTED_OVERVIEW,
                expectedModel.getFilteredLeaveList().size());
        assertCommandSuccess(findLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_findNoLeave_success() {
        Index index = Index.fromOneBased(3);
        FindLeaveCommand findLeaveCommand = new FindLeaveCommand(index);
        Person employee = TestUtil.getPerson(model, index);
        LeaveContainsPersonPredicate predicate = new LeaveContainsPersonPredicate(employee);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
        expectedModel.updateFilteredLeaveList(predicate);
        String expectedMessage = String.format(MESSAGE_LEAVES_LISTED_OVERVIEW,
                expectedModel.getFilteredLeaveList().size());
        assertCommandSuccess(findLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_indexOutOfBounds_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        FindLeaveCommand findLeaveCommand = new FindLeaveCommand(outOfBoundIndex);
        assertCommandFailure(findLeaveCommand, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(3);
        FindLeaveCommand findLeaveCommand = new FindLeaveCommand(index);
        String expectedMessage = FindLeaveCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expectedMessage, findLeaveCommand.toString());
    }

    @Test
    public void equals() {
        Index indexOne = Index.fromOneBased(1);
        Index indexTwo = Index.fromOneBased(2);

        FindLeaveCommand firstCommand = new FindLeaveCommand(indexOne);
        FindLeaveCommand secondCommand = new FindLeaveCommand(indexTwo);

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        FindLeaveCommand firstCommandCopy = new FindLeaveCommand(indexOne);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different tags -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
