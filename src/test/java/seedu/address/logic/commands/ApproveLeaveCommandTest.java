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

public class ApproveLeaveCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());

    @Test
    public void execute_approveLeave_success() {
        Index indexLastLeave = TestUtil.getLastLeaveIndex(model);
        Leave originalLeave = TestUtil.getLeave(model, indexLastLeave);
        LeaveBuilder leaveInList = new LeaveBuilder(originalLeave);
        Leave approvedLeave = leaveInList.withStatus(StatusType.APPROVED).build();
        ApproveLeaveCommand approveLeaveCommand = new ApproveLeaveCommand(indexLastLeave);
        String expectedMessage = String.format(
                ApproveLeaveCommand.MESSAGE_APPROVE_LEAVE_SUCCESS,
                Messages.format(approvedLeave));
        Model expectedModel = new ModelManager(
                getTypicalAddressBook(),
                new LeavesBook(model.getLeavesBook()), new UserPrefs());
        expectedModel.setLeave(originalLeave, approvedLeave);
        assertCommandSuccess(approveLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateApproveLeave_failure() {
        Index indexLastLeave = TestUtil.getLastLeaveIndex(model);
        Leave originalLeave = TestUtil.getLeave(model, indexLastLeave);
        LeaveBuilder leaveInList = new LeaveBuilder(originalLeave);
        Leave approvedLeave = leaveInList.withStatus(StatusType.APPROVED).build();
        model.setLeave(originalLeave, approvedLeave);
        ApproveLeaveCommand approveLeaveCommand = new ApproveLeaveCommand(indexLastLeave);
        String expectedMessage = String.format(
                ApproveLeaveCommand.MESSAGE_DUPLICATE_LEAVE_APPROVE,
                Messages.format(approvedLeave));
        assertCommandFailure(approveLeaveCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Index outOfBoundIndex = TestUtil.getInvalidLeaveIndex(model);
        ApproveLeaveCommand approveLeaveCommand = new ApproveLeaveCommand(outOfBoundIndex);
        String expectedMessage = MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX;
        assertCommandFailure(approveLeaveCommand, model, expectedMessage);
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        ApproveLeaveCommand approveLeaveCommand = new ApproveLeaveCommand(index);
        String expected = ApproveLeaveCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, approveLeaveCommand.toString());
    }

    @Test
    public void equalsMethod() {
        final Index index = Index.fromOneBased(1);
        final ApproveLeaveCommand standardCommand = new ApproveLeaveCommand(index);

        // same values -> returns true
        ApproveLeaveCommand commandWithSameValues = new ApproveLeaveCommand(index);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        Index differentIndex = Index.fromOneBased(2);
        assertFalse(standardCommand.equals(new ApproveLeaveCommand(differentIndex)));
    }
}
