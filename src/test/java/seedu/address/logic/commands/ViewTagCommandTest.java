package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewTagCommand.MESSAGE_VIEW_TAG_NONE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewTagCommandTest {
    @Test
    public void execute_viewTagNone_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        assertCommandSuccess(new ViewTagCommand(), model, MESSAGE_VIEW_TAG_NONE, expectedModel);
    }

    @Test
    public void execute_viewTag_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ViewTagCommand viewTagCommand = new ViewTagCommand();
        CommandResult expectedCommandResult = viewTagCommand.execute(model);
        assertCommandSuccess(viewTagCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void toStringMethod() {
        ViewTagCommand viewTagCommand = new ViewTagCommand();
        String expected = ViewTagCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, viewTagCommand.toString());
    }
}
