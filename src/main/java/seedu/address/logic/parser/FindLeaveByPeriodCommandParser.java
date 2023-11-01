package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_START;

import seedu.address.logic.commands.FindLeaveByPeriodCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.LeaveInPeriodPredicate;

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
                ArgumentTokenizer.tokenize(userInput, PREFIX_LEAVE_START, PREFIX_LEAVE_END);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LEAVE_START, PREFIX_LEAVE_END);

        Date startDate = argMultimap.getValue(PREFIX_LEAVE_START)
                .map(Date::of)
                .orElse(null);
        Date endDate = argMultimap.getValue(PREFIX_LEAVE_END)
                .map(Date::of)
                .orElse(null);

        LeaveInPeriodPredicate predicate = new LeaveInPeriodPredicate(startDate, endDate);
        return new FindLeaveByPeriodCommand(predicate);
    }
}
