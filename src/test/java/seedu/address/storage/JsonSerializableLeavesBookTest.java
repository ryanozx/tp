package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyLeavesBook;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class JsonSerializableLeavesBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableLeavesBookTest");
    private static final Path VALID_DATA_FILE = TEST_DATA_FOLDER.resolve("ValidLeaves.json");
    private static final Path INVALID_PERSON_DATA_FILE = TEST_DATA_FOLDER.resolve("invalidPersonLeaves.json");
    private static final Path DUPLICATE_DATA_FILE = TEST_DATA_FOLDER.resolve("duplicateLeaves.json");
    private static final Path INVALID_FIELD_DATA_FILE = TEST_DATA_FOLDER.resolve("invalidFieldLeaves.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        JsonSerializableLeavesBook dataFromFile = JsonUtil.readJsonFile(
                VALID_DATA_FILE, JsonSerializableLeavesBook.class).get();
        ReadOnlyLeavesBook addressBookFromFile = dataFromFile.toModelType(addressBook);
        assertEquals(addressBookFromFile, getTypicalLeavesBook());
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableLeavesBook dataFromFile = JsonUtil.readJsonFile(
                INVALID_PERSON_DATA_FILE, JsonSerializableLeavesBook.class).get();
        assertThrows(PersonNotFoundException.class, () -> dataFromFile.toModelType(getTypicalAddressBook()));
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableLeavesBook dataFromFile = JsonUtil.readJsonFile(
                DUPLICATE_DATA_FILE, JsonSerializableLeavesBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableLeavesBook.MESSAGE_DUPLICATE_LEAVE, () ->
                dataFromFile.toModelType(getTypicalAddressBook()));
    }

    @Test
    public void toModelType_invalidFieldFile_throwsIllegalValueException() throws Exception {
        JsonSerializableLeavesBook dataFromFile = JsonUtil.readJsonFile(
                INVALID_FIELD_DATA_FILE, JsonSerializableLeavesBook.class).get();
        assertThrows(IllegalValueException.class, () -> dataFromFile.toModelType(getTypicalAddressBook()));
    }

}
