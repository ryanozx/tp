package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Leave's description in the leaves book.
 */
public class Description {

    public static final String DESCRIPTION_PLACEHOLDER = "NONE";
    public static final String MESSAGE_CONSTRAINTS = "Leave descriptions should only contain"
                + " alphanumeric characters, spaces, dashes, commas, apostrophes and full stops.";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum} \\-',.]*$";

    private final String description;

    /**
     * Constructs a {@code Description}.
     *
     * @param description A valid description.
     * @throws IllegalArgumentException if description is not empty and contains illegal characters
     */
    public Description(String description) throws IllegalArgumentException {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns a default description that is empty.
     *
     * @return A default description that is empty.
     */
    public static Description getDefault() {
        return new Description("");
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && description.equals(((Description) other).description)); // state check
    }

    /**
     * Returns whether the description is empty.
     * @return True if empty, false otherwise.
     */
    public boolean isEmpty() {
        return description.isEmpty();
    }
}
