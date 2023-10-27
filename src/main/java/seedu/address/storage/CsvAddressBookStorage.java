package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.CsvMissingFieldException;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CsvFile;
import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a CSV storage for the address book
 */
public class CsvAddressBookStorage implements AddressBookStorage {
    private final Path filePath;

    /**
     * Constructs a CsvAddressBook
     * @param filePath
     */
    public CsvAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<CsvFile> file = CsvUtil.readCsvFile(filePath);
        if (file.isEmpty()) {
            return Optional.empty();
        }
        List<CsvAdaptedPerson> persons = getPersons(file.get());

        try {
            if (persons.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new CsvSerializableAddressBook(persons).toModelType());
        } catch (IllegalValueException e) {
            throw new DataLoadingException(e);
        }
    }

    /**
     * Returns a list of CsvAdaptedPersons initialised with values read from a CsvFile object
     * @param file CsvFile containing field values of Persons
     * @return List of CsvAdaptedPersons
     */
    private static List<CsvAdaptedPerson> getPersons(CsvFile file) {
        return file.getRows().map(row -> {
            try {
                return CsvAdaptedPerson.deserialisePerson(row);
            } catch (CsvMissingFieldException e) {
                return null;
            }
        })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        List<CsvAdaptedPerson> csvPersons = addressBook.getPersonList().stream()
                .map(CsvAdaptedPerson::new).collect(Collectors.toList());
        CsvSerializableAddressBook csvAddressBook = new CsvSerializableAddressBook(csvPersons);
        CsvFile fileToSave = csvAddressBook.saveAddressBook();

        CsvUtil.saveCsvFile(fileToSave, filePath);
    }
}
