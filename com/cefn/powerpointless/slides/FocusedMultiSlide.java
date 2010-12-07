/**
 * 
 */
package com.cefn.powerpointless.slides;

import com.cefn.powerpointless.App;
import com.cefn.powerpointless.Slide;

import processing.core.PApplet;


public abstract class FocusedMultiSlide extends MultiSlide{
  int focus = -1;
  
  public FocusedMultiSlide(Slide[] slides){super(slides);}
  
  public boolean keyPressed(PApplet env){
    if(getFocusedSlide() != null && getFocusedSlide().keyPressed(env)){
          return true;
    }
    else if (focus >= 0 && env.keyCode == App.BACKSPACE){ //back up
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