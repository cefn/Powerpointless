/**
 * 
 */
package com.cefn.powerpointless.slides;

import com.cefn.powerpointless.Slide;

import processing.core.PApplet;
import processing.core.PGraphics;

public abstract class AbstractSlide implements Slide{
  float rendered = 0;
  float lastframe = 0;
  public boolean handleFrame(PGraphics g, float frame){
    rendered += frame - lastframe;
    return true;
  }
  public boolean mousePressed(PApplet env){
    return false;
  }
  public boolean mouseReleased(PApplet env){
    return false;
  }
  public boolean mouseClicked(PApplet env){
    return false;
  }
  public boolean mouseMoved(PApplet env){
    return false;
  }
  public boolean mouseDragged(PApplet env){
    return false;
  }
  public boolean keyPressed(PApplet env){
    return false;
  }
  public boolean keyReleased(PApplet env){
    return false;
  }
  public boolean keyTyped(PApplet env){
    return false;
  }
}