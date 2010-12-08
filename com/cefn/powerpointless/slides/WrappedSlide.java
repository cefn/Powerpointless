/**
 * 
 */
package com.cefn.powerpointless.slides;

import codeanticode.glgraphics.GLConstants;

import com.cefn.powerpointless.App;
import com.cefn.powerpointless.Slide;

import processing.core.PApplet;
import processing.core.PGraphics;

public class WrappedSlide extends AbstractSlide{
  Slide wrapped;
  PGraphics wrappedg = null;
  public WrappedSlide(Slide wrapped){
    this.wrapped = wrapped;
  }
  public boolean handleFrame(PGraphics g, float frame){
    PGraphics wg = wrapGraphics(g.parent);
    wg.beginDraw();
    wg.background(0f,0f);
    wrapped.handleFrame(wg, frame);
    g.image(wg, 0, 0, g.width, g.height);
    wg.endDraw();
    return true;
  }
  PGraphics wrapGraphics(PApplet applet){
      if(wrappedg == null){
        wrappedg = applet.createGraphics(applet.width, applet.height, App.P3D);
      }
      return wrappedg;
  }
  public boolean mousePressed(PApplet env){
    return wrapped.mousePressed(env);
  }
  public boolean mouseReleased(PApplet env){
    return wrapped.mouseReleased(env);
  }
  public boolean mouseClicked(PApplet env){
    return wrapped.mouseClicked(env);
  }
  public boolean mouseMoved(PApplet env){
    return wrapped.mouseMoved(env);
  }
  public boolean mouseDragged(PApplet env){
    return wrapped.mouseDragged(env);
  }
  public boolean keyPressed(PApplet env){
    return wrapped.keyPressed(env);
  }
  public boolean keyReleased(PApplet env){
    return wrapped.keyReleased(env);
  }
  public boolean keyTyped(PApplet env){
    return wrapped.keyTyped(env);
  }
}