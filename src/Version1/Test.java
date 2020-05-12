package Version1;

import processing.core.PApplet;
import ddf.minim.analysis.*;
import ddf.minim.*;

public class Test extends PApplet{
	Minim minim;
	AudioPlayer player;
	FFT fft;
	float[] angle1;
	float[] angle2;
	float[] y, x;
	PlayerPanel pp = new PlayerPanel();

	public static void main(String[] args) {
		PApplet.main("Version1.Test");
		PApplet.main("Version1.PlayerPanel");
	}
	
	public void settings() {
    	size(400,400);
    	smooth(28);
	}
	
	public void setup() {
		pp.setup();
		
	}
	
	public void draw() {
		pp.draw();
	}

}
