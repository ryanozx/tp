package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * An abstract class for an Immutable AddressBook that can be serialised to different types.
 */
abstract class SerializableAddressBook<T extends AdaptedPerson> {
    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    protected final List<T> persons = new ArrayList<>();

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (T adaptedPerson : persons) {
            Person person = adaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        return addressBook;
    }
}
