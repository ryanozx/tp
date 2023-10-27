package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyLeavesBook;

public class JsonSerializableLeavesBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableLeavesBookTest");
    private static final Path VALID_DATA_FILE = TEST_DATA_FOLDER.resolve("ValidLeaves.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        JsonSerializableLeavesBook dataFromFile = JsonUtil.readJsonFile(VALID_DATA_FILE,
                JsonSerializableLeavesBook.class).get();
        ReadOnlyLeavesBook addressBookFromFile = dataFromFile.toModelType(addressBook);
        assertEquals(addressBookFromFile, getTypicalLeavesBook());
    }


}
