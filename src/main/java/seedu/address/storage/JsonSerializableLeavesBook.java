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
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Status;
import seedu.address.model.person.Person;

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
    // public LeavesBook toModelType(AddressBook addressBook) throws IllegalValueException {
    //     LeavesBook leavesBook = new LeavesBook();
    //     for (JsonAdaptedLeave jsonAdaptedLeave : leaves) {
    //         Leave leave = jsonAdaptedLeave.toModelType();
    //         if (leavesBook.hasLeave(leave)) {
    //             throw new IllegalValueException(MESSAGE_DUPLICATE_LEAVE);
    //         }
    //         leavesBook.addLeave(leave);
    //     }
    //     return leavesBook;
    // }
    public LeavesBook toModelType(AddressBook addressBook) throws IllegalValueException {
        LeavesBook leavesBook = new LeavesBook();
        for (JsonAdaptedLeave jsonAdaptedLeave : leaves) {
            String title = jsonAdaptedLeave.getTitle();
            String description = jsonAdaptedLeave.getDescription();
            Status status = Status.of(jsonAdaptedLeave.getStatus());
            Date start = Date.of(jsonAdaptedLeave.getStart());
            Date end = Date.of(jsonAdaptedLeave.getEnd());
            Person employee = addressBook.getPerson(jsonAdaptedLeave.getEmployee());
            Leave leave = new Leave(employee, title, start, end, description, status);
            if (leavesBook.hasLeave(leave)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LEAVE);
            }
            leavesBook.addLeave(leave);
        }
        return leavesBook;
    }
}
