package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes tags from an existing employee in the address book.
 */
public class DeleteTagCommand extends Command {
    public static final String COMMAND_WORD = "delete-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete tags from the employee identified "
        + "by the index number used in the displayed employee list.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + PREFIX_TAG + "TAG...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_TAG + "remote";

    public static final String MESSAGE_NO_TAGS_REMOVED = "At least one tag must be provided.";
    public static final String MESSAGE_DUPLICATE_TAG = "Some of the tags are not found on this employee.";
    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Edited Employee : %1$s";

    private final Index index;
    private final Collection<Tag> tagsToRemove;

    /**
     * @param index of the employee in the filtered employee list to remove tags from
     * @param tags tags to remove from the employee
     */
    public DeleteTagCommand(Index index, Collection<Tag> tags) {
        requireNonNull(index);
        requireNonNull(tags);

        this.index = index;
        this.tagsToRemove = new HashSet<>(tags);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (!personToEdit.hasAllTags(tagsToRemove)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        Person editedPerson = new Person(personToEdit);
        editedPerson.removeTags(tagsToRemove);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);


        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS, Messages.format(editedPerson)));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        DeleteTagCommand otherAddTagCommand = (DeleteTagCommand) other;
        return index.equals(otherAddTagCommand.index) && tagsToRemove.equals(otherAddTagCommand.tagsToRemove);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("tagsToAdd", tagsToRemove)
                .toString();
    }

}
