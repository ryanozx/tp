package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_LEAVES_LISTED_OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.leave.LeaveInPeriodPredicate;

/**
 * Returns a list of leaves that coincide with one or more days of a given period.
 */
public class FindLeaveByPeriodCommand extends Command {
    public static final String COMMAND_WORD = "find-leave-range";
    public static final String MESSAGE_USAGE = "Finds all leaves that happen within the queried range."
            + "The start and end dates are optional - if none are supplied, all leaves are returned."
            + "If only one of them is supplied, all leaves that end on and after the queried start date are"
            + " returned (if the start date is supplied) or all leaves that start before and on the queried"
            + " end date are returned (if the end date is supplied).\n"
            + "Parameters: "
            + "[" + PREFIX_LEAVE_DATE_START + "START_DATE] "
            + "[" + PREFIX_LEAVE_DATE_END + "END_DATE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LEAVE_DATE_START + "2023-10-30" + " "
            + PREFIX_LEAVE_DATE_END + "2023-10-31";

    private final LeaveInPeriodPredicate predicate;


    /**
     * Constructs a FindLeaveByPeriodCommand for query for all leaves that occur in a given period
     * @param predicate Predicate containing period that leaves should coincide with
     */
    public FindLeaveByPeriodCommand(LeaveInPeriodPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLeaveList(predicate);

        return new CommandResult(
                String.format(MESSAGE_LEAVES_LISTED_OVERVIEW, model.getFilteredLeaveList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof FindLeaveByPeriodCommand)) {
            return false;
        }
        FindLeaveByPeriodCommand otherCommand = (FindLeaveByPeriodCommand) other;
        return this.predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
