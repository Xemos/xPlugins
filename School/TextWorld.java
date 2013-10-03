package School;

import javax.swing.*; // blah blah
import java.awt.*; 

@SuppressWarnings("serial")
public class TextWorld extends JApplet
{
	/*
	 * 
	 * Author: Antony Torres
	 * 
	 */
	
	public void paint( Graphics g)
	{
	
		setBackground(Color.black);
		
		int xPoints[] = {190,230,50,250};
		int yPoints[] = {100,100,350,350};
		
		
		g.setColor(Color.white);	
		g.drawLine(0, 330, 500, 330);
		g.setColor(Color.cyan);	
		g.fillOval(150, 50, 100, 100);
		g.setColor(Color.yellow);	
		g.fillPolygon(xPoints, yPoints, 4);
		g.setColor(Color.GRAY);
		g.fillOval(100, 100, 200, 50);
		g.setColor(Color.green);	
		g.fillOval(150, 95, 100, 20);
		
		g.setColor(Color.cyan);	
		g.fillOval(150, 90, 100, 20);
		g.setColor(Color.yellow);	
		g.fillOval(50, 330, 200, 40);
				
		

	}

}
