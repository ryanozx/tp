package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Leave's title in the leaves book.
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS = "Leave titles should only contain"
            + " alphanumeric characters, spaces, and dashes."
            + "It should not be blank";
    public static final String VALIDATION_REGEX = "^[\\p{Alnum} \\-']+$";

    private final String title;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid title.
     * @throws IllegalArgumentException if title
     */
    public Title(String title) throws NullPointerException, IllegalArgumentException {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_CONSTRAINTS);
        this.title = title;
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidTitle(String test) {
        return test.trim().length() > 0 && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Title // instanceof handles nulls
                && title.equals(((Title) other).title)); // state check
    }

}
