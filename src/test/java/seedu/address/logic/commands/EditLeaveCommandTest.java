
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status.StatusType;
import seedu.address.model.leave.exceptions.EndBeforeStartException;
import seedu.address.testutil.EditLeaveDescriptorBuilder;
import seedu.address.testutil.LeaveBuilder;

public class EditLeaveCommandTest {
    private static final String DEFAULT_TITLE = "New Title";
    private static final String DEFAULT_DESCRIPTION = "New Leave Description";
    private static final String DEFAULT_EMPTY_DESCRIPTION = "";
    private static final StatusType DEFAULT_STATUS = StatusType.REJECTED;
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(),
            new UserPrefs());
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
        Leave editedLeave = leaveInList.withTitle(DEFAULT_TITLE).withDescription(DEFAULT_DESCRIPTION)
            .withStatus(DEFAULT_STATUS).build();

        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withTitle(DEFAULT_TITLE)
            .withDescription(DEFAULT_DESCRIPTION).withStatus(DEFAULT_STATUS).build();
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
        Leave editedLeave = new LeaveBuilder(leaveInFilteredList).withTitle(DEFAULT_TITLE).build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, new EditLeaveDescriptorBuilder()
                .withTitle(DEFAULT_TITLE).build());

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
        EditLeaveDescriptor descriptor = new EditLeaveDescriptorBuilder().withTitle(DEFAULT_TITLE).build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(outOfBoundsIndex, descriptor);

        assertCommandFailure(editLeaveCommand, model, Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLeaveIndexFilteredList_failure() {
        showLeaveByPerson(model, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Index outOfBoundsIndex = INDEX_THIRD_LEAVE;

        assertTrue(outOfBoundsIndex.getZeroBased() < model.getLeavesBook().getLeaveList().size());

        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(outOfBoundsIndex, new EditLeaveDescriptorBuilder()
                .withTitle(DEFAULT_TITLE).build());
        assertCommandFailure(editLeaveCommand, model, Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidLeaveDateRange_failure() {
        assertFalse(DEFAULT_END.getDate().isBefore(DEFAULT_START.getDate()));
        // both start and end supplied, new end is before new start
        // process start then end
        assertThrows(EndBeforeStartException.class, () -> new EditLeaveDescriptorBuilder()
                .withStart(DEFAULT_END)
                .withEnd(DEFAULT_START)
                .build());

        // process end then start
        assertThrows(EndBeforeStartException.class, () -> new EditLeaveDescriptorBuilder()
                .withEnd(DEFAULT_START)
                .withStart(DEFAULT_END)
                .build());

        // only start supplied, start is after existing end
        Date lateStart = Date.of(DEFAULT_END.getDate().plusDays(1));
        EditLeaveDescriptor lateStartDescriptor = new EditLeaveDescriptorBuilder().withStart(lateStart).build();
        EditLeaveCommand lateStartCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, lateStartDescriptor);
        assertCommandFailure(lateStartCommand, model, Range.MESSAGE_END_BEFORE_START_ERROR);


        // only end supplied, end is after existing start
        Date earlyEnd = Date.of(DEFAULT_START.getDate().minusDays(1));
        EditLeaveDescriptor earlyEndDescriptor = new EditLeaveDescriptorBuilder().withEnd(earlyEnd).build();
        EditLeaveCommand earlyEndCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, earlyEndDescriptor);
        assertCommandFailure(earlyEndCommand, model, Range.MESSAGE_END_BEFORE_START_ERROR);
    }

    @Test
    public void editLeaveDescriptor_copyConstructor() {
        Leave editedLeave = new LeaveBuilder().build();
        EditLeaveDescriptor expectedLeaveDescriptor = new EditLeaveDescriptorBuilder(editedLeave).build();
        EditLeaveDescriptor actualLeaveDescriptor = new EditLeaveDescriptor(expectedLeaveDescriptor);
        assertEquals(expectedLeaveDescriptor, actualLeaveDescriptor);
    }

    @Test
    public void editLeaveDescriptor_isAnyFieldEdited() {
        // no fields edited -> returns false
        EditLeaveDescriptor notEditedLeaveDescriptor = new EditLeaveDescriptorBuilder().build();
        assertFalse(notEditedLeaveDescriptor.isAnyFieldEdited());

        EditLeaveDescriptor titleEditedDescriptor = new EditLeaveDescriptorBuilder().withTitle(DEFAULT_TITLE).build();
        assertTrue(titleEditedDescriptor.isAnyFieldEdited());

        EditLeaveDescriptor descriptionNonEmptyDescriptor = new EditLeaveDescriptorBuilder()
                .withDescription(DEFAULT_DESCRIPTION).build();
        assertTrue(descriptionNonEmptyDescriptor.isAnyFieldEdited());

        EditLeaveDescriptor descriptionEmptyDescriptor = new EditLeaveDescriptorBuilder()
                .withDescription(DEFAULT_EMPTY_DESCRIPTION).build();
        assertTrue(descriptionEmptyDescriptor.isAnyFieldEdited());

        EditLeaveDescriptor startEditedDescriptor = new EditLeaveDescriptorBuilder().withStart(DEFAULT_START).build();
        assertTrue(startEditedDescriptor.isAnyFieldEdited());

        EditLeaveDescriptor endEditedDescriptor = new EditLeaveDescriptorBuilder().withEnd(DEFAULT_END).build();
        assertTrue(endEditedDescriptor.isAnyFieldEdited());

        EditLeaveDescriptor statusEditedDescriptor = new EditLeaveDescriptorBuilder()
                .withStatus(DEFAULT_STATUS).build();
        assertTrue(statusEditedDescriptor.isAnyFieldEdited());
    }

    @Test
    public void editLeaveDescriptor_equals() {
        EditLeaveDescriptor emptyDescriptor = new EditLeaveDescriptorBuilder().build();
        assertEquals(emptyDescriptor, emptyDescriptor);

        assertFalse(emptyDescriptor.equals(1));

        EditLeaveDescriptor secondEmptyDescriptor = new EditLeaveDescriptorBuilder().build();
        assertEquals(emptyDescriptor, secondEmptyDescriptor);

        EditLeaveDescriptor descriptorWithTitle = new EditLeaveDescriptorBuilder().withTitle(DEFAULT_TITLE).build();
        assertNotEquals(emptyDescriptor, descriptorWithTitle);

        EditLeaveDescriptor descriptorWithNonEmptyDescription = new EditLeaveDescriptorBuilder()
                .withDescription(DEFAULT_DESCRIPTION).build();
        assertNotEquals(emptyDescriptor, descriptorWithNonEmptyDescription);

        EditLeaveDescriptor descriptorWithEmptyDescription = new EditLeaveDescriptorBuilder()
                .withDescription(DEFAULT_EMPTY_DESCRIPTION).build();
        assertNotEquals(emptyDescriptor, descriptorWithEmptyDescription);

        EditLeaveDescriptor descriptorWithStart = new EditLeaveDescriptorBuilder()
                .withStart(DEFAULT_START).build();
        assertNotEquals(emptyDescriptor, descriptorWithStart);

        EditLeaveDescriptor descriptorWithEnd = new EditLeaveDescriptorBuilder()
                .withEnd(DEFAULT_END).build();
        assertNotEquals(emptyDescriptor, descriptorWithEnd);

        EditLeaveDescriptor descriptorWithStatus = new EditLeaveDescriptorBuilder()
                .withStatus(DEFAULT_STATUS).build();
        assertNotEquals(emptyDescriptor, descriptorWithStatus);
    }

    @Test
    public void equals() {
        Leave leave = new LeaveBuilder().build();
        EditLeaveDescriptor defaultDescriptor = new EditLeaveDescriptorBuilder(leave).build();
        EditLeaveCommand editLeaveCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, defaultDescriptor);

        // same command instance
        assertEquals(editLeaveCommand, editLeaveCommand);

        // diff types
        assertFalse(editLeaveCommand.equals(1));

        // same index and descriptor
        EditLeaveCommand sameCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, defaultDescriptor);
        assertEquals(editLeaveCommand, sameCommand);

        // different index
        EditLeaveCommand diffIndexCommand = new EditLeaveCommand(INDEX_SECOND_LEAVE, defaultDescriptor);
        assertNotEquals(editLeaveCommand, diffIndexCommand);

        // diff descriptor
        EditLeaveDescriptor diffDescriptor = new EditLeaveDescriptorBuilder().build();
        EditLeaveCommand diffDescriptorCommand = new EditLeaveCommand(INDEX_FIRST_LEAVE, diffDescriptor);
        assertNotEquals(editLeaveCommand, diffDescriptorCommand);
    }
}
