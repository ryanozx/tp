package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportContactCommand;

/**
 * ExportContactCommandParser implementation is used to test ExportCommandParser
 */
public class ExportCommandParserTest {
    private final ExportContactCommandParser parser = new ExportContactCommandParser();

    @Test
    public void parse_validFilePaths_success() {
        Path targetFilePath = Paths.get(ExportContactCommand.EXPORT_DEST, "TestFile.csv");

        assertParseSuccess(parser, "TestFile", new ExportContactCommand(targetFilePath));
        // parser should not append csv more than once
        assertParseSuccess(parser, "TestFile.csv", new ExportContactCommand(targetFilePath));
        // parser should replace invalid file extension provided by user with valid file extension
        assertParseSuccess(parser, "TestFile.txt", new ExportContactCommand(targetFilePath));
        // parser should recognise the supplied extension to be the suffix that follows the last period
        assertParseSuccess(parser, "TestFile.tar.gz.txt", new ExportContactCommand(
                Paths.get(ExportContactCommand.EXPORT_DEST, "TestFile.tar.gz.csv")
        ));
        // parser should extract file name from user input if user provides a path
        assertParseSuccess(parser, ExportContactCommand.EXPORT_DEST + "/" + "TestFile",
                new ExportContactCommand(targetFilePath));
        // parser should ignore parent directories
        assertParseSuccess(parser, "../TestFile", new ExportContactCommand(targetFilePath));
        assertParseSuccess(parser, "fakeParentDir/TestFile", new ExportContactCommand(targetFilePath));
        assertParseSuccess(parser, "fakeGrandparentDir/fakeParentDir/TestFile.txt",
                new ExportContactCommand(targetFilePath));
    }

    @Test
    public void parse_invalidFilePaths_failure() {
        String usageErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportContactCommand.MESSAGE_USAGE);

        // parser should reject empty inputs, or inputs with only spaces
        assertParseFailure(parser, "", usageErrorMessage);
        assertParseFailure(parser, "    ", usageErrorMessage);
        // parser should reject directories
        assertParseFailure(parser, "parentDir/", usageErrorMessage);
    }
}
