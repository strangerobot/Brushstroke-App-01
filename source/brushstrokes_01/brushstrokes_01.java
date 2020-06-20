import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import g4p_controls.*; 
import processing.pdf.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class brushstrokes_01 extends PApplet {



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

public void setup()
{
  selectInput("Select an image file:", "fileSelected");
  //surface.setResizable(true);
  
  noStroke();
  createGUI();
  rectMode(RADIUS);


}


public void draw()
{
 
  if(changed && image!=null)
  drawimage();

}

public void drawimage()
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
    

    float scale=(r*rselected+g*gselected+b*bselected)/(255*(rselected+gselected+bselected+0.1f));
    
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
public void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    println("User selected " + selection.getAbsolutePath());
    image= loadImage(selection.getAbsolutePath());
    fileresize();
  }
}

public void fileresize()
{
 image.resize(10*resolutionval,10*resolutionval); 
}
/* =========================================================
 * ====                   WARNING                        ===
 * =========================================================
 * The code in this tab has been generated from the GUI form
 * designer and care should be taken when editing this file.
 * Only add/edit code inside the event handlers i.e. only
 * use lines between the matching comment tags. e.g.

 void myBtnEvents(GButton button) { //_CODE_:button1:12356:
     // It is safe to enter your event code here  
 } //_CODE_:button1:12356:
 
 * Do not rename this tab!
 * =========================================================
 */

public void scalesilderevent(GCustomSlider source, GEvent event) { //_CODE_:scaleslider:577184:
  println("scaleslider - GCustomSlider >> GEvent." + event + " @ " + millis());
  size=source.getValueI();
  changed=true;

} //_CODE_:scaleslider:577184:

public void transp_event(GCustomSlider source, GEvent event) { //_CODE_:transp:544442:
  println("transp - GCustomSlider >> GEvent." + event + " @ " + millis());
  transparency=source.getValueI();
  changed=true;
} //_CODE_:transp:544442:

public void button1_click1(GButton source, GEvent event) { //_CODE_:button1:901599:
  println("button1 - GButton >> GEvent." + event + " @ " + millis());
  recording=true;

   changed=true;
} //_CODE_:button1:901599:

public void randomnessevent(GCheckbox source, GEvent event) { //_CODE_:randomness:920193:
  println("randomness - GCheckbox >> GEvent." + event + " @ " + millis());
  randomnessval=!randomnessval;
  changed=true;
} //_CODE_:randomness:920193:

public void square_clicked1(GOption source, GEvent event) { //_CODE_:square:220890:
  println("square - GOption >> GEvent." + event + " @ " + millis());
  selectedshape=1;
  changed=true;
} //_CODE_:square:220890:

public void rectangle_clicked1(GOption source, GEvent event) { //_CODE_:rectangle:931373:
  println("rectangle - GOption >> GEvent." + event + " @ " + millis());
  selectedshape=2;
  changed=true;
} //_CODE_:rectangle:931373:

public void circle_clicked1(GOption source, GEvent event) { //_CODE_:circle:926468:
  println("circle - GOption >> GEvent." + event + " @ " + millis());
  selectedshape=3;
  changed=true;
} //_CODE_:circle:926468:

public void ellipse_clicked1(GOption source, GEvent event) { //_CODE_:ellipsee:900198:
  println("ellipsee - GOption >> GEvent." + event + " @ " + millis());
  selectedshape=4;
  changed=true;
} //_CODE_:ellipsee:900198:

public void amplitude_change1(GSlider source, GEvent event) { //_CODE_:amplitude:827565:
  println("amplitude - GSlider >> GEvent." + event + " @ " + millis());
  noiseamplitude=source.getValueF();
  changed=true;
} //_CODE_:amplitude:827565:

public void button2_click1(GButton source, GEvent event) { //_CODE_:button2:731896:
  println("button2 - GButton >> GEvent." + event + " @ " + millis());
  selectInput("Select an image file:", "fileSelected");
  changed=true;
} //_CODE_:button2:731896:

public void button3_click1(GButton source, GEvent event) { //_CODE_:button3:893049:
  println("button3 - GButton >> GEvent." + event + " @ " + millis());
  changed=true;
} //_CODE_:button3:893049:

public void Rr_clicked1(GCheckbox source, GEvent event) { //_CODE_:Rr:322301:
  println("Rr - GCheckbox >> GEvent." + event + " @ " + millis());
  if (source.isSelected())
  rselected=1;
  else
  rselected=0;
  changed=true;
} //_CODE_:Rr:322301:

public void Gg_clicked1(GCheckbox source, GEvent event) { //_CODE_:Gg:583692:
  if (source.isSelected())
  gselected=1;
  else
  gselected=0;
  changed=true;
  println("checkbox1 - GCheckbox >> GEvent." + event + " @ " + millis());
} //_CODE_:Gg:583692:

public void Bb_clicked1(GCheckbox source, GEvent event) { //_CODE_:Bb:586438:
  println("Bb - GCheckbox >> GEvent." + event + " @ " + millis());
  if (source.isSelected())
  bselected=1;
  else
  bselected=0;
  changed=true;
} //_CODE_:Bb:586438:

public void resolution_change1(GSlider source, GEvent event) { //_CODE_:resolution:670958:
  println("resolution - GSlider >> GEvent." + event + " @ " + millis());
  resolutionval=source.getValueI();
  fileresize();
  changed=true;
} //_CODE_:resolution:670958:



// Create all the GUI controls. 
// autogenerated do not edit
public void createGUI(){
  G4P.messagesEnabled(false);
  G4P.setGlobalColorScheme(GCScheme.BLUE_SCHEME);
  G4P.setMouseOverEnabled(false);
  surface.setTitle("Brushstrokes");
  scaleslider = new GCustomSlider(this, 16, 80, 200, 32, "grey_blue");
  scaleslider.setShowValue(true);
  scaleslider.setShowLimits(true);
  scaleslider.setLimits(10, 1, 255);
  scaleslider.setNumberFormat(G4P.INTEGER, 0);
  scaleslider.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  scaleslider.setOpaque(true);
  scaleslider.addEventHandler(this, "scalesilderevent");
  transp = new GCustomSlider(this, 16, 160, 200, 40, "grey_blue");
  transp.setShowValue(true);
  transp.setShowLimits(true);
  transp.setLimits(255.0f, 1.0f, 255.0f);
  transp.setNumberFormat(G4P.DECIMAL, 2);
  transp.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  transp.setOpaque(true);
  transp.addEventHandler(this, "transp_event");
  label2 = new GLabel(this, 16, 144, 80, 16);
  label2.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label2.setText("Transparency");
  label2.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  label2.setOpaque(true);
  label1 = new GLabel(this, 16, 64, 112, 16);
  label1.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label1.setText("Stroke Size");
  label1.setLocalColorScheme(GCScheme.YELLOW_SCHEME);
  label1.setOpaque(true);
  button1 = new GButton(this, 17, 710, 206, 52);
  button1.setText("save pdf and jpg");
  button1.setLocalColorScheme(GCScheme.RED_SCHEME);
  button1.addEventHandler(this, "button1_click1");
  randomness = new GCheckbox(this, 16, 384, 120, 16);
  randomness.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
  randomness.setText("randomness");
  randomness.setLocalColorScheme(GCScheme.RED_SCHEME);
  randomness.setOpaque(true);
  randomness.addEventHandler(this, "randomnessevent");
  togGroup1 = new GToggleGroup();
  square = new GOption(this, 16, 288, 120, 16);
  square.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
  square.setText("square");
  square.setOpaque(true);
  square.addEventHandler(this, "square_clicked1");
  rectangle = new GOption(this, 16, 272, 120, 16);
  rectangle.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
  rectangle.setText("rectangle");
  rectangle.setOpaque(true);
  rectangle.addEventHandler(this, "rectangle_clicked1");
  circle = new GOption(this, 16, 256, 120, 16);
  circle.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
  circle.setText("circle");
  circle.setOpaque(true);
  circle.addEventHandler(this, "circle_clicked1");
  ellipsee = new GOption(this, 16, 240, 120, 16);
  ellipsee.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
  ellipsee.setText("ellipse");
  ellipsee.setOpaque(true);
  ellipsee.addEventHandler(this, "ellipse_clicked1");
  togGroup1.addControl(square);
  togGroup1.addControl(rectangle);
  rectangle.setSelected(true);
  togGroup1.addControl(circle);
  togGroup1.addControl(ellipsee);
  amplitude = new GSlider(this, 16, 432, 175, 36, 10.0f);
  amplitude.setShowValue(true);
  amplitude.setShowLimits(true);
  amplitude.setLimits(1.0f, 1.0f, 400.0f);
  amplitude.setShowTicks(true);
  amplitude.setNumberFormat(G4P.DECIMAL, 0);
  amplitude.setLocalColorScheme(GCScheme.RED_SCHEME);
  amplitude.setOpaque(true);
  amplitude.addEventHandler(this, "amplitude_change1");
  label3 = new GLabel(this, 16, 416, 80, 16);
  label3.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label3.setText("scale");
  label3.setLocalColorScheme(GCScheme.RED_SCHEME);
  label3.setOpaque(true);
  button2 = new GButton(this, 16, 16, 109, 32);
  button2.setText("New Image");
  button2.addEventHandler(this, "button2_click1");
  button3 = new GButton(this, 144, 16, 83, 32);
  button3.setText("Redraw");
  button3.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  button3.addEventHandler(this, "button3_click1");
  label4 = new GLabel(this, 16, 544, 124, 20);
  label4.setText("Color Channel");
  label4.setOpaque(true);
  Rr = new GCheckbox(this, 16, 560, 120, 16);
  Rr.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
  Rr.setText("R");
  Rr.setOpaque(true);
  Rr.addEventHandler(this, "Rr_clicked1");
  Rr.setSelected(true);
  Gg = new GCheckbox(this, 16, 576, 120, 16);
  Gg.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
  Gg.setText("G");
  Gg.setOpaque(true);
  Gg.addEventHandler(this, "Gg_clicked1");
  Gg.setSelected(true);
  Bb = new GCheckbox(this, 16, 592, 120, 16);
  Bb.setIconAlign(GAlign.LEFT, GAlign.MIDDLE);
  Bb.setText("B");
  Bb.setOpaque(true);
  Bb.addEventHandler(this, "Bb_clicked1");
  Bb.setSelected(true);
  resolution = new GSlider(this, 240, 32, 155, 20, 10.0f);
  resolution.setShowValue(true);
  resolution.setShowLimits(true);
  resolution.setLimits(1, 1, 100);
  resolution.setShowTicks(true);
  resolution.setNumberFormat(G4P.INTEGER, 0);
  resolution.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  resolution.setOpaque(true);
  resolution.addEventHandler(this, "resolution_change1");
  label5 = new GLabel(this, 240, 16, 155, 20);
  label5.setTextAlign(GAlign.CENTER, GAlign.MIDDLE);
  label5.setText("Resolution");
  label5.setLocalColorScheme(GCScheme.GREEN_SCHEME);
  label5.setOpaque(true);
}

// Variable declarations 
// autogenerated do not edit
GCustomSlider scaleslider; 
GCustomSlider transp; 
GLabel label2; 
GLabel label1; 
GButton button1; 
GCheckbox randomness; 
GToggleGroup togGroup1; 
GOption square; 
GOption rectangle; 
GOption circle; 
GOption ellipsee; 
GSlider amplitude; 
GLabel label3; 
GButton button2; 
GButton button3; 
GLabel label4; 
GCheckbox Rr; 
GCheckbox Gg; 
GCheckbox Bb; 
GSlider resolution; 
GLabel label5; 
  public void settings() {  size(800,800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "brushstrokes_01" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
