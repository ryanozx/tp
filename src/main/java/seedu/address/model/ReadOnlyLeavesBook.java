package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.leave.Leave;

/**
 * Unmodifiable view of a leaves book
 */
public interface ReadOnlyLeavesBook {

    /**
     * Returns an unmodifiable view of the leaves list.
     * This list will not contain any duplicate leaves.
     */
    ObservableList<Leave> getLeaveList();
}
