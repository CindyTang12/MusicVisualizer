import processing.core.PApplet;

import processing.core.PFont;
import processing.core.PImage;

import java.io.File;

import ddf.minim.*;
import ddf.minim.analysis.*;

public class PlayerPanel extends PApplet{
	AudioMetaData meta;
	Minim minim;
	AudioPlayer player;
	FFT fft;
	
	int blue = color(33, 57, 55);
	int white = color(237, 221, 187);
	int grey = color(61, 59, 38);
	int black = color(12, 10, 11);
	
	int playButtonAlpha = 150;
	int loadFileAlpha = 150;
	int progressBarAlpha = 150; // bar
	int soundVisionAlpha = 50;  // visual;

	String state = "PLAY";
	String title = "";
	
	boolean fileLoaded = false;
	PFont Arial;
	PImage bckgrd;

	float songProgress;
	float volume = (float) 0.5;
	float volumeSliderX;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("test");
//		Bottons bt = new Bottons();

	}
	
	// method used only for setting the size of the window
    public void settings(){
    	size(400,400);
    	smooth(28);
    }

    // identical use to setup in Processing IDE except for size()
    public void setup(){
        stroke(255);
        strokeWeight(10);
        Arial = loadFont("data/Arial.vlw");
        bckgrd = loadImage("background.png");
        volumeSliderX = width-30;
        minim = new Minim(this);
        
    }

    // identical use to draw in Processing IDE
    public void draw(){
    	background(0);
    	if (fileLoaded) {
//    	    soundVision();                  //Calls visualizer function
    	    player.setGain(volume);
    	    
    	    fill(0, 0, 0, 255);
    	    textSize(21);    //Prints title out
    	    textAlign(CENTER);
    	    text(title, width/2, 30);
    	  }
    	bottons();
    }
    
    public void mouseReleased() {
	  if (fileLoaded && player.position()>=player.length()) {          
	    state = "PLAY";                                        //Resets song when it finishes.
	  }
	  if (mouseX>0 && mouseX<80 && mouseY < 40) {
	    if (state == "PLAY" && fileLoaded) {                              
	      state = "PAUSE";                                  //Plays song.
	      player.play();
	      player.setVolume(volume); //~~~~~~~~~~~~~~~~~~Volume?------------------\\
	    } else if (state == "PAUSE") {
	      state = "PLAY";                                  //Pauses song.
	      player.pause();
	    }
	  }

	  if (mouseX>width-110 && mouseX<width && mouseY < 40) {
	    selectInput("Select a file to process:", "fileSelected");    //Calls selectInput if button pressed.
		
	  }
	}
    
    public void fileSelected(File selection) {
	  if (selection == null) {
	    println("Window was closed or the user hit cancel.");
	  } else {
		String filepath = selection.getAbsolutePath();
	    println("User selected " + filepath);
	    player = minim.loadFile(filepath);
	    fft = new FFT( player.bufferSize(), player.sampleRate());
	    meta = player.getMetaData();
	    fileLoaded = true;
	    title = meta.title();
	  }
	}

    
    public void bottons() {
    	playButton();
    	loadButton();
    	progressBar();
    	volumeSlider();
    }
    
    public void loadButton() {
	  int x = width-110;
  	  int y = 0;
  	  if (mouseX>width-110 && mouseX<width && mouseY < 40) {
  	    stroke(black);
  	    if (loadFileAlpha<255) {
  	      loadFileAlpha+=10;
  	    }
  	  } else if (loadFileAlpha>150) {
  	    loadFileAlpha-=7; 
  	  }
  	  stroke(grey, loadFileAlpha);
	  fill(255, loadFileAlpha);
	  strokeWeight(4);
	  rect(-4+x, -4+y, 120, 40);

	  textAlign(CENTER);
	  fill(black, loadFileAlpha);
	  textSize(10);
	  textFont(Arial);
	  text("Load File", 55+x, 25+y);
    }
    
    public void playButton() {
    	if (mouseX>0 && mouseX<80 && mouseY < 40) {
  	    stroke(black);
  	    if (playButtonAlpha<255) {
  	      playButtonAlpha+=10;
  	    }
  	  } else if (playButtonAlpha>150) {
  	    playButtonAlpha-=7;
  	  }
  	  stroke(white, playButtonAlpha);
  	  fill(255, playButtonAlpha);
  	  strokeWeight(4);
  	  rect(-4, -4, 90, 50);

  	  textAlign(CENTER);
  	  fill(black, playButtonAlpha);
  	  textSize(10);
  	  textFont(Arial);
  	  text(state, 42, 30);
    }
    
    public void progressBar() {
	  if (fileLoaded) songProgress = map(player.position(), 0, player.length(), 0, width);

	  if (mouseY < height-20 && mouseY > 20 && mouseX > 20 && mouseX < width-20 ) {
	    stroke(black);
	    if (progressBarAlpha<255) {
	      progressBarAlpha+=10;
	    }
	  } else if (progressBarAlpha>50) {
	    progressBarAlpha-=7;
	  }

	  fill(blue, progressBarAlpha);
	  stroke(grey, progressBarAlpha);  
	  line(0, height-40, width, height-40);  //static line.

	  fill(black, progressBarAlpha);
	  textSize(14);
	  textAlign(LEFT);
	  if (fileLoaded) text(player.position()/60000 + ":" + nf(player.position()%60000/1000,2) + "/" + player.length()/60000 + ":" + player.length()%60000/1000, 5, height-20);

	  stroke(0, 160, 176, progressBarAlpha+50);
	  line(0, height-40, songProgress, height-40); // progress
    }
    
    public void volumeSlider() {
	  volume = map(volumeSliderX, width-105, width-10, -50, 50);
	  stroke(grey, progressBarAlpha+80);
	  line(width-105, 60, width-10, 60);
	  fill(0, 160, 176, progressBarAlpha+80);
	  ellipse(volumeSliderX, 60, 10, 10);

	  if (mouseX>width-105 && mouseX<width-10 && mouseY>40 && mouseY<70 && mousePressed) {
	    volumeSliderX = mouseX;
	  }
    }
}
