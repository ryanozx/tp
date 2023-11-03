package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.LeavesBook;
import seedu.address.model.ReadOnlyLeavesBook;
import seedu.address.testutil.FileAndPathUtil;


public class CsvLeavesBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "CsvLeavesBookStorageTest");

    private Optional<ReadOnlyLeavesBook> readLeavesBook(String filePath) throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        return new CsvLeavesBookStorage(addToTestDataPathIfNotNull(filePath))
                .readLeavesBook(addressBook);
    }

    private Path addToTestDataPathIfNotNull(String filePath) {
        return FileAndPathUtil.addToTestDataPathIfNotNull(TEST_DATA_FOLDER, filePath);
    }

    @Test
    public void readLeavesBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readLeavesBook(null));
    }

    @Test
    public void readLeavesBook_invalidFilePath_emptyResult() throws Exception {
        assertFalse(readLeavesBook("nonExistentFile.csv").isPresent());
    }

    @Test
    public void readLeavesBook_notCsvFile_throwsException() {
        assertThrows(DataLoadingException.class, () -> readLeavesBook("notCsvFile.txt"));
    }

    /*
    @Test
    public void readLeavesBook_invalidLeavesAddressBook_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readLeavesBook("invalidPersonAddressBook.csv"));
    }

    @Test
    public void readAddressBook_validAndInvalidPersonAddressBook_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () ->
                readLeavesBook("validAndInvalidPersonAddressBook.csv"));
    }

    @Test
    public void readAddressBook_missingField_emptyResult() throws Exception {
        assertFalse(readLeavesBook("missingFieldCsvFile.csv").isPresent());
    }
    */

    @Test
    public void readAndSaveLeavesBook_validLeavesBook_success() throws Exception {
        Path savePath = addToTestDataPathIfNotNull("testSaveFile.csv");
        LeavesBook original = getTypicalLeavesBook();
        CsvLeavesBookStorage lvStorage = new CsvLeavesBookStorage(savePath);

        // save in new data and read back
        lvStorage.saveLeavesBook(original, savePath);
        ReadOnlyLeavesBook readBack = lvStorage.readLeavesBook(savePath, getTypicalAddressBook()).get();
        assertEquals(readBack, original);

        FileAndPathUtil.cleanupCreatedFiles(savePath);

        // save and read again without specifying the file path
        lvStorage.saveLeavesBook(original);
        readBack = lvStorage.readLeavesBook(getTypicalAddressBook()).get();
        assertEquals(readBack, original);

        FileAndPathUtil.cleanupCreatedFiles(savePath);
    }

    @Test
    public void saveLeavesBook_nullLeavesBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLeavesBook(null, "shouldNotExist.csv"));
    }

    private void saveLeavesBook(ReadOnlyLeavesBook leavesBook, String filePath) throws IOException {
        new CsvLeavesBookStorage(addToTestDataPathIfNotNull(filePath))
                .saveLeavesBook(leavesBook);
    }

    @Test
    public void saveLeavesBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveLeavesBook(getTypicalLeavesBook(), null));
    }

    @Test
    public void getAddressBookFilePath() {
        Path testPath = addToTestDataPathIfNotNull("mockFile.csv");
        CsvLeavesBookStorage lvStorage = new CsvLeavesBookStorage(testPath);
        assertEquals(lvStorage.getLeavesBookFilePath(), testPath);
    }
}
