package com.cefn.powerpointless;

import processing.core.PApplet;
import processing.core.PGraphics;

/** SLIDE DEFS */

public interface Slide{
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