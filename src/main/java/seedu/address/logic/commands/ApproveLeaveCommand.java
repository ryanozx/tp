package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Status;
import seedu.address.model.person.ComparablePerson;

/**
 * Approves an existing leave in the leave book.
 */
public class ApproveLeaveCommand extends Command {
    public static final String COMMAND_WORD = "approve-leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Approves leave request identified "
            + "by the index number used in the leave book. "
            + "The specified leave request will be approved.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_APPROVE_LEAVE_SUCCESS = "Approved Leave: %1$s";
    public static final String MESSAGE_DUPLICATE_LEAVE_APPROVE = "Leave previously approved: %1$s";

    private final Index index;

    /**
     * @param index of the leave in the leave book to approve
     */
    public ApproveLeaveCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Leave> leaveList = model.getLeavesBook().getLeaveList();

        if (index.getZeroBased() >= leaveList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEAVE_INDEX);
        }

        Leave leaveToApprove = leaveList.get(index.getZeroBased());

        if (leaveToApprove.getStatus().equals("APPROVED")) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_LEAVE_APPROVE, Messages.format(leaveToApprove)));
        }

        Leave approvedLeave = createApprovedLeave(leaveToApprove);

        model.setLeave(leaveToApprove, approvedLeave);
        return new CommandResult(String.format(MESSAGE_APPROVE_LEAVE_SUCCESS, Messages.format(approvedLeave)));
    }

    private static Leave createApprovedLeave(Leave leaveToApprove) {
        assert leaveToApprove != null;

        ComparablePerson employee = leaveToApprove.getEmployee();
        String title = leaveToApprove.getTitle();
        String description = leaveToApprove.getDescription();
        Date start = leaveToApprove.getStart();
        Date end = leaveToApprove.getEnd();
        Status approvedStatus = Status.of("APPROVED");

        return new Leave(employee, title, start, end, description, approvedStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ApproveLeaveCommand)) {
            return false;
        }

        ApproveLeaveCommand otherApproveLeaveCommand = (ApproveLeaveCommand) other;
        return index.equals(otherApproveLeaveCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
