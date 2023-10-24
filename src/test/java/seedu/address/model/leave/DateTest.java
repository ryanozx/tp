package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Date.of((String) null));
        assertThrows(NullPointerException.class, () -> Date.of((LocalDate) null));
    }

    @Test
    public void constructor_incorrectStringFormat_throwsIllegalArgumentException() {
        assertThrows(DateTimeParseException.class, () -> Date.of("2020-01-01-01"));
        assertThrows(DateTimeParseException.class, () -> Date.of("2020/01/01"));
    }

    @Test
    public void constructor_leapYear_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> Date.of("2020-02-30"));
    }

    @Test
    public void constructor_invalidDate_throwsDateTimeException() {
        assertThrows(DateTimeException.class, () -> Date.of("2020-02-31"));
        assertThrows(DateTimeException.class, () -> Date.of("2020-04-31"));
        assertThrows(DateTimeException.class, () -> Date.of("2020-06-31"));
        assertThrows(DateTimeException.class, () -> Date.of("2020-09-31"));
        assertThrows(DateTimeException.class, () -> Date.of("2020-11-31"));
    }

    @Test
    public void constructor_validDate() {
        assertTrue(Date.of("2020-01-01").getDate().toString().equals("2020-01-01"));
        assertTrue(Date.of(LocalDate.of(2020, 1, 1)).getDate().toString().equals("2020-01-01"));
    }

    @Test
    public void isBeforeMethod() {
        assertTrue(Date.of("2020-01-01").isBefore(Date.of("2020-01-02")));
        assertFalse(Date.of("2020-01-01").isBefore(Date.of("2020-01-01")));
        assertFalse(Date.of("2020-01-02").isBefore(Date.of("2020-01-01")));
    }

    @Test
    public void toStringMethod() {
        assertTrue(Date.of("2020-01-01").toString().equals("2020-01-01"));
    }

    @Test
    public void equalsMethod() {
        Date date = Date.of("2020-01-01");

        // same values -> returns true
        assertTrue(date.equals(Date.of("2020-01-01")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(Date.of("2020-01-02")));
    }

    @Test
    public void hashcodeMethod() {
        assertTrue(Date.of("2020-01-01").hashCode() == Date.of("2020-01-01").hashCode());
        assertFalse(Date.of("2020-01-01").hashCode() == Date.of("2020-01-02").hashCode());
    }
}
