package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * View all tags available in alphabetically order.
 */
public class ViewTagCommand extends Command {
    public static final String COMMAND_WORD = "view-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all tags available in alphabetically order.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_VIEW_TAG_NONE = "There are currently no tags";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Person> people = model.getFilteredPersonList();
        ArrayList<String> tagsAll = new ArrayList<>();

        if (people.isEmpty()) {
            return new CommandResult(MESSAGE_VIEW_TAG_NONE);
        }

        String tags = "Available tag(s):" + "\n";

        for (Person person : people) {
            Set<Tag> temp = person.getTags();
            for (Tag t : temp) {
                if (!tagsAll.contains(t.toString())) {
                    tagsAll.add(t.toString());
                }
            }
        }

        Collections.sort(tagsAll);

        int len = tagsAll.size();
        for (String s : tagsAll) {
            tags = tags + s + "\n";
        }
        return new CommandResult(tags);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}

