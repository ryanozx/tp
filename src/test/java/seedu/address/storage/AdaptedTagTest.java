package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;

public class AdaptedTagTest {

    private static final String VALID_TAGNAME = "test";
    private static final String VALID_ALPHANUMERIC_TAGNAME = "testTag123";
    private static final String VALID_MULTIWORD_TAGNAME = "test tag";

    private static final String ILLEGAL_CHAR_TAGNAME = "t@st3r!";
    private static final String ILLEGAL_CHAR_MULTIWORD_TAGNAME = "test t@g";


    private static final Tag expectedSingleWordTag = new Tag(VALID_TAGNAME);
    private static final Tag expectedMultiWordTag = new Tag(VALID_MULTIWORD_TAGNAME);

    @Test
    public void toModelType_validName_noExceptionsThrown() {
        AdaptedTag singleWordTag = new AdaptedTag(VALID_TAGNAME);
        assertDoesNotThrow(() -> {
            singleWordTag.toModelType();
        });

        AdaptedTag alphanumericTag = new AdaptedTag(VALID_ALPHANUMERIC_TAGNAME);
        assertDoesNotThrow(() -> {
            alphanumericTag.toModelType();
        });

        AdaptedTag multiWordTag = new AdaptedTag(VALID_MULTIWORD_TAGNAME);
        assertDoesNotThrow(() -> {
            multiWordTag.toModelType();
        });
    }

    @Test
    public void toModelType_validName_stringConstructor() throws Exception {
        AdaptedTag singleWordTag = new AdaptedTag(VALID_TAGNAME);
        assertEquals(singleWordTag.toModelType(), expectedSingleWordTag);

        AdaptedTag multiWordTag = new AdaptedTag(VALID_MULTIWORD_TAGNAME);
        assertEquals(multiWordTag.toModelType(), expectedMultiWordTag);
    }

    @Test
    public void toModelType_validName_tagConstructor() throws Exception {
        AdaptedTag singleWordTag = new AdaptedTag(expectedSingleWordTag);
        assertEquals(singleWordTag.toModelType(), expectedSingleWordTag);

        AdaptedTag multiWordTag = new AdaptedTag(expectedMultiWordTag);
        assertEquals(multiWordTag.toModelType(), expectedMultiWordTag);
    }

    @Test
    public void toModelType_invalidName_throwsException() {
        AdaptedTag illegalCharTag = new AdaptedTag(ILLEGAL_CHAR_TAGNAME);
        assertThrows(IllegalValueException.class, Tag.MESSAGE_CONSTRAINTS,
                illegalCharTag::toModelType);

        AdaptedTag illegalCharMultiwordTag = new AdaptedTag(ILLEGAL_CHAR_MULTIWORD_TAGNAME);
        assertThrows(IllegalValueException.class, Tag.MESSAGE_CONSTRAINTS,
                illegalCharMultiwordTag::toModelType);
    }

    @Test
    public void getTagName() {
        AdaptedTag singleWordTag = new AdaptedTag(VALID_TAGNAME);
        assertEquals(singleWordTag.getTagName(), VALID_TAGNAME);

        AdaptedTag multiWordTag = new AdaptedTag(VALID_MULTIWORD_TAGNAME);
        assertEquals(multiWordTag.getTagName(), VALID_MULTIWORD_TAGNAME);
    }
}
