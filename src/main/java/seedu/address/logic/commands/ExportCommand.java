package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Exports records to file
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String EXPORT_DEST = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports records to CSV file. "
            + "Parameters: "
            + "FILENAME";

    public static final String MESSAGE_SUCCESS = "Employee records have been saved to %s!";

    public static final String MESSAGE_FAILED = "Employee records could not be saved!";

    public final Path filePath;

    /**
     * Creates an ExportCommand to export records to the specified file name
     * @param filePath Path to save file to
     */
    public ExportCommand(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            FileUtil.createFile(this.filePath);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILED);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand otherExportCommand = (ExportCommand) other;
        return filePath.equals(otherExportCommand.filePath);
    }
}
