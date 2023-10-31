package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

/**
 * Adds an employee to the address book.
 */
public class AddLeaveCommand extends Command {

    public static final String COMMAND_WORD = "add-leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a leave of an employee to the leave book. "
            + "Parameters: "
            + "INDEX "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_DATE_START + "DATE START "
            + PREFIX_DATE_END + "DATE END "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_TITLE + "John's Paternity Leave "
            + PREFIX_DATE_START + "2023-10-28 "
            + PREFIX_DATE_END + "2023-10-29 "
            + PREFIX_DESCRIPTION + "John's Paternity Leave Description ";

    public static final String MESSAGE_SUCCESS = "New leave is added : %1$s";
    public static final String MESSAGE_DUPLICATE_LEAVE = "This leave has already exists for the employee";

    private Leave toAdd;

    private final Index index;
    private final String title;
    private final Date dateStart;
    private final Date dateEnd;
    private final String description;
    /**
     * Creates an AddLeaveCommand to add the specified {@code Leave}
     */
    public AddLeaveCommand(Index index, String title, Date dateStart, Date dateEnd, String description) {
        this.index = index;
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
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

        toAdd = new Leave(personToEdit, title, dateStart, dateEnd, description);

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

        AddLeaveCommand otherAddCommand = (AddLeaveCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
