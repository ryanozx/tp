package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyLeavesBook;

/**
 * A class to access LeavesBook data stored as a json file on the hard disk.
 */
public class JsonLeavesBookStorage implements LeavesBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonLeavesBookStorage.class);

    private final Path filePath;

    public JsonLeavesBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLeavesBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLeavesBook> readLeavesBook(AddressBook addressBook) throws DataLoadingException {
        return readLeavesBook(filePath, addressBook);
    }

    /**
     * Similar to {@link #readLeavesBook()} ()}
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if the file is not found.
     */
    public Optional<ReadOnlyLeavesBook> readLeavesBook(Path filePath, AddressBook addressBook)
            throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializableLeavesBook> jsonLeavesBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableLeavesBook.class);
        if (!jsonLeavesBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonLeavesBook.get().toModelType(addressBook));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void saveLeavesBook(ReadOnlyLeavesBook leavesBook) throws IOException {
        saveLeavesBook(leavesBook, filePath);
    }

    /**
     * Similar to {@link #saveLeavesBook(ReadOnlyLeavesBook)}
     * @param filePath location of the data. Cannot be null.
     */
    public void saveLeavesBook(ReadOnlyLeavesBook leavesBook, Path filePath) throws IOException {
        requireNonNull(leavesBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableLeavesBook(leavesBook), filePath);
    }
}
