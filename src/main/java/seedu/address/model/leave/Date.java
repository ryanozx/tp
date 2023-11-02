package seedu.address.model.leave;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Represents a Date in the address book.
 */
public class Date {
    public static final String MESSAGE_INVALID_END_DATE =
            "The end date is earlier than the start date!";

    public static final String MESSAGE_CONSTRAINTS =
            "Date should be valid and in a format of `yyyy-MM-dd`";

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
    private final LocalDate date;

    private Date(LocalDate date) {
        this.date = date;
    }

    /**
     * Factory method for constructing a Date object with a LocalDate object.
     *
     * @param date
     * @return Date object
     */
    public static Date of(LocalDate date) {
        requireAllNonNull(date);
        return new Date(date);
    }

    /**
     * Factory method for constructing a Date object with a String.
     *
     * @param date
     * @return Date object
     */
    public static Date of(String date) throws IllegalArgumentException {
        requireAllNonNull(date);
        return new Date(LocalDate.parse(date, formatter));
    }

    public boolean isBefore(Date other) {
        return date.isBefore(other.date);
    }

    public LocalDate getDate() {
        return date;
    }
    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Date
                && date.equals(((Date) other).date));
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
