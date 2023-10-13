package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.FindSomeTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagsContainSomeTagsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindSomeTagCommand object
 */
public class FindSomeTagCommandParser implements Parser<FindSomeTagCommand> {
    public static final String MESSAGE_INVALID_TAG = "Tags names should be alphanumeric.";
    /**
     * Parses the given {@code String} of arguments in the context of the FindSomeTagCommand
     * and returns a FindSomeTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSomeTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSomeTagCommand.MESSAGE_USAGE));
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

        return new FindSomeTagCommand(new TagsContainSomeTagsPredicate(tagList));
    }

}
