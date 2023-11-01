package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;

import seedu.address.logic.commands.FindLeaveByPeriodCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.LeaveInPeriodPredicate;
import seedu.address.model.leave.Range;

/**
 * Parses input arguments and creates a FindLeaveByPeriodCommand object
 */
public class FindLeaveByPeriodCommandParser implements Parser<FindLeaveByPeriodCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindLeaveByPeriodCommand
     * and returns a FindLeaveByPeriod object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindLeaveByPeriodCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_LEAVE_DATE_START, PREFIX_LEAVE_DATE_END);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LEAVE_DATE_START, PREFIX_LEAVE_DATE_END);

        String startDate = argMultimap.getValue(PREFIX_LEAVE_DATE_START).orElse(null);
        String endDate = argMultimap.getValue(PREFIX_LEAVE_DATE_END).orElse(null);

        Range dateRange = ParserUtil.parseNullableRange(startDate, endDate);

        LeaveInPeriodPredicate predicate = new LeaveInPeriodPredicate(dateRange);
        return new FindLeaveByPeriodCommand(predicate);
    }
}
