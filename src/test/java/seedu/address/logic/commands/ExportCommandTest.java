package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
public class ExportCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox", "ExportCommandTest");

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Path addToTestDataPathIfNotNull(String testFileInTestDataFolder) {
        return testFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(testFileInTestDataFolder)
                : null;
    }

    /**
     * Deletes file specified by the file path, if the file exists. Used to clean up test files to ensure that
     * a new file is created every time the test is run.
     * @param filePath Path of file to delete
     */
    private void cleanupCreatedFiles(Path filePath) {
        if (Files.exists(filePath)) {
            File fileToDelete = new File(filePath.toString());
            if (!fileToDelete.delete()) {
                fail(String.format("Could not clean up test file: %s", filePath));
            }
        }
    }
    @Test
    public void execute_validFilePath_success() {
        Path testFilePath = addToTestDataPathIfNotNull("testFile.csv");
        ExportCommand command = new ExportCommand(testFilePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, testFilePath);
        assertCommandSuccess(command, model, expectedMessage, model);
        assertTrue(Files.exists(testFilePath));
        cleanupCreatedFiles(testFilePath);
    }

    @Test
    public void equals() {
        Path sameFilePath = addToTestDataPathIfNotNull("sameFile.csv");
        Path diffFilePath = addToTestDataPathIfNotNull("diffFile.csv");
        ExportCommand exportFirstCommand = new ExportCommand(sameFilePath);
        ExportCommand exportSecondCommand = new ExportCommand(sameFilePath);
        ExportCommand exportDiffCommand = new ExportCommand(diffFilePath);


        // An export command is equal to itself
        assertEquals(exportFirstCommand, exportFirstCommand);
        // Two export commands with the same file path are equal
        assertEquals(exportFirstCommand, exportSecondCommand);
        // An export command is not equal to a different type
        assertNotEquals(exportFirstCommand, 1);
        // Two export commands with diff file paths are different
        assertNotEquals(exportFirstCommand, exportDiffCommand);
    }
}
