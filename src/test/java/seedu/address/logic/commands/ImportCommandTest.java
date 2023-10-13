package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.stage.FileChooser.ExtensionFilter;
import seedu.address.commons.controllers.FileDialogHandler;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_fileChosen_success() {
        String fileName = "testFile.csv";
        ImportCommand command = new ImportCommand(new MockSuccessfulFileDialogHandler(fileName));
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, fileName);
        assertCommandSuccess(command, model, expectedMessage, model);
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
        String expectedMessage = ImportCommand.MESSAGE_FAILED;
        assertCommandSuccess(command, model, expectedMessage, model);
    }

    private static class MockUnsuccessfulFileDialogHandler implements FileDialogHandler {
        @Override
        public Optional<File> openFile(String title, ExtensionFilter... extensions) {
            return Optional.empty();
        }
    }
}
