package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

/**
 * Wrapper for a filtered list of people to enable the export of these
 * records in CSV format
 */
public class ReadOnlyFilteredAddressBook implements ReadOnlyAddressBook {
    private final ObservableList<Person> persons;
    private final ObservableList<Leave> leaves;

    /**
     * Constructs a ReadOnlyFilteredAddressBook object
     * @param model Current model of the address book data
     */
    public ReadOnlyFilteredAddressBook(Model model) {
        this.persons = model.getFilteredPersonList();
        this.leaves = model.getFilteredLeaveList();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons;
    }

    @Override
    public ObservableList<Leave> getLeaveList() {
        return leaves;
    }
}
