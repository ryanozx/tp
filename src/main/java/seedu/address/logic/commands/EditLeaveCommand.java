package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

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
import seedu.address.model.leave.Leave;

public class EditLeaveCommand extends Command {

    public static final String COMMAND_WORD = "edit-leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the leave identified "
            + "by the index number used in the displayed leave list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameter: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_START + "START] "
            + "[" + PREFIX_END + "END]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + "medical leave"
            + PREFIX_START + "2023-10-23";

    public static final String MESSAGE_EDIT_LEAVE_SUCCESS = "Edited Leave: %1$s";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditLeaveDescriptor editLeaveDescriptor;

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
            throw new CommandException(Messages.MESSAGE_INVALID_LEAVE_INDEX);
        }

        Leave leaveToEdit = lastShownList.get(index.getZeroBased());
        Leave editedLeave = createEditedLeave(leaveToEdit, editLeaveDescriptor);

        model.setLeave(leaveToEdit, editedLeave);

        return new CommandResult(String.format(MESSAGE_EDIT_LEAVE_SUCCESS, Messages.format(editedLeave)));
    }

    private static Leave createEditedLeave(Leave leaveToEdit, EditLeaveDescriptor editLeaveDescriptor) {
        assert leaveToEdit != null;

        String updatedTitle = editLeaveDescriptor.getTitle().orElse(leaveToEdit.getTitle());
        String updatedDescription = editLeaveDescriptor.getDescription().orElse(leaveToEdit.getDescription());
        Date updatedStart = editLeaveDescriptor.getStart().orElse(leaveToEdit.getStart());
        Date updatedEnd = editLeaveDescriptor.getEnd().orElse(leaveToEdit.getEnd());

        return new Leave(leaveToEdit.getEmployee(), updatedTitle, updatedStart, updatedEnd, updatedDescription, leaveToEdit.getStatusEnum());
    }

    public static class EditLeaveDescriptor {

        private String title;
        private String description;
        private Date start;
        private Date end;

        public EditLeaveDescriptor() {}

        public EditLeaveDescriptor(EditLeaveDescriptor toCopy) {
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setStart(toCopy.start);
            setEnd(toCopy.end);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, description, start, end);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Optional<String> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Optional<Date> getStart() {
            return Optional.ofNullable(start);
        }

        public void setEnd(Date end) {
            this.end = end;
        }

        public Optional<Date> getEnd() {
            return Optional.ofNullable(end);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if(!(other instanceof EditLeaveDescriptor)) {
                return false;
            }

            EditLeaveDescriptor otherEditleaveDescriptor = (EditLeaveDescriptor) other;
            return Objects.equals(title, otherEditleaveDescriptor.title) 
                && Objects.equals(description, otherEditleaveDescriptor.description)
                && Objects.equals(start, otherEditleaveDescriptor.start)
                && Objects.equals(end, otherEditleaveDescriptor.end);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("title", title)
                    .add("description", description)
                    .add("start", start)
                    .add("end", end)
                    .toString();
        }
    }
}
