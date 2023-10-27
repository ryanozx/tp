package seedu.address.model.person;

/**
 * Interface to allow for comparing Person objects by name
 */
public interface ComparablePerson {

    /**
     * Returns true if both persons have the same name.
     *
     * @param otherPerson
     * @return
     */
    boolean isSamePerson(ComparablePerson otherPerson);

    /**
     * Returns the Name field of a ComparablePerson object
     *
     * @return
     */
    Name getName();
}
