package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_LEAVES_LISTED_OVERVIEW;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.leave.LeaveHasStatusPredicate;

/**
 * Returns a list of leaves that have a particular status
 */
public class FindLeaveByStatusCommand extends Command {
    public static final String COMMAND_WORD = "find-leave-status";
    public static final String MESSAGE_USAGE = "Finds all leaves that contain the given status."
            + "Parameters: STATUS"
            + "Example: " + COMMAND_WORD + " "
            + "APPROVED";
    public static final String MESSAGE_FAILED = "Command should only contain one of the following words: "
            + "APPROVED / PENDING / REJECTED";
    private final LeaveHasStatusPredicate predicate;

    /**
     * Constructs a FindLeaveByStatusCommand object
     * @param predicate Predicate containing status that leaves should have
     */
    public FindLeaveByStatusCommand(LeaveHasStatusPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredLeaveList(predicate);

        return new CommandResult(
                String.format(MESSAGE_LEAVES_LISTED_OVERVIEW, model.getFilteredLeaveList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FindLeaveByStatusCommand)) {
            return false;
        }

        FindLeaveByStatusCommand otherCommand = (FindLeaveByStatusCommand) other;
        return this.predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
