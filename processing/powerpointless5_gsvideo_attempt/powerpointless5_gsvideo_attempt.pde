import codeanticode.gsvideo.*;
import codeanticode.glgraphics.*;

import processing.opengl.*;

import fullscreen.*; 

fullscreen.FullScreen fullscreen = null;

float frame = 0;
int[] res = {
  640,480,
  800,600,
  1024,768,
  1400,900
};

int chosenres = 1;

PFont stagefont = createFont("Helvetica",24);
String stagemsg = "End of Slideshow\nBrought to you by Powerpointless\nA Curiosity Collective Project";
Slide stage = null;

Slide content = null;

Slide authoredContent(){
  MovieSlide movie1 = slideMovie(dataPath("1.mp4"));
  MovieSlide movie2 = slideMovie(dataPath("2.mp4"));
  MovieSlide movie3 = slideMovie(dataPath("3.mp4"));
  return slideSeries(new Slide[]{
        slideImage(dataPath("slide03.jpg")),
        movie1,
        movie2,
        movie3,
        slidePetal(new Slide[]{movie1,movie2,movie3}),
        slidePetal(new Slide[]{
          movie1,
          slideImage(dataPath("slide03.jpg")), slideImage(dataPath("slide03.jpg")), slideImage(dataPath("slide03.jpg")), slideImage(dataPath("slide03.jpg")),
          slideWrap(slideGrid(new Slide[]{
            slideImage(dataPath("slide03.jpg")), movie1, slideImage(dataPath("slide03.jpg")), 
            movie2, slideImage(dataPath("slide03.jpg")), movie3, 
            slideImage(dataPath("slide03.jpg")), movie1, slideImage(dataPath("slide03.jpg"))
          })),
        })
  });
}

void setup(){
  size(resWidth(), resHeight(), P3D); //GLConstants.GLGRAPHICS
  frameRate(30);
  fullscreen = new fullscreen.FullScreen(this);
  //fullscreen.enter();
  setupStage();
  setupContent();
}

void setupStage(){
  PGraphics stageimg = createGraphics(resWidth(), resHeight(), P3D);
  stageimg.beginDraw();
  stageimg.background(0);
  stageimg.fill(255);
  stageimg.stroke(255);
  stageimg.textFont(stagefont);
  float msgwidth = stageimg.textWidth(stagemsg);
  stageimg.text(stagemsg, (resWidth() - msgwidth)/2, resHeight()/2);
  stageimg.endDraw();
  stage = new ImageSlide(stageimg){
    boolean keyPressed(){
      if(key=='r' || key=='R'){
        nextRes();
        return true;
      }
      return false;
    }
  };
}

void setupContent(){
  content = authoredContent();
  if(content == null){ //if content missing, autofill from directory
    SerialSlide auto = new SerialSlide(slidesFromDir(sketchPath(""),"jpg"));
    if(auto == null){ //if no files in directory, print error on stage
      stagemsg = "No images found";
      setupStage();
      content = stage;
    }
    else{ //otherwise run the slides
      content = auto;
    }
  }
}

MovieSlide slideMovie(String filename){
  return new MovieSlide(this, filename);
}

Slide slideImage(String filename){
  return new ImageSlide(loadImage(filename));
}

Slide slideSeries(Slide[] slides){
  return new SerialSlide(slides);
}

Slide slidePetal(Slide[] slides){
  return new PetalMultiSlide(slides);
}

Slide slideGrid(Slide[] slides){
  return new GridMultiSlide(slides);
}

Slide slideWrap(Slide wrapped){
  return new WrappedSlide(wrapped);
}

void draw(){
  g.background(0);
  if(!content.handleFrame(g, frame)){
    stage.handleFrame(g,frame);
  }
  frame++;
}

void movieEvent(GSMovie movie) {
  movie.read();
}

void keyPressed(){
  if(!content.keyPressed(this)){
    stage.keyPressed(this);
  }
}

void mousePressed(){
  if(!content.mousePressed(this)){
    stage.mousePressed(this);  
  }
}

void mouseReleased(){
  if(!content.mouseReleased(this)){
    stage.mouseReleased(this);  
  }
}

void mouseClicked(){
  if(!content.mouseClicked(this)){
    stage.mouseClicked(this);  
  }
}

void mouseMoved(){
  if(!content.mouseMoved(this)){
    stage.mouseMoved(this);  
  }
}

void mouseDragged(){
  if(!content.mouseDragged(this)){
    stage.mouseDragged(this);  
  }
}

Slide[] slidesFromDir(String dirname, final String filematch){
  File dir = new File(dirname);
  File[] files = dir.listFiles(new FilenameFilter(){ 
    public boolean accept(File dir, String name) {
      return name.indexOf(filematch) != -1;
    }
  }
  );
  Slide[] slides = new Slide[files.length];
  for(int idx = 0; idx < files.length; idx++){
    slides[idx] = new ImageSlide(loadImage(files[idx].getAbsolutePath()));
  }
  return slides;
}

int resWidth(){
  return res[chosenres * 2];
}

int resHeight(){
  return res[(chosenres * 2) + 1];
}

void nextRes(){
  int newres = chosenres;
  newres += 1;
  if(newres >= res.length / 2){
    newres = 0;
  }
  chooseRes(newres);
}

void chooseRes(int newres){
  chosenres = newres;
  setRes(resWidth(), resHeight());
}

void setRes(int w, int h){
  g.resize(w,h);
  //fullscreen.setResolution(w, h);
  setupStage();
}

/** SLIDE DEFS */

interface Slide{
  boolean handleFrame(PGraphics g, float frame);
  boolean mousePressed(PApplet env);
  boolean mouseReleased(PApplet env);
  boolean mouseClicked(PApplet env);
  boolean mouseMoved(PApplet env);
  boolean mouseDragged(PApplet env);
  boolean keyPressed(PApplet env);
  boolean keyReleased(PApplet env);
  boolean keyTyped(PApplet env);  
  public static float MAX_FLOAT_INT = 16777216;
}

static abstract class AbstractSlide implements Slide{
  float rendered = 0;
  float lastframe = 0;
  boolean handleFrame(PGraphics g, float frame){
    rendered += frame - lastframe;
    return true;
  }
  boolean mousePressed(PApplet env){
    return false;
  }
  boolean mouseReleased(PApplet env){
    return false;
  }
  boolean mouseClicked(PApplet env){
    return false;
  }
  boolean mouseMoved(PApplet env){
    return false;
  }
  boolean mouseDragged(PApplet env){
    return false;
  }
  boolean keyPressed(PApplet env){
    return false;
  }
  boolean keyReleased(PApplet env){
    return false;
  }
  boolean keyTyped(PApplet env){
    return false;
  }
}

public static class ImageSlide extends AbstractSlide{
  PImage img;
  public ImageSlide(PImage img){
    this.img = img;
  }
  boolean handleFrame(PGraphics g, float frame){
    super.handleFrame(g,frame);
    g.image(img,0,0,g.width,g.height);
    return true;
  }
}

static class MovieSlide extends AbstractSlide {
  GSMovie mov;
  GLTexture tex;
  
  public MovieSlide(PApplet applet, String filename){
    this.mov = new GSMovie(applet, filename);
    //this.tex = new GLTexture(applet);
    mov.loop();
  }
  
  boolean handleFrame(PGraphics g, float frame){
    super.handleFrame(g, frame);
    //Should work according to http://users.design.ucla.edu/~acolubri/processing/gsvideo/home/advanced.html
    //tex.putPixelsIntoTexture(this.mov);
    //g.image(tex,0,0,g.width,g.height);
    g.image(this.mov,0,0,g.width,g.height);
    return true;
  }
  
}


public static class WrappedSlide extends AbstractSlide{
  Slide wrapped;
  PGraphics wrappedg = null;
  public WrappedSlide(Slide wrapped){
    this.wrapped = wrapped;
  }
  boolean handleFrame(PGraphics g, float frame){
    PGraphics wg = wrapGraphics(g.parent);
    wg.beginDraw();
    wg.background(0);
    wrapped.handleFrame(wg, frame);
    g.image(wg, 0, 0, g.width, g.height);
    wg.endDraw();
    return true;
  }
  PGraphics wrapGraphics(PApplet applet){
      if(wrappedg == null){
        wrappedg = applet.createGraphics(applet.width, applet.height, P3D);
      }
      return wrappedg;
  }
  boolean mousePressed(PApplet env){
    return wrapped.mousePressed(env);
  }
  boolean mouseReleased(PApplet env){
    return wrapped.mouseReleased(env);
  }
  boolean mouseClicked(PApplet env){
    return wrapped.mouseClicked(env);
  }
  boolean mouseMoved(PApplet env){
    return wrapped.mouseMoved(env);
  }
  boolean mouseDragged(PApplet env){
    return wrapped.mouseDragged(env);
  }
  boolean keyPressed(PApplet env){
    return wrapped.keyPressed(env);
  }
  boolean keyReleased(PApplet env){
    return wrapped.keyReleased(env);
  }
  boolean keyTyped(PApplet env){
    return wrapped.keyTyped(env);
  }
}

public static abstract class MultiSlide extends AbstractSlide{
  Slide[] slides;
  public MultiSlide(Slide[] slides){
    this.slides = slides;
  }
  Slide getSlide(float current){
    if(current < MAX_FLOAT_INT){
      return getSlide((int)current);    
    }
    else{ 
      throw new RuntimeException("Operating outside safe integer range for floats"); 
    }
  }  
  Slide getSlide(int current){
    return slides[current];
  }
}

public static class SerialSlide extends MultiSlide{
  int current = 0;
  public SerialSlide(Slide[] slides){
    super(slides);
  }

  boolean handleFrame(PGraphics g, float frame){
    super.handleFrame(g, frame);
    if(currentSlide() != null){
      if(currentSlide().handleFrame(g, rendered)){
        return true;
      }
      else{
        nextSlide();
        return handleFrame(g, rendered);
      }
    }
    return false;
  }

  boolean keyPressed(PApplet env){
      if(env.keyCode == RIGHT){
        nextSlide();
        return true;
      }
      else if(env.keyCode == LEFT){
        lastSlide();
        return true;
      }
      else if(currentSlide() != null){
        return currentSlide().keyPressed(env);
      }
    return false;
  }

  Slide currentSlide(){
    if(current >=0 && current < slides.length){
      return getSlide(current);
    }
    else{
      return null;
    }
  }

  void lastSlide(){
    rendered = 0;
    current--;  
  }

  void nextSlide(){
    rendered = 0;
    current++;
  }

}

public static abstract class FocusedMultiSlide extends MultiSlide{
  int focus = -1;
  
  public FocusedMultiSlide(Slide[] slides){super(slides);}
  
  boolean keyPressed(PApplet env){
    if(getFocusedSlide() != null && getFocusedSlide().keyPressed(env)){
          return true;
    }
    else if (focus >= 0 && env.keyCode == BACKSPACE){ //back up
      focus = -1;
      return true;
    }
    else{
      if(Character.isDigit(env.key)){ //drill down
        int digit = Character.getNumericValue(env.key);
        if(digit >= 0 && digit <= slides.length){
          if(digit == 0 || focus == digit - 1){
            focus = -1;        
          }
          else{
            focus = digit - 1;
          }
          return true;
        }
      }
    }
    //pass event to focused slide if unhandled
    return false;
  }
  
  Slide getFocusedSlide(){
    return (focus >= 0 && focus < slides.length? slides[focus] : null);
  }

}

public static abstract class TweenMultiSlide extends FocusedMultiSlide{
  float[] tween;
  
  public TweenMultiSlide(Slide[] slides){
    super(slides);
    tween = new float[slides.length];
    Arrays.fill(tween, 1);
  }
  
  boolean handleFrame(PGraphics g, float frame){
    for(int i = 0; i < slides.length; i++){
        g.pushMatrix();
        applyTween(i, g);
        getSlide(i).handleFrame(g, frame);
        g.popMatrix();
    }
    retween();
    return true;
  }

  void retween(){
    float[] sorted = (float[])tween.clone();
    Arrays.sort(sorted);
    for(int i = 0; i < tween.length;i++){
      if(i == focus){
        if(Arrays.binarySearch(sorted, 0.9) <= 0){ //brings selected forward if others are at the back
          tween[i] = tween[i] * 0.9;          
        }
      }
      else{
        tween[i] = Math.max(tween[i], 0.01); //handles infinitesimal
        tween[i] = Math.min(1.0, tween[i] * 1.1); //makes unselected more twisted and distant
      }
    }
  }
  
  abstract void applyTween(int i, PGraphics g);
  
}

public static class PetalMultiSlide extends TweenMultiSlide{

  float sector;
  float petalscale;

  public PetalMultiSlide(Slide[] slides){
    super(slides);
    sector = TWO_PI / slides.length;
    petalscale = 1.0/sqrt(slides.length);
  }

  void applyTween(int i, PGraphics g){
    g.rotateY(sin(sector * i) * petalscale * tween[i]);
    g.rotateX(-cos(sector * i) * petalscale * tween[i]);
    g.translate(0, 0, -g.width * 3 * tween[i]);
    g.scale(1.0 + ((petalscale - 1.0) * tween[i]));
  }

}

public static class GridMultiSlide extends TweenMultiSlide{
  float rows,cols;
  float rowshare, colshare;
  public GridMultiSlide(Slide[] slides){
    this(slides, 3, 3);
  }
  public GridMultiSlide(Slide[] slides, float rows, float cols){
    super(slides);
    this.rows = rows;
    this.cols = cols;
    this.rowshare = PI / (3 * rows);
    this.colshare = PI / (3 * cols);
  }
  
  boolean handleFrame(PGraphics g, float frame){
    return super.handleFrame(g, frame);
  }
 
  void applyTween(int i, PGraphics g){
    float hpos = ((cols - 1) / 2.0) - floor(i % rows);
    float vpos = floor(i / rows) - ((rows - 1) / 2.0);
    g.rotateX(rowshare * vpos * tween[i]);
    g.rotateY(colshare * hpos * tween[i]);
    g.translate(0, 0, -g.width * 3 * tween[i]);
  }

}

