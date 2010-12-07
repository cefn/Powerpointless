/**
 * 
 */
package com.cefn.powerpointless.slides;

import processing.core.PApplet;
import processing.core.PGraphics;
import codeanticode.glgraphics.GLTexture;
import codeanticode.gsvideo.GSMovie;

public class MovieSlide extends AbstractSlide {
  GSMovie mov;
  GLTexture tex;
  
  public MovieSlide(PApplet applet, String filename){
    this.mov = new GSMovie(applet, filename);
    //this.tex = new GLTexture(applet);
    mov.loop();
  }
  
  public boolean handleFrame(PGraphics g, float frame){
    super.handleFrame(g, frame);
    /** Should work according to http://users.design.ucla.edu/~acolubri/processing/gsvideo/home/advanced.html
    tex.putPixelsIntoTexture(this.mov);
    g.image(tex,0,0,g.width,g.height);
    */
    g.image(this.mov,0,0,g.width,g.height);
    return true;
  }
  
}