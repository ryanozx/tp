package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.TagsContainSomeTagsPredicate;

/**
 * Find Persons with some specified tags
 */
public class FindSomeTagCommand extends Command {

    public static final String COMMAND_WORD = "find-some-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all employees whose tags match some "
            + "specified tags (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: t/TAG [t/MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD
            + " t/full time t/remote\n";

    private final TagsContainSomeTagsPredicate predicate;

    /**
     * @param predicate tags to match with employees
     */
    public FindSomeTagCommand(TagsContainSomeTagsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindSomeTagCommand)) {
            return false;
        }

        FindSomeTagCommand otherFindSomeTagCommand = (FindSomeTagCommand) other;
        return predicate.equals(otherFindSomeTagCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

}
