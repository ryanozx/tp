package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.util.Optional;

import seedu.address.commons.controllers.FileDialogHandler;
import seedu.address.commons.controllers.FileDialogHandlerImpl;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Imports records from file
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_SUCCESS = "Employee records have been imported from %s!";

    public static final String MESSAGE_FAILED = "Employee records were not imported.";

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
        Optional<File> importedFile = fileHandler.openFile("Open Record File", FileDialogHandlerImpl.CSV_EXTENSION);
        if (importedFile.isEmpty()) {
            return new CommandResult(MESSAGE_FAILED);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, importedFile.get().getName()));
    }
}
