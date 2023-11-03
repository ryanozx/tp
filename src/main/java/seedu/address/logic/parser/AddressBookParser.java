package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddLeaveCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.ApproveLeaveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLeaveCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditLeaveCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportContactCommand;
import seedu.address.logic.commands.ExportLeaveCommand;
import seedu.address.logic.commands.FindAllLeaveCommand;
import seedu.address.logic.commands.FindAllTagCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindLeaveByPeriodCommand;
import seedu.address.logic.commands.FindLeaveByStatusCommand;
import seedu.address.logic.commands.FindLeaveCommand;
import seedu.address.logic.commands.FindSomeTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportContactCommand;
import seedu.address.logic.commands.ImportLeaveCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RejectLeaveCommand;
import seedu.address.logic.commands.ViewTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case DeleteTagCommand.COMMAND_WORD:
            return new DeleteTagCommandParser().parse(arguments);

        case FindAllTagCommand.COMMAND_WORD:
            return new FindAllTagCommandParser().parse(arguments);

        case FindSomeTagCommand.COMMAND_WORD:
            return new FindSomeTagCommandParser().parse(arguments);

        case ViewTagCommand.COMMAND_WORD:
            return new ViewTagCommand();

        case ImportContactCommand.COMMAND_WORD:
            return new ImportContactCommand();

        case ImportLeaveCommand.COMMAND_WORD:
            return new ImportLeaveCommand();

        case ExportContactCommand.COMMAND_WORD:
            return new ExportContactCommandParser().parse(arguments);

        case ExportLeaveCommand.COMMAND_WORD:
            return new ExportLeaveCommandParser().parse(arguments);

        case DeleteLeaveCommand.COMMAND_WORD:
            return new DeleteLeaveCommandParser().parse(arguments);

        case AddLeaveCommand.COMMAND_WORD:
            return new AddLeaveCommandParser().parse(arguments);

        case ApproveLeaveCommand.COMMAND_WORD:
            return new ApproveLeaveCommandParser().parse(arguments);

        case EditLeaveCommand.COMMAND_WORD:
            return new EditLeaveCommandParser().parse(arguments);

        case FindAllLeaveCommand.COMMAND_WORD:
            return new FindAllLeaveCommand();

        case FindLeaveCommand.COMMAND_WORD:
            return new FindLeaveCommandParser().parse(arguments);

        case FindLeaveByPeriodCommand.COMMAND_WORD:
            return new FindLeaveByPeriodCommandParser().parse(arguments);

        case FindLeaveByStatusCommand.COMMAND_WORD:
            return new FindLeaveByStatusCommandParser().parse(arguments);

        case RejectLeaveCommand.COMMAND_WORD:
            return new RejectLeaveCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
