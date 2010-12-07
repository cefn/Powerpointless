/**
 * 
 */
package com.cefn.powerpointless.slides;

import com.cefn.powerpointless.App;
import com.cefn.powerpointless.Slide;

import processing.core.PApplet;
import processing.core.PGraphics;

public class SerialSlide extends MultiSlide{
  int current = 0;
  public SerialSlide(Slide[] slides){
    super(slides);
  }

  public boolean handleFrame(PGraphics g, float frame){
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

  public boolean keyPressed(PApplet env){
      if(env.keyCode == App.RIGHT){
        nextSlide();
        return true;
      }
      else if(env.keyCode == App.LEFT){
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