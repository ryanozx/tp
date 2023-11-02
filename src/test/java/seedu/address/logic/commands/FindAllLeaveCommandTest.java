package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        CommandResult expectedCommandResult = findAllLeaveCommand.execute(model);
        assertCommandSuccess(findAllLeaveCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void toStringMethod() {
        FindAllLeaveCommand findAllLeaveCommand = new FindAllLeaveCommand();
        String expected = FindAllLeaveCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, findAllLeaveCommand.toString());
    }
}
