package Version1;

import processing.core.PApplet;
import ddf.minim.analysis.*;
import ddf.minim.*;

public class Visualization2 extends PApplet{
	Minim minim;
	AudioPlayer player;
	FFT fft;
	int bands = 512;
	float[] spectrum = new float[bands];
	
	public static void main(String[] args) {
		PApplet.main("Version1.Visualization2");
	}

	public void settings() {
		size(800, 800);
	}

	
	public void setup() {
		minim = new Minim(this);
		player = minim.loadFile("test.mp3");
		player.play();
		// buffersize = 1024, samplerate = 48000 fft.specSize = 513
		fft = new FFT(player.bufferSize(), player.sampleRate()/2);
	}
	
	public void draw() {

	}
	
	public void go() {
		float radius = 200;
		  fill(26, 21, 24, 20);
		  noStroke();
		  rect(0, 0, width, height);
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
		fft.forward(player.mix);
	    noFill();
		// 给定长度为1024的信号，频谱中将有512个频带，每个频带的宽度为23Hz
		 for (int i = 0; i < fft.specSize() - 200; i+=2)
		 {
			 if(fft.getBand(i) > 0.2) {
				 float rad = map(i, 0, fft.specSize() - 200, 0, (float) (2 * Math.PI));
				 rad = (float) (rad - Math.PI/2);
				 float posX = (float) ((radius + fft.getBand(i) * 0.9) * cos(rad));
				 float posY = (float) ((radius + fft.getBand(i) * 0.9 ) * sin(rad));
			     line(radius * cos(rad), radius * sin(rad), posX, posY);
			 }
			 else {
				 noStroke();
			 }
		 }
		 
	}
}