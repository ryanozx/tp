package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_LEAVES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.BENSON_LEAVE;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.LeaveInPeriodPredicate;
import seedu.address.model.leave.Range;

public class FindLeaveByPeriodCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());

    @Test
    public void equals() {
        Date firstDate = Date.of("2023-10-30");
        Date secondDate = Date.of("2023-10-31");

        LeaveInPeriodPredicate firstPredicate = new LeaveInPeriodPredicate(
                Range.createNonNullRange(firstDate, firstDate));
        LeaveInPeriodPredicate secondPredicate = new LeaveInPeriodPredicate(
                Range.createNonNullRange(secondDate, secondDate));

        FindLeaveByPeriodCommand firstCommand = new FindLeaveByPeriodCommand(firstPredicate);
        FindLeaveByPeriodCommand secondCommand = new FindLeaveByPeriodCommand(secondPredicate);

        // same command
        assertEquals(firstCommand, firstCommand);
        // diff types
        assertFalse(firstCommand.equals("1"));
        // same predicate
        FindLeaveByPeriodCommand firstCommandCopy = new FindLeaveByPeriodCommand(firstPredicate);
        assertEquals(firstCommand, firstCommandCopy);
        // diff predicate
        assertNotEquals(firstCommand, secondCommand);
    }

    @Test
    public void execute_noLeavesFound() {
        String expectedMessage = String.format(MESSAGE_LEAVES_LISTED_OVERVIEW, 0);
        LeaveInPeriodPredicate predicate = new LeaveInPeriodPredicate(
                Range.createNullableRange(null, Date.of("2019-12-31"))
        );
        FindLeaveByPeriodCommand command = new FindLeaveByPeriodCommand(predicate);
        expectedModel.updateFilteredLeaveList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLeaveList());
    }

    @Test
    public void execute_twoPeopleFound() {
        String expectedMessage = String.format(MESSAGE_LEAVES_LISTED_OVERVIEW, 2);
        LeaveInPeriodPredicate predicate = new LeaveInPeriodPredicate(
                Range.createNullableRange(Date.of("2020-01-01"), Date.of("2020-01-02"))
        );
        FindLeaveByPeriodCommand command = new FindLeaveByPeriodCommand(predicate);
        expectedModel.updateFilteredLeaveList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_LEAVE, BENSON_LEAVE), model.getFilteredLeaveList());
    }

    @Test
    public void toStringMethod() {
        LeaveInPeriodPredicate predicate = new LeaveInPeriodPredicate(
                Range.createNullableRange(Date.of("2020-01-01"), Date.of("2020-01-02"))
        );
        FindLeaveByPeriodCommand command = new FindLeaveByPeriodCommand(predicate);
        String expected = FindLeaveByPeriodCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }
}
