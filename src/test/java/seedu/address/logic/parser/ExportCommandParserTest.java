package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
public class ExportCommandParserTest {
    private final ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validFilePaths_success() {
        Path targetFilePath = Paths.get(ExportCommand.EXPORT_DEST, "TestFile.csv");

        assertParseSuccess(parser, "TestFile", new ExportCommand(targetFilePath));
        // parser should not append csv more than once
        assertParseSuccess(parser, "TestFile.csv", new ExportCommand(targetFilePath));
        // parser should replace invalid file extension provided by user with valid file extension
        assertParseSuccess(parser, "TestFile.txt", new ExportCommand(targetFilePath));
        // parser should recognise the supplied extension to be the suffix that follows the last period
        assertParseSuccess(parser, "TestFile.tar.gz.txt", new ExportCommand(
                Paths.get(ExportCommand.EXPORT_DEST, "TestFile.tar.gz.csv")
        ));
        // parser should extract file name from user input if user provides a path
        assertParseSuccess(parser, ExportCommand.EXPORT_DEST + "/" + "TestFile",
                new ExportCommand(targetFilePath));
        // parser should ignore parent directories
        assertParseSuccess(parser, "../TestFile", new ExportCommand(targetFilePath));
        assertParseSuccess(parser, "fakeParentDir/TestFile", new ExportCommand(targetFilePath));
        assertParseSuccess(parser, "fakeGrandparentDir/fakeParentDir/TestFile.txt",
                new ExportCommand(targetFilePath));
    }

    @Test
    public void parse_invalidFilePaths_failure() {
        String usageErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

        // parser should reject empty inputs, or inputs with only spaces
        assertParseFailure(parser, "", usageErrorMessage);
        assertParseFailure(parser, "    ", usageErrorMessage);
        // parser should reject directories
        assertParseFailure(parser, "parentDir/", usageErrorMessage);
    }
}
