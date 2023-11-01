package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;

/**
 * Wrapper for a filtered list of people to enable the export of these
 * records in CSV format
 */
public class ReadOnlyFilteredLeavesBook implements ReadOnlyLeavesBook {
    private final ObservableList<Leave> leaves;

    /**
     * Constructs a ReadOnlyFilteredAddressBook object
     * @param model Current model of the address book data
     */
    public ReadOnlyFilteredLeavesBook(Model model) {
        this.leaves = model.getFilteredLeaveList();
    }

    @Override
    public ObservableList<Leave> getLeaveList() {
        return leaves;
    }
}


