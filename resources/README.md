**Basic Image Manipulation and Enhancement Application** 


**Description**  
This object-oriented programming project utilizes MVC architecture
and the Command Design Pattern to create a basic, functional
image manipulation and enhancement application. The application
is run in the terminal and accepts user input via command line.
The application supports PNG, JPG, PPM image types and can
perform the following commands: 

*brighten increment image-name dest-image-name: brighten the image by the given
increment to create a new image, referred to henceforth by the given destination
name. The increment may be positive (brightening) or negative (darkening).*

*sharpen image-name dest-image-name: sharpen the given image and
store the result in another image with the given name.*

*green-component image-name dest-image-name: Create an image with the
green-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.*

*save image-path image-name: Save the image with the given name to the
specified path which should include the name of the file.*

*blur image-name dest-image-name: blur the given image and
store the result in another image with the given name.*

*horizontal-flip image-name dest-image-name: Flip an image horizontally
to create a new image, referred to henceforth by the given destination name.*

*intensity-component image-name dest-image-name: Create an image with the
intensity-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.*

*help-- lists all commands and their usage.*

*sepia image-name dest-image-name: produce a sepia-toned version of
the given image and store the result in another image with the given name.*

*rgb-split image-name dest-image-name-red dest-image-name-green
dest-image-name-blue: split the given image into three images containing
its red, green and blue components respectively. These would be the same
images that would be individually produced with the red-component,
green-component and blue-component commands.*

*rgb-combine image-name red-image green-image blue-image: Combine the
three greyscale images into a single image that gets its red, green and
blue components from the three images respectively.*

*load image-path image-name: Load an image from the specified path and refer
to it henceforth in the program by the given image name.*

*vertical-flip image-name dest-image-name: Flip an image vertically
to create a new image, referred to henceforth by the given destination name.*

*value-component image-name dest-image-name: Create an image with the
value-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.*

*quit-- quits program*

*luma-component image-name dest-image-name: Create an image with the
luma-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.*

*blue-component image-name dest-image-name: Create an image with the
blue-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.*

*red-component image-name dest-image-name: Create an image with the
red-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.*

**Model**  
**View**  
**Controller**  
