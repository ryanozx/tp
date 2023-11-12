package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress() + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void hasAllTags_allTagsPresent_returnsTrue() {
        assertTrue(ALICE.hasAllTags(ALICE.getTags()));
    }

    @Test
    public void hasAllTags_notAllTagsPresent_returnsFalse() {
        assertFalse(ALICE.hasAllTags(BOB.getTags()));
    }

    @Test
    public void hasAllTags_emptyCollection_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ALICE.hasAllTags(new HashSet<>()));
    }

    @Test
    public void hasAllTags_null_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ALICE.hasAllTags(null));
    }

    @Test
    public void hasTag_tagPresent_returnsTrue() {
        assertTrue(ALICE.hasTag(ALICE.getTags().iterator().next()));
    }

    @Test
    public void hasTag_tagNotPresent_returnsFalse() {
        assertFalse(ALICE.hasTag(BOB.getTags().iterator().next()));
    }

    @Test
    public void hasTag_null_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ALICE.hasTag(null));
    }

    @Test
    public void removeTags_allTagsPresent_returnsTrue() {
        Person person = new PersonBuilder(ALICE).build();
        person.removeTags(ALICE.getTags());
        assertTrue(person.getTags().isEmpty());
    }

    @Test
    public void removeTags_notAllTagsPresent_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ALICE.removeTags(BOB.getTags()));
    }

    @Test
    public void removeTags_emptyCollection_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ALICE.removeTags(new HashSet<>()));
    }

    @Test
    public void removeTags_null_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ALICE.removeTags(null));
    }

    @Test
    public void removeTag_tagPresent_returnsTrue() {
        Person person = new PersonBuilder(ALICE).build();
        person.removeTag(ALICE.getTags().iterator().next());
        assertTrue(person.getTags().isEmpty());
    }

    @Test
    public void removeTag_tagNotPresent_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ALICE.removeTag(BOB.getTags().iterator().next()));
    }

    @Test
    public void removeTag_null_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> ALICE.removeTag(null));
    }

}
