/**
 * 
 */
package com.cefn.powerpointless.slides;

import com.cefn.powerpointless.App;
import com.cefn.powerpointless.Slide;

import processing.core.PGraphics;


public class GridMultiSlide extends TweenMultiSlide{
  float rows,cols;
  float rowshare, colshare;
  public GridMultiSlide(Slide[] slides){
    this(slides, 3, 3);
  }
  public GridMultiSlide(Slide[] slides, float rows, float cols){
    super(slides);
    this.rows = rows;
    this.cols = cols;
    this.rowshare = App.PI / (3 * rows);
    this.colshare = App.PI / (3 * cols);
  }
  
  public boolean handleFrame(PGraphics g, float frame){
    return super.handleFrame(g, frame);
  }
 
  void applyTween(int i, PGraphics g){
    float hpos = ((cols - 1) / 2.0f) - App.floor(i % rows);
    float vpos = App.floor(i / rows) - ((rows - 1) / 2.0f);
    g.rotateX(rowshare * vpos * tween[i]);
    g.rotateY(colshare * hpos * tween[i]);
    g.translate(0, 0, -g.width * 3 * tween[i]);
  }

}