package Stardust;

import processing.core.PApplet;

public class Test extends PApplet{
	int pq = 8000;
	float[] posX = new float[pq];
	float[] posY = new float[pq];
	float[] velX = new float[pq];
	float[] velY = new float[pq];
	
	public static void main(String[] args) {
		PApplet.main("Stardust.Test");

	}
	
	public void settings() {
		size(1000, 618);
	}
	
	public void setup() {
		stroke(255, 255, 255);
		for(int i = 1; i < pq; i++) {
			posX[i] = random(0, width);
			posY[i] = random(0, height);
		}
		
	}
	
	public void draw() {
		background(0, 128);
		velX[0] =  (float) (velX[0] * 0.2 + (mouseX - posX[0]) * 0.2);
		velY[0] = (float)(velY[0] * 0.2 + (mouseY - posY[0]) * 0.2);
		
		posX[0] += velX[0];
		posY[0] += velY[0];
		
		for(int i = 1; i < pq; i++) {
			float num = 1024 / (sq(posX[0] - posX[i]) + sq(posY[0] - posY[i]));
			velX[i] = (float) (velX[i] + (velX[0] - velX[i]) * num);
			velY[i] = (float) (velY[i] + (velY[0] - velY[i]) * num);
			
			posX[i] += velX[i];
			posY[i] += velY[i];
			
			if((posX[i] < 0 && velX[i] <0) || (posX[i] > width && velX[i] > 0)) {
				velX[i] = -velX[i];
			}
			
			if((posY[i] < 0 && velY[i] <0) || (posY[i] > width && velY[i] > 0)) {
				velY[i] = -velY[i];
			}
			point(posX[i], posY[i]);
		}
	}
	
	public void mousePressed() {
		for(int i = 1; i < pq; i++) {
			posX[i] = random(0, width);
			posY[i] = random(0, height);
		}
	}
}
