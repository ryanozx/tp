package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaves.BENSON_LEAVE_2;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.ReadOnlyLeavesBook;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class JsonLeavesBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonLeavesBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readLeavesBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readLeavesBook(null));
    }

    private java.util.Optional<ReadOnlyLeavesBook> readLeavesBook(String filePath) throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        return new JsonLeavesBookStorage(Paths.get(filePath)).readLeavesBook(
                addToTestDataPathIfNotNull(filePath), addressBook);
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readLeavesBook_missingFile_emptyResult() throws Exception {
        assertFalse(readLeavesBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void readLeavesBook_success() {
        Path filePath = Paths.get("src", "test", "data", "JsonLeavesBookStorageTest", "ValidLeaves.json");
        try {
            AddressBook addressBook = getTypicalAddressBook();
            ReadOnlyLeavesBook leavesBook = new JsonLeavesBookStorage(filePath).readLeavesBook(addressBook).get();
            assertEquals(leavesBook, getTypicalLeavesBook());
        } catch (DataLoadingException dle) {
            throw new AssertionError("This should not happen.", dle);
        }
    }

    @Test
    public void readLeavesBook_invalidPerson_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> readLeavesBook("InvalidEmployeeName.json"));
    }

    @Test
    public void readLeavesBook_invalidDateFields_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readLeavesBook("InvalidDateFields.json"));
    }

    @Test
    public void readLeavesBook_notJsonFormat_throwsDataConversionException() {
        assertThrows(DataLoadingException.class, () -> readLeavesBook("NotJsonFormat.json"));
    }

    @Test
    public void readAndSaveLeavesBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempLeavesBook.json");
        LeavesBook original = getTypicalLeavesBook();
        AddressBook addressBook = getTypicalAddressBook();
        JsonLeavesBookStorage jsonLeavesBookStorage = new JsonLeavesBookStorage(filePath);

        // Save in new file and read back
        jsonLeavesBookStorage.saveLeavesBook(original, filePath);
        ReadOnlyLeavesBook readBack = jsonLeavesBookStorage.readLeavesBook(addressBook).get();
        assertEquals(original, new LeavesBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addLeave(BENSON_LEAVE_2);
        original.removeLeave(ALICE_LEAVE);
        jsonLeavesBookStorage.saveLeavesBook(original, filePath);
        readBack = jsonLeavesBookStorage.readLeavesBook(addressBook).get();
        assertEquals(original, new LeavesBook(readBack));

        // Save and read without specifying file path
        original.addLeave(ALICE_LEAVE);
        jsonLeavesBookStorage.saveLeavesBook(original); // file path not specified
        readBack = jsonLeavesBookStorage.readLeavesBook(addressBook).get();
        assertEquals(original, new LeavesBook(readBack));
    }

    @Test
    public void saveLeavesBook_nullLeavesBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLeavesBook(null, "SomeFile.json"));
    }

    @Test
    public void saveLeavesBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLeavesBook(new LeavesBook(), null));
    }

    /**
     * Saves {@code leavesBook} at the specified {@code filePath}.
     *
     * @param leavesBook
     * @param filePath
     */
    private void saveLeavesBook(ReadOnlyLeavesBook leavesBook, String filePath) {
        try {
            new JsonLeavesBookStorage(Paths.get(filePath))
                    .saveLeavesBook(leavesBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("This should not happen.", ioe);
        }
    }
}
