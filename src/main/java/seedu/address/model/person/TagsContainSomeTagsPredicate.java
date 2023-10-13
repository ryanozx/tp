package seedu.address.model.person;
import seedu.address.model.tag.Tag;

import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TagsContainSomeTagsPredicate implements Predicate<Person> {
    private final List<Tag> tags;

    public TagsContainSomeTagsPredicate(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return tags.stream()
                .anyMatch(tag -> person.getTags().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsContainSomeTagsPredicate)) {
            return false;
        }

        TagsContainSomeTagsPredicate otherNameContainsKeywordsPredicate = (TagsContainSomeTagsPredicate) other;
        return tags.equals(otherNameContainsKeywordsPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}
