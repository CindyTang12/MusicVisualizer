package Version1;
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
	String filepath;
	Visualization1 vl = new Visualization1();
	
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
	float volume = (float) 0.5;
	float volumeSliderX;
	
	public static void main(String[] args) {
		PApplet.main("Version1.PlayerPanel");
	}

	
	// method used only for setting the size of the window
    public void settings(){
    	size(600,600);
    }

    // identical use to setup in Processing IDE except for size()
    public void setup(){
//        stroke(255);
//        strokeWeight(10);
        Arial = loadFont("data/Arial.vlw");
        volumeSliderX = width-30;
        minim = new Minim(this);
    }

    // identical use to draw in Processing IDE
    public void draw(){
    	fill(26, 21, 24, 20);
		noStroke();
		rect(0, 0, width, height);
    	if (fileLoaded) {
//    	    soundVision();                  //Calls visualizer function
    	    player.setGain(volume);
    	    
    	    fill(0, 0, 0, 255);
    	    textSize(21);    //Prints title out
    	    textAlign(CENTER);
    	    text(title, width/2, 30);
    	    fft = new FFT(player.bufferSize(), player.sampleRate()/2);
    	    fft.forward(player.mix);
    	  }
    	bottons();
    	if(playing()) {
    		go();
    	}
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
		filepath = selection.getAbsolutePath();
	    player = minim.loadFile(filepath);
	    fileLoaded = true;
	  }
	}
    
    public boolean playing() {
    	if(state == "PAUSE") return true;
    	else return false;
    }

    public void bottons() {
    	playButton();
    	loadButton();
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
  	  rect(-4, -4, 90, 50);

  	  textAlign(CENTER);
  	  fill(black, playButtonAlpha);
  	  textSize(10);
  	  textFont(Arial);
  	  text(state, 42, 30);
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
    
    public void go() {
		float radius = 150;
		  translate(width/2, height/2);
		  fill(-1, 10);
		  stroke(-1, 50);
		  int bsize = player.bufferSize();
		  beginShape();
		  noFill();
		  for (int i = 0; i < bsize; i+=30)
		  {
		    float x2 = (radius + player.left.get(i)*40)*cos(i*2*PI/bsize);
		    float y2 = (radius + player.left.get(i)*40)*sin(i*2*PI/bsize);
		    vertex(x2, y2);
		    pushStyle();
		    stroke(-1);
		    strokeWeight(1);
		    popStyle();
		  }
		  endShape();
	    noFill();
		// 给定长度为1024的信号，频谱中将有512个频带，每个频带的宽度为23Hz
		 for (int i = 0; i < fft.specSize(); i+=2)
		 {
			 if(fft.getBand(i) > 0.2) {
				 float rad = map(i, 0, fft.specSize() - 200, 0, (float) (2 * Math.PI));
				 rad = (float) (rad - Math.PI/2);
				 float posX = (float) ((radius + fft.getBand(i)) * cos(rad));
				 float posY = (float) ((radius + fft.getBand(i)) * sin(rad));
			     line(radius * cos(rad), radius * sin(rad), posX, posY);
			 }
			 else {
				 noStroke();
			 }
		 }
		 
	}
}
