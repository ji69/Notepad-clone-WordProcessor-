# Notepad-clone-WordProcessor-
This code is a Java program that implements a basic word processor. The program uses the JavaFX library to create a graphical user interface (GUI) for the word processor.

The program starts by importing the necessary JavaFX classes and defining a public class `WordProcessor` that extends the `Application` class. The `Application` class is a base class for JavaFX applications, and its `start()` method is the entry point for the program.

The `WordProcessor` class defines several private instance variables, including a `TextArea` for entering and editing text, a `File` object for keeping track of the current file being edited, and `TextField` objects for finding and replacing text in the `TextArea`.

The `main()` method simply calls the `launch()` method, which starts the JavaFX application.

The `start()` method creates a `BorderPane` object as the root node of the scene graph, which represents the GUI for the word processor. The program creates a `MenuBar` object and adds two `Menu` objects to it: "File" and "Edit". Each `Menu` object contains several `MenuItem` objects for performing various actions, such as opening and saving files, cutting and pasting text, and finding and replacing text.

The program also creates a `TextArea` object and sets its font to Arial, size 12. The start() method then defines the actions for each of the `MenuItem` objects. For example, when the "New" menu item is selected, the textArea is cleared and the file variable is set to null.

The program also defines a `FileChooser` object for selecting files to open and save, and uses a `BufferedReader` and `FileReader` to read text from a selected file and display it in the `textArea`. When the user saves a file, the program uses a `FileWriter` to write the text in the textArea to a file.

Finally, the program defines a "Page Setup" dialog box for setting the page layout when printing a document. The dialog box contains several ComboBox
and TextField objects for selecting the paper size, orientation, and margins of the page. When the user clicks the "Save" button, the program creates a `PrinterJob` object and uses its showPageSetupDialog() method to display the page setup dialog box.
