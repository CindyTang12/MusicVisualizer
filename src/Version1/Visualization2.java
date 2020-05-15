package Version1;

import processing.core.PApplet;
import ddf.minim.analysis.*;
import ddf.minim.*;

public class Visualization2 extends PApplet{
	Minim minim;
	AudioPlayer player;
	FFT fft;
	float[] angle1;
	float[] angle2;
	float[] y, x;
	
	public static void main(String[] args) {
		PApplet.main("Version1.Visualization2");
	}

	public void settings() {
		size(1000, 618);
	}

	
	public void setup() {
		minim = new Minim(this);
		player = minim.loadFile("test.mp3");
		player.play();
		fft = new FFT(player.bufferSize(), player.sampleRate());
		y = new float[fft.specSize()];
		x = new float[fft.specSize()];
		angle1 = new float[fft.specSize()];
		angle2 = new float[fft.specSize()];
		print(fft.specSize());
		frameRate(120);
	}
	
	public void draw() {
		background(0);
		fft.forward(player.mix);
		go();
	}
	
	public void go() {

	}
	

	
}


