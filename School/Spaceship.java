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
	
		Font Big = new Font("SimHei", Font.PLAIN, 24);
		Font Med = new Font("Ariel", Font.BOLD, 12);
		Font Small = new Font("Ariel", Font.PLAIN, 5);
		
		setBackground(Color.gray);
		
		g.drawRect(150, 150, 5, 5);
		g.drawOval(150, 150, 100, 100);
		
		g.setColor(Color.BLUE);
		g.fill3DRect(70, 80, 50, 50, true);
		g.setColor(Color.cyan);
		g.fill3DRect(85, 100, 50, 50, true);
		g.setColor(Color.green);
		g.fill3DRect(100, 120, 50, 50, true);
		g.setColor(Color.black);
		
		g.fillRoundRect(10, 200, 50, 50, 12, 12);
		g.setFont(Big);
		g.drawString("Hello Worldling", 0, 15);
		g.setFont(Small);
		g.setColor(Color.GREEN);
		g.drawString("Java rocks", 0, 50);
		g.setFont(Med);
		g.setColor(new Color(153,238,255));
		g.drawString("Skiing is fun", 50, 80);
		g.setFont(Small);
		g.setColor(Color.RED);
		g.drawString("To be, or not 2 B", 50, 65);
		
		

	}

}
