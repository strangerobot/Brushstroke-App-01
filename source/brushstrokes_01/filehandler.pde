void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    println("User selected " + selection.getAbsolutePath());
    image= loadImage(selection.getAbsolutePath());
    fileresize();
  }
}

void fileresize()
{
 image.resize(10*resolutionval,10*resolutionval); 
}
