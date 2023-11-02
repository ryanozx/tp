package seedu.address.storage;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.model.ReadOnlyLeavesBook;

/**
 * An Immutable LeavesBook that is serializable to JSON format.
 */
@JsonRootName(value = "leavesbook")
public class JsonSerializableLeavesBook extends SerializableLeavesBook<JsonAdaptedLeave> {
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
}
