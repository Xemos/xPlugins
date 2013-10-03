package School;

import javax.swing.*; // blah blah

import java.awt.*; 

@SuppressWarnings("serial")
public class Spaceship extends JApplet
{
	/*
	 * 
	 * Author: Antony Torres
	 * 
	 */
	
	public void paint( Graphics g)
	{
		drawBackground(g);
		drawShip(g);
		drawHuman(g);		
	}
	
	public void drawShip(Graphics g)
	{
		//draw the Beam
		int xPoints[] = {240,170,50,250};
		int yPoints[] = {100,100,350,350};
		g.setColor(new Color(255,255,204));	
		g.fillPolygon(xPoints, yPoints, 4);
		g.fillOval(50, 330, 200, 40);
		// Main ship
		g.setColor(Color.cyan);	
		g.fillOval(150, 50, 100, 100);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(100, 105, 200, 50);
		g.setColor(Color.GRAY);
		g.fillOval(100, 100, 200, 50);
		g.setColor(Color.green);	
		g.fillOval(150, 95, 100, 20);
		g.setColor(Color.cyan);	
		g.fillOval(150, 90, 100, 20);
		// Lights
		g.setColor(Color.YELLOW);
		g.fillOval(115, 120, 10, 5);
		g.fillOval(145, 130, 10, 5);
		g.fillOval(180, 135, 10, 5);
		g.fillOval(215, 135, 10, 5);
		g.fillOval(250, 130, 10, 5);
		g.fillOval(280, 120, 10, 5);
	}
	public void drawBackground(Graphics g)
	{
		setBackground(Color.black);
		g.setColor(new Color(00,85,00));	
		g.fillRect(0, 330, 1000, 500);
	}
	public void drawHuman(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawLine(160, 300, 150, 320);
		g.drawLine(170, 320, 160, 300);
		g.drawLine(160, 300, 160, 280);
		g.drawLine(160, 285, 150, 280);
		g.drawLine(160, 285, 170, 280);
		g.drawOval(155, 270, 10, 10);
		g.setColor(Color.gray);
		g.drawString("Help!!", 170, 260);
	}
}