package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ViewTagCommand.MESSAGE_VIEW_TAG_NONE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ViewTagCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_VIEW_TAG_NONE, false, false);
        assertCommandSuccess(new ViewTagCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void toStringMethod() {
        ViewTagCommand viewTagCommand = new ViewTagCommand();
        String expected = ViewTagCommand.class.getCanonicalName() + "{}";
        assertEquals(expected, viewTagCommand.toString());
    }
}
