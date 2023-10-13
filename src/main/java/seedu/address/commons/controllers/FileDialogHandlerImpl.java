package seedu.address.commons.controllers;

import java.io.File;
import java.util.Optional;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Handles file dialogs
 */
public class FileDialogHandlerImpl implements FileDialogHandler {

    public static final ExtensionFilter CSV_EXTENSION = new ExtensionFilter("CSV Files", "*.csv");
    public static final String CSV_SUFFIX = ".csv";

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
