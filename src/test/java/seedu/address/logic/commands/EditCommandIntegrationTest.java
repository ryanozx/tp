package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.EditCommand.MESSAGE_EDIT_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.PersonEntry;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.LeaveBuilder;
import seedu.address.testutil.PersonBuilder;


public class EditCommandIntegrationTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        AddressBook ab = new AddressBook();
        LeavesBook lb = new LeavesBook();
        Person person = new PersonBuilder()
                .withName(ALICE.getName().toString())
                .withAddress(ALICE.getAddress().toString())
                .withEmail(ALICE.getEmail().toString())
                .withPhone(ALICE.getPhone().toString()).build();
        ab.addPerson(person);


        // personEntry used to ensure that leaves are associated to employees via ComparablePerson::isSamePerson
        // rather than equals
        Leave loadedLeave = new LeaveBuilder().withEmployee(new PersonEntry(ALICE.getName().toString())).build();
        lb.addLeave(loadedLeave);

        model = new ModelManager(ab, lb, new UserPrefs());
    }

    @Test
    public void execute_renamePerson_success() {
        // Checks that editing a person's name changes both addressBook and leaveBook entries, even for leaves
        // that were not created in the current session
        EditPersonDescriptor editDescriptor = new EditPersonDescriptorBuilder()
                .withName(BOB.getName().toString()).build();

        Index editCommandIndex = Index.fromOneBased(1);
        EditCommand command = new EditCommand(editCommandIndex, editDescriptor);

        AddressBook expectedAb = new AddressBook();
        Person editedPerson = new PersonBuilder()
                .withName(BOB.getName().toString())
                .withAddress(ALICE.getAddress().toString())
                .withEmail(ALICE.getEmail().toString())
                .withPhone(ALICE.getPhone().toString()).build();
        expectedAb.addPerson(editedPerson);

        LeavesBook expectedLb = new LeavesBook();
        Leave expectedLeave = new LeaveBuilder().withEmployee(new PersonEntry(BOB.getName().toString())).build();
        expectedLb.addLeave(expectedLeave);

        Model expectedModel = new ModelManager(expectedAb, expectedLb, new UserPrefs());

        assertCommandSuccess(command, model,
                String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)),
                expectedModel);
        assertEquals(model.getLeavesBook(), expectedModel.getLeavesBook());
        assertEquals(model.getFilteredLeaveList(), expectedModel.getFilteredLeaveList());
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        model.addPerson(BOB);

        EditPersonDescriptor editDescriptor = new EditPersonDescriptorBuilder()
                .withName(BOB.getName().toString()).build();
        Index editCommandIndex = Index.fromOneBased(1);
        EditCommand command = new EditCommand(editCommandIndex, editDescriptor);

        assertCommandFailure(command, model, MESSAGE_DUPLICATE_PERSON);
    }
}
