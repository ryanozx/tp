package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "testing#*";
        assertThrows(IllegalArgumentException.class, Description.MESSAGE_CONSTRAINTS, ()
                -> new Description(invalidDescription));
    }

    @Test
    public void constructor_emptyDescription_valid() {
        String emptyDescription = "";
        assertEquals(new Description(emptyDescription).toString(), emptyDescription);
    }

    @Test
    public void factoryConstructor_getDefault() {
        assertEquals(Description.getDefault().toString(), "");
    }

    @Test
    public void constructor_validDescription_success() {
        String validDescription = "testing";
        assertEquals(new Description(validDescription).toString(), validDescription);

        validDescription = "Alice's maternity leave. Please, refer to attachment or something";
        assertEquals(new Description(validDescription).toString(), validDescription);
    }

    @Test
    public void isEmpty() {
        String nonEmptyDescription = "testing";
        assertFalse(new Description(nonEmptyDescription).isEmpty());

        String emptyDescription = "";
        assertTrue(new Description(emptyDescription).isEmpty());
    }

    @Test
    public void equalsMethod() {
        Description description = new Description("testing");
        Description descriptionCopy = new Description("testing");
        assertTrue(description.equals(descriptionCopy));

        // different description
        Description differentDescription = new Description("testing123");
        assertFalse(description.equals(differentDescription));

        // different object
        assertFalse(description.equals(new Object()));

        // null
        assertFalse(description.equals(null));

        // same object
        assertTrue(description.equals(description));
    }
}
