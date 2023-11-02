package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments for child Export parsers
 */
public abstract class ExportCommandParser {

    private final Path exportFolder = Path.of(ExportCommand.EXPORT_DEST);

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns a file name for the child Export commands to save to.
     * @param args String containing file name to parse
     * @param messageUsage Message to display if file name is not of the expected format
     * @throws ParseException if the user input does not conform to the expected format
     */
    public Path parseFileName(String args, String messageUsage) throws ParseException {
        String trimmedArgs = args.trim();

        boolean inputPathIsDirectory = trimmedArgs.endsWith("/");

        if (!FileUtil.isValidPath(trimmedArgs) || inputPathIsDirectory) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        Path fileName = getFileName(trimmedArgs);

        if (fileName.toString().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }

        String fileWithExtension = appendExtension(fileName.toString());
        return exportFolder.resolve(fileWithExtension);
    }

    private Path getFileName(String path) {
        Path filePath = Paths.get(path);
        System.out.println(filePath);
        return filePath.getFileName();
    }

    /**
     * Strips the extension provided by the user (if any) and appends the CSV extension
     * @param path File path provided by user
     * @return File path containing CSV extension
     */
    private String appendExtension(String path) {
        int extensionPos = path.lastIndexOf('.');
        boolean pathContainsExtension = extensionPos > -1;

        String pathStrippedExtension;
        if (pathContainsExtension) {
            pathStrippedExtension = path.substring(0, extensionPos);
        } else {
            pathStrippedExtension = path;
        }

        return pathStrippedExtension.concat(CsvUtil.EXTENSION);
    }
}
