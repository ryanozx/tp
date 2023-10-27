package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class JsonAdaptedPersonTest {
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<AdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(AdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_personParameterConstructor_returnsPerson() throws Exception {
        AdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_stringParameterConstructor_returnsPerson() throws Exception {
        AdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS);
        assertEquals(BENSON, person.toModelType());
    }
}
