package seedu.address.model.leave;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.LeaveNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * A list of Leaves that enforces uniqueness between its elements and does not allow nulls.
 * A Leave is considered unique by comparing using {@code Leave#isSameLeave(Leave)}. As such, adding and updating of
 * Leaves uses Leave#isSameLeave(Leave) for equality so as to ensure that the Leave being added or updated is
 * unique in terms of identity in the UniqueLeaveList. However, the removal of a Leave uses Leave#equals(Object) so
 * as to ensure that the Leave with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Leave#isSameLeave(Leave)
 */
public class UniqueLeaveList implements Iterable<Leave> {

    private final ObservableList<Leave> internalList = FXCollections.observableArrayList();
    private final ObservableList<Leave> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Leave as the given argument.
     */
    public boolean contains(Leave toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameLeave);
    }

    /**
     * Adds a Leave to the list.
     * The Leave must not already exist in the list.
     */
    public void add(Leave toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLeaveException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Leave {@code target} in the list with {@code editedLeave}.
     * {@code target} must exist in the list.
     * The Leave identity of {@code editedLeave} must not be the same as another existing Leave in the list.
     */
    public void setLeave(Leave target, Leave editedLeave) {
        requireAllNonNull(target, editedLeave);
        if (!contains(target)) {
            throw new LeaveNotFoundException();
        }
        int index = internalList.indexOf(target);

        if (!target.isSameLeave(editedLeave) && contains(editedLeave)) {
            throw new DuplicateLeaveException();
        }

        internalList.set(index, editedLeave);
    }

    /**
     * Removes the equivalent Leave from the list.
     * The Leave must exist in the list.
     */
    public void remove(Leave toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LeaveNotFoundException();
        }
    }

    public void setLeaves(UniqueLeaveList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Leaves}.
     * {@code Leaves} must not contain duplicate Leaves.
     */
    public void setLeaves(List<Leave> Leaves) {
        requireAllNonNull(Leaves);
        if (!LeavesAreUnique(Leaves)) {
            throw new DuplicateLeaveException();
        }

        internalList.setAll(Leaves);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Leave> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Leave> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueLeaveList)) {
            return false;
        }

        UniqueLeaveList otherUniqueLeaveList = (UniqueLeaveList) other;
        return internalList.equals(otherUniqueLeaveList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code Leaves} contains only unique Leaves.
     */
    private boolean LeavesAreUnique(List<Leave> Leaves) {
        for (int i = 0; i < Leaves.size() - 1; i++) {
            for (int j = i + 1; j < Leaves.size(); j++) {
                if (Leaves.get(i).isSameLeave(Leaves.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
