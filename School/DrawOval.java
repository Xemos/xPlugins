package School;

import java.awt.Graphics;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

public class DrawOval extends JApplet {
	
private int number1, number2, number3, number4;
	
	public void init()
	{
	
		
		number1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the distance from left to start."));
		number2 = Integer.parseInt(JOptionPane.showInputDialog("Enter the distance from top to start."));
		number3 = Integer.parseInt(JOptionPane.showInputDialog("Enter the width."));
		number4 = Integer.parseInt(JOptionPane.showInputDialog("Enter the height."));
			
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		g.drawOval(number1, number2, number3, number4);
	
		
	}

}
