package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.LeavesBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Status.StatusType;
import seedu.address.testutil.LeaveBuilder;
import seedu.address.testutil.TestUtil;

public class RejectLeaveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
    private final StatusType rejectedStatus = StatusType.REJECTED;
    @Test
    public void execute_rejectLeave_success() {
        Index indexLastLeave = TestUtil.getLastLeaveIndex(model);
        Leave originalLeave = TestUtil.getLeave(model, indexLastLeave);
        LeaveBuilder leaveInList = new LeaveBuilder(originalLeave);
        Leave rejectedLeave = leaveInList.withStatus(rejectedStatus).build();
        RejectLeaveCommand rejectLeaveCommand = new RejectLeaveCommand(indexLastLeave);
        String expectedMessage = String.format(
                RejectLeaveCommand.MESSAGE_REJECT_LEAVE_SUCCESS,
                Messages.format(rejectedLeave));
        Model expectedModel = new ModelManager(
                getTypicalAddressBook(),
                new LeavesBook(model.getLeavesBook()), new UserPrefs());
        expectedModel.setLeave(originalLeave, rejectedLeave);
        assertCommandSuccess(rejectLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRejectLeave_failure() {
        Index indexLastLeave = TestUtil.getLastLeaveIndex(model);
        Leave originalLeave = TestUtil.getLeave(model, indexLastLeave);
        LeaveBuilder leaveInList = new LeaveBuilder(originalLeave);
        Leave rejectedLeave = leaveInList.withStatus(rejectedStatus).build();
        model.setLeave(originalLeave, rejectedLeave);
        RejectLeaveCommand rejectLeaveCommand = new RejectLeaveCommand(indexLastLeave);
        String expectedMessage = String.format(
                RejectLeaveCommand.MESSAGE_DUPLICATE_LEAVE_REJECT,
                Messages.format(rejectedLeave));
        assertCommandFailure(rejectLeaveCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index outOfBoundIndex = TestUtil.getInvalidLeaveIndex(model);
        RejectLeaveCommand rejectLeaveCommand = new RejectLeaveCommand(outOfBoundIndex);
        String expectedMessage = MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX;
        assertCommandFailure(rejectLeaveCommand, model, expectedMessage);
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        RejectLeaveCommand rejectLeaveCommand = new RejectLeaveCommand(index);
        String expected = RejectLeaveCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, rejectLeaveCommand.toString());
    }

    @Test
    public void equalsMethod() {
        final Index index = Index.fromOneBased(1);
        final RejectLeaveCommand standardCommand = new RejectLeaveCommand(index);

        // same values -> returns true
        RejectLeaveCommand commandWithSameValues = new RejectLeaveCommand(index);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        Index differentIndex = Index.fromOneBased(2);
        assertFalse(standardCommand.equals(new RejectLeaveCommand(differentIndex)));
    }
}
