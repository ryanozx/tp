package seedu.address.logic.parser;

import seedu.address.logic.commands.FindAllTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagsContainAllTagsPredicate;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.List;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindAllTagCommand object
 */
public class FindAllTagCommandParser implements Parser<FindAllTagCommand> {
    public static final String MESSAGE_INVALID_TAG = "Tags names should be alphanumeric.";
    /**
     * Parses the given {@code String} of arguments in the context of the FindAllTagCommand
     * and returns a FindAllTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindAllTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAllTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        List<Tag> tagList = new ArrayList<>();
        for (String keyword : tagKeywords) {
            try {
                Tag tag = new Tag(keyword);
                tagList.add(tag);
            } catch (IllegalArgumentException ie) {
                throw new ParseException(MESSAGE_INVALID_TAG, ie);
            }
        }

        return new FindAllTagCommand(new TagsContainAllTagsPredicate(tagList));
    }

}
