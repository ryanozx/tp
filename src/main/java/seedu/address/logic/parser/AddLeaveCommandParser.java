package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Date;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddLeaveCommandParser implements Parser<AddLeaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLeaveCommand parse(String args) throws ParseException {
        Index index;

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DATE_START,
                        PREFIX_DATE_END, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_DATE_START,
                PREFIX_DATE_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_DATE_START,
                PREFIX_DATE_END, PREFIX_DESCRIPTION);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE), pe);
        }

        String title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Date dateStart = ParserUtil.parseDateStart(argMultimap.getValue(PREFIX_DATE_START).get());
        Date dateEnd = ParserUtil.parseDateEnd(argMultimap.getValue(PREFIX_DATE_END).get(),
                argMultimap.getValue(PREFIX_DATE_START).get());

        String description = "none";
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }
        return new AddLeaveCommand(index, title, dateStart, dateEnd, description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
