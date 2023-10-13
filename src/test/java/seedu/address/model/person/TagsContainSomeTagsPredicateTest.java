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

public class TagsContainSomeTagsPredicateTest {

    @Test
    public void equals() {
        List<Tag> tagList1 = new ArrayList<>();
        List<Tag> tagList2 = new ArrayList<>();
        tagList1.add(new Tag("fullTime"));
        tagList2.add(new Tag("partTime"));
        tagList2.add(new Tag("remote"));

        TagsContainSomeTagsPredicate firstPredicate = new TagsContainSomeTagsPredicate(tagList1);
        TagsContainSomeTagsPredicate secondPredicate = new TagsContainSomeTagsPredicate(tagList2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagsContainSomeTagsPredicate firstPredicateCopy = new TagsContainSomeTagsPredicate(tagList1);
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
        TagsContainSomeTagsPredicate predicate =
                new TagsContainSomeTagsPredicate(tagList1);
        assertTrue(predicate.test(new PersonBuilder().withTags("fullTime").build()));

        // Multiple keywords
        predicate = new TagsContainSomeTagsPredicate(tagList2);
        assertTrue(predicate.test(new PersonBuilder().withTags("partTime")
                        .withTags("remote").build()));

        // Only one matching keyword
        predicate = new TagsContainSomeTagsPredicate(tagList2);
        assertTrue(predicate.test(new PersonBuilder().withTags("fullTime")
                .withTags("remote").build()));

        // Mixed-case keywords
        predicate = new TagsContainSomeTagsPredicate(tagList2);
        assertTrue(predicate.test(new PersonBuilder().withTags("parttime")
                .withTags("remote").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("fullTime"));

        // Zero keywords
        TagsContainSomeTagsPredicate predicate =
                new TagsContainSomeTagsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder()
                .withTags("parttime").build()));

        // Non-matching keyword
        predicate = new TagsContainSomeTagsPredicate(tagList);
        assertFalse(predicate.test(new PersonBuilder().withTags("parttime")
                .withTags("remote").build()));

    }

    @Test
    public void toStringMethod() {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(new Tag("fullTime"));

        TagsContainSomeTagsPredicate predicate = new TagsContainSomeTagsPredicate(tagList);

        String expected = TagsContainSomeTagsPredicate.class.getCanonicalName() + "{tag=" + "fullTime" + "}";
        assertEquals(expected, predicate.toString());
    }
}
