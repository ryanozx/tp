package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.LeavesBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FindAllLeaveCommand.
 */
public class FindAllLeaveCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getLeavesBook(), new UserPrefs());
    }

    @Test
    public void execute_findAllLeave_success() {
        FindAllLeaveCommand findAllLeaveCommand = new FindAllLeaveCommand();
        CommandResult expectedCommandResult = new CommandResult(
                String.format(FindAllLeaveCommand.MESSAGE_LEAVE_COUNT, model.getFilteredLeaveList().size()));
        assertCommandSuccess(findAllLeaveCommand, model, expectedCommandResult, expectedModel);

        // test for empty leaves book
        Model emptyModel = new ModelManager(getTypicalAddressBook(), new LeavesBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(
                emptyModel.getAddressBook(), emptyModel.getLeavesBook(), new UserPrefs());
        CommandResult expectedEmptyCommandResult = new CommandResult(FindAllLeaveCommand.MESSAGE_FIND_LEAVE_NONE);
        assertCommandSuccess(findAllLeaveCommand, emptyModel, expectedEmptyCommandResult, expectedEmptyModel);
    }

    @Test
    public void toStringMethod() {
        FindAllLeaveCommand findAllLeaveCommand = new FindAllLeaveCommand();
        String expected = FindAllLeaveCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, findAllLeaveCommand.toString());
    }
}
