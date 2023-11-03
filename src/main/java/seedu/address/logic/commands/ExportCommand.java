package seedu.address.logic.commands;

import java.nio.file.Path;

/**
 * Base ExportCommand class that contains fields shared by commands that export files,
 * such as file paths, destination paths, and messages
 */
public abstract class ExportCommand extends Command {
    public static final String EXPORT_DEST = "export";

    public static final String MESSAGE_USAGE = "%s: Exports %s records to CSV file. "
            + "Parameters: "
            + "FILENAME";

    public static final String MESSAGE_SUCCESS = "%s records have been saved to %s!";

    public static final String MESSAGE_FAILED = "%s records could not be saved!";

    public final Path filePath;

    /**
     * Creates an ExportCommand to export records to the specified file name
     * @param filePath Path to save file to
     */
    public ExportCommand(Path filePath) {
        this.filePath = filePath;
    }
}
