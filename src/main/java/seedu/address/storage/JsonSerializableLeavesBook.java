package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.ReadOnlyLeavesBook;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * An Immutable LeavesBook that is serializable to JSON format.
 */
@JsonRootName(value = "leavesbook")
public class JsonSerializableLeavesBook {

    public static final String MESSAGE_DUPLICATE_LEAVE = "Leaves list contains duplicate leave(s).";
    private final List<JsonAdaptedLeave> leaves = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableLeavesBook} with the given leaves.
     */
    @JsonCreator
    public JsonSerializableLeavesBook(@JsonProperty("leaves") List<JsonAdaptedLeave> leaves) {
        this.leaves.addAll(leaves);
    }

    /**
     * Converts a given {@code ReadOnlyLeavesBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableLeavesBook}.
     */
    public JsonSerializableLeavesBook(ReadOnlyLeavesBook source) {
        leaves.addAll(source.getLeaveList().stream().map(JsonAdaptedLeave::new).collect(Collectors.toList()));
    }

    /**
     * Converts this leaves book into the model's {@code LeavesBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public LeavesBook toModelType(AddressBook addressBook) throws IllegalValueException {
        LeavesBook leavesBook = new LeavesBook();
        for (JsonAdaptedLeave jsonAdaptedLeave : leaves) {
            Leave leave = jsonAdaptedLeave.toModelType();
            if (leavesBook.hasLeave(leave)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LEAVE);
            }
            if (!addressBook.hasPerson(leave.getEmployee())) {
                throw new PersonNotFoundException();
            }
            leavesBook.addLeave(leave);
        }
        return leavesBook;
    }
}
