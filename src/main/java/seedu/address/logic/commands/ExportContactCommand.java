package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFilteredAddressBook;
import seedu.address.storage.CsvAddressBookStorage;

/**
 * Exports AddressBook to a CSV file
 */
public class ExportContactCommand extends ExportCommand {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = String.format(ExportCommand.MESSAGE_USAGE, COMMAND_WORD, "employee");
    public static final String MESSAGE_FAILED = String.format(ExportCommand.MESSAGE_FAILED, "Employee");

    /**
     * Creates an ExportContactCommand to export records to the specified file name
     * @param filePath Path to save file to
     */
    public ExportContactCommand(Path filePath) {
        super(filePath);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            ReadOnlyFilteredAddressBook filteredAddressBook = new ReadOnlyFilteredAddressBook(model);
            CsvAddressBookStorage abStorage = new CsvAddressBookStorage(filePath);
            abStorage.saveAddressBook(filteredAddressBook);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILED);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, "Employee", filePath));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportContactCommand)) {
            return false;
        }

        ExportContactCommand otherExportContactCommand = (ExportContactCommand) other;
        return filePath.equals(otherExportContactCommand.filePath);
    }
}
