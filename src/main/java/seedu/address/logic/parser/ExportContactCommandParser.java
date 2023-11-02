package seedu.address.logic.parser;

import java.nio.file.Path;

import seedu.address.logic.commands.ExportContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input into a ExportContactCommand
 */
public class ExportContactCommandParser extends ExportCommandParser implements Parser<ExportContactCommand> {

    @Override
    public ExportContactCommand parse(String userInput) throws ParseException {
        Path fileName = parseFileName(userInput, ExportContactCommand.MESSAGE_USAGE);
        return new ExportContactCommand(fileName);
    }
}
