package School;

//import javax.swing.*;
import java.awt.*;

public class NeedMethods {
	
	public void paint(Graphics g)
	{
		for(int x = 1; x < 10; x+=2){
			drawRows(g,x * 10,20);
			drawRows(g,x * 10 + 10,30);
		}
		
	}
	
	public void drawRows(Graphics g,int x, int y){
		g.fillRect(x, y, 10, 10);
		g.fillRect(x +20, y, 10, 10);
		g.fillRect(x +40, y, 10, 10);
		g.fillRect(x +60, y, 10, 10);
		g.fillRect(x +80, y, 10, 10);
	}

}
