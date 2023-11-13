package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final LeavesBook leavesBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Leave> filteredLeaves;

    /**
     * Initializes a ModelManager with the given addressBook, leavesBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyLeavesBook leavesBook,
                        ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, leavesBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + " and leaves book: " + leavesBook
                + " and user prefs " + userPrefs);
        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.leavesBook = new LeavesBook(leavesBook);
        filteredLeaves = new FilteredList<>(this.leavesBook.getLeaveList());
    }

    public ModelManager() {
        this(new AddressBook(), new LeavesBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public Path getLeavesBookFilePath() {
        return userPrefs.getLeavesBookFilePath();
    }

    @Override
    public void setLeavesBookFilePath(Path leavesBookFilePath) {
        requireNonNull(leavesBookFilePath);
        userPrefs.setAddressBookFilePath(leavesBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }


    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
        leavesBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
        leavesBook.setPerson(target, editedPerson);
    }

    //=========== LeavesBook ================================================================================
    @Override
    public ReadOnlyLeavesBook getLeavesBook() {
        return leavesBook;
    }

    @Override
    public void deleteLeave(Leave leaveToDelete) {
        leavesBook.removeLeave(leaveToDelete);
    }

    @Override
    public void setLeave(Leave target, Leave editedLeave) {
        requireAllNonNull(target, editedLeave);

        leavesBook.setLeave(target, editedLeave);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    //=========== LeavesBook ================================================================================
    @Override
    public void setLeavesBook(ReadOnlyLeavesBook leavesBook) {
        this.leavesBook.resetData(leavesBook);
    }

    //=========== Filtered Leave List Accessors =============================================================

    @Override
    public boolean hasLeave(Leave leave) {
        requireNonNull(leave);
        return leavesBook.hasLeave(leave);
    }

    @Override
    public void addLeave(Leave leave) {
        leavesBook.addLeave(leave);
        updateFilteredLeaveList(PREDICATE_SHOW_ALL_LEAVES);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Leave} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Leave> getFilteredLeaveList() {
        return filteredLeaves;
    }

    @Override
    public void updateFilteredLeaveList(Predicate<Leave> predicate) {
        requireNonNull(predicate);
        filteredLeaves.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        // TODO implement leaves import so the below test case passes
        return addressBook.equals(otherModelManager.addressBook)
                && leavesBook.equals(otherModelManager.leavesBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredLeaves.equals(otherModelManager.filteredLeaves);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("addressBook", addressBook)
                .add("leavesBook", leavesBook)
                .add("userPrefs", userPrefs)
                .add("filteredPersons", filteredPersons)
                .add("filteredLeaves", filteredLeaves)
                .toString();
    }
}

