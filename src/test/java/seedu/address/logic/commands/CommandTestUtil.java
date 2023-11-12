package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.leave.LeaveContainsPersonPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_REMOTE = "remote";
    public static final String VALID_TAG_FULL_TIME = "full time";

    public static final String NAME_DESC_AMY = " " + PREFIX_PERSON_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_PERSON_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PERSON_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PERSON_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_PERSON_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_PERSON_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_PERSON_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_PERSON_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_PERSON_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_PERSON_TAG + VALID_TAG_HUSBAND;
    public static final String TAG_DESC_REMOTE = " " + PREFIX_PERSON_TAG + VALID_TAG_REMOTE;
    public static final String TAG_DESC_FULL_TIME = " " + PREFIX_PERSON_TAG + VALID_TAG_FULL_TIME;
    public static final String TAG_EMPTY = " " + PREFIX_PERSON_TAG;

    public static final String INVALID_NAME_DESC = " " + PREFIX_PERSON_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PERSON_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_PERSON_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " "
            + PREFIX_PERSON_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_PERSON_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final String VALID_LEAVE_TITLE = "Medical leave";
    public static final String VALID_LEAVE_DESCRIPTION = "going on medical leave";
    public static final String VALID_LEAVE_DATE_START = "2023-10-30";
    public static final String VALID_LEAVE_DATE_END = "2023-10-31";
    public static final String VALID_LEAVE_STATUS_APPROVED = "APPROVED";

    public static final String VALID_LEAVE_TITLE_DESC = " " + PREFIX_LEAVE_TITLE + VALID_LEAVE_TITLE;
    public static final String VALID_LEAVE_DESCRIPTION_DESC = " " + PREFIX_LEAVE_DESCRIPTION + VALID_LEAVE_DESCRIPTION;
    public static final String VALID_LEAVE_START_DATE_DESC = " " + PREFIX_LEAVE_DATE_START + VALID_LEAVE_DATE_START;
    public static final String VALID_LEAVE_END_DATE_DESC = " " + PREFIX_LEAVE_DATE_END + VALID_LEAVE_DATE_END;
    public static final String VALID_LEAVE_STATUS_DESC = " " + PREFIX_LEAVE_STATUS + VALID_LEAVE_STATUS_APPROVED;

    public static final String INVALID_LEAVE_TITLE_DESC = " " + PREFIX_LEAVE_TITLE + "Medical leave&";
    public static final String INVALID_LEAVE_DESCRIPTION_DESC = " " + PREFIX_LEAVE_DESCRIPTION + "Going to childcare&";
    public static final String INVALID_LEAVE_DATE_START_DESC = " " + PREFIX_LEAVE_DATE_START + "2023-13-11";
    public static final String INVALID_LEAVE_DATE_END_DESC = " " + PREFIX_LEAVE_DATE_END + "2024-12-32";
    public static final String INVALID_LEAVE_LATE_DATE_START_DESC = " " + PREFIX_LEAVE_DATE_START
            + VALID_LEAVE_DATE_END;
    public static final String INVALID_LEAVE_EARLY_DATE_END_DESC = " " + PREFIX_LEAVE_DATE_END + VALID_LEAVE_DATE_START;
    public static final String INVALID_LEAVE_STATUS_DESC = " " + PREFIX_LEAVE_STATUS + "NONSENSE";

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().toString().split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the leave that belongs to the {@code person}
     */
    public static void showLeaveByPerson(Model model, Person person) {
        LeaveContainsPersonPredicate p = new LeaveContainsPersonPredicate(person);
        model.updateFilteredLeaveList(p);
    }

}
