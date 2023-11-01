package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all employees whose tags match all "
            + "the specified tags exactly (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " t/full time t/remote\n";

    private final TagsContainAllTagsPredicate predicate;
    private final Logger logger = LogsCenter.getLogger(getClass());

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
        logger.info("predicate: " + this.predicate); //dummy logger

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
