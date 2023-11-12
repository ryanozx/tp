package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements ComparablePerson {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    /**
     * Constructor for Person object that is used to copy another Person object.
     *
     * @param toCopy Person object to copy.
     */
    public Person(Person toCopy) {
        requireNonNull(toCopy);
        this.name = toCopy.name;
        this.phone = toCopy.phone;
        this.email = toCopy.email;
        this.address = toCopy.address;
        this.tags.addAll(toCopy.tags);
    }

    @Override
    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Adds tags in toAdd to tags
     */
    public void addTags(Collection<Tag> toAdd) {
        tags.addAll(toAdd);
    }

    /**
     * Add tag to tags
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Checks if any tag in tags is in this.tags
     */
    public boolean hasAnyTags(Collection<Tag> tags) {
        return tags.stream().anyMatch(this.tags::contains);
    }

    /**
     * Checks if this person has all the tags in the argument collection.
     *
     * @param tags Collection of tags to check.
     * @return true if this person has all the tags in the argument collection, false otherwise.
     */
    public boolean hasAllTags(Collection<Tag> tags) {
        assert tags != null;
        assert !tags.isEmpty();
        return this.tags.containsAll(tags);
    }

    /**
     * Checks if this person has the argument tag.
     *
     * @param tag Tag to check.
     * @return true if this person has the argument tag, false otherwise.
     */
    public boolean hasTag(Tag tag) {
        assert tag != null;
        return this.tags.contains(tag);
    }

    /**
     * Removes the tags in the argument collection from this person.
     *
     * @param tags Collection of tags to remove.
     */
    public void removeTags(Collection<Tag> tags) {
        assert hasAllTags(tags);
        this.tags.removeAll(tags);
    }

    /**
     * Removes a tag from this person.
     *
     * @param tag Tag to remove.
     */
    public void removeTag(Tag tag) {
        assert hasTag(tag);
        this.tags.remove(tag);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(ComparablePerson otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
