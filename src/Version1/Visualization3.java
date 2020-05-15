package Version1;

import processing.core.PApplet;
import processing.sound.*;
import processing.sound.FFT;
import ddf.minim.analysis.*;
import ddf.minim.*;

public class Visualization3 extends PApplet{
	FFT fft;
	AudioIn in;
	int bands = 512;
	float[] spectrum = new float[bands];
	
	public static void main(String[] args) {
		PApplet.main("Version1.Visualization3");
	}

	public void settings() {
		size(displayWidth, displayHeight);
	}

	
	public void setup() {
	  background(255);
	  // Create an Input stream which is routed into the Amplitude analyzer
	  fft = new FFT(this, bands);
	  AudioIn in = new AudioIn(this, 0);
	  // start the Audio Input
	  in.start();
	  // patch the AudioIn
	  fft.input(in);
	}
	
	public void draw() {
	  background(255);
	  fft.analyze(spectrum);
	  for(int i = 0; i < bands; i++){
	  // The result of the FFT is normalized
	  // draw the line for frequency band i scaling it up by 5 to get more amplitude.
	  line( i, height, i, height - spectrum[i]*height*5 );
	  }
	}
}


