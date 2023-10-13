package seedu.address.model.person;
import seedu.address.model.tag.Tag;

import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class TagsContainAllTagsPredicate implements Predicate<Person> {
    private final List<Tag> tags;

    public TagsContainAllTagsPredicate(List<Tag> tags) {
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
        if (!(other instanceof TagsContainAllTagsPredicate)) {
            return false;
        }

        TagsContainAllTagsPredicate otherNameContainsKeywordsPredicate = (TagsContainAllTagsPredicate) other;
        return tags.equals(otherNameContainsKeywordsPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", tags).toString();
    }
}
