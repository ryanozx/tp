package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.PersonEntry;
import seedu.address.model.person.Person;
import seedu.address.testutil.LeaveBuilder;
import seedu.address.testutil.PersonBuilder;


public class DeleteCommandIntegrationTest {
    private Model model;

    private final Person alice = new PersonBuilder()
            .withName(ALICE.getName().toString())
            .withAddress(ALICE.getAddress().toString())
            .withEmail(ALICE.getEmail().toString())
            .withPhone(ALICE.getPhone().toString()).build();
    private final Person bob = new PersonBuilder()
            .withName(BOB.getName().toString())
            .withAddress(BOB.getAddress().toString())
            .withEmail(BOB.getEmail().toString())
            .withPhone(BOB.getPhone().toString()).build();

    private final Leave aliceLeave = new LeaveBuilder().withEmployee(
            new PersonEntry(ALICE.getName().toString())).build();
    private final Leave bobLeave = new LeaveBuilder().withEmployee(
            new PersonEntry(BOB.getName().toString())).build();

    @Test
    public void execute_deletePerson_success() {
        // Checks that deleting a person deletes them from both address book as well as their associated leaves
        // from the leaves book
        Index deleteCommandIndex = Index.fromOneBased(1);
        DeleteCommand command = new DeleteCommand(deleteCommandIndex);

        assertNotEquals(aliceLeave.getEmployee(), alice);
        assertNotEquals(bobLeave.getEmployee(), bob);

        AddressBook ab = new AddressBook();
        ab.addPerson(alice);
        ab.addPerson(bob);
        LeavesBook lb = new LeavesBook();
        lb.addLeave(aliceLeave);
        lb.addLeave(bobLeave);
        model = new ModelManager(ab, lb, new UserPrefs());

        AddressBook expectedAb = new AddressBook();
        expectedAb.addPerson(bob);
        LeavesBook expectedLb = new LeavesBook();
        expectedLb.addLeave(bobLeave);
        Model expectedModel = new ModelManager(expectedAb, expectedLb, new UserPrefs());

        // ensure that we will be deleting alice
        assertEquals(model.getFilteredPersonList().get(0), alice);

        assertCommandSuccess(command, model,
                String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(alice)),
                expectedModel);
    }

    @Test
    public void execute_indexOutOfBounds_throwsCommandException() {
        Index deleteCommandIndex = Index.fromOneBased(2);
        DeleteCommand command = new DeleteCommand(deleteCommandIndex);

        AddressBook ab = new AddressBook();
        ab.addPerson(alice);
        LeavesBook lb = new LeavesBook();
        model = new ModelManager(ab, lb, new UserPrefs());

        assertCommandFailure(command, model, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
