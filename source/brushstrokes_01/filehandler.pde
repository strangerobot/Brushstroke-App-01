void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    println("User selected " + selection.getAbsolutePath());
    original= loadImage(selection.getAbsolutePath());
    fileresize();
  }
}

PImage original;

void fileresize()
{
 
 image= original.get();
 image.resize(10*resolutionval,10*resolutionval); 
}
