package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_END;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DATE_START;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEAVE_TITLE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LEAVE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LEAVE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_LEAVE;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyLeavesBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Title;
import seedu.address.model.person.Person;
import seedu.address.testutil.LeaveBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddLeaveCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());
    @Test
    public void constructor_nullLeave_throwsNullPointerException() {
        Range validDateRange = Range.createNonNullRange(Date.of(VALID_LEAVE_DATE_START),
                Date.of(VALID_LEAVE_DATE_END));
        //index null
        assertThrows(NullPointerException.class, () -> new AddLeaveCommand(null, new Title(VALID_LEAVE_TITLE),
                validDateRange, new Description(VALID_LEAVE_DESCRIPTION)));

        //title null
        assertThrows(NullPointerException.class, () -> new AddLeaveCommand(INDEX_THIRD_LEAVE, null,
                validDateRange, new Description(VALID_LEAVE_DESCRIPTION)));

        //range null
        assertThrows(NullPointerException.class, () -> new AddLeaveCommand(INDEX_THIRD_LEAVE,
                new Title(VALID_LEAVE_TITLE),
                null, new Description(VALID_LEAVE_DESCRIPTION)));

        //description null
        assertThrows(NullPointerException.class, () -> new AddLeaveCommand(INDEX_THIRD_LEAVE,
                new Title(VALID_LEAVE_TITLE),
                validDateRange, null));

        //all null
        assertThrows(NullPointerException.class, () -> new AddLeaveCommand(null, null,
                null, null));

    }

    @Test
    public void execute_leaveAcceptedByModel_success() throws Exception {

        AddLeaveCommandTest.ModelStubAcceptingLeaveAdded modelStub = new AddLeaveCommandTest
                .ModelStubAcceptingLeaveAdded();
        Leave validLeave = new LeaveBuilder().build();

        Range dateRange = Range.createNonNullRange(validLeave.getStart(), validLeave.getEnd());


        CommandResult commandResult = new AddLeaveCommand(INDEX_FIRST_LEAVE, validLeave.getTitle(),
                dateRange, validLeave.getDescription()).execute(modelStub);

        assertEquals(String.format(AddLeaveCommand.MESSAGE_SUCCESS, Messages.format(validLeave)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validLeave), modelStub.leavesAdded);
    }

    @Test
    public void execute_duplicateLeave_throwsCommandException() {
        Leave validLeave = new LeaveBuilder().build();
        Range dateRange = Range.createNonNullRange(validLeave.getStart(), validLeave.getEnd());

        AddLeaveCommand addLeaveCommand = new AddLeaveCommand(INDEX_FIRST_LEAVE, validLeave.getTitle(),
                dateRange, validLeave.getDescription());

        AddLeaveCommandTest.ModelStub modelStub = new AddLeaveCommandTest.ModelStubWithLeave(validLeave);

        assertThrows(CommandException.class,
                AddLeaveCommand.MESSAGE_DUPLICATE_LEAVE, () -> addLeaveCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        Leave aliceLeave = new LeaveBuilder().withEmployee(alice).build();
        Leave aliceLeaveDifferentTitle = new LeaveBuilder().withEmployee(alice).withTitle("Different Title").build();
        Leave aliceLeaveDifferentRange = new LeaveBuilder().withEmployee(alice)
                .withStart(Date.of("2000-01-01")).withEnd(Date.of("2000-01-01")).build();
        Leave aliceLeaveDifferentDescription = new LeaveBuilder().withEmployee(alice)
                .withDescription("Different Description").build();
        Leave bobLeave = new LeaveBuilder().withEmployee(bob).build();
        Range aliceDateRange = Range.createNonNullRange(aliceLeave.getStart(), aliceLeave.getEnd());
        Range aliceDifferentDateRange = Range.createNonNullRange(aliceLeaveDifferentRange.getStart(),
                aliceLeaveDifferentRange.getEnd());

        Range bobDateRange = Range.createNonNullRange(bobLeave.getStart(), aliceLeave.getEnd());

        AddLeaveCommand addAliceCommand = new AddLeaveCommand(INDEX_FIRST_LEAVE, aliceLeave.getTitle(),
                aliceDateRange, aliceLeave.getDescription());
        AddLeaveCommand addAliceDifferentTitleCommand = new AddLeaveCommand(INDEX_FIRST_LEAVE,
                aliceLeaveDifferentTitle.getTitle(),
                aliceDateRange, aliceLeave.getDescription());
        AddLeaveCommand addAliceDifferentRangeCommand = new AddLeaveCommand(INDEX_FIRST_LEAVE,
                aliceLeave.getTitle(),
                aliceDifferentDateRange, aliceLeave.getDescription());
        AddLeaveCommand addAliceDifferentDescriptionCommand = new AddLeaveCommand(INDEX_FIRST_LEAVE,
                aliceLeave.getTitle(),
                aliceDifferentDateRange, aliceLeaveDifferentDescription.getDescription());
        AddLeaveCommand addBobCommand = new AddLeaveCommand(INDEX_SECOND_LEAVE, bobLeave.getTitle(),
                bobDateRange, bobLeave.getDescription());

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddLeaveCommand addAliceCommandCopy = new AddLeaveCommand(INDEX_FIRST_LEAVE, aliceLeave.getTitle(),
                aliceDateRange, aliceLeave.getDescription());
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different leave -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));

        //Title is not equal
        assertFalse(addAliceCommand.equals(addAliceDifferentTitleCommand));

        //Range is not equal
        assertFalse(addAliceCommand.equals(addAliceDifferentRangeCommand));

        //Description is not equal
        assertFalse(addAliceCommand.equals(addAliceDifferentDescriptionCommand));
    }

    @Test
    public void toStringMethod() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Leave aliceLeave = new LeaveBuilder().withEmployee(alice).withStart(ALICE_LEAVE.getStart())
                .withEnd(ALICE_LEAVE.getEnd()).build();
        Range aliceDateRange = Range.createNonNullRange(aliceLeave.getStart(), aliceLeave.getEnd());
        AddLeaveCommand addAliceCommand = new AddLeaveCommand(INDEX_FIRST_LEAVE, aliceLeave.getTitle(),
                aliceDateRange, aliceLeave.getDescription());
        String expected = AddLeaveCommand.class.getCanonicalName() + "{title=" + ALICE_LEAVE.getTitle()
                + ", description=" + ALICE_LEAVE.getDescription() + ", start=" + ALICE_LEAVE.getStart()
                + ", end=" + ALICE_LEAVE.getEnd() + "}";
        assertEquals(expected, addAliceCommand.toString());
    }

    /**
     * A default model stub that have all the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return model.getFilteredPersonList();
        }
        @Override
        public ObservableList<Leave> getFilteredLeaveList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Replaces leave book data with the data in {@code leavesBook}.
         *
         * @param leave
         */
        @Override
        public void deleteLeave(Leave leave) {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void setLeavesBook(ReadOnlyLeavesBook leavesBook) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns true if a leave with the same identity as {@code leave} exists in the leave book.
         *
         * @param leave
         */
        @Override
        public boolean hasLeave(Leave leave) {
            return false;
        }

        /**
         * Returns true if a leave belonging to same employee has overlapping dates as {@code leave} exists in the
         * leave book.
         *
         * @param leave
         * @return
         */
        @Override
        public boolean hasConcurrentLeave(Leave leave) {
            return false;
        }

        /**
         * Adds the given leave.
         * {@code leave} must not already exist in the leave book.
         *
         * @param leave
         */
        @Override
        public void addLeave(Leave leave) {
            throw new AssertionError(
                    "This method should not be called.");
        }

        /**
         * Replaces the given leave {@code target} with {@code editedPerson}.
         * {@code target} must exist in the leave book.
         * The leave identity of {@code editedLeave} must not be the same as another existing leave in the leave book.
         *
         * @param target
         * @param editedLeave
         */
        @Override
        public void setLeave(Leave target, Leave editedLeave) {
            throw new AssertionError("This method should not be called.");
        }


        /**
         * Returns the user prefs' address book file path.
         */
        @Override
        public Path getLeavesBookFilePath() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        /**
         * Sets the user prefs' address book file path.
         *
         * @param leavesBookFilePath
         */
        @Override
        public void setLeavesBookFilePath(Path leavesBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        /**
         * Returns the LeavesBook
         */
        @Override
        public ReadOnlyLeavesBook getLeavesBook() {
            throw new AssertionError(
                    "This method should not be called.");
        }

        @Override
        public void updateFilteredLeaveList(Predicate<Leave> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single leave.
     */
    private class ModelStubWithLeave extends ModelStub {
        private final Leave leave;

        ModelStubWithLeave(Leave leave) {
            requireNonNull(leave);
            this.leave = leave;
        }

        @Override
        public boolean hasLeave(Leave leave) {
            requireNonNull(leave);
            return this.leave.isSameLeave(leave);
        }
    }

    /**
     * A Model stub that always accept the leave being added.
     */
    private class ModelStubAcceptingLeaveAdded extends ModelStub {
        final ArrayList<Leave> leavesAdded = new ArrayList<>();

        @Override
        public boolean hasLeave(Leave leave) {
            requireNonNull(leave);
            return leavesAdded.stream().anyMatch(leave::isSameLeave);
        }

        @Override
        public void addLeave(Leave leave) {
            requireNonNull(leave);
            leavesAdded.add(leave);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public ReadOnlyLeavesBook getLeavesBook() {
            return new LeavesBook();
        }
    }

}
