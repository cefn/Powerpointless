/**
 * 
 */
package com.cefn.powerpointless.slides;

import com.cefn.powerpointless.Slide;

public abstract class MultiSlide extends AbstractSlide{
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