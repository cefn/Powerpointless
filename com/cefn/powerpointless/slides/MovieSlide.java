/**
 * 
 */
package com.cefn.powerpointless.slides;

import org.gstreamer.Bus;
import org.gstreamer.ClockTime;
import org.gstreamer.GstObject;

import com.cefn.powerpointless.CefnPipeline;

import processing.core.PApplet;
import processing.core.PGraphics;
import codeanticode.glgraphics.GLTexture;
import codeanticode.gsvideo.GSMovie;
import codeanticode.gsvideo.GSPipeline;

public class MovieSlide extends AbstractSlide {
  //GSMovie mov;
  CefnPipeline pipe;
  //GLTexture tex;
  
  public MovieSlide(PApplet applet, String filename){
	  this.pipe = new CefnPipeline(applet,filename);
	/*
    this.mov = new GSMovie(applet, filename);
    //this.tex = new GLTexture(applet);
    mov.loop();
    */
  }
  
  public boolean handleFrame(PGraphics g, float frame){
    super.handleFrame(g, frame);
    /** Should work according to http://users.design.ucla.edu/~acolubri/processing/gsvideo/home/advanced.html
    tex.putPixelsIntoTexture(this.mov);
    g.image(tex,0,0,g.width,g.height);
    */
    //g.image(this.mov,0,0,g.width,g.height);
    g.image(this.pipe,0,0,g.width,g.height);
    return true;
  }
  
}