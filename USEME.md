# Supported Commands: #

*load image-path image-name*: Load an image from the specified path and refer
to it henceforth in the program by the given image name.

*save image-path image-name*: Save the image with the given name to the
specified path which should include the name of the file.

*red-component image-name dest-image-name*: Create an image with the
red-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.

*green-component image-name dest-image-name*: Create an image with the
green-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.

*blue-component image-name dest-image-name*: Create an image with the
blue-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.

*rgb-split image-name dest-image-name-red dest-image-name-green
dest-image-name-blue*: split the given image into three images containing
its red, green and blue components respectively. These would be the same
images that would be individually produced with the red-component,
green-component and blue-component commands.

*rgb-combine image-name red-image green-image blue-image*: Combine the
three greyscale images into a single image that gets its red, green and
blue components from the three images respectively.

*value-component image-name dest-image-name*: Create an image with the
value-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.

*intensity-component image-name dest-image-name*: Create an image with the
intensity-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.

*luma-component image-name dest-image-name*: Create an image with the
luma-component of the image with the given name, and refer to it henceforth in
the program by the given destination name.

*brighten increment image-name dest-image-name*: brighten the image by the given
increment to create a new image, referred to henceforth by the given destination
name. The increment may be positive (brightening) or negative (darkening).

*horizontal-flip image-name dest-image-name*: Flip an image horizontally
to create a new image, referred to henceforth by the given destination name.

*vertical-flip image-name dest-image-name*: Flip an image vertically
to create a new image, referred to henceforth by the given destination name.

*blur image-name dest-image-name*: blur the given image and
store the result in another image with the given name. 
split p: may be added as two additional parameters if split preview of operation is desired.

*sharpen image-name dest-image-name*: sharpen the given image and
store the result in another image with the given name. 
split p: may be added as two additional parameters if split preview of operation is desired.

*sepia image-name dest-image-name*: produce a sepia-toned version of
the given image and store the result in another image with the given name. 
split p: may be added as two additional parameters if split preview of operation is desired.

*greyscale image-name dest-image-name*: produce a greyscale image and save it with the given name.

*compress percentage image-name dest-image-name*: compress image by given percentage to create a new 
image, referred to henceforth as the destination image name. Percentages between 0 and 100 are 
considered valid.

*histogram image-name dest-image-name*: creates a histogram for the RGB intensities of the given 
image. The image is saved in the database under the destination image name.

*color-correct image-name dest-image-name*: corrects the colors of an image by aligning the 
meaningful peaks of its RGB histogram. Referred to henceforth as dest-image-name. split p: may be 
added as two additional parameters if split preview of operation is desired.

*levels-adjust b m w image-name dest-image-name*: where b, m and w are the three relevant black, 
mid and white values respectively. These values should be ascending in that order, and should be 
within 0 and 255 for this command to work correctly. split p: may be added as two additional 
parameters if split preview of operation is desired.

*split p*: an additional keyword that must go at the end of a command, followed by the
split percentage as an integer [1-99]. This keyword generates an image where the 
first p% of the image shows the result of the operation, whereas the remainder
is unchanged. Only supported by blur, sharpen, sepia, greyscale, color correction and levels 
adjustment.

*help--*: lists all commands and their usage.

*quit--*: quits program

## Example Command Usage ## 
*The following is pulled from the provided script file.* 

#load base image
load resources/mushroom.jpg mushroom

#red-component demo
red-component mushroom mushroom-red
save resources/mushroom_images/mushroom-red.jpg mushroom-red

#green-component demo
green-component mushroom mushroom-green
save resources/mushroom_images/mushroom-green.jpg mushroom-green

#blue-component demo
blue-component mushroom mushroom-blue
save resources/mushroom_images/mushroom-blue.jpg mushroom-blue

#value-component demo
value-component mushroom mushroom-value
save resources/mushroom_images/mushroom-value.jpg mushroom-value

#intensity-component demo
intensity-component mushroom mushroom-intensity
save resources/mushroom_images/mushroom-intensity.jpg mushroom-intensity

#luma-component demo
luma-component mushroom mushroom-luma
save resources/mushroom_images/mushroom-luma.jpg mushroom-luma

#horizontal-flip demo
horizontal-flip mushroom mushroom-horizontal
save resources/mushroom_images/mushroom-horizontal.jpg mushroom-horizontal

#vertical-flip demo
vertical-flip mushroom mushroom-vertical
save resources/mushroom_images/mushroom-vertical.jpg mushroom-vertical

#brighten and darken demo
brighten 25 mushroom mushroom-brighter
brighten -25 mushroom mushroom-darker
save resources/mushroom_images/mushroom-brighter.jpg mushroom-brighter
save resources/mushroom_images/mushroom-darker.jpg mushroom-darker

#blur demo
blur mushroom mushroom-blurred
save resources/mushroom_images/mushroom-blurred.jpg mushroom-blurred

#sepia demo
sepia mushroom mushroom-sepia
save resources/mushroom_images/mushroom-sepia.jpg mushroom-sepia

#sharpen demo
sharpen mushroom mushroom-sharpened
save resources/mushroom_images/mushroom-sharpened.jpg mushroom-sharpened

#rgb-split demo
rgb-split mushroom mushroom-red-comp mushroom-green-comp mushroom-blue-comp
save resources/mushroom_images/mushroom-red-comp.jpg mushroom-red-comp
save resources/mushroom_images/mushroom-green-comp.jpg mushroom-green-comp
save resources/mushroom_images/mushroom-blue-comp.jpg mushroom-blue-comp

#rgb-combine demo
rgb-combine mushroom-combined mushroom-red-comp mushroom-green-comp mushroom-blue-comp
save resources/mushroom_images/mushroom-combined.jpg mushroom-combined

#compression, 50%
compress 50 mushroom mushroom-compressed-50
histogram mushroom-compressed-50 mushroom-compressed-50-hist
save resources/mushroom_images/mushroom-compressed-50.jpg mushroom-compressed-50
save resources/mushroom_images/mushroom-compressed-50-hist.jpg mushroom-compressed-50-hist


#compression, 90%
compress 90 mushroom mushroom-compressed-90
histogram mushroom-compressed-90 mushroom-compressed-90-hist
save resources/mushroom_images/mushroom-compressed-90.jpg mushroom-compressed-90
save resources/mushroom_images/mushroom-compressed-90-hist.jpg mushroom-compressed-90-hist

#color-correction demo
color-correct mushroom mushroom-color-corrected
histogram mushroom-color-corrected mushroom-color-corrected-hist
save resources/mushroom_images/mushroom-color-corrected.png mushroom-color-corrected
save resources/mushroom_images/mushroom-color-corrected-hist.png mushroom-color-corrected-hist

#level-adjustment A
levels-adjust 30 80 200 mushroom mushroom-level-A
histogram mushroom-level-A mushroom-level-A-hist
save resources/mushroom_images/mushroom-level-A.png mushroom-level-A
save resources/mushroom_images/mushroom-level-A-hist.png mushroom-level-A-hist

#level-adjustment B
levels-adjust 70 180 200 mushroom mushroom-level-B
histogram mushroom-level-B mushroom-level-B-hist
save resources/mushroom_images/mushroom-level-B.png mushroom-level-B
save resources/mushroom_images/mushroom-level-B-hist.png mushroom-level-B-hist

#split demo A
sepia mushroom mushroom-splitA split 50
save resources/mushroom_images/mushroom-splitA.jpg mushroom-splitA

#split demo B
color-correct mushroom mushroom-splitB split 50
save resources/mushroom_images/mushroom-splitB.jpg mushroom-splitB


