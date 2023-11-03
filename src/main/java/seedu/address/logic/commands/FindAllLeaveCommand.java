package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEAVES;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;

/**
 * Find all leaves in the leave list.
 */
public class FindAllLeaveCommand extends Command {
    public static final String COMMAND_WORD = "find-all-leave";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all leaves available in the leave list.\n";
    public static final String MESSAGE_FIND_LEAVE_NONE = "There are currently no leave requests.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLeaveList(PREDICATE_SHOW_ALL_LEAVES);
        int leaveSize = model.getFilteredLeaveList().size();

        logger.info(""); //dummy logger

        if (leaveSize == 0) {
            return new CommandResult(MESSAGE_FIND_LEAVE_NONE);
        }

        String leave = "Current # of Leave Request(s):" + leaveSize;
        return new CommandResult(leave);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
