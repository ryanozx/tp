package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyLeavesBook;

/**
 * Represents a storage for {@link seedu.address.model.LeavesBook}.
 */
public interface LeavesBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLeavesBookFilePath();

    /**
     * Returns LeavesBook data as a {@link seedu.address.model.LeavesBook}.
     * Returns {@code null} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyLeavesBook> readLeavesBook(AddressBook addressBook) throws DataLoadingException;

    /**
     * @see #getLeavesBookFilePath()
     */
    Optional<ReadOnlyLeavesBook> readLeavesBook(Path filePath, AddressBook addressBook) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyLeavesBook} to the storage.
     * @param leavesBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLeavesBook(ReadOnlyLeavesBook leavesBook) throws IOException;

    /**
     * @see #saveLeavesBook(ReadOnlyLeavesBook)
     */
    void saveLeavesBook(ReadOnlyLeavesBook leavesBook, Path filePath) throws IOException;
}
