package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.testutil.FileAndPathUtil;

public class FileUtilTest {
    private static final Path TEST_FOLDER_PATH = Paths.get(
            "src", "test", "data", "sandbox", "FileUtilTest");
    @TempDir
    public Path testFolder;
    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void writeAndReadToFile_stringInput_success() throws IOException {
        Path filePath = testFolder.resolve("testFile.txt");
        String input = "Hello\nworld!\n";

        FileUtil.writeToFile(filePath, input);

        String readString = FileUtil.readFromFile(filePath);
        assertEquals(input, readString);
    }

    @Test
    public void writeAndReadToFile_streamInput_success() throws IOException {
        Path filePath = testFolder.resolve("testFile.txt");
        String firstLine = "Hello";
        String secondLine = "world!";

        Stream<String> lineStream = Stream.of(firstLine, secondLine);
        FileUtil.writeToFile(filePath, lineStream);

        String expectedResult = "Hello\nworld!\n";
        String readString = FileUtil.readFromFile(filePath);
        assertEquals(expectedResult, readString);
    }

    private Path addToTestDataPathIfNotNull(String fileName) {
        return FileAndPathUtil.addToTestDataPathIfNotNull(TEST_FOLDER_PATH, fileName);
    }

}
