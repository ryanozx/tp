package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Leave> PREDICATE_SHOW_ALL_LEAVES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    ReadOnlyLeavesBook getLeavesBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered leave list */
    ObservableList<Leave> getFilteredLeaveList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void deleteLeave(Leave leaveToDelete);

    /**
     * Replaces leave book data with the data in {@code leavesBook}.
     */
    void setLeavesBook(ReadOnlyLeavesBook leavesBook);

    /**
     * Returns true if a leave with the same identity as {@code leave} exists in the leave book.
     */
    boolean hasLeave(Leave leave);


    /**
     * Adds the given leave.
     * {@code leave} must not already exist in the leave book.
     */
    void addLeave(Leave leave);

    /**
     * Replaces the given leave {@code target} with {@code editedPerson}.
     * {@code target} must exist in the leave book.
     * The leave identity of {@code editedLeave} must not be the same as another existing leave in the leave book.
     */
    void setLeave(Leave target, Leave editedLeave);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getLeavesBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setLeavesBookFilePath(Path leavesBookFilePath);

    /**
     * Updates the filter of the filtered leave list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLeaveList(Predicate<Leave> predicate);
}
