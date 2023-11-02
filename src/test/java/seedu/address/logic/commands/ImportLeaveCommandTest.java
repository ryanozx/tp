package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.FileAndPathUtil.MockSuccessfulFileDialogHandler;
import static seedu.address.testutil.TypicalLeaves.getTypicalLeavesBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.stage.FileChooser;
import seedu.address.commons.controllers.FileDialogHandler;
import seedu.address.model.LeavesBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.FileAndPathUtil;

public class ImportLeaveCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvFiles");
    private static final Path INVALID_ADDRESS_BOOK_PATH = Paths.get("src", "test", "data",
            "CsvLeavesBookStorageTest", "validAndInvalidLeavesBook.csv");
    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalLeavesBook(), new UserPrefs());

    private Path addToTestDataPathIfNotNull(String filename) {
        return FileAndPathUtil.addToTestDataPathIfNotNull(TEST_DATA_FOLDER, filename);
    }
    @Test
    public void execute_fileChosen_success() {
        Path filepath = addToTestDataPathIfNotNull("typicalLeavesBook.csv");
        ImportLeaveCommand command = new ImportLeaveCommand(
                new MockSuccessfulFileDialogHandler(filepath.toString()));
        Model actualModel = new ModelManager(getTypicalAddressBook(), new LeavesBook(), new UserPrefs());
        String expectedMessage = String.format(ImportLeaveCommand.MESSAGE_SUCCESS, filepath.getFileName());
        assertCommandSuccess(command, actualModel, expectedMessage, model);
    }

    @Test
    public void execute_fileNotChosen_failed() {
        ImportContactCommand command = new ImportContactCommand(new MockUnsuccessfulFileDialogHandler());
        String expectedMessage = ImportContactCommand.MESSAGE_NO_FILE_SELECTED;
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    private static class MockUnsuccessfulFileDialogHandler implements FileDialogHandler {
        @Override
        public Optional<File> openFile(String title, FileChooser.ExtensionFilter... extensions) {
            return Optional.empty();
        }
    }

    @Test
    public void execute_invalidLeavesBook_throwsException() {
        ImportLeaveCommand command = new ImportLeaveCommand(
                new MockSuccessfulFileDialogHandler(INVALID_ADDRESS_BOOK_PATH.toString()));
        Model actualModel = new ModelManager(getTypicalAddressBook(), new LeavesBook(), new UserPrefs());
        String expectedMessage = String.format(ImportLeaveCommand.MESSAGE_FAILED,
                INVALID_ADDRESS_BOOK_PATH.getFileName());
        assertCommandFailure(command, actualModel, expectedMessage);
    }

    @Test
    public void execute_emptyAddressBook_throwsException() {
        Path filePath = addToTestDataPathIfNotNull("emptyLeavesBook.csv");
        ImportLeaveCommand command = new ImportLeaveCommand(
                new MockSuccessfulFileDialogHandler(filePath.toString()));
        Model actualModel = new ModelManager(getTypicalAddressBook(), new LeavesBook(), new UserPrefs());
        String expectedMessage = String.format(ImportLeaveCommand.MESSAGE_EMPTY_LEAVES_BOOK,
                filePath.getFileName());
        assertCommandFailure(command, actualModel, expectedMessage);
    }
}
