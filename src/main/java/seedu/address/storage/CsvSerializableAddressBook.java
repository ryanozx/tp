package seedu.address.storage;

import java.util.List;

import seedu.address.commons.util.CsvFile;
import seedu.address.commons.util.CsvUtil;


/**
 * An Immutable AddressBook that is serializable to CSV format.
 */
class CsvSerializableAddressBook extends SerializableAddressBook<CsvAdaptedPerson> {

    public CsvSerializableAddressBook(List<CsvAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Creates a CsvFile from the list of AdaptedPersons in the serialisableAddressBook
     * @return CsvFile containing records of all persons in this address book
     */
    public CsvFile saveAddressBook() {
        CsvFile addressBookFile = new CsvFile(CsvAdaptedPerson.getHeader(), CsvUtil.DELIMITER);
        persons.forEach(addressBookFile::addRow);
        return addressBookFile;
    }
}
