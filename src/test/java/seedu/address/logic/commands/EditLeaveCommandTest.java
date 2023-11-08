
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLeaveByPerson;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LEAVE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LEAVE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_LEAVE;
import static seedu.address.testutil.TypicalLeaves.DEFAULT_END;
import static seedu.address.testutil.TypicalLeaves.DEFAULT_START;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status.StatusType;
import seedu.address.testutil.EditLeaveDescriptorBuilder;
import seedu.address.testutil.LeaveBuilder;

public class EditLeaveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Leave editedLeave = new LeaveBuilder().build();
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder(editedLeave).build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, descriptor);

        String expectedMessage = String.format(EditLeaveCommand.MESSAGE_EDIT_LEAVE_SUCCESS,
                Messages.format(editedLeave));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new LeavesBook(model.getLeavesBook()), new UserPrefs());
        expectedModel.setLeave(model.getFilteredLeaveList().get(0), editedLeave);

        assertCommandSuccess(editLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLeave = Index.fromOneBased(model.getFilteredLeaveList().size());
        Leave lastLeave = model.getFilteredLeaveList().get(indexLastLeave.getZeroBased());

        LeaveBuilder leaveInList = new LeaveBuilder(lastLeave);
        Leave editedLeave = leaveInList.withTitle("NEW LEAVE").withDescription("NEW LEAVE DESCRIPTION")
            .withStatus(StatusType.REJECTED).build();

        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withTitle("NEW LEAVE")
            .withDescription("NEW LEAVE DESCRIPTION").withStatus(StatusType.REJECTED).build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(indexLastLeave, descriptor);

        String expectedMessage = String.format(EditLeaveCommand.MESSAGE_EDIT_LEAVE_SUCCESS,
                Messages.format(editedLeave));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new LeavesBook(model.getLeavesBook()), new UserPrefs());
        expectedModel.setLeave(lastLeave, editedLeave);

        assertCommandSuccess(editLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, new EditLeaveDescriptor());
        Leave editedLeave = model.getFilteredLeaveList().get(INDEX_FIRST_LEAVE.getZeroBased());

        String expectedMessage = String.format(EditLeaveCommand.MESSAGE_EDIT_LEAVE_SUCCESS,
                Messages.format(editedLeave));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new LeavesBook(model.getLeavesBook()), new UserPrefs());

        assertCommandSuccess(editLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLeaveByPerson(model, model.getFilteredPersonList().get(0));

        Leave leaveInFilteredList = model.getFilteredLeaveList().get(INDEX_FIRST_LEAVE.getZeroBased());
        Leave editedLeave = new LeaveBuilder(leaveInFilteredList).withTitle("NEW LEAVE").build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, new EditLeaveDescriptorBuilder()
                .withTitle("NEW LEAVE").build());

        String expectedMessage = String.format(EditLeaveCommand.MESSAGE_EDIT_LEAVE_SUCCESS,
                Messages.format(editedLeave));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new LeavesBook(model.getLeavesBook()), new UserPrefs());
        expectedModel.setLeave(model.getFilteredLeaveList().get(0), editedLeave);
        showLeaveByPerson(expectedModel, expectedModel.getFilteredPersonList().get(0));

        assertCommandSuccess(editLeaveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLeaveUnfilteredList_failure() {
        Leave firstLeave = model.getFilteredLeaveList().get(INDEX_FIRST_LEAVE.getZeroBased());
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder(firstLeave).build();
        // third index has same employee as first index
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(INDEX_THIRD_LEAVE, descriptor);
        assertCommandFailure(editLeaveCommand, model, EditLeaveCommand.MESSAGE_DUPLICATED_LEAVE);
    }

    @Test
    public void execute_duplicateLeaveFilteredList_failure() {
        showLeaveByPerson(model, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        Leave firstLeave = model.getFilteredLeaveList().get(INDEX_FIRST_LEAVE.getZeroBased());
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder(firstLeave).build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(INDEX_SECOND_LEAVE, descriptor);
        assertCommandFailure(editLeaveCommand, model, EditLeaveCommand.MESSAGE_DUPLICATED_LEAVE);
    }

    @Test
    public void execute_invalidLeaveIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withTitle("NEW TITLE").build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(outOfBoundsIndex, descriptor);

        assertCommandFailure(editLeaveCommand, model, Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLeaveIndexFilteredList_failure() {
        showLeaveByPerson(model, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Index outOfBoundsIndex = INDEX_THIRD_LEAVE;

        assertTrue(outOfBoundsIndex.getZeroBased() < model.getLeavesBook().getLeaveList().size());

        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(outOfBoundsIndex, new EditLeaveDescriptorBuilder()
                .withTitle("NEW TITLE").build());
        assertCommandFailure(editLeaveCommand, model, Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLeaveDateRange_failure() {
        assertFalse(DEFAULT_END.getDate().isBefore(DEFAULT_START.getDate()));
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withStart(DEFAULT_END).withEnd(DEFAULT_START)
            .build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, descriptor);

        assertCommandFailure(editLeaveCommand, model, Range.MESSAGE_INVALID_END_DATE);
    }

}
