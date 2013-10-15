package School;

import javax.swing.*;

import java.awt.*;

public class Midterm extends JApplet
{
	JLabel mylabel;
	JCheckBox attending;
	JTextArea comments;
	JTextField name;
	JComboBox<String> list;
	JRadioButton jb1, jb2, jb3;
	ButtonGroup group;
	
	public void init()
	{
		setLayout( new BoxLayout( getContentPane( ), BoxLayout.Y_AXIS ) );
		attending= new JCheckBox("Will you be attending?",false);
		name = new JTextField( 10 );
		comments = new JTextArea ( 3 , 10);
		list = new JComboBox<String>();
		list.addItem("Appetizer");
		list.addItem("Main Dish");
		list.addItem("Dessert");
		jb1 = new JRadioButton( "0" );
    	jb2 = new JRadioButton( "1" );
    	jb3 = new JRadioButton( "2" );
    	group = new ButtonGroup( );
		group.add( jb1 );
		group.add( jb2 );
		group.add( jb3 );
		
		
		JPanel row1 = new JPanel( );
		JPanel row2 = new JPanel( );
		JPanel row3 = new JPanel( ); 
		JPanel row4 = new JPanel( );
		
		row1.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		row2.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		row3.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		row4.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		
		
		//row 1
		mylabel = new JLabel("<HTML><font size=+4 COLOR=RED>Party ROCK!!!</font><P><i>invitation form</i>");
		row1.add(mylabel);
		row1.add(attending);
		
		//row 2
		mylabel = new JLabel("Name: ");
		row2.add(mylabel);
		row2.add(name);
		mylabel = new JLabel("           What will be be bringing: ");
		row2.add(mylabel);
		row2.add(list);
		
		//row 3
		mylabel = new JLabel("How many guests will you bring?: ");
		row3.add(mylabel);
		row3.add( jb1 );
		row3.add( jb2 );
		row3.add( jb3 );
				
		//row 4
		mylabel = new JLabel("Comments: ");
		row4.add(mylabel);
		
		
		add(row1);
		add(row2);
		add(row3);
		add(row4);
		add(comments);
		
	}
}
