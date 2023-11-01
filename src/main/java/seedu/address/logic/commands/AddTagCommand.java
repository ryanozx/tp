package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_TAG;
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
 * Adds a tag to an employee in the address book
 */
public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "add-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds tags to the employee identified "
        + "by the index number used in the displayed employee list.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + PREFIX_PERSON_TAG + "TAG...\n"
        + "Example: " + COMMAND_WORD + " 1 "
        + PREFIX_PERSON_TAG + "full time";

    public static final String MESSAGE_NO_TAGS_ADDED = "At least one tag must be provided.";
    public static final String MESSAGE_DUPLICATE_TAG = "This employee already has some of the tags.";
    public static final String MESSAGE_ADD_TAG_SUCCESS = "Edited Employee : %1$s";

    private final Index index;
    private final Collection<Tag> tagsToAdd;

    /**
     * @param index of the employee in the filtered employee list to add tags to
     * @param tagsToAdd tags to add to the employee
     */
    public AddTagCommand(Index index, Collection<Tag> tagsToAdd) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);

        this.index = index;
        this.tagsToAdd = new HashSet<>(tagsToAdd);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (personToEdit.hasAnyTags(tagsToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        Person editedPerson = new Person(personToEdit);
        editedPerson.addTags(tagsToAdd);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);


        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        AddTagCommand otherAddTagCommand = (AddTagCommand) other;
        return index.equals(otherAddTagCommand.index) && tagsToAdd.equals(otherAddTagCommand.tagsToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("tagsToAdd", tagsToAdd)
                .toString();
    }
}
