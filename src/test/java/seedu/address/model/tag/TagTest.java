package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equalsMethod() {
        // same object
        Tag tag = new Tag("Valid Tag");
        assertTrue(tag.equals(tag));

        // different class
        assertFalse(tag.equals(new Object()));

        // same class, different tag name
        Tag tag2 = new Tag("Other Valid Tag");
        assertFalse(tag.equals(tag2));

        // same class, same tag name
        Tag tag3 = new Tag("Valid Tag");
        assertTrue(tag.equals(tag3));
    }

}
