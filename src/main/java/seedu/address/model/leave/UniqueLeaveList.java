package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.LeaveNotFoundException;
import seedu.address.model.person.Person;

/**
 * A list of leaves that enforces uniqueness between its elements and does not allow nulls.
 * A leave is considered unique by comparing using {@code Leave#isSameLeave(Leave)}. As such, adding and updating of
 * leaves uses Leave#isSameLeave(Leave) for equality so as to ensure that the leave being added or updated is
 * unique in terms of identity in the UniqueLeaveList. However, the removal of a leave uses Leave#equals(Object) so
 * as to ensure that the leave with exactly the same fields will be removed.
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
     * Returns true if the list contains an equivalent leave as the given argument.
     */
    public boolean contains(Leave leave) {
        requireNonNull(leave);
        return internalList.stream().anyMatch(leave::isSameLeave);
    }

    /**
     * Returns true if the list contains a leave that overlaps with the given argument.
     *
     * @param leave
     * @return
     */
    public boolean hasConcurrentLeave(Leave leave) {
        requireNonNull(leave);
        return internalList.stream().anyMatch(leave::isConcurrent);
    }

    /**
     * Adds a leave to the list.
     * The leave must not already exist in the list.
     */
    public void add(Leave toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateLeaveException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the leave {@code target} in the list with {@code editedLeave}.
     * {@code target} must exist in the list.
     * The leave identity of {@code editedLeave} must not be the same as another existing leave in the list.
     */
    public void setLeave(Leave target, Leave editedLeave) {
        requireAllNonNull(target, editedLeave);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new LeaveNotFoundException();
        }

        if (!target.isSameLeave(editedLeave) && contains(editedLeave)) {
            throw new DuplicateLeaveException();
        }

        internalList.set(index, editedLeave);
    }

    /**
     * Returns true if {@code leaves} contains only unique leaves.
     */
    private boolean leavesAreUnique(List<Leave> leaves) {
        for (int i = 0; i < leaves.size() - 1; i++) {
            for (int j = i + 1; j < leaves.size(); j++) {
                if (leaves.get(i).isSameLeave(leaves.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Replaces the contents of this list with {@code leaves}.
     * {@code leaves} must not contain duplicate leaves.
     */
    public void setLeaves(List<Leave> leaves) {
        requireAllNonNull(leaves);
        if (!leavesAreUnique(leaves)) {
            throw new DuplicateLeaveException();
        }

        internalList.setAll(leaves);
    }

    public void setLeaves(UniqueLeaveList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Removes the equivalent leave from the list.
     * The leave must exist in the list.
     */
    public void remove(Leave toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new LeaveNotFoundException();
        }
    }

    /**
     * Removes the leaves which belongs to Person p.
     * @see Leave#belongsTo(Person)
     */
    public void removePerson(Person p) {
        List<Leave> toRemove = internalList.stream().filter((l) -> l.belongsTo(p)).collect(Collectors.toList());
        toRemove.forEach(this::remove);
    }

    /**
     * Replaces leaves belonging to {@code target} with {@code editedPerson}
     * @see Leave#belongsTo(Person)
     */
    public void setPerson(Person target, Person editedPerson) {
        List<Leave> toEdit = internalList.stream().filter((l) -> l.belongsTo(target)).collect(Collectors.toList());
        toEdit.forEach((l) -> setLeave(l, l.copyWithNewPerson(editedPerson)));
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
}
