# Basic Image Manipulation and Enhancement Tool #

### Description ### 

This object-oriented programming project utilizes MVC architecture
and the Command Design Pattern to create a basic, functional
image manipulation and enhancement application. The application
can either be run in the terminal using the command line argument "-text" or
can be run using the built GUI by running the application with 0 command line
arguments.
The application supports PNG, JPG, PPM, and Bitmap image types and can
perform the commands outlined in the USEME.

### Design Updates ###

11/30: The goal of this update was to build an effective graphical user interface with the Java 
Swing library while maintaining the MVC architecture and still allowing for the application to be 
used in script mode. A new controller was created to drive the application while it runs with 
the GUI. Additionally, a Features interface was added to the controller package in an effort to
decouple the view from the controller during action events. Furthermore, a new view was created to 
support the GUI and handle the action events that coincide with the components of the Java 
Swing library. 

Minor design modifications were necessary to be made to the model in order to better
accommodate the controller and view that was built for the GUI. The insertImage()
method was overloaded in order to reenter an image into the database with a separate name.
Additionally, image IO was moved completely out of the model. Although we intended to
achieve this in the previous update, there were still some lingering operations that were improperly
placed. All other previous design decisions remain untouched.

Lastly, due to the implementation of the GUI, some of the primary MVC classes were renamed in order
to establish a more accurate representation of their roles. The controllers and views
interfaces that operate in the text-based version of the application are now referred to with
prefix "Script" while the GUI-related classes are named with the prefix "GUI". 


11/16: In order to properly adhere to the Command Design Pattern, changes were made to the
model interface in an effort to clearly include a catalog of basic commands supported by any Image
object. The model interface now contains methods representative of the commands and implements them
appropriately. Additionally, the model was refined to prevent the capability to manipulate image
bodies, via the removal of the setPixel() method. There is now no way to directly modify an Image.

The controller can now only delegate manipulation to the model via a command, limiting
the controller's access and increasing security. This reduces cohesion between
various segments of the application.
A utility file for operations involving the split keyword was added in SplitUtil.
This was moved to its own class to prevent clutter and provide a convenient location to expand
split functionality. Additionally, we moved ImageUtil to ImageController
because the Model shouldn't be in change of IO, but this isn't interface-related,
so we didn't want it in the view either.

Finally, the Main method was modified in order to ensure that all interaction with the user goes
directly through the view, and that all application processes are handled by the controller. The
controller is now in change of both view calls and model calls, and synchronizing the two
appropriately. This allows for a proper MVC architecture.

11/1: First stable, complete project version.

### Model ###

*Pixel (Class)*: Represents a pixel with 3 channels (RGB) and contains various methods
to fetch and modify these values.

*Image (Interface)*: Outlines an implementation of an image and describes various methods
all Image implementations must have, such as getters for width, height, and name and
setters/getters for pixels at a specific index.

*SimpleImage (Class)*: Implements Image as a simple color image. The body of this
image is a double array of Pixels.

*StorageModel (Interface)*: This interface describes generic methods the model/database of
an image processing application. It maintains a set of stored images, and provides
methods to retrieve images by name and modify the contents stored.

*ImageStorageModel (Class)*: Implements StorageModel. Uses a hashtable to store
objects of type Image, with the image's name as the key. Implements methods to
get, insert, and remove an image from the database. Also provides a method to retrieve
the number of images currently stored.

*Filter (Class)*: An object that contains data of filters available in
the application, as well as a method to access them by name. Initialized once at the start of
any filter-related command.

*LinearColorTransformation (Class)*: An object that contains data of linear color transformations
available in the application, as well as a method to access them by name. Initialized once at the
start of any color-transformation-related command.

*HistogramUtil (Class)*: An object that contains all necessary methods to perform operations on
an Image object that involve its RGB histogram. This currently includes the ColorCorrect and
Histogram commands.

*CompressionUtil (Class)*: An object that contains all necessary methods to run the compress
command on an Image object.

*LevelsAdjustUtil (Class)*: An object that contains all necessary methods to run levels adjust
command on an Image object.

### View ###

*ScriptView (Interface)*: Outlines an implementation for a basic text-based view.
Includes methods to accept a user command, and display the status of various method
executions.

*ScriptViewImpl (Class)*: Implements ScriptView. Implements methods to read in a user command, and output a
status message.
Input and output buffers all have a layer of abstraction. In Main, System.in and
System.out are used, but within the tests for this class other streams are used.

*GUIView (Interface)*: Outlines an implementation for a GUI view. Includes methods to
to display an image, add application features and call said features. 

*GUIViewImpl (Class)*: Implements GUIView. Uses the Java Swing library to build the GUI
and includes all necessary methods from the interface. 

### Controller ###

*CommandController (Interface)*: Contains execute() for all command objects.
Execute accepts a String array of pre-parsed command words/tokens. To follow adherence with the
Command Design
Pattern, commands are encapsulated in objects that implement
CommandController. They are all located in the "commands" package and instantiated in the
Controller.
Their naming convention is as follows: commandnameCommand. There is a separate class for each of the
commands
listed in the project description. Each command implements execute() in a manner relevant to their
name, as described
in the list at the first part of this document. Added implementations for Compression, Histogram, Color Correction,
and Level Correction.

*ScriptController (Interface)*: Outlines all the necessary commands for the application's controller.
Includes a method to parse a String in to relevant command keywords, a runCommand() method
to run an individual command, and a method to run a script file.

*ScriptControllerImpl (Class)*: Implements the ScriptController Interface. Instantiates all the command objects
and puts them
in a HashMap for easy retrieval. ScriptControllerImpl is responsible for parsing the command line input
from the user,
and running commands with the parsed arguments. When a command is run, its command object is
retrieved from the HashMap and
execute() is called.

*Features (Interface)*: Outlines all the necessary commands for the application's controller to
be used when the program is being run with the GUI. Unlike the ScriptController, the Features Interface will handle
the GUI action events.

*GUIControllerImpl (Class)*: Implements Features. Handles all the image processing baseed 
on information passed from the view. 

*ImageUtil (Class)*: Provides methods to read png, gif, png, bitmap, and ppm files
into a generic Image object used internally by ImageStorageModel.

*SplitUtil (Class)*: A utility file for operations involving the split keyword.



### Run Instructions & Details ###

To run this program, go to the directory of the "ImageProcessingApp.jar" file in your terminal.  

There are three options for running the program: 
1) java -jar ImageProcessingApp.jar
2) java -jar ImageProcessingApp.jar -text
3) java -jar ImageProcessingApp.jar -file scriptFileName

Option 1 will run the program with the GUI.  
Option 2 will run the program in the terminal with text/script.   
Option 3 will run the provided script file and then terminate the program.  

If using Option 1:  
Successful compilation should greet you by opening a new window running the application. 
See USEME.md for more information on how to navigate the GUI. 

If using Option 2:  

Successful compilation of the program should greet you with the following in the
command terminal:  
"Image Processing Application by Rocky and Griffin"

Proceed by entering a command with proper usage as defined in USEME.md.  

A reminder that the "help" command will print that same list. "quit" will allow you to exit the
program.

### Resources ### 

Mushroom Image Credit: https://www.viewbug.com/blog/small-things-in-nature-photo-contest-finalists

In the project directory, there is also a UML diagram of the application, generated by IntelliJ.