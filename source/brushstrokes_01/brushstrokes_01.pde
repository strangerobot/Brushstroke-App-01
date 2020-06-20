import g4p_controls.*;
import processing.pdf.*;
int scaleFactor=10;
PGraphics hires;
int windowsize=800;
PImage image;
int size=10;
int transparency=255;
boolean changed=true;
boolean recording=false;
boolean randomnessval=false;


int selectedshape=2;
int rselected=1,gselected=1,bselected=1;

int resolutionval=4;
float noiseamplitude=1;

void setup()
{
  selectInput("Select an image file:", "fileSelected");
  //surface.setResizable(true);
  size(800,800);
  noStroke();
  createGUI();
  rectMode(RADIUS);


}


void draw()
{
 
  if(changed && image!=null)
  drawimage();

}

void drawimage()
{
 
  if(recording)
  {
   beginRecord(PDF, "output/frame-######.PDF");
   hires = createGraphics(
                        width * scaleFactor,
                        height * scaleFactor,
                        JAVA2D);
  beginRecord(hires);
  hires.strokeWeight(0);
  hires.stroke(0,0);
  hires.scale(scaleFactor);
  }
  
  
  
  background(0); 
  blendMode(NORMAL);
  image.loadPixels();
   
  for (int i = 0; i < image.width*image.height; i++) {
    float r = red(image.pixels[i]);
    float g = green(image.pixels[i]);
    float b = blue(image.pixels[i]);
    
    fill(r,g,b,transparency);
    

    float scale=(r*rselected+g*gselected+b*bselected)/(255*(rselected+gselected+bselected+0.1));
    
    pushMatrix();
    
    translate((i%image.width)*windowsize/image.width,(i/image.width)*windowsize/image.height);
    ///gui switching between randomness
    if(randomnessval)
    rotate(PI*noise((i%image.width)*windowsize/image.width/noiseamplitude,(i/image.width)*windowsize/image.height/noiseamplitude)); 
    
    ///gui switching between shapes
    if(selectedshape==1)
    rect(0,0,size*scale,size*scale);
    else
    if(selectedshape==2)
    rect(0,0,size*scale/4,size*scale);
    else
    if(selectedshape==4)
    ellipse(0,0,size*scale/4,size*scale);
    else if(selectedshape==3)
    ellipse(0,0,size*scale,size*scale);
    
    popMatrix();
}
  image.updatePixels();
  changed=false;
  blendMode(NORMAL);
  if(recording)
   {
   endRecord(); 
   hires.save("output/"+frameCount+"-hires.PNG");
   recording=false;
   }
  
}
