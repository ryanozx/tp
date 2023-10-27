package seedu.address.commons.controllers;

import java.io.File;
import java.util.Optional;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import seedu.address.commons.util.CsvUtil;

/**
 * Handles file dialogs
 */
public class FileDialogHandlerImpl implements FileDialogHandler {

    private static final String EXTENSION_FORMAT = "*%s";
    public static final ExtensionFilter CSV_EXTENSION = new ExtensionFilter("CSV Files",
            String.format(EXTENSION_FORMAT, CsvUtil.EXTENSION));

    /**
     * Creates a file dialog for the user to select a file to open
     * @param title Title of dialog
     * @param extensions Extension filters that contain the permitted file extensions
     * @return An Optional object containing the file if selected, else an empty Optional object
     */
    @Override
    public Optional<File> openFile(String title, ExtensionFilter ...extensions) {
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.getExtensionFilters().addAll(extensions);
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            return Optional.of(selectedFile);
        }
        return Optional.empty();
    }
}
