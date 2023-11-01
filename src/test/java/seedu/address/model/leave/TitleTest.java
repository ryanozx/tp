package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TitleTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_emptyTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, Title.MESSAGE_CONSTRAINTS, () -> new Title(invalidTitle));
    }

    @Test
    public void constructor_blankTitle_throwsIllegalArgumentException() {
        String invalidTitle = "    ";
        assertThrows(IllegalArgumentException.class, Title.MESSAGE_CONSTRAINTS, () -> new Title(invalidTitle));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "title*#";
        assertThrows(IllegalArgumentException.class, Title.MESSAGE_CONSTRAINTS, () -> new Title(invalidTitle));
    }

    @Test
    public void toStringMethod() {
        String validTitle = "title";
        assertEquals(new Title(validTitle).toString(), validTitle);
    }

    @Test
    public void equalsMethod() {
        Title title = new Title("title");
        Title titleCopy = new Title("title");
        assertTrue(title.equals(titleCopy));

        // different title
        Title differentTitle = new Title("title123");
        assertFalse(title.equals(differentTitle));

        // different object
        assertFalse(title.equals(new Object()));

        // null
        assertFalse(title.equals(null));
    }
}
