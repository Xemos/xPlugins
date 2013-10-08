package School;

import java.awt.*;

import javax.swing.*;

import java.util.*;

public class WeatherClip extends JApplet{
	Calendar rightNow;
	int month, hour;
	Random random;
	
	public void paint(Graphics g)
	{
		rightNow = Calendar.getInstance();
		hour = rightNow.get(Calendar.HOUR_OF_DAY);
		month = rightNow.get(Calendar.MONTH);
		random = new Random();
		
		month=1;
		hour = 8;
		
		
		drawSky(g);
		drawHouse(g);
		
		
		switch(month)
		{
			case 12:
			case 1:
			case 2:
				g.setColor(Color.WHITE);
				g.fillRect(0, 160, 200, 40);
				drawSnow(g);
				break;
			case 3:
			case 4:
			case 5:
				g.setColor(Color.GREEN);
				g.fillRect(0, 160, 200, 40);
				drawRain(g);
				break;
			case 6:
			case 7:
			case 8:
				g.setColor(Color.GREEN);
				g.fillRect(0, 160, 200, 40);
				break;
			case 9:
			case 10:
			case 11:
				g.setColor(new Color(222,184,135));
				g.fillRect(0, 160, 200, 40);
				break;
		
		}
		drawTree(g);
		
	}
	public void drawTree(Graphics g)
	{
		g.setColor(new Color(139,69,19));
		g.fillArc(150, 120, 10, 100, 0, 90);
		g.drawLine(160, 150, 175, 140);
		g.drawLine(158, 135, 168, 130);
		g.drawLine(160, 150, 140, 140);
		g.drawLine(158, 135, 148, 130);
	}
	public void drawHouse(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillArc(30, 50, 100, 90, 210, 120);
		g.setColor(Color.red);
		g.fillRect(40, 120, 80, 50);
		g.setColor(Color.BLACK);
		g.fillRect(60, 140, 10, 15);
		g.fillRect(80, 140, 20, 30);
	}
	public void drawSnow(Graphics gr)
	{
		gr.setColor(Color.white);
		for(int x=0;x<10;x++)
		{
			gr.fillOval(random.nextInt(200), random.nextInt(200), 5, 5);
		}
	}
	public void drawRain(Graphics gr)
	{
		int x, y;
		gr.setColor(Color.blue);
		for(int z=0;z<20;z++)
		{
			x = random.nextInt(200);
			y = random.nextInt(200);
			gr.drawLine(x, y, x-5, y-5);

		}
	}
	public void drawSky(Graphics g)
	{
		if(hour >= 8 && hour <=20)
		{
			if(month == 12 || month == 1 || month == 2)
				g.setColor(new Color(138,173,217));
			else
				g.setColor(Color.CYAN);
		}
		else
			g.setColor(new Color(25,25,112));
	
		g.fillRect(0, 0, 200, 160);
	
	}
}
