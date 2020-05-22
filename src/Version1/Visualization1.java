package Version1;

import processing.core.PApplet;
import ddf.minim.analysis.*;
import ddf.minim.*;

public class Visualization1 extends PApplet{
	Minim minim;
	AudioPlayer player;
	int  r = 250;
	
	public static void main(String[] args) {
		PApplet.main("Version1.Visualization1");
	}

	public void settings() {
		size(displayWidth, displayHeight);
	}

	
	public void setup() {
		//size(600, 400);
		minim = new Minim(this);
		player = minim.loadFile("test.mp3");
		player.play();
		background(0);
	}
	
	public void draw() {
		outer();
	}
	
	public void outer() {
	  fill(26, 21, 24, 20);
	  noStroke();
	  rect(0, 0, width, height);
	  translate(width/2, height/2);
	  noFill();
	  fill(-1, 10);
	  stroke(-1, 50);
	  int bsize = player.bufferSize();
	  beginShape();
	  noFill();
	  stroke(-1, 50);
	  for (int i = 0; i < bsize; i+=30)
	  {
	    float x2 = (r + player.left.get(i)*150)*cos(i*2*PI/bsize);
	    float y2 = (r + player.left.get(i)*150)*sin(i*2*PI/bsize);
	    vertex(x2, y2);
	    pushStyle();
	    stroke(-1);
	    strokeWeight(2);
	    point(x2, y2);
	    popStyle();
	  }
	  endShape();
	  noStroke();
	}
	
}


