package com.cefn.powerpointless;

import java.io.File;
import java.io.FilenameFilter;

import com.cefn.powerpointless.slides.GridMultiSlide;
import com.cefn.powerpointless.slides.ImageSlide;
import com.cefn.powerpointless.slides.MovieSlide;
import com.cefn.powerpointless.slides.PetalMultiSlide;
import com.cefn.powerpointless.slides.SerialSlide;
import com.cefn.powerpointless.slides.WrappedSlide;

import codeanticode.gsvideo.*;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

import fullscreen.*; 

public class App extends PApplet{
	
	public static void main(String[] args) {
        PApplet.main(new String[] {App.class.getName()});
    }

	FullScreen fullscreen = null;

	float frame = 0;
	int[] res = {
			640,480,
			800,600,
			1024,768,
			1280,800,
			1400,900
	};

	int chosenres = 1;

	PFont stagefont = createFont("Helvetica",24);
	String stagemsg = "End of Slideshow\nBrought to you by Powerpointless\nA Curiosity Collective Project";
	Slide stage = null;

	Slide content = null;

	String sourcedir = "data/";

	Slide authoredContent(){
		MovieSlide semaphore = slideMovie(sourcedir + "heathcliffe.mov");
		return slideSeries(new Slide[]{
				slideImage("slide03.jpg"),
				slidePetal(slidesFromDir("/home/cefn/Documents/code/powerpointless/katya", "jpg")),
				slidePetal(new Slide[]{
						semaphore,
						slideImage("slide01.jpg"), slideImage("slide02.jpg"), slideImage("slide03.jpg"), slideImage("slide04.jpg"),
						slideWrap(slideGrid(new Slide[]{
								slideImage("slide01.jpg"), semaphore, slideImage("slide02.jpg"), 
								semaphore, slideImage("slide03.jpg"), semaphore, 
								slideImage("slide04.jpg"), semaphore, slideImage("slide02.jpg")
						})),
				})
		});
	}

	public void setup(){
		size(resWidth(), resHeight(), P3D);
		frameRate(15);
		fullscreen = new FullScreen(this);
		fullscreen.setShortcutsEnabled(true);
		setupStage();
		setupContent();
	}

	void setupStage(){
		PGraphics stageimg = createGraphics(resWidth(), resHeight(), P3D);
		stageimg.beginDraw();
		stageimg.background(0);
		stageimg.fill(255);
		stageimg.stroke(255);
		stageimg.textFont(stagefont);
		float msgwidth = stageimg.textWidth(stagemsg);
		stageimg.text(stagemsg, (resWidth() - msgwidth)/2, resHeight()/2);
		stageimg.endDraw();
		stage = new ImageSlide(stageimg){
			boolean keyPressed(){
				if(key=='r' || key=='R'){
					nextRes();
					return true;
				}
				return false;
			}
		};
	}

	void setupContent(){
		content = authoredContent();
		if(content == null){ //if content missing, autofill from directory
			SerialSlide auto = new SerialSlide(slidesFromDir(sketchPath(""),"jpg"));
			if(auto == null){ //if no files in directory, print error on stage
				stagemsg = "No images found";
				setupStage();
				content = stage;
			}
			else{ //otherwise run the slides
				content = auto;
			}
		}
	}

	MovieSlide slideMovie(String filename){
		return new MovieSlide(this, filename);
	}

	Slide slideImage(String filename){
		return new ImageSlide(loadImage(filename));
	}

	Slide slideSeries(Slide[] slides){
		return new SerialSlide(slides);
	}

	Slide slidePetal(Slide[] slides){
		return new PetalMultiSlide(slides);
	}

	Slide slideGrid(Slide[] slides){
		return new GridMultiSlide(slides);
	}

	Slide slideWrap(Slide wrapped){
		return new WrappedSlide(wrapped);
	}

	public void draw(){
		g.background(0);
		if(!content.handleFrame(g, frame)){
			stage.handleFrame(g,frame);
		}
		frame++;
	}

	public void movieEvent(GSMovie movie) {
		movie.read();
	}

	public void keyPressed(){
		if(!content.keyPressed(this)){
			stage.keyPressed(this);
		}
	}

	public void mousePressed(){
		if(!content.mousePressed(this)){
			stage.mousePressed(this);  
		}
	}

	public void mouseReleased(){
		if(!content.mouseReleased(this)){
			stage.mouseReleased(this);  
		}
	}

	public void mouseClicked(){
		if(!content.mouseClicked(this)){
			stage.mouseClicked(this);  
		}
	}

	public void mouseMoved(){
		if(!content.mouseMoved(this)){
			stage.mouseMoved(this);  
		}
	}

	public void mouseDragged(){
		if(!content.mouseDragged(this)){
			stage.mouseDragged(this);  
		}
	}

	Slide[] slidesFromDir(String dirname, final String filematch){
		File dir = new File(dirname);
		File[] files = dir.listFiles(new FilenameFilter(){ 
			public boolean accept(File dir, String name) {
				return name.indexOf(filematch) != -1;
			}
		}
		);
		Slide[] slides = new Slide[files.length];
		for(int idx = 0; idx < files.length; idx++){
			slides[idx] = new ImageSlide(loadImage(files[idx].getAbsolutePath()));
		}
		return slides;
	}

	int resWidth(){
		return res[chosenres * 2];
	}

	int resHeight(){
		return res[(chosenres * 2) + 1];
	}

	void nextRes(){
		int newres = chosenres;
		newres += 1;
		if(newres >= res.length / 2){
			newres = 0;
		}
		chooseRes(newres);
	}

	void chooseRes(int newres){
		chosenres = newres;
		setRes(resWidth(), resHeight());
	}

	void setRes(int w, int h){
		g.resize(w,h);
		fullscreen.setResolution(w, h);
		setupStage();
	}

}

