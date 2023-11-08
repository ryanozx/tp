package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LEAVE;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.testutil.LeaveBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddLeaveCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
    }

    @Test
    public void execute_newLeave_success() {
        Leave validLeave = new LeaveBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getLeavesBook(), new UserPrefs());
        expectedModel.addLeave(validLeave);

        Range dateRange = Range.createNonNullRange(validLeave.getStart(), validLeave.getEnd());

        assertCommandSuccess(new AddLeaveCommand(INDEX_FIRST_LEAVE, validLeave.getTitle(),
                dateRange, validLeave.getDescription()), model,
                String.format(AddLeaveCommand.MESSAGE_SUCCESS, Messages.format(validLeave)),
                expectedModel);
    }

    @Test
    public void execute_duplicateLeave_throwsCommandException() {
        Leave leaveInList = model.getLeavesBook().getLeaveList().get(0);

        Range dateRange = Range.createNonNullRange(leaveInList.getStart(), leaveInList.getEnd());
        assertCommandFailure(new AddLeaveCommand(INDEX_FIRST_LEAVE, leaveInList.getTitle(),
                        dateRange, leaveInList.getDescription()), model,
                AddLeaveCommand.MESSAGE_DUPLICATE_LEAVE);
    }

}
