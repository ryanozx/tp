package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagsContainAllTagsPredicateTest {

    @Test
    public void equals() {
        List<Tag> tagList1 = new ArrayList<>();
        List<Tag> tagList2 = new ArrayList<>();
        tagList1.add(new Tag("fullTime"));
        tagList2.add(new Tag("partTime"));
        tagList2.add(new Tag("remote"));

        TagsContainAllTagsPredicate firstPredicate = new TagsContainAllTagsPredicate(tagList1);
        TagsContainAllTagsPredicate secondPredicate = new TagsContainAllTagsPredicate(tagList2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainAllTagsPredicate firstPredicateCopy = new TagsContainAllTagsPredicate(tagList1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {

        List<Tag> tagList1 = new ArrayList<>();
        List<Tag> tagList2 = new ArrayList<>();
        tagList1.add(new Tag("fullTime"));
        tagList2.add(new Tag("partTime"));
        tagList2.add(new Tag("remote"));
        // One keyword
        TagsContainAllTagsPredicate predicate =
                new TagsContainAllTagsPredicate(tagList1);
        assertTrue(predicate.test(new PersonBuilder()
                .withTags("fullTime").build()));

        // Multiple keywords
        predicate = new TagsContainAllTagsPredicate(tagList2);
        assertTrue(predicate.test(new PersonBuilder()
                .withTags("partTime", "remote").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("fullTime"));

        // Zero keywords
        TagsContainAllTagsPredicate predicate =
                new TagsContainAllTagsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withTags("parttime").build()));

        // Only one matching keyword
        predicate = new TagsContainAllTagsPredicate(tagList);
        assertFalse(predicate.test(new PersonBuilder()
                .withTags("fullTime", "remote").build()));

        // Non-matching keyword
        predicate = new TagsContainAllTagsPredicate(tagList);
        assertFalse(predicate.test(new PersonBuilder()
                .withTags("partTime", "remote").build()));

        // Mixed-case keywords
        predicate = new TagsContainAllTagsPredicate(tagList);
        assertFalse(predicate.test(new PersonBuilder()
                .withTags("FullTime").build()));
    }

    @Test
    public void toStringMethod() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("fullTime"));

        TagsContainAllTagsPredicate predicate = new TagsContainAllTagsPredicate(tagList);

        String expected = TagsContainAllTagsPredicate.class.getCanonicalName() + "{tags=" + "[[fullTime]]" + "}";
        assertEquals(expected, predicate.toString());
    }
}
