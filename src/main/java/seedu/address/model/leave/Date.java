package seedu.address.model.leave;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Date in the address book.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should be valid and in a format of `yyyy-MM-dd`";

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
    private final LocalDate date;

    /**
     * Constructs a Date object from a LocalDate object
     * @param date LocalDate containing date
     */
    private Date(LocalDate date) {
        this.date = date;
    }

    /**
     * Factory method for constructing a Date object with a LocalDate object.
     *
     * @param date LocalDate to construct Date from
     * @return Date object
     */
    public static Date of(LocalDate date) {
        requireNonNull(date);
        return new Date(date);
    }

    /**
     * Factory method for constructing a Date object with a String.
     *
     * @param date String containing date in "yyyy-MM-dd" format
     * @return Date object
     * @throws DateTimeParseException if the string does not follow the format
     */
    public static Date of(String date) throws DateTimeParseException {
        requireNonNull(date);
        return new Date(LocalDate.parse(date, formatter));
    }

    /**
     * Returns true if this date occurs before the other date
     * @param other Date to compare against
     * @return True if this date occurs before the other date, False otherwise
     */
    public boolean isBefore(Date other) {
        return date.isBefore(other.date);
    }

    /**
     * Returns true if this date occurs after the other date
     * @param other Date to compare against
     * @return True if this date occurs after the other date, False otherwise
     */
    public boolean isAfter(Date other) {
        return date.isAfter(other.date);
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

    public String toFormattedString() {
        return formatter.format(date);
    }
}
