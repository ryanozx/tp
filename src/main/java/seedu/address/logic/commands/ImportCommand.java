package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEAVES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.File;
import java.util.Optional;

import seedu.address.commons.controllers.FileDialogHandler;
import seedu.address.commons.controllers.FileDialogHandlerImpl;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.CsvAddressBookStorage;

/**
 * Imports records from file
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_SUCCESS = "Employee records have been imported from %s!";

    public static final String MESSAGE_NO_FILE_SELECTED = "Employee records were not imported.";

    public static final String MESSAGE_FAILED = "Records in file %s could not be imported, "
            + "import cancelled.";

    public static final String MESSAGE_EMPTY_ADDRESS_BOOK = "No valid records found in file %s, "
            + "import cancelled.";

    private final FileDialogHandler fileHandler;

    /**
     * Constructs a default ImportCommand that triggers a file dialog
     */
    public ImportCommand() {
        fileHandler = new FileDialogHandlerImpl();
    }

    /**
     * Constructs an ImportCommand that uses the specified fileHandler. This constructor is primarily used to construct
     * ImportCommands that use mock FileDialogHandlers.
     * @param fileHandler FileDialogHandler to invoke when executing the command.
     */
    public ImportCommand(FileDialogHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<File> importedFile = fileHandler.openFile("Open Record File",
                FileDialogHandlerImpl.CSV_EXTENSION);

        if (importedFile.isPresent()) {
            String filename = importedFile.get().getName();
            CsvAddressBookStorage tempAddressBook = new CsvAddressBookStorage(importedFile.get().toPath());

            try {
                ReadOnlyAddressBook newAddressBook = tempAddressBook.readAddressBook()
                        .orElseThrow(() -> new CommandException(
                                String.format(MESSAGE_EMPTY_ADDRESS_BOOK, filename)));
                model.setAddressBook(newAddressBook);
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                model.updateFilteredLeaveList(PREDICATE_SHOW_ALL_LEAVES);
                return new CommandResult(String.format(MESSAGE_SUCCESS, filename));
            } catch (DataLoadingException e) {
                throw new CommandException(String.format(MESSAGE_FAILED, filename));
            }
        }
        return new CommandResult(MESSAGE_NO_FILE_SELECTED);
    }
}
