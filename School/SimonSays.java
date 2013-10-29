package School;

/*
 * CIS218 Fall 2013 (Lab9)
 * Author: Antony Torres
 * Date: 10 / 29 / 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.*;

import java.util.*;				//for Random

public class SimonSays extends JApplet implements MouseListener 
{

   // define variables:
   //North
   JLabel    title, center;
   JTextArea bottom;
   Image img;
   Random random;
      
   public void init( )
   {
        setLayout( new BorderLayout() );
        random = new Random( );
        setSize(238,415);

	
		//Call the functions
		doTitle();
		doBottom();
		doCenter();
		this.addMouseListener(this);

   }//init()
   
   public void doTitle( )
   {
		title = new JLabel( "Simon Says", JLabel.CENTER );
		title.setFont( new Font("Serif", Font.BOLD, 20 ) );
		title.setForeground( Color.YELLOW );
		JPanel titlePanel = new JPanel( );
		titlePanel.setBackground( Color.BLACK );
		titlePanel.add(title);
		add( titlePanel, BorderLayout.NORTH );
   }//doTitle()
   
   public void doBottom( )
   {
	   	bottom = new JTextArea("Welcome to Simon Says! \nTo play, click on a color \nand follow the instructions.",1,5);
	   	bottom.setFont( new Font("Serif", Font.BOLD, 11 ) );
	   	bottom.setForeground( Color.WHITE );
	   	bottom.setBackground( Color.BLACK );
	   	bottom.setEditable(false);

	   	JScrollPane scroll = new JScrollPane (bottom,
	   		   JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	   
	   	scroll.setPreferredSize(new Dimension(this.getWidth(),160));
	   		
		add( scroll, BorderLayout.SOUTH  );
   }//doBottom()
   
   public void doCenter( )
   {
	   img = getImage( getCodeBase( ), "simon.gif" );
	   center = new JLabel( new ImageIcon(img) );
	   JPanel centerPanel = new JPanel( );
		centerPanel.setBackground( Color.BLACK );
		centerPanel.add(center);
		add( centerPanel , BorderLayout.CENTER);
   }//doCenter()

   // implementation for MouseListener
   public void mouseClicked(MouseEvent me)
   {
	    
	   	int x = me.getX();
	    int y = me.getY();
	    int num = random.nextInt(5);
	  
	     bottom.append("\nSimon says, ");
	     
	    

	    //if mouse pointer is located in upper left x 116 y 119
	    if(y < 119)
	    {
	    	if(x < 116) // red
	    	{
	    		switch(num)
	    		{
	    			case 0:
    					bottom.append("Put your hands on your head!");
    					break;
	    			case 1:
    					bottom.append("Put your hands on your ears!");
    					break;
	    			case 2:
    					bottom.append("Put your hands on your knees!");
    					break;
	    			case 3:
    					bottom.append("Put your hands on your ankles!");
    					break;
	    			case 4:
    					bottom.append("Put your hands on your toes!");
    					break;
	    		}
	    	}
	    	else // green
	    	{
	    		switch(num)
	    		{
	    			case 0:
    					bottom.append("Touch your toes!");
    					break;
	    			case 1:
    					bottom.append("Touch your eyeball!");
    					break;
	    			case 2:
    					bottom.append("Touch your feet!");
    					break;
	    			case 3:
    					bottom.append("Touch your belly!");
    					break;
	    			case 4:
    					bottom.append("Touch your bum!");
    					break;
	    		}
	    	}
	    }
	    else
	    {
	    	if(x < 116) // blue
	    	{
	    		switch(num)
	    		{
	    			case 0:
    					bottom.append("Jump "+ (num+2) + " times on your right foot!");
    					break;
	    			case 1:
    					bottom.append("Jump "+ (num+2) + " times on your face!");
    					break;
	    			case 2:
    					bottom.append("Jump "+ (num+2) + " times on your left foot!");
    					break;
	    			case 3:
    					bottom.append("Jump "+ (num+2) + " times on your bum!");
    					break;
	    			case 4:
    					bottom.append("Jump "+ (num+2) + " times on your hands!");
    					break;
	    		}
	    	}
	    	else // yellow
	    	{
	    		switch(num)
	    		{
	    			case 0:
    					bottom.append("Quack like a duck!");
    					break;
	    			case 1:
    					bottom.append("Bark like a dog!");
    					break;
	    			case 2:
    					bottom.append("Moo like a cow!");
    					break;
	    			case 3:
    					bottom.append("Meow like a cat!");
    					break;
	    			case 4:
    					bottom.append("Bingdingdingdading like a fox!");
    					break;
	    		}
	    	}
	    }

	  

	    bottom.setCaretPosition(bottom.getDocument().getLength());
	    
   }//mouseClicked()

@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent me) {
	// TODO Auto-generated method stub
	
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
   
   
}//End of Class