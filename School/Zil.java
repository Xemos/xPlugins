package School;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;

import javax.swing.Timer;

public class Zil extends JApplet implements KeyListener
{
    Image img;
    JLabel zil;
    int x=0, y=0, speed=3;     // x,y coordinates and how many pixels move
    JPanel pane;
   
    
    public void init()
    {
    	this.setPreferredSize(new Dimension(640, 480));
        pane = new JPanel( );
        pane.setLayout( null );
        pane.setBackground( Color.CYAN );
        img = getImage( getCodeBase( ), "zil_walk_front.gif" );
        zil = new JLabel( new ImageIcon(img) );
        
        zil.setSize( img.getWidth(this), img.getHeight(this) );
        
        addKeyListener(this);
        pane.add( zil, 0, 0 );
        add( pane, BorderLayout.CENTER );
        setFocusable(true);
    }
    public void keyReleased( KeyEvent ke ) { }
    public void keyTyped( KeyEvent ke ) { }
    
   
    
    public void keyPressed( KeyEvent ke )
    {
        int code = ke.getKeyCode( );
                if( code == KeyEvent.VK_UP )
        {
        	this.zil.setIcon(new ImageIcon(getImage( getCodeBase( ), "zil_walk_back.gif" )));
        	repaint();
        	//y -= speed;
        	  for(int z = speed; z > 0; z--)  
        	  {
        		  y-=z;
           		  zil.setLocation(x,y); 
        	  }
        }
        else if ( code == KeyEvent.VK_DOWN )
        {
        	this.zil.setIcon(new ImageIcon(getImage( getCodeBase( ), "zil_walk_front.gif" )));
        	repaint();
          //  y += speed;
        	  for(int z = speed; z > 0; z--) 
        	  {
        		  y+=z;
              	zil.setLocation(x,y); 
        	  }
        }
        else if ( code == KeyEvent.VK_LEFT )
        {
        	this.zil.setIcon(new ImageIcon(getImage( getCodeBase( ), "zil_walk_left.gif" )));
        	repaint();
            //x -= speed;
        	  for(int z = speed; z > 0; z--)  
        	  {
        		  x-=z;
        		  
              	zil.setLocation(x,y); 
        	  }
        }
        else if ( code == KeyEvent.VK_RIGHT )
        {
        	
        	this.zil.setIcon(new ImageIcon(getImage( getCodeBase( ), "zil_walk_right.gif" )));
        	repaint();
        	//x += speed;
        	  for(int z = speed; z > 0; z--)   
        	  {
        		  x+=z;
              	zil.setLocation(x,y); 
        	  }
        }
        
      
            
        
        
    }
}