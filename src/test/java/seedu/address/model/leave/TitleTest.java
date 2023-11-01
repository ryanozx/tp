package seedu.address.model.leave;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        String validTitle = "title";
        assertEquals(new Title(validTitle), new Title(validTitle));
    }
}
