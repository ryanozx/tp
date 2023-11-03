package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEAVES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.leave.LeaveContainsPersonPredicate;
import seedu.address.model.person.Person;

/**
 * Find Leaves with the exact employee of the given index
 */
public class FindLeaveCommand extends Command {
    public static final String COMMAND_WORD = "find-leave";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all leaves whose employee matches "
            + "the given index of the employee and displays them as a list with index numbers.\n"
            + "Parameters: INDEX (must be a positive integer within in the range of the Employee List)\n"
            + "Example: " + COMMAND_WORD + " "
            + "1";

    private final Index index;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * @param index the corresponding employee of the index to match with employees
     */
    public FindLeaveCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> personList = model.getFilteredPersonList();

        if (index.getZeroBased() >= personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LEAVE_INDEX);
        }

        Person employee = personList.get(index.getZeroBased());
        LeaveContainsPersonPredicate predicate = new LeaveContainsPersonPredicate(employee);
        model.updateFilteredLeaveList(PREDICATE_SHOW_ALL_LEAVES);
        model.updateFilteredLeaveList(predicate);

        logger.info("predicate: " + predicate); //dummy logger

        return new CommandResult(
                String.format(Messages.MESSAGE_LEAVES_LISTED_OVERVIEW, model.getFilteredLeaveList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindLeaveCommand)) {
            return false;
        }

        FindLeaveCommand otherFindLeaveCommand = (FindLeaveCommand) other;
        return index.equals(otherFindLeaveCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}

