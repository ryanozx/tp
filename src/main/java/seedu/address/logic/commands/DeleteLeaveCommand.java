package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Leave;

/**
  * Deletes a Leave identified using it's displayed index from the address book.
  */
public class DeleteLeaveCommand extends Command {

    public static final String COMMAND_WORD = "delete-leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the leave identified by the index number used in the displayed leave list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_LEAVE_SUCCESS = "Deleted Leave: %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the leave in the filtered leave list to delete
     */
    public DeleteLeaveCommand(Index targetIndex) {
        requireNonNull(targetIndex);

        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Leave> lastShownList = model.getFilteredLeaveList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
        }

        Leave leaveToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteLeave(leaveToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_LEAVE_SUCCESS, Messages.format(leaveToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteLeaveCommand)) {
            return false;
        }

        DeleteLeaveCommand otherDeleteLeaveCommand = (DeleteLeaveCommand) other;

        return targetIndex.equals(otherDeleteLeaveCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
