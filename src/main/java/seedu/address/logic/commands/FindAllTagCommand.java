package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.TagsContainAllTagsPredicate;

/**
 * Find Persons with the exact tags
 */
public class FindAllTagCommand extends Command {

    public static final String COMMAND_WORD = "find-all-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags match all "
            + "the specified tags exactly (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD
            + " fullTime remote\n";

    private final TagsContainAllTagsPredicate predicate;

    /**
     * @param predicate tags to match with employees
     */
    public FindAllTagCommand(TagsContainAllTagsPredicate predicate) {
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
        if (!(other instanceof FindAllTagCommand)) {
            return false;
        }

        FindAllTagCommand otherFindAllTagCommand = (FindAllTagCommand) other;
        return predicate.equals(otherFindAllTagCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

}
