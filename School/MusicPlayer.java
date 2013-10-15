package School;

import javax.swing.*;

import java.awt.*;

public class MusicPlayer extends JApplet 
{
	
	DefaultListModel<String> music;
	JList<String> musicList;
	JComboBox<String> list;
	
	public void init()
	{
		
		music = new DefaultListModel<String>( );
		musicList = new JList<String>( music ); 
		
		music.add( 0, "LMFAO - Party Rock Anthem ft. Lauren Bennett" );
		music.add( 1, "Deadmau 5 - Ghosts N Stuff" );
		music.add( 2, "SKRILLEX - Bangarang feat. Sirah" );
		music.add( 3, "Substep Infrabass - Christus Chemicalus Cannibalus" );
		
		list = new JComboBox<String>();
		list.addItem("Dubstep");
		list.addItem("Rock");
		list.addItem("Country");
		
		
		
		JPanel row1 = new JPanel( );
		JPanel row2 = new JPanel( );
		JPanel row3 = new JPanel( ); 
		JPanel row3left = new JPanel( ); 
		JPanel row3right = new JPanel( ); 
		JPanel row4 = new JPanel( );

		setLayout( new BoxLayout( getContentPane( ), BoxLayout.Y_AXIS ) );
		row1.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		row2.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		row3.setLayout( new GridLayout(1,2));
		row3left.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		row3right.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		row4.setLayout( new FlowLayout( FlowLayout.LEFT ) );
				
		//row 1
		JLabel mylabel;
		mylabel = new JLabel("<HTML><font size=+2 COLOR=BLUE>Now playing</font><P><i>--- LMFAO - Party Rock Anthem ft. Lauren Bennett</i>");
		row1.add(mylabel);
		
		//row 2
		row2.add(new Button("Play"));
		row2.add(new Button("Stop"));
		row2.add(new Button("Pause"));
		
		//row 3
		mylabel = new JLabel("<HTML><br><font COLOR=BLUE align='left'>Music list:</font>");
		row3left.add(mylabel);
		row3.add(row3left);
		
		row3right.add(list);
		row3.add(row3right);
		
		//row 4
		row4.add(musicList);
		
		add(row1);
		add(row2);
		add(row3);
		add(row4);
	}
	
	
	
	
}
