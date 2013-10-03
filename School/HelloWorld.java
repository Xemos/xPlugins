package School;

import javax.swing.*; // blah blah
import java.awt.*; 

@SuppressWarnings("serial")
public class HelloWorld extends JApplet
{
	/*
	 * 
	 * Author: Antony Torres
	 * 
	 */
	
	public void paint( Graphics g)
	{
	
		g.setColor(Color.BLUE);
		g.fill3DRect(60, 60, 50, 50, true);
		g.setColor(Color.cyan);
		g.fill3DRect(80, 80, 50, 50, true);
		g.setColor(Color.green);
		g.fill3DRect(100, 100, 50, 50, true);
		g.setColor(Color.black);
		g.drawString("(0,0)", 0, 0);
		g.drawString("(100,10)", 100, 10);
		g.drawString("(20,50)", 20, 50);
		g.drawString("(190,90)", 190, 90);
		

	}

}
