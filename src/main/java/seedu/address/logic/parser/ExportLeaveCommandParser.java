package seedu.address.logic.parser;

import java.nio.file.Path;

import seedu.address.logic.commands.ExportLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input into a ExportLeaveCommand
 */
public class ExportLeaveCommandParser extends ExportCommandParser implements Parser<ExportLeaveCommand> {

    @Override
    public ExportLeaveCommand parse(String userInput) throws ParseException {
        Path fileName = parseFileName(userInput, ExportLeaveCommand.MESSAGE_USAGE);
        return new ExportLeaveCommand(fileName);
    }
}
