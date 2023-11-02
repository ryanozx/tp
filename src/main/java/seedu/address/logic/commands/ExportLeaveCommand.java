package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFilteredLeavesBook;
import seedu.address.storage.CsvLeavesBookStorage;

/**
 * Exports LeavesBook to a CSV file
 */
public class ExportLeaveCommand extends ExportCommand {
    public static final String COMMAND_WORD = "export-leave";

    public static final String MESSAGE_USAGE = String.format(ExportCommand.MESSAGE_USAGE, COMMAND_WORD, "leaves");
    public static final String MESSAGE_FAILED = String.format(ExportCommand.MESSAGE_FAILED, "Leaves");

    /**
     * Creates an ExportLeaveCommand to export records to the specified file name
     * @param filePath Path to save file to
     */
    public ExportLeaveCommand(Path filePath) {
        super(filePath);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            ReadOnlyFilteredLeavesBook filteredLeavesBook = new ReadOnlyFilteredLeavesBook(model);
            CsvLeavesBookStorage lvStorage = new CsvLeavesBookStorage(filePath);
            lvStorage.saveLeavesBook(filteredLeavesBook);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILED);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, "Leaves", filePath));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportLeaveCommand)) {
            return false;
        }

        ExportLeaveCommand otherExportLeaveCommand = (ExportLeaveCommand) other;
        return filePath.equals(otherExportLeaveCommand.filePath);
    }
}
