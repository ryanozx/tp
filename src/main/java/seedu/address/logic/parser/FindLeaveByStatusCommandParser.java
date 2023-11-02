package seedu.address.logic.parser;

import seedu.address.logic.commands.FindLeaveByStatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.LeaveHasStatusPredicate;
import seedu.address.model.leave.Status;

/**
 * Parses input arguments and creates a FindLeaveByStatusCommand object
 */
public class FindLeaveByStatusCommandParser implements Parser<FindLeaveByStatusCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindLeaveByStatusCommand
     * and returns a FindLeaveByPeriod object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindLeaveByStatusCommand parse(String userInput) throws ParseException {
        try {
            // trims the input and is case-agnostic
            String trimmedUpperCaseInput = userInput.trim().toUpperCase();
            LeaveHasStatusPredicate predicate = new LeaveHasStatusPredicate(
                    Status.of(trimmedUpperCaseInput)
            );
            return new FindLeaveByStatusCommand(predicate);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ParseException(FindLeaveByStatusCommand.MESSAGE_FAILED);
        }
    }
}
