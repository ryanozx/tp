package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteLeaveCommand object
 */
public class DeleteLeaveCommandParser implements Parser<DeleteLeaveCommand> {

    /**
     * Parses the given {@code String} of arguments inthe context of the DeleteLeaveCommand
     * and returns a DeleteLeaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLeaveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteLeaveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLeaveCommand.MESSAGE_USAGE), pe);
        }
    }
}
