package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.stage.FileChooser.ExtensionFilter;
import seedu.address.commons.controllers.FileDialogHandler;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.FileAndPathUtil;

public class ImportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvFiles");
    private static final Path INVALID_ADDRESS_BOOK_PATH = Paths.get("src", "test", "data",
            "CsvAddressBookStorageTest", "validAndInvalidPersonAddressBook.csv");
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());

    private Path addToTestDataPathIfNotNull(String filename) {
        return FileAndPathUtil.addToTestDataPathIfNotNull(TEST_DATA_FOLDER, filename);
    }
    @Test
    public void execute_fileChosen_success() {
        Path filepath = addToTestDataPathIfNotNull("typicalPersonsAddressBook.csv");
        ImportCommand command = new ImportCommand(
                new MockSuccessfulFileDialogHandler(filepath.toString()));
        Model actualModel = new ModelManager();
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, filepath.getFileName());
        assertCommandSuccess(command, actualModel, expectedMessage, model);
    }

    private static class MockSuccessfulFileDialogHandler implements FileDialogHandler {

        private final String pathname;

        public MockSuccessfulFileDialogHandler(String filename) {
            this.pathname = filename;
        }

        @Override
        public Optional<File> openFile(String title, ExtensionFilter ...extensions) {
            File outputFile = new File(pathname);
            return Optional.of(outputFile);
        }
    }

    @Test
    public void execute_fileNotChosen_failed() {
        ImportCommand command = new ImportCommand(new MockUnsuccessfulFileDialogHandler());
        String expectedMessage = ImportCommand.MESSAGE_NO_FILE_SELECTED;
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    private static class MockUnsuccessfulFileDialogHandler implements FileDialogHandler {
        @Override
        public Optional<File> openFile(String title, ExtensionFilter... extensions) {
            return Optional.empty();
        }
    }

    @Test
    public void execute_invalidAddressBook_throwsException() {
        ImportCommand command = new ImportCommand(
                new MockSuccessfulFileDialogHandler(INVALID_ADDRESS_BOOK_PATH.toString()));
        Model actualModel = new ModelManager();
        String expectedMessage = String.format(ImportCommand.MESSAGE_FAILED,
                INVALID_ADDRESS_BOOK_PATH.getFileName());
        assertCommandFailure(command, actualModel, expectedMessage);
    }

    @Test
    public void execute_emptyAddressBook_throwsException() {
        Path filePath = addToTestDataPathIfNotNull("emptyAddressBook.csv");
        ImportCommand command = new ImportCommand(
                new MockSuccessfulFileDialogHandler(filePath.toString()));
        Model actualModel = new ModelManager();
        String expectedMessage = String.format(ImportCommand.MESSAGE_EMPTY_ADDRESS_BOOK,
                filePath.getFileName());
        assertCommandFailure(command, actualModel, expectedMessage);
    }
}
