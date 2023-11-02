# Basic Image Manipulation and Enhancement Tool #


### Description ### 
This object-oriented programming project utilizes MVC architecture
and the Command Design Pattern to create a basic, functional
image manipulation and enhancement application. The application
is run in the terminal and accepts user input via command line.
The application supports PNG, JPG, PPM, and Bitmap image types and can
perform the following commands: 

- *load image-path image-name*: Load an image from the specified path and refer
  to it henceforth in the program by the given image name.


- *save image-path image-name*: Save the image with the given name to the
  specified path which should include the name of the file.


- *red-component image-name dest-image-name*: Create an image with the
  red-component of the image with the given name, and refer to it henceforth in
  the program by the given destination name.


- *green-component image-name dest-image-name*: Create an image with the
  green-component of the image with the given name, and refer to it henceforth in
  the program by the given destination name.


- *blue-component image-name dest-image-name*: Create an image with the
  blue-component of the image with the given name, and refer to it henceforth in
  the program by the given destination name.


- *value-component image-name dest-image-name*: Create an image with the
  value-component of the image with the given name, and refer to it henceforth in
  the program by the given destination name.


- *intensity-component image-name dest-image-name*: Create an image with the
  intensity-component of the image with the given name, and refer to it henceforth in
  the program by the given destination name.


- *luma-component image-name dest-image-name*: Create an image with the
  luma-component of the image with the given name, and refer to it henceforth in
  the program by the given destination name.


- *horizontal-flip image-name dest-image-name*: Flip an image horizontally
  to create a new image, referred to henceforth by the given destination name.


- *vertical-flip image-name dest-image-name*: Flip an image vertically
  to create a new image, referred to henceforth by the given destination name.


- *brighten increment image-name dest-image-name*: brighten the image by the given
  increment to create a new image, referred to henceforth by the given destination
  name. The increment may be positive (brightening) or negative (darkening).


- *rgb-split image-name dest-image-name-red dest-image-name-green
  dest-image-name-blue*: split the given image into three images containing
  its red, green and blue components respectively. These would be the same
  images that would be individually produced with the red-component,
  green-component and blue-component commands.


- *rgb-combine image-name red-image green-image blue-image*: Combine the
  three greyscale images into a single image that gets its red, green and
  blue components from the three images respectively.


- *blur image-name dest-image-name*: blur the given image and
  store the result in another image with the given name.


- *sharpen image-name dest-image-name*: sharpen the given image and
store the result in another image with the given name.


- *sepia image-name dest-image-name*: produce a sepia-toned version of
the given image and store the result in another image with the given name.  


- *run fileName*: runs a script file with 1 command and its necessary arguments per line.  


- *help--* lists all commands and their usage.


- *quit--* quits program

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

*ImageUtil (Class)*: Provides methods to read png, gif, png, bitmap, and ppm files
into a generic Image object used internally by ImageStorageModel.

*Filter (Class)*: An object that contains data of filters available in
the application, as well as a method to access them by name. Initialized once at the start of
any filter-related command. 

*LinearColorTransformation (Class)*: An object that contains data of linear color transformations
available in the application, as well as a method to access them by name. Initialized once at the 
start of any color-transformation-related command.


### View ###

*View (Interface)*: Outlines an implementation for a basic text-based view.
Includes methods to accept a user command, and display the status of various method
executions.

*ImageView (Class)*: Implements View. Implements methods to read in a user command, and output a status message.
Input and output buffers all have a layer of abstraction. In Main, System.in and 
System.out are used, but within the tests for this class other streams are used.

### Controller ###
*CommandController (Interface)*: Contains execute() for all command objects.
Execute accepts a String array of pre-parsed command words/tokens. To follow adherence with the Command Design 
Pattern, commands are encapsulated in objects that implement
CommandController. They are all located in the "commands" package and instantiated in the Controller. 
Their naming convention is as follows: commandnameCommand. There is a separate class for each of the commands
listed in the project description. Each command implements execute() in a manner relevant to their name, as described
in the list at the first part of this document. 

*Controller (Interface)*: Outlines all the necessary commands for the application's controller. 
Includes a method to parse a String in to relevant command keywords, a runCommand() method
to run an individual command, and a method to run a script file.

*ImageController (Class)*: Implements the Controller Interface. Instantiates all the command objects and puts them
in a HashMap for easy retrieval. ImageController is responsible for parsing the command line input from the user,
and running commands with the parsed arguments. When a command is run, its command object is retrieved from the HashMap and
execute() is called. 


### Run Instructions & Details ###
To run this program, go to the directory of the "ImageProcessingApp.jar" file in your terminal.  
In the command line, type: java -jar ImageProcessingApp.jar  

Successful compilation of the program should greet you with the following in the 
command terminal:  
"Image Processing Application by Rocky and Griffin"

Proceed by entering a command with proper usage as defined in the Description portion of this README.   

A reminder that the "help" command will print that same list. Quit will allow you to exit the program.  


*Resources*  
Rather than individual commands, a script file can be used with the "run" command.
Try running the provided script, "sample_script.txt". See the "Details" section of the README for usage.

To view a sample image and the results of running each command on said image ("sample_script.txt" does this), 
view the "mushroom.jpg" and "mushroom_images" in the resources folder.  
Mushroom Image Credit: https://www.viewbug.com/blog/small-things-in-nature-photo-contest-finalists

In the project directory, there is also a UML diagram of the application, generated by IntelliJ. 



