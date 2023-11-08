package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PersonEntryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PersonEntry(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new PersonEntry(invalidName));
    }

    @Test
    public void constructor_success() {
        String validName = "Valid Name";
        PersonEntry personEntry = new PersonEntry(validName);
        assertTrue(personEntry.getName().toString().equals(validName));
    }

    @Test
    public void toStringMethod() {
        String validName = "Valid Name";
        PersonEntry personEntry = new PersonEntry(validName);
        assertTrue(personEntry.toString().equals(validName));
    }

    @Test
    public void isSamePersonMethod() {
        // same person object
        PersonEntry personEntry = new PersonEntry("Valid Name");
        assertTrue(personEntry.isSamePerson(personEntry));

        // null person object
        assertFalse(personEntry.isSamePerson(null));

        // different person object, same name
        PersonEntry personEntry2 = new PersonEntry("Valid Name");
        assertTrue(personEntry.isSamePerson(personEntry2));

        // different person object, different name
        PersonEntry personEntry3 = new PersonEntry("Other Valid Name");
        assertFalse(personEntry.isSamePerson(personEntry3));

    }
}
