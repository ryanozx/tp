package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * An abstract class for an Immutable LeavesBook that can be serialised to different types.
 */
abstract class SerializableLeavesBook<T extends AdaptedLeave> {
    public static final String MESSAGE_DUPLICATE_LEAVE = "Leaves list contains duplicate leave(s).";
    protected final List<T> leaves = new ArrayList<>();

    /**
     * Converts this leaves book into the model's {@code LeavesBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public LeavesBook toModelType(AddressBook addressBook) throws IllegalValueException, PersonNotFoundException {
        LeavesBook leavesBook = new LeavesBook();
        for (T adaptedLeave : leaves) {
            Leave leave = adaptedLeave.toModelType();
            if (leavesBook.hasLeave(leave)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LEAVE);
            }
            if (!addressBook.hasPerson(leave.getEmployee())) {
                continue;
            }
            leavesBook.addLeave(leave);
        }
        return leavesBook;
    }
}
