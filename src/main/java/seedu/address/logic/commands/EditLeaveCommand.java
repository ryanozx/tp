package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
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
import seedu.address.model.leave.exceptions.DuplicateLeaveException;
import seedu.address.model.leave.exceptions.EndBeforeStartException;

/**
 * Edits the details of an existing leave in the leave book.
 */
public class EditLeaveCommand extends Command {

    public static final String COMMAND_WORD = "edit-leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the leave identified "
            + "by the index number used in the displayed leave list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameter: INDEX (must be a positive integer) "
            + "[" + PREFIX_LEAVE_TITLE + "TITLE] "
            + "[" + PREFIX_LEAVE_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_LEAVE_DATE_START + "START] "
            + "[" + PREFIX_LEAVE_DATE_END + "END]"
            + "[" + PREFIX_LEAVE_STATUS + "STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LEAVE_TITLE + "medical leave "
            + PREFIX_LEAVE_DATE_START + "2023-10-23";

    public static final String MESSAGE_EDIT_LEAVE_SUCCESS = "Edited Leave: %1$s";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    public static final String MESSAGE_DUPLICATED_LEAVE =
        "Leave entry with matching employee, start date and end already exists";

    private final Index index;
    private final EditLeaveDescriptor editLeaveDescriptor;

    /**
     * @param index of the leave in the filtered leave list to edit
     * @param editLeaveDescriptor details to edit the leave with
     */
    public EditLeaveCommand(Index index, EditLeaveDescriptor editLeaveDescriptor) {
        requireNonNull(index);
        requireNonNull(editLeaveDescriptor);

        this.index = index;
        this.editLeaveDescriptor = editLeaveDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Leave> lastShownList = model.getFilteredLeaveList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_LEAVE_DISPLAYED_INDEX);
        }

        Leave leaveToEdit = lastShownList.get(index.getZeroBased());
        try {
            Leave editedLeave = createEditedLeave(leaveToEdit, editLeaveDescriptor);
            model.setLeave(leaveToEdit, editedLeave);
            return new CommandResult(String.format(MESSAGE_EDIT_LEAVE_SUCCESS, Messages.format(editedLeave)));
        } catch (EndBeforeStartException ebse) {
            throw new CommandException(Range.MESSAGE_END_BEFORE_START_ERROR);
        } catch (DuplicateLeaveException dle) {
            throw new CommandException(MESSAGE_DUPLICATED_LEAVE);
        }
    }

    private static Leave createEditedLeave(Leave leaveToEdit, EditLeaveDescriptor editLeaveDescriptor)
            throws EndBeforeStartException {
        assert leaveToEdit != null;

        Title updatedTitle = editLeaveDescriptor.getTitle().orElse(leaveToEdit.getTitle());
        Description updatedDescription = editLeaveDescriptor.getDescription().orElse(leaveToEdit.getDescription());

        Date startDate = editLeaveDescriptor.getStart().orElse(leaveToEdit.getStart());
        Date endDate = editLeaveDescriptor.getEnd().orElse(leaveToEdit.getEnd());
        Range updatedRange = Range.createNonNullRange(startDate, endDate);
        Status updatedStatus = editLeaveDescriptor.getStatus().orElse(leaveToEdit.getStatus());

        return new Leave(leaveToEdit.getEmployee(), updatedTitle, updatedRange, updatedDescription, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditLeaveCommand)) {
            return false;
        }

        EditLeaveCommand otherEditLeaveCommand = (EditLeaveCommand) other;
        return index.equals(otherEditLeaveCommand.index)
            && editLeaveDescriptor.equals(otherEditLeaveCommand.editLeaveDescriptor);
    }

    /**
     * Stores the deatils to edit the leave with. Each non-empty field value will replace the
     * corresponsing field value of the leave.
     */
    public static class EditLeaveDescriptor {

        private Title title;
        private Description description;
        private Date start;
        private Date end;
        private Status status;

        public EditLeaveDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditLeaveDescriptor(EditLeaveDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setStart(toCopy.start);
            setEnd(toCopy.end);
            setStatus(toCopy.status);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, description, start, end, status);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setStart(Date start) throws EndBeforeStartException {
            boolean isStartAfterEnd = this.end != null && start.isAfter(this.end);
            if (isStartAfterEnd) {
                throw new EndBeforeStartException();
            }
            this.start = start;
        }

        public Optional<Date> getStart() {
            return Optional.ofNullable(start);
        }

        public void setEnd(Date end) throws EndBeforeStartException {
            boolean isEndBeforeStart = this.start != null && end.isBefore(this.start);
            if (isEndBeforeStart) {
                throw new EndBeforeStartException();
            }
            this.end = end;
        }

        public Optional<Date> getEnd() {
            return Optional.ofNullable(end);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditLeaveDescriptor)) {
                return false;
            }

            EditLeaveDescriptor otherEditLeaveDescriptor = (EditLeaveDescriptor) other;
            return Objects.equals(title, otherEditLeaveDescriptor.title)
                && Objects.equals(description, otherEditLeaveDescriptor.description)
                && Objects.equals(start, otherEditLeaveDescriptor.start)
                && Objects.equals(end, otherEditLeaveDescriptor.end)
                && Objects.equals(status, otherEditLeaveDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("title", title)
                    .add("description", description)
                    .add("start", start)
                    .add("end", end)
                    .add("status", status)
                    .toString();
        }
    }
}
