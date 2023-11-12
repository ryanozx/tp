package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Leave;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.Title;
import seedu.address.model.person.Person;

/**
 * Adds an employee to the address book.
 */
public class AddLeaveCommand extends Command {

    public static final String COMMAND_WORD = "add-leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a leave of an employee to the leave book. "
            + "Parameters: "
            + "INDEX "
            + PREFIX_LEAVE_TITLE + "TITLE "
            + PREFIX_LEAVE_DATE_START + "DATE START "
            + PREFIX_LEAVE_DATE_END + "DATE END "
            + "[" + PREFIX_LEAVE_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_LEAVE_TITLE + "John's Paternity Leave "
            + PREFIX_LEAVE_DATE_START + "2023-10-28 "
            + PREFIX_LEAVE_DATE_END + "2023-10-29 "
            + PREFIX_LEAVE_DESCRIPTION + "John's Paternity Leave Description [OPTIONAL] ";

    public static final String MESSAGE_SUCCESS = "New leave is added : %1$s";
    public static final String MESSAGE_DUPLICATE_LEAVE = "This leave has already exists for the employee";

    private Leave toAdd;

    private final Index index;
    private final Title title;
    private final Range dateRange;
    private final Description description;

    /**
     * Creates an AddLeaveCommand to add the specified {@code Leave}
     */
    public AddLeaveCommand(Index index, Title title, Range dates, Description description) {
        requireNonNull(index);
        requireNonNull(title);
        requireNonNull(dates);
        requireNonNull(description);
        this.index = index;
        this.title = title;
        this.dateRange = dates;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        toAdd = new Leave(personToEdit, title, dateRange, description);

        if (model.hasLeave(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LEAVE);
        }

        model.addLeave(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLeaveCommand)) {
            return false;
        }

        AddLeaveCommand otherAddLeaveCommand = (AddLeaveCommand) other;
        return index.equals(otherAddLeaveCommand.index) && title.equals(otherAddLeaveCommand.title)
                && dateRange.equals(otherAddLeaveCommand.dateRange)
                && description.equals(otherAddLeaveCommand.description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("title", title)
                .add("description", description)
                .add("start", dateRange.getStartDate().get())
                .add("end", dateRange.getEndDate().get())
                .toString();
    }
}
