package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLeaveCommand;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

public class EditLeaveCommandParser implements Parser<EditLeaveCommand> {

    public EditLeaveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = 
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_START, PREFIX_END);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLeaveCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TITLE, PREFIX_DESCRIPTION, PREFIX_START, PREFIX_END);

        EditLeaveDescriptor editLeaveDescriptor = new EditLeaveDescriptor();

        argMultimap.getValue(PREFIX_TITLE).ifPresent(editLeaveDescriptor::setTitle);
        argMultimap.getValue(PREFIX_DESCRIPTION).ifPresent(editLeaveDescriptor::setDescription);

        if(argMultimap.getValue(PREFIX_START).isPresent()) {
            editLeaveDescriptor.setStart(ParserUtil.parseDate(argMultimap.getValue(PREFIX_START)));
        }
        argMultimap.getValue(PREFIX_START).ifPresent((s) -> editLeaveDescriptor.setStart(ParserUtil.parseDate(s)));
        argMultimap.getValue(PREFIX_END).ifPresent((s) -> editLeaveDescriptor.setEnd(ParserUtil.parseDate(s)));

        if (!editLeaveDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLeaveCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLeaveCommand(index, editLeaveDescriptor);
    }
}
