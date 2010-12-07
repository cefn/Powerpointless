/**
 * 
 */
package com.cefn.powerpointless.slides;

import com.cefn.powerpointless.App;
import com.cefn.powerpointless.Slide;

import processing.core.PGraphics;


public class PetalMultiSlide extends TweenMultiSlide{

  float sector;
  float petalscale;

  public PetalMultiSlide(Slide[] slides){
    super(slides);
    sector = App.TWO_PI / slides.length;
    petalscale = 1.0f/App.sqrt(slides.length);
  }

  void applyTween(int i, PGraphics g){
    g.rotateY(App.sin(sector * i) * petalscale * tween[i]);
    g.rotateX(-App.cos(sector * i) * petalscale * tween[i]);
    g.translate(0, 0, -g.width * 3 * tween[i]);
    g.scale(1.0f + ((petalscale - 1.0f) * tween[i]));
  }

}