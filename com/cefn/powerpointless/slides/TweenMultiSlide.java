/**
 * 
 */
package com.cefn.powerpointless.slides;

import java.util.Arrays;

import com.cefn.powerpointless.Slide;

import processing.core.PGraphics;

public abstract class TweenMultiSlide extends FocusedMultiSlide{
  float[] tween;
  
  public TweenMultiSlide(Slide[] slides){
    super(slides);
    tween = new float[slides.length];
    Arrays.fill(tween, 1);
  }
  
  public boolean handleFrame(PGraphics g, float frame){
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
        if(Arrays.binarySearch(sorted, 0.9f) <= 0){ //brings selected forward if others are at the back
          tween[i] = tween[i] * 0.9f;          
        }
      }
      else{
        tween[i] = Math.max(tween[i], 0.01f); //handles infinitesimal
        tween[i] = Math.min(1.0f, tween[i] * 1.1f); //makes unselected more twisted and distant
      }
    }
  }
  
  abstract void applyTween(int i, PGraphics g);
  
}