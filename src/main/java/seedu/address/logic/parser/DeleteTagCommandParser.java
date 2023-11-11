package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_TAG;

import java.util.Collection;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.InvalidIndexException;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteTagCommand Object
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns an DeleteTagCommand object for execution.
     *
     * @param args arguments to be parsed
     * @return DeleteTagCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PERSON_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (InvalidIndexException iie) {
            throw new ParseException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE), pe);
        }

        Collection<String> tags = argMultimap.getAllValues(PREFIX_PERSON_TAG);

        if (isTagsEmpty(tags)) {
            throw new ParseException(DeleteTagCommand.MESSAGE_NO_TAGS_REMOVED);
        }

        return new DeleteTagCommand(index, ParserUtil.parseTags(tags));
    }

    private boolean isTagsEmpty(Collection<String> tags) throws ParseException {
        assert tags != null;

        return tags.isEmpty() || (tags.size() == 1 && tags.contains(""));

    }

}
