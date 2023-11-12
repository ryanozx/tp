package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_NO_STATUS_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Title;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DATE_START, PREFIX_LEAVE_DATE_END,
                PREFIX_LEAVE_DESCRIPTION, PREFIX_LEAVE_STATUS);
        if (!arePrefixesPresent(argMultimap, PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DATE_START,
                PREFIX_LEAVE_DATE_END)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DATE_START,
                PREFIX_LEAVE_DATE_END, PREFIX_LEAVE_DESCRIPTION);

        checkNoStatusProvided(argMultimap);

        Index index = extractIndexFromInput(argMultimap);
        Title title = extractTitleFromInput(argMultimap);
        Range dateRange = extractRangeFromInput(argMultimap);
        Description description = extractDescriptionFromInput(argMultimap);

        return new AddLeaveCommand(index, title, dateRange, description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Verifies that the user does not add a status to the command, which previously caused an
     * uncaught exception that resulted in the app being non-responsive.
     * @param argMultimap Multimap containing command arguments.
     * @throws ParseException if a status was provided in the command.
     */
    private void checkNoStatusProvided(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_LEAVE_STATUS)) {
            throw new ParseException(String.format(MESSAGE_NO_STATUS_PREFIX, AddLeaveCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the index from the preamble in the multimap.
     * @param argMultimap Multimap containing command arguments.
     * @return Index parsed from preamble.
     * @throws ParseException if the preamble does not contain a valid index.
     */
    private Index extractIndexFromInput(ArgumentMultimap argMultimap) throws ParseException {
        // Note: since ParserUtil::parseIndex can also throw InvalidIndexException for numeric values that are not
        // positive Integer values, future work could include catching this exception, which inherits from
        // ParseException, and having it throw a different message instead
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLeaveCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the title from the command.
     * @param argMultimap Multimap containing command arguments.
     * @return Parsed Title instance.
     * @throws ParseException if the title provided is not valid.
     */
    private Title extractTitleFromInput(ArgumentMultimap argMultimap) throws ParseException {
        // this assertion should be true as we already checked for prefix existence previously
        assert(argMultimap.getValue(PREFIX_LEAVE_TITLE).isPresent());
        return ParserUtil.parseTitle(argMultimap.getValue(PREFIX_LEAVE_TITLE).get());
    }

    /**
     * Parses the range from the command.
     * @param argMultimap Multimap containing command arguments.
     * @return Parsed Range instance.
     * @throws ParseException if the start and end dates provided are not valid.
     */
    private Range extractRangeFromInput(ArgumentMultimap argMultimap) throws ParseException {
        assert(argMultimap.getValue(PREFIX_LEAVE_DATE_START).isPresent()
                && argMultimap.getValue(PREFIX_LEAVE_DATE_END).isPresent());
        return ParserUtil.parseNonNullRange(argMultimap.getValue(PREFIX_LEAVE_DATE_START).get(),
                argMultimap.getValue(PREFIX_LEAVE_DATE_END).get());
    }

    /**
     * Parses the description from the command if it is provided. Otherwise, the description will be
     * replaced with a placeholder value (NULL).
     * @param argMultimap Multimap containing command arguments.
     * @return Parsed Description instance if provided in the command, otherwise a Description instance
     *      containing a placeholder value.
     * @throws ParseException if a description was provided in the command, but is invalid.
     */
    private Description extractDescriptionFromInput(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> descriptionValue = argMultimap.getValue(PREFIX_LEAVE_DESCRIPTION);
        if (descriptionValue.isPresent()) {
            return ParserUtil.parseDescription(descriptionValue.get());
        } else {
            return new Description(Description.DESCRIPTION_PLACEHOLDER);
        }
    }
}
