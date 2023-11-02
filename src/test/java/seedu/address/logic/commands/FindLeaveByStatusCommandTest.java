package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_LEAVES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE_2;
import static seedu.address.testutil.TypicalLeaves.BENSON_LEAVE;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.LeaveHasStatusPredicate;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Status.StatusType;

public class FindLeaveByStatusCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());

    @Test
    public void equals() {
        Status approvedStatus = Status.of(StatusType.APPROVED);
        Status pendingStatus = Status.of(StatusType.PENDING);
        Status rejectedStatus = Status.of(StatusType.REJECTED);

        LeaveHasStatusPredicate approvedPredicate = new LeaveHasStatusPredicate(approvedStatus);
        LeaveHasStatusPredicate pendingPredicate = new LeaveHasStatusPredicate(pendingStatus);
        LeaveHasStatusPredicate rejectedPredicate = new LeaveHasStatusPredicate(rejectedStatus);

        FindLeaveByStatusCommand approvedCommand = new FindLeaveByStatusCommand(approvedPredicate);
        FindLeaveByStatusCommand pendingCommand = new FindLeaveByStatusCommand(pendingPredicate);
        FindLeaveByStatusCommand rejectedCommand = new FindLeaveByStatusCommand(rejectedPredicate);

        // same command
        assertEquals(approvedCommand, approvedCommand);
        // diff types
        assertFalse(approvedCommand.equals("1"));
        // same predicate
        FindLeaveByStatusCommand approvedCommandCopy = new FindLeaveByStatusCommand(
                approvedPredicate);
        assertEquals(approvedCommand, approvedCommandCopy);
        // diff predicate
        assertNotEquals(approvedCommand, pendingCommand);
        assertNotEquals(approvedCommand, rejectedCommand);
        assertNotEquals(pendingCommand, rejectedCommand);
    }

    @Test
    public void execute_noLeavesFound() {
        String expectedMessage = String.format(MESSAGE_LEAVES_LISTED_OVERVIEW, 0);
        LeaveHasStatusPredicate predicate = new LeaveHasStatusPredicate(Status.of(StatusType.APPROVED));
        FindLeaveByStatusCommand command = new FindLeaveByStatusCommand(predicate);
        expectedModel.updateFilteredLeaveList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLeaveList());
    }

    @Test
    public void execute_threePeopleFound() {
        String expectedMessage = String.format(MESSAGE_LEAVES_LISTED_OVERVIEW, 3);
        LeaveHasStatusPredicate predicate = new LeaveHasStatusPredicate(Status.of(StatusType.PENDING));
        FindLeaveByStatusCommand command = new FindLeaveByStatusCommand(predicate);
        expectedModel.updateFilteredLeaveList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_LEAVE, BENSON_LEAVE, ALICE_LEAVE_2), model.getFilteredLeaveList());
    }

    @Test
    public void toStringMethod() {
        LeaveHasStatusPredicate predicate = new LeaveHasStatusPredicate(Status.of(StatusType.PENDING));
        FindLeaveByStatusCommand command = new FindLeaveByStatusCommand(predicate);
        String expected = FindLeaveByStatusCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
