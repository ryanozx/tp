package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.UniqueLeaveList;
import seedu.address.model.person.Person;

/**
 * Wraps all data at the leaves-book level
 * Duplicates are not allowed (by .isSameLeave comparison)
 */
public class LeavesBook implements ReadOnlyLeavesBook {

    private final UniqueLeaveList leaves;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        leaves = new UniqueLeaveList();
    }

    public LeavesBook() {
    }

    /**
     * Creates an LeavesBook using the Leaves in the {@code toBeCopied}
     */
    public LeavesBook(ReadOnlyLeavesBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the leave list with {@code leaves}.
     * {@code leaves} must not contain duplicate leaves.
     */
    public void setLeaves(List<Leave> leaves) {
        this.leaves.setLeaves(leaves);
    }

    /**
     * Resets the existing data of this {@code LeavesBook} with {@code newData}.
     */
    public void resetData(ReadOnlyLeavesBook newData) {
        requireNonNull(newData);

        setLeaves(newData.getLeaveList());
    }

    //// leave-level operations

    /**
     * Returns true if a leave with the same identity as {@code leave} exists in the leaves book.
     */
    public boolean hasLeave(Leave leave) {
        requireNonNull(leave);
        return leaves.contains(leave);
    }

    /**
     * Adds a leave to the leaves book.
     * The leave must not already exist in the leaves book.
     */
    public void addLeave(Leave l) {
        leaves.add(l);
    }

    /**
     * Replaces the given leave {@code target} in the list with {@code editedLeave}.
     * {@code target} must exist in the leaves book.
     * The leave identity of {@code editedLeave} must not be the same as another existing leave in the leaves book.
     */
    public void setLeave(Leave target, Leave editedLeave) {
        requireNonNull(editedLeave);

        leaves.setLeave(target, editedLeave);
    }

    /**
     * Removes {@code key} from this {@code LeavesBook}.
     * {@code key} must exist in the leaves book.
     */
    public void removeLeave(Leave key) {
        leaves.remove(key);
    }

    public void removePerson(Person p) {
        leaves.removePerson(p);
    }

    public void setPerson(Person target, Person editedPerson) {
        leaves.setPerson(target, editedPerson);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("leaves", leaves.asUnmodifiableObservableList())
                .toString();
    }

    @Override
    public ObservableList<Leave> getLeaveList() {
        return leaves.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        return other instanceof LeavesBook
                && leaves.equals(((LeavesBook) other).leaves);
    }

    @Override
    public int hashCode() {
        return leaves.hashCode();
    }
}
