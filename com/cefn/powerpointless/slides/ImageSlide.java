/**
 * 
 */
package com.cefn.powerpointless.slides;

import processing.core.PGraphics;
import processing.core.PImage;

public class ImageSlide extends AbstractSlide{
  PImage img;
  public ImageSlide(PImage img){
    this.img = img;
  }
  public boolean handleFrame(PGraphics g, float frame){
    super.handleFrame(g,frame);
    g.image(img,0,0,g.width,g.height);
    return true;
  }
}