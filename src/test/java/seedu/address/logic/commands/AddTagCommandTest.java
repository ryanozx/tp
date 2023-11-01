package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddTagCommand.
 */
public class AddTagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());

    @Test
    public void execute_oneTagUnfilteredList_success() {
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, List.of(new Tag(VALID_TAG_HUSBAND)));

        Person firstPerson = model.getFilteredPersonList().get(0);

        Person editedPerson = new Person(firstPerson);
        editedPerson.addTag(new Tag(VALID_TAG_HUSBAND));

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new LeavesBook(model.getLeavesBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_multipleTagsUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        AddTagCommand addTagCommand = new AddTagCommand(
                indexLastPerson, List.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));

        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        Person editedPerson = new Person(lastPerson);
        editedPerson.addTags(List.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new LeavesBook(model.getLeavesBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noTagsSpecifiedFilteredList_success() {
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, List.of());

        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new LeavesBook(model.getLeavesBook()), new UserPrefs());

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, List.of(new Tag(VALID_TAG_HUSBAND)));

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Person editedPerson = new Person(personInFilteredList);
        editedPerson.addTag(new Tag(VALID_TAG_HUSBAND));

        String expectedMessage = String.format(AddTagCommand.MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new LeavesBook(model.getLeavesBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addTagCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateTag_failure() {
        Tag tag = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased())
                .getTags().stream().findFirst().get();
        AddTagCommand addTagCommand = new AddTagCommand(INDEX_FIRST_PERSON, List.of(tag));

        assertCommandFailure(addTagCommand, model, AddTagCommand.MESSAGE_DUPLICATE_TAG);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, List.of(new Tag(VALID_TAG_FRIEND)));

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddTagCommand addTagCommand = new AddTagCommand(outOfBoundIndex, List.of(new Tag(VALID_TAG_FRIEND)));

        assertCommandFailure(addTagCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddTagCommand standardCommand = new AddTagCommand(
                INDEX_FIRST_PERSON, List.of(new Tag(VALID_TAG_FRIEND), new Tag(VALID_TAG_HUSBAND)));

        // same values -> returns true
        AddTagCommand commandWithSameValues = new AddTagCommand(
                INDEX_FIRST_PERSON, List.of(new Tag(VALID_TAG_HUSBAND), new Tag(VALID_TAG_FRIEND)));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(
                        INDEX_SECOND_PERSON, List.of(new Tag(VALID_TAG_HUSBAND), new Tag(VALID_TAG_FRIEND)))));

        // different tagsToAdd -> returns false
        assertFalse(standardCommand.equals(new AddTagCommand(INDEX_FIRST_PERSON, List.of(new Tag(VALID_TAG_HUSBAND)))));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Collection<Tag> tagsToAdd = new HashSet<>(List.of(new Tag(VALID_TAG_FRIEND)));
        AddTagCommand addTagCommand = new AddTagCommand(index, tagsToAdd);
        String expected = AddTagCommand.class.getCanonicalName() + "{index=" + index + ", tagsToAdd="
                + tagsToAdd + "}";
        assertEquals(expected, addTagCommand.toString());
    }
}
