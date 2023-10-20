package seedu.address.testutil;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Contains methods for handling data files in tests.
 */
public class FileAndPathUtil {
    /**
     * Returns the path of the file given the path of its enclosing folder
     * @param testDataFolder Folder where file is located
     * @param testFileInTestDataFolder Name of file
     * @return Path to file
     */
    public static Path addToTestDataPathIfNotNull(Path testDataFolder, String testFileInTestDataFolder) {
        return testFileInTestDataFolder != null
                ? testDataFolder.resolve(testFileInTestDataFolder)
                : null;
    }

    /**
     * Deletes file specified by the file path, if the file exists. Used to clean up test files to ensure that
     * a new file is created every time the test is run.
     * @param filePath Path of file to delete
     */
    public static void cleanupCreatedFiles(Path filePath) {
        if (Files.exists(filePath)) {
            File fileToDelete = new File(filePath.toString());
            if (!fileToDelete.delete()) {
                fail(String.format("Could not clean up test file: %s", filePath));
            }
        }
    }
}
