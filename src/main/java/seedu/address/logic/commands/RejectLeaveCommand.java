package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Title;
import seedu.address.model.person.ComparablePerson;

/**
 * Rejects an existing leave in the leave book.
 */
public class RejectLeaveCommand extends Command {
    public static final String COMMAND_WORD = "reject-leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reject leave request identified "
            + "by the index number used in the leave book. "
            + "The specified leave request will be rejected.\n"
            + "Parameters: INDEX (must be a positive integer within in the range of the Leave List)\n) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_REJECT_LEAVE_SUCCESS = "Rejected Leave: %1$s";
    public static final String MESSAGE_DUPLICATE_LEAVE_REJECT = "Leave previously rejected: %1$s";

    private final Index index;

    /**
     * @param index of the leave in the leave book to reject
     */
    public RejectLeaveCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Leave> leaveList = model.getFilteredLeaveList();

        if (index.getZeroBased() >= leaveList.size()) {
            throw new CommandException(MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
        }

        Leave leaveToReject = leaveList.get(index.getZeroBased());

        if (leaveToReject.getStatus().getStatusType().equals(Status.StatusType.REJECTED)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_LEAVE_REJECT, Messages.format(leaveToReject)));
        }

        Leave rejectedLeave = createRejectedLeave(leaveToReject);

        model.setLeave(leaveToReject, rejectedLeave);
        return new CommandResult(String.format(MESSAGE_REJECT_LEAVE_SUCCESS, Messages.format(rejectedLeave)));
    }

    private static Leave createRejectedLeave(Leave leaveToReject) {
        assert leaveToReject != null;

        ComparablePerson employee = leaveToReject.getEmployee();
        Title title = leaveToReject.getTitle();
        Description description = leaveToReject.getDescription();
        Date start = leaveToReject.getStart();
        Date end = leaveToReject.getEnd();
        Range dateRange = Range.createNonNullRange(start, end);
        Status rejectedStatus = Status.of(Status.StatusType.REJECTED);

        return new Leave(employee, title, dateRange, description, rejectedStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RejectLeaveCommand)) {
            return false;
        }

        RejectLeaveCommand otherRejectedLeaveCommand = (RejectLeaveCommand) other;
        return index.equals(otherRejectedLeaveCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
