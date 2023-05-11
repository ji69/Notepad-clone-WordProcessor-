/* @author Jhon Vincent
 */
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import javafx.event.ActionEvent;

public class WordProcessor extends Application {

    private TextArea textArea;
    private File file;
    private TextField findField;
    private TextField replaceField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // create a menu bar with "File" and "Edit" menus
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem openMenuItem = new MenuItem("Open");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem saveAsMenuItem = new MenuItem("Save As...");
        MenuItem pageSetupMenuItem = new MenuItem("Page Setup");
        MenuItem exitMenuItem = new MenuItem("Exit");
        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, pageSetupMenuItem, new SeparatorMenuItem(), exitMenuItem);
        Menu editMenu = new Menu("Edit");
        MenuItem cutMenuItem = new MenuItem("Cut");
        MenuItem copyMenuItem = new MenuItem("Copy");
        MenuItem pasteMenuItem = new MenuItem("Paste");
        MenuItem findMenuItem = new MenuItem("Find and Replace");
        editMenu.getItems().addAll(cutMenuItem, copyMenuItem, pasteMenuItem, new SeparatorMenuItem(), findMenuItem);
        menuBar.getMenus().addAll(fileMenu, editMenu);

        // create a text area to enter and edit text
        textArea = new TextArea();
        // set the font , font weight ,and font size
        textArea.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        // set the actions for menu items
        newMenuItem.setOnAction(event -> {
            textArea.clear();
            file = null;
        });
        
        newMenuItem.setOnAction(event -> {
    // New menu item is clicked

    // Clear the text area
    textArea.clear();

    // Reset the file reference to null
    file = null;
});

openMenuItem.setOnAction((ActionEvent event) -> {
    // Open menu item is clicked

    // Create a file chooser dialog
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open File");

    // Show the file chooser dialog and wait for a file selection
    file = fileChooser.showOpenDialog(primaryStage);

    if (file != null) {
        // If a file is selected

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder content = new StringBuilder();

            // Read the file line by line and append the content to a StringBuilder
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Set the content of the text area to the content read from the file
            textArea.setText(content.toString());
        } catch (IOException e) {
            // Handle any IO errors
        }
    }
});

saveMenuItem.setOnAction((ActionEvent event) -> {
    // Save menu item is clicked

    if (file == null) {
        // If no file is currently associated

        // Prompt for a new file using the "Save As" method
        saveAs();
    } else {
        // If a file is already associated

        try (FileWriter writer = new FileWriter(file)) {
            // Create a file writer to write the text area content to the file

            // Write the text area content to the file
            writer.write(textArea.getText());
        } catch (IOException e) {
            // Handle any IO errors
        }
    }
});

        saveAsMenuItem.setOnAction(event -> saveAs());
        pageSetupMenuItem.setOnAction((ActionEvent event) -> {
            // create a dialog to select the page settings
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Page Setup");
            
            // create a button bar to hold the save and cancel buttons
            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
            
            // create a vbox to hold the page settings
            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(20));
            
            // create labels and text fields for page settings
            Label paperSizeLabel = new Label("Paper Size:");
            ComboBox<String> paperSizeComboBox = new ComboBox<>();
            paperSizeComboBox.getItems().addAll("A4", "Letter", "Legal");
            paperSizeComboBox.getSelectionModel().selectFirst();
            Label orientationLabel = new Label("Orientation:");
            ComboBox<String> orientationComboBox = new ComboBox<>();
            orientationComboBox.getItems().addAll("Portrait", "Landscape");
            orientationComboBox.getSelectionModel().selectFirst();
            
            Label marginLabel = new Label("Margins (inches):");
            HBox marginBox = new HBox(10);
            marginBox.setAlignment(Pos.CENTER_LEFT);
            TextField topMarginField = new TextField("0.75");
            topMarginField.setPrefWidth(40);
            Label topMarginLabel = new Label("Top");
            TextField bottomMarginField = new TextField("0.75");
            bottomMarginField.setPrefWidth(40);
            Label bottomMarginLabel = new Label("Bottom");
            TextField leftMarginField = new TextField("0.75");
            leftMarginField.setPrefWidth(40);
            Label leftMarginLabel = new Label("Left");
            TextField rightMarginField = new TextField("0.75");
            rightMarginField.setPrefWidth(40);
            Label rightMarginLabel = new Label("Right");
            marginBox.getChildren().addAll(topMarginField, topMarginLabel, bottomMarginField, bottomMarginLabel, leftMarginField, leftMarginLabel, rightMarginField, rightMarginLabel);
            
            vbox.getChildren().addAll(paperSizeLabel, paperSizeComboBox, orientationLabel, orientationComboBox, marginLabel, marginBox);
            dialog.getDialogPane().setContent(vbox);
            
            // set the action for the save button
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    // create a PrinterJob object to get the default PrinterJob and the page layout
                    PrinterJob job = PrinterJob.createPrinterJob();
                    if (job != null) {
                        job.showPageSetupDialog(primaryStage);
                        job.endJob();
                    }
                }
                return null;
            });
            
            // show the dialog
            dialog.showAndWait();
        });
        exitMenuItem.setOnAction(event -> primaryStage.close());

        cutMenuItem.setOnAction(event -> textArea.cut());
        copyMenuItem.setOnAction(event -> textArea.copy());
        pasteMenuItem.setOnAction(event -> textArea.paste());

        findMenuItem.setOnAction(event -> {
            // create a dialog for find and replace
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Find and Replace");

            // create a button bar to hold the find and replace buttons
            ButtonType findButtonType = new ButtonType("Find", ButtonBar.ButtonData.OK_DONE);
            ButtonType replaceButtonType = new ButtonType("Replace", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(findButtonType, replaceButtonType, ButtonType.CANCEL);

            // create a vbox to hold the find and replace fields
            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(20));

            // create labels and text fields for find and replace
            Label findLabel = new Label("Find:");
            findField = new TextField();
            Label replaceLabel = new Label("Replace:");
            replaceField = new TextField();

            vbox.getChildren().addAll(findLabel, findField, replaceLabel, replaceField);
            dialog.getDialogPane().setContent(vbox);

            // set the action for the find and replace buttons
            Button findButton = (Button) dialog.getDialogPane().lookupButton(findButtonType);
            findButton.setOnAction(e -> find());

            Button replaceButton = (Button) dialog.getDialogPane().lookupButton(replaceButtonType);
            replaceButton.setOnAction(e -> replace());

            // Show the dialog and wait for it to close
            dialog.showAndWait();
        });

        root.setTop(menuBar);
        root.setCenter(textArea);

        // Create a scene with the root pane
        Scene scene = null;

        // Set the scene to the primary stage
        primaryStage.setScene(scene);

        // Show the primary stage (application window)
        primaryStage.show();

        //when the "Cut," "Copy," and "Paste" menu items are clicked. In this case, the corresponding methods of the text area (cut, copy, and paste) are invoked
        cutMenuItem.setOnAction(event -> textArea.cut());
        copyMenuItem.setOnAction(event -> textArea.copy());
        pasteMenuItem.setOnAction(event -> textArea.paste());

        findMenuItem.setOnAction(event -> {
            // create a dialog to search and replace text
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Find and Replace");

            // create a button bar to hold the find and cancel buttons
            ButtonType findButtonType = new ButtonType("Find", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(findButtonType, ButtonType.CANCEL);

            // create a vbox to hold the search and replace fields
            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(20));

            // create labels and text fields for search and replace
            Label findLabel = new Label("Find:");
            findField = new TextField();
            Label replaceLabel = new Label("Replace:");
            replaceField = new TextField();

            vbox.getChildren().addAll(findLabel, findField, replaceLabel, replaceField);
            dialog.getDialogPane().setContent(vbox);

            // set the action for the find button
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == findButtonType) {
                    String findText = findField.getText();
                    String replaceText = replaceField.getText();
                    if (!findText.isEmpty()) {
                        String text = textArea.getText();
                        int index = text.indexOf(findText);
                        if (index != -1) {
                            // highlight the found text
                            textArea.selectRange(index, index + findText.length());

                            // ask user if they want to replace the text
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Replace the selected text?");
                            alert.setContentText("Do you want to replace the selected text with \"" + replaceText + "\"?");
                            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
                            alert.showAndWait().ifPresent(buttonType -> {
                                if (buttonType == ButtonType.YES) {
                                    // replace the selected text
                                    textArea.replaceSelection(replaceText);
                                }
                            });
                        } else {
                            // inform user that text was not found
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setHeaderText("Text not found");
                            alert.setContentText("The text \"" + findText + "\" was not found.");
                            alert.showAndWait();
                        }
                    }
                }
                return null;
            });

            // show the dialog
            dialog.showAndWait();
        });

        // create a find and replace dialog
        findMenuItem.setOnAction(event -> {
            // create a dialog to find and replace text
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Find and Replace");

            // create a button bar to hold the find and replace buttons
            ButtonType findButtonType = new ButtonType("Find", ButtonBar.ButtonData.OK_DONE);
            ButtonType replaceButtonType = new ButtonType("Replace", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(findButtonType, replaceButtonType, ButtonType.CANCEL);

            // create a vbox to hold the find and replace components
            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(20));

            // create labels and text fields for find and replace
            Label findLabel = new Label("Find:");
            findField = new TextField();
            Label replaceLabel = new Label("Replace:");
            replaceField = new TextField();

            vbox.getChildren().addAll(findLabel, findField, replaceLabel, replaceField);
            dialog.getDialogPane().setContent(vbox);

            // set the action for the find button
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == findButtonType) {
                    // Find button is clicked

                    // Get the text from the text area
                    String text = textArea.getText();

                    // Find the text in the text area
                    String find = findField.getText();

                    // Find the starting index of the first occurrence of the text to find
                    int start = text.indexOf(find);

                    // If the text to find is found in the text area
                    if (start >= 0) {


                        // Select/highlight the found text in the text area
                        textArea.selectRange(start, start + find.length());
                    } else {

                        /* If the text to find is not found in the text area
                         Show an information alert indicating no matches found*/
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Find and Replace");
                        alert.setHeaderText(null);
                        alert.setContentText("No matches found.");
                        alert.showAndWait();
                    }

                    // Replace button is clicked
                } else if (dialogButton == replaceButtonType) {


                    // Get the text from the text area
                    String text = textArea.getText();

                    // Get the text to find from the find field
                    String find = findField.getText();

                    // Get the text to replace with from the replace field
                    String replace = replaceField.getText();

                    // If the text to find is found in the text area
                    int start = text.indexOf(find);


                    // Replace the found text with the replacement text in the text area
                    if (start >= 0) {
                        textArea.replaceText(start, start + find.length(), replace);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Find and Replace");
                        alert.setHeaderText(null);
                        alert.setContentText("No matches found.");
                        alert.showAndWait();
                    }
                }
                return null;
            });

            // show the dialog
            dialog.showAndWait();
        });

        // Set the menu box and text area in the center of the root pane
        root.setTop(menuBar);
        root.setCenter(textArea);

        // Create a scene with the root pane and set it to the stage
        scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Word Processor");
        primaryStage.show();
    }

    private void replace() {
    }

    private void find() {
    }

    // to save project file in file explorer but save the file with .txt to make it easier to compile
    private void saveAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save As");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files (.txt)", ".txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
            String filePath = selectedFile.getPath();
            if (!filePath.endsWith(".txt")) {
                selectedFile = new File(filePath + ".txt");
            }
            file = selectedFile;
            try {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(textArea.getText());
                }
            } catch (IOException e) {
            }
        }
    }
}
