package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.CsvMismatchedColumnException;
import seedu.address.commons.exceptions.DataLoadingException;

/**
 * Converts a CSV file to a CsvFile object and vice-versa
 */
public class CsvUtil {

    public static final String DELIMITER = ";";
    public static final String EXTENSION = ".csv";
    private static final String NOT_CSV_FILETYPE_ERROR_MESSAGE = "File %s is not a CSV file";
    private static final Logger logger = LogsCenter.getLogger(JsonUtil.class);

    /**
     * Takes a CSV filepath and reads its contents into a CsvFile object. Rows that do not match the header row will
     * be skipped over.
     * @param filePath CSV filepath to read from
     * @return CsvFile containing the contents of the CSV file
     * @throws DataLoadingException if file cannot be read
     */
    public static Optional<CsvFile> readCsvFile(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        boolean isCsvFileType = filePath.toString().endsWith(EXTENSION);

        if (!isCsvFileType) {
            throw new DataLoadingException(new Exception(
                    String.format(NOT_CSV_FILETYPE_ERROR_MESSAGE, filePath)));
        }

        if (!Files.exists(filePath)) {
            return Optional.empty();
        }

        logger.info("CSV file " + filePath + " found.");

        CsvFile readFile;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String header = br.readLine();
            readFile = new CsvFile(header, DELIMITER);

            String row;
            while ((row = br.readLine()) != null) {
                try {
                    readFile.addRow(row);
                } catch (CsvMismatchedColumnException ignored) {
                    // if the row cannot be added, just move on to the next row
                }
            }
        } catch (IOException e) {
            throw new DataLoadingException(e);
        }
        return Optional.of(readFile);
    }

    /**
     * Saves the CsvFile to the specified file path.
     * @param file CsvFile object containing headers and values to be saved
     * @param filePath Path of file to be written to.
     * @throws IOException if file cannot be opened or created
     */
    public static void saveCsvFile(CsvFile file, Path filePath) throws IOException {
        requireNonNull(file);
        requireNonNull(filePath);

        FileUtil.writeToFile(filePath, file.getFileStream());
    }
}
