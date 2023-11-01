package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.parseIndex;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ApproveLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ApproveLeave object
 */
public class ApproveLeaveCommandParser implements Parser<ApproveLeaveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ApproveLeaveCommand
     * and returns an ApproveLeaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ApproveLeaveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;

        try {
            index = parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ApproveLeaveCommand.MESSAGE_USAGE), pe);
        }

        return new ApproveLeaveCommand(index);
    }
}
