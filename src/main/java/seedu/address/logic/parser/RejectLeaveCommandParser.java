package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.ParserUtil.parseIndex;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RejectLeaveCommand;
import seedu.address.logic.parser.exceptions.InvalidIndexException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RejectLeaveCommand object
 */
public class RejectLeaveCommandParser implements Parser<RejectLeaveCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RejectLeaveCommand
     * and returns an RejectLeaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RejectLeaveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;

        try {
            index = parseIndex(args);
        } catch (InvalidIndexException iie) {
            throw new ParseException(MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RejectLeaveCommand.MESSAGE_USAGE), pe);
        }

        return new RejectLeaveCommand(index);
    }
}
