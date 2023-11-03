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
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyLeavesBook;

/**
 * Represents a CSV storage for the leaves book
 */
public class CsvLeavesBookStorage implements LeavesBookStorage {
    private final Path filePath;

    public CsvLeavesBookStorage(Path filePath) {
        this.filePath = filePath;
    }
    @Override
    public Path getLeavesBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLeavesBook> readLeavesBook(AddressBook addressBook) throws DataLoadingException {
        return readLeavesBook(filePath, addressBook);
    }

    @Override
    public Optional<ReadOnlyLeavesBook> readLeavesBook(Path filePath, AddressBook addressBook)
            throws DataLoadingException {

        Optional<CsvFile> file = CsvUtil.readCsvFile(filePath);
        if (file.isEmpty()) {
            return Optional.empty();
        }
        List<CsvAdaptedLeave> leaves = getLeaves(file.get());

        try {
            if (leaves.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(new CsvSerializableLeavesBook(leaves).toModelType(addressBook));
        } catch (IllegalValueException e) {
            throw new DataLoadingException(e);
        }
    }

    /**
     * Returns a list of CsvAdaptedLeaves initialised with values read from a CsvFile object
     * @param file CsvFile containing field values of Leaves
     * @return List of CsvAdaptedLeaves
     */
    private static List<CsvAdaptedLeave> getLeaves(CsvFile file) {
        return file.getRows().map(row -> {
            try {
                return CsvAdaptedLeave.deserialiseLeave(row);
            } catch (CsvMissingFieldException e) {
                return null;
            }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    }

    @Override
    public void saveLeavesBook(ReadOnlyLeavesBook leavesBook) throws IOException {
        saveLeavesBook(leavesBook, filePath);
    }

    @Override
    public void saveLeavesBook(ReadOnlyLeavesBook leavesBook, Path filePath) throws IOException {
        requireNonNull(leavesBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        List<CsvAdaptedLeave> csvLeaves = leavesBook.getLeaveList().stream()
                .map(CsvAdaptedLeave::new).collect(Collectors.toList());
        CsvSerializableLeavesBook csvLeavesBook = new CsvSerializableLeavesBook(csvLeaves);
        CsvFile fileToSave = csvLeavesBook.saveLeavesBook();

        CsvUtil.saveCsvFile(fileToSave, filePath);
    }
}
