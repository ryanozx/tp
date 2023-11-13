package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_TITLE;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditLeaveCommand;
import seedu.address.logic.commands.EditLeaveCommand.EditLeaveDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.leave.Range;
import seedu.address.model.leave.exceptions.EndBeforeStartException;

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
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_LEAVE_TITLE, PREFIX_LEAVE_DESCRIPTION, PREFIX_LEAVE_DATE_START,
                PREFIX_LEAVE_DATE_END, PREFIX_LEAVE_STATUS);

        Index index = extractIndexFromInput(argMultimap);
        EditLeaveDescriptor editLeaveDescriptor = new EditLeaveDescriptor();

        setTitleIfExists(editLeaveDescriptor, argMultimap);
        setDescriptionIfExists(editLeaveDescriptor, argMultimap);
        setStartIfExists(editLeaveDescriptor, argMultimap);
        setEndIfExists(editLeaveDescriptor, argMultimap);
        setStatusIfExists(editLeaveDescriptor, argMultimap);

        if (!editLeaveDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditLeaveCommand.MESSAGE_NOT_EDITED);
        }

        return new EditLeaveCommand(index, editLeaveDescriptor);
    }

    /**
     * Retrieves index value from ArgumentMultimap object.
     * @param argMultimap ArgumentMultimap containing index.
     * @return Index constructed from value in argMultimap.
     * @throws ParseException if no valid index is found in argMultimap.
     */
    private Index extractIndexFromInput(ArgumentMultimap argMultimap) throws ParseException {
        try {
            return ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLeaveCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Modifies the title field of editLeaveDescriptor to contain the value of the title in argMultimap.
     * @param editLeaveDescriptor EditLeaveDescriptor object to modify.
     * @param argMultimap Multimap containing title value.
     * @throws ParseException if title is not valid.
     */
    private void setTitleIfExists(EditLeaveDescriptor editLeaveDescriptor, ArgumentMultimap argMultimap)
            throws ParseException {
        Optional<String> title = argMultimap.getValue(PREFIX_LEAVE_TITLE);
        if (title.isPresent()) {
            editLeaveDescriptor.setTitle(ParserUtil.parseTitle(title.get()));
        }
    }

    /**
     * Modifies the title field of editLeaveDescriptor to contain the value of the description in argMultimap.
     * @param editLeaveDescriptor EditLeaveDescriptor object to modify.
     * @param argMultimap Multimap containing description value.
     * @throws ParseException if description is not valid.
     */
    private void setDescriptionIfExists(EditLeaveDescriptor editLeaveDescriptor, ArgumentMultimap argMultimap)
            throws ParseException {
        Optional<String> desc = argMultimap.getValue(PREFIX_LEAVE_DESCRIPTION);
        if (desc.isPresent()) {
            editLeaveDescriptor.setDescription(ParserUtil.parseDescription(desc.get()));
        }
    }

    /**
     * Modifies the title field of editLeaveDescriptor to contain the value of the start in argMultimap.
     * @param editLeaveDescriptor EditLeaveDescriptor object to modify.
     * @param argMultimap Multimap containing start value.
     * @throws ParseException if start is not valid.
     */
    private void setStartIfExists(EditLeaveDescriptor editLeaveDescriptor, ArgumentMultimap argMultimap)
            throws ParseException {
        Optional<String> start = argMultimap.getValue(PREFIX_LEAVE_DATE_START);
        if (start.isPresent()) {
            try {
                editLeaveDescriptor.setStart(ParserUtil.parseDate(start.get()));
            } catch (EndBeforeStartException e) {
                throw new ParseException(Range.MESSAGE_END_BEFORE_START_ERROR);
            }
        }
    }

    /**
     * Modifies the end field of editLeaveDescriptor to contain the value of the end in argMultimap.
     * @param editLeaveDescriptor EditLeaveDescriptor object to modify.
     * @param argMultimap Multimap containing end value.
     * @throws ParseException if end is not valid.
     */
    private void setEndIfExists(EditLeaveDescriptor editLeaveDescriptor, ArgumentMultimap argMultimap)
            throws ParseException {
        Optional<String> end = argMultimap.getValue(PREFIX_LEAVE_DATE_END);
        if (end.isPresent()) {
            try {
                editLeaveDescriptor.setEnd(ParserUtil.parseDate(end.get()));
            } catch (EndBeforeStartException e) {
                throw new ParseException(Range.MESSAGE_END_BEFORE_START_ERROR);
            }
        }
    }

    /**
     * Modifies the status field of editLeaveDescriptor to contain the value of the status in argMultimap.
     * @param editLeaveDescriptor EditLeaveDescriptor object to modify.
     * @param argMultimap Multimap containing status value.
     * @throws ParseException if status is not valid.
     */
    private void setStatusIfExists(EditLeaveDescriptor editLeaveDescriptor, ArgumentMultimap argMultimap)
            throws ParseException {
        Optional<String> status = argMultimap.getValue(PREFIX_LEAVE_STATUS);
        if (status.isPresent()) {
            editLeaveDescriptor.setStatus(ParserUtil.parseStatus(status.get()));
        }
    }
}
