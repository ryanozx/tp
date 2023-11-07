package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;

import java.time.format.DateTimeParseException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLeaveCommand;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Date;
import seedu.address.model.leave.Description;
import seedu.address.model.leave.Status;
import seedu.address.model.leave.Title;

/**
 * Parses input arguments and creates a new EditLeaveCommand object
 */
public class EditLeaveCommandParser implements Parser<EditLeaveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditLeaveCommand
     * and returns an EditLeaveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditLeaveCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DESCRIPTION,
                PREFIX_LEAVE_DATE_START, PREFIX_LEAVE_DATE_END, PREFIX_LEAVE_STATUS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLeaveCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DESCRIPTION, PREFIX_LEAVE_DATE_START,
                PREFIX_LEAVE_DATE_END, PREFIX_LEAVE_STATUS);

        EditLeaveDescriptor editLeaveDescriptor = new EditLeaveDescriptor();

        try {
            argMultimap.getValue(PREFIX_LEAVE_TITLE).ifPresent((s) ->
                editLeaveDescriptor.setTitle(ParserUtil.parseTitle(s)));
        } catch (IllegalArgumentException e) {
            throw new ParseException(Title.MESSAGE_CONSTRAINTS);
        }

        try {
            argMultimap.getValue(PREFIX_LEAVE_DESCRIPTION).ifPresent((s) ->
                    editLeaveDescriptor.setDescription(ParserUtil.parseDescription(s)));
        } catch (IllegalArgumentException e) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }

        try {
            argMultimap.getValue(PREFIX_LEAVE_DATE_START).ifPresent((s) -> editLeaveDescriptor.setStart(Date.of(s)));
        } catch (DateTimeParseException e) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }

        try {
            argMultimap.getValue(PREFIX_LEAVE_DATE_END).ifPresent((s) -> editLeaveDescriptor.setEnd(Date.of(s)));
        } catch (DateTimeParseException e) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }

        try {
            argMultimap.getValue(PREFIX_LEAVE_STATUS).ifPresent((s) -> editLeaveDescriptor.setStatus(Status.of(s)));
        } catch (IllegalArgumentException e) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }

        if (!editLeaveDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLeaveCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLeaveCommand(index, editLeaveDescriptor);
    }
}
