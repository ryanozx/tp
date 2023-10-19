package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.testutil.FileAndPathUtil;

public class CsvUtilTest {
    private static final Path TEST_FOLDER_PATH = Paths.get(
            "src", "test", "data", "CsvUtilTest");
    private static final String DELIMITER = ";";
    private static final List<String> HEADER_LIST = Arrays.asList("first", "second", "third");
    private static final String HEADER = String.join(DELIMITER, HEADER_LIST);

    private static final List<String> FIRST_ROW_VALS = Arrays.asList("firstVal", "secondVal", "thirdVal");
    private static final String FIRST_ROW = String.join(DELIMITER, FIRST_ROW_VALS);

    @Test
    public void readCsvFile_nullFilePath_throwsException() {
        assertThrows(NullPointerException.class, () -> CsvUtil.readCsvFile(null));
    }

    @Test
    public void readCsvFile_wrongFileType_throwsException() {
        Path invalidExtensionFile = addToTestDataPathIfNotNull("badExtension.txt");
        assertThrows(DataLoadingException.class, () -> CsvUtil.readCsvFile(invalidExtensionFile));
    }

    private Path addToTestDataPathIfNotNull(String fileName) {
        return FileAndPathUtil.addToTestDataPathIfNotNull(TEST_FOLDER_PATH, fileName);
    }

    @Test
    public void readCsvFile_nonExistentFile_returnsEmpty() throws Exception {
        Path nonExistentFile = addToTestDataPathIfNotNull("nonExistentFile.csv");
        Optional<CsvFile> csvFile = CsvUtil.readCsvFile(nonExistentFile);
        assertFalse(csvFile.isPresent());
    }

    @Test
    public void readCsvFile_columnCountMatchesHeader_returnsFile() throws Exception {
        Path file = addToTestDataPathIfNotNull("csvFileMatchingColumns.csv");
        Optional<CsvFile> csvFile = CsvUtil.readCsvFile(file);

        List<String> lines = csvFile.get().getFileStream()
                .collect(Collectors.toList());
        assert(lines.size() == 2);
        assertEquals(lines.get(0), HEADER);
        assertEquals(lines.get(1), FIRST_ROW);
    }

    @Test
    public void readCsvFile_columnCountMismatch_skipsMisMatch() throws Exception {
        // if no. of values provided for the row is less than the number of columns, row
        // is still read into the CsvFile as the other columns will be padded

        // if no. of values provided for the row is more than the number of columns, the
        // entire row will be skipped
        Path file = addToTestDataPathIfNotNull("csvFileMismatchColumns.csv");
        Optional<CsvFile> csvFile = CsvUtil.readCsvFile(file);
        List<String> secondRowVals = Arrays.asList("fourthVal", "fifthVal", "");
        String expectedSecondRow = String.join(DELIMITER, secondRowVals);

        List<String> lines = csvFile.get().getFileStream()
                .collect(Collectors.toList());
        assert(lines.size() == 3);
        assertEquals(lines.get(0), HEADER);
        assertEquals(lines.get(1), FIRST_ROW);
        assertEquals(lines.get(2), expectedSecondRow);
    }

    @Test
    public void saveCsvFile_nullFile_throwsException() {
        assertThrows(NullPointerException.class, () -> CsvUtil.saveCsvFile(null,
                addToTestDataPathIfNotNull("nonRead.csv")));
    }

    @Test
    public void saveCsvFile_nullPath_throwsException() {
        CsvFile file = new CsvFile(HEADER, DELIMITER);
        assertThrows(NullPointerException.class, () -> CsvUtil.saveCsvFile(file,
                addToTestDataPathIfNotNull(null)));
    }

    @Test
    public void saveAndReadCsvFile_success() throws Exception {
        CsvFile file = new CsvFile(HEADER, DELIMITER);
        file.addRow(FIRST_ROW);
        Path savePath = addToTestDataPathIfNotNull("tempFile.csv");
        CsvUtil.saveCsvFile(file, savePath);

        Optional<CsvFile> readFile = CsvUtil.readCsvFile(savePath);
        List<String> lines = readFile.get().getFileStream()
                .collect(Collectors.toList());

        assert(lines.size() == 2);
        assertEquals(lines.get(0), HEADER);
        assertEquals(lines.get(1), FIRST_ROW);
        FileAndPathUtil.cleanupCreatedFiles(savePath);
    }
}
