package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.testutil.FileAndPathUtil;

public class CsvAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "CsvAddressBookStorageTest");

    private Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new CsvAddressBookStorage(addToTestDataPathIfNotNull(filePath))
                .readAddressBook();
    }

    private Path addToTestDataPathIfNotNull(String filePath) {
        return FileAndPathUtil.addToTestDataPathIfNotNull(TEST_DATA_FOLDER, filePath);
    }

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    @Test
    public void readAddressBook_invalidFilePath_emptyResult() throws Exception {
        assertFalse(readAddressBook("nonExistentFile.csv").isPresent());
    }

    @Test
    public void readAddressBook_notCsvFile_throwsException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notCsvFile.txt"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPersonAddressBook.csv"));
    }

    @Test
    public void readAddressBook_validAndInvalidPersonAddressBook_throwsDataLoadingException() {
        assertThrows(DataLoadingException.class, () ->
                readAddressBook("validAndInvalidPersonAddressBook.csv"));
    }

    @Test
    public void readAddressBook_missingField_emptyResult() throws Exception {
        assertFalse(readAddressBook("missingFieldCsvFile.csv").isPresent());
    }

    @Test
    public void readAndSaveAddressBook_validPersonAddressBook_success() throws Exception {
        Path savePath = addToTestDataPathIfNotNull("testSaveFile.csv");
        AddressBook original = getTypicalAddressBook();
        CsvAddressBookStorage abStorage = new CsvAddressBookStorage(savePath);

        // save in new data and read back
        abStorage.saveAddressBook(original, savePath);
        ReadOnlyAddressBook readBack = abStorage.readAddressBook(savePath).get();
        assertEquals(readBack, original);

        FileAndPathUtil.cleanupCreatedFiles(savePath);

        // save and read again without specifying the file path
        abStorage.saveAddressBook(original);
        readBack = abStorage.readAddressBook().get();
        assertEquals(readBack, original);

        FileAndPathUtil.cleanupCreatedFiles(savePath);
    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "shouldNotExist.csv"));
    }

    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        new CsvAddressBookStorage(addToTestDataPathIfNotNull(filePath))
                .saveAddressBook(addressBook);
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(getTypicalAddressBook(), null));
    }

    @Test
    public void getAddressBookFilePath() {
        Path testPath = addToTestDataPathIfNotNull("mockFile.csv");
        CsvAddressBookStorage abStorage = new CsvAddressBookStorage(testPath);
        assertEquals(abStorage.getAddressBookFilePath(), testPath);
    }
}
