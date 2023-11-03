package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_LEAVES;

import java.io.File;
import java.util.Optional;

import seedu.address.commons.controllers.FileDialogHandler;
import seedu.address.commons.controllers.FileDialogHandlerImpl;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyLeavesBook;
import seedu.address.storage.CsvLeavesBookStorage;

/**
 * Imports Leaves from file
 */
public class ImportLeaveCommand extends Command {
    public static final String COMMAND_WORD = "import-leave";

    public static final String MESSAGE_SUCCESS = "Leave records have been imported from %s!";

    public static final String MESSAGE_NO_FILE_SELECTED = "Leave records were not imported.";

    public static final String MESSAGE_FAILED = "Records in file %s could not be imported, "
            + "import cancelled.";

    public static final String MESSAGE_EMPTY_LEAVES_BOOK = "No valid records found in file %s, "
            + "import cancelled.";

    private final FileDialogHandler fileHandler;

    /**
     * Constructs a default ImportLeaveCommand that triggers a file dialog
     */
    public ImportLeaveCommand() {
        fileHandler = new FileDialogHandlerImpl();
    }

    /**
     * Constructs an ImportLeaveCommand that uses the specified fileHandler. This constructor is primarily used to
     * construct ImportLeaveCommands that use mock FileDialogHandlers.
     * @param fileHandler FileDialogHandler to invoke when executing the command.
     */
    public ImportLeaveCommand(FileDialogHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<File> importedFile = fileHandler.openFile("Open Leaves File",
                FileDialogHandlerImpl.CSV_EXTENSION);

        if (importedFile.isPresent()) {
            String filename = importedFile.get().getName();
            CsvLeavesBookStorage tempLeavesBook = new CsvLeavesBookStorage(importedFile.get().toPath());

            try {
                ReadOnlyLeavesBook newLeavesBook = tempLeavesBook.readLeavesBook((AddressBook) model.getAddressBook())
                        .orElseThrow(() -> new CommandException(
                                String.format(MESSAGE_EMPTY_LEAVES_BOOK, filename)));
                model.setLeavesBook(newLeavesBook);
                model.updateFilteredLeaveList(PREDICATE_SHOW_ALL_LEAVES);
                return new CommandResult(String.format(MESSAGE_SUCCESS, filename));
            } catch (DataLoadingException e) {
                throw new CommandException(String.format(MESSAGE_FAILED, filename));
            }
        }
        return new CommandResult(MESSAGE_NO_FILE_SELECTED);
    }
}
