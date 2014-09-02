
//FifteenPuzzle.java
/**
* This class is for the homework of course Jave II Unit6 problem 6 
* The fifteen puzzle  will let you move the square button to the empty space to enentually place the number tiles in order 
*
*@author Linghong Chen
*@version Last modified on April19th, 2014
**/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FifteenPuzzle implements ActionListener
{
	int empty=15;
	int clicked; 
	JButton shuffle; 
	JButton exit;
	ArrayList<JButton> buttons;
	JPanel[] panel;

	public static void main (String [] args)
	{		
		FifteenPuzzle fifteen = new FifteenPuzzle();
 	}

 	//set up FifteenPuzzle construct

 	public FifteenPuzzle()
 	{
		JFrame puzzle = new JFrame("Fifteen Puzzele");
		puzzle.setSize(400,300);
		
		JPanel center = new JPanel();
		JPanel bottom = new JPanel();
		puzzle.add(center, BorderLayout.NORTH);
		puzzle.add(bottom, BorderLayout.SOUTH);

		center.setLayout(new GridLayout(4,4));
		panel = new JPanel[16];
		buttons = new ArrayList<JButton>();
		//assign number to each button and add actionListener
		for (int i=0; i<15; i++)
		{
			panel[i] = new JPanel();
			panel[i].setLayout(new GridLayout(1,1));
			center.add(panel[i]);
			JButton button = new JButton(Integer.toString(i+1));
			buttons.add(i, button);
			panel[i].add(buttons.get(i));
			buttons.get(i).addActionListener(this);
		}
		JButton button = new JButton("Empty");
		buttons.add(15, button);
		panel[15]= new JPanel();
		center.add(panel[15]);


		//button[15].setText("");  //the last button should be empty string

		shuffle = new JButton("SHUFFLE");
		exit = new JButton("EXIT");
		shuffle.setBackground(Color.BLUE);
		bottom.add(shuffle, BorderLayout.WEST);
		bottom.add(exit, BorderLayout.EAST);
		shuffle.addActionListener(this);
		exit.addActionListener(this);

		puzzle.setVisible(true);
		puzzle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	public void	movebutton(int clicked)
	{	
		panel[empty].add(buttons.get(clicked));
		panel[clicked].remove(buttons.get(clicked));
        panel[clicked].repaint();
		buttons.set(empty, buttons.get(clicked));
		buttons.set(clicked,buttons.get(empty));
		empty = clicked;
		if(correctorder())
		{
			JFrame alert = new JFrame("Congrautlation!");
			alert.setSize(300,150);
			alert.setVisible(true);
			JLabel notice = new JLabel("Congrautlation! You have won!");
			alert.add(notice);
		}
	}

	public void	alert()
	{
		JFrame alert = new JFrame("Warning");
		alert.setSize(300,150);
		alert.setVisible(true);
		JLabel warn= new JLabel("You should click a button next to empty button to move the number !");
		alert.add(warn);
	}

	/*
	*Generate a random integer number between 1-4
	*then according to this number to move the empty button to the 4 shufflebale position.
	* do this for certain amount of time, I currently set it is as difficulty = 20.
	*the puzzle has 4 rows and 4 columns, so the index number of the array divide by 4 
	*will get the puzzle button's position (x,y) 
	* shift to right, equals its x number plus 1, i.e., the index number + 1
	* shift to left, equals its x number minus 1, i.e., the index number - 1
	*shift to top, equals its x number plus 1, i.e., the index number + 4
	*shift to bottom, equals its x number minus 1, i.e., the index number -4
	* the new empty position should be inside the puzzle, i.e., x and y must be 0, or 1, or 2, or 3
	**/
	public void	shuffle()
	{			
		int difficulty =20;
		for (int i=0; i<difficulty; i++)
		{
			int shufflenumber = (int)(Math.random()*4)+1;
			System.out.println("shufflenumber: "+shufflenumber);
			if (shufflenumber ==1 && (empty%4-1)>-1) 	//shuffle empty position to left
			{
	
					panel[empty].add(buttons.get(empty-1));
					panel[empty-1].remove(buttons.get(empty-1));
					panel[empty-1].repaint();
					buttons.set(empty,buttons.get(empty-1));
					buttons.set(empty-1,buttons.get(empty));
					empty=empty-1;
			}
			else if (shufflenumber ==2  && (empty%4+1)<4) 	//shuffle empty position to right	
			{
	
					panel[empty].add(buttons.get(empty+1));
					panel[empty+1].remove(buttons.get(empty+1));
					panel[empty+1].repaint();
					buttons.set(empty,buttons.get(empty+1));
					buttons.set(empty+1,buttons.get(empty));
					empty=empty+1;
			}
	
			else if (shufflenumber ==3 && (empty/4-1)>-1) 	//shuffle empty position to top	
			{
	
					panel[empty].add(buttons.get(empty-4));
					panel[empty-4].remove(buttons.get(empty-4));
					panel[empty-4].repaint();
					buttons.set(empty,buttons.get(empty-4));
					buttons.set(empty-4,buttons.get(empty));
					empty=empty-4;
			} 

			else if (shufflenumber ==4 && empty/4+1<4) 	//shuffle empty position to bottom
			{
					panel[empty].add(buttons.get(empty+4));
					panel[empty+4].remove(buttons.get(empty+4));
					panel[empty+4].repaint();
					buttons.set(empty,buttons.get(empty+4));
					buttons.set(empty+4,buttons.get(empty));
					empty=empty+4;
			}
		
			else 										//else do it again, don't count this run
				i=i-1;

		}
	}
		
	public boolean correctorder()
	{	
		boolean result;
		for (int i=0;i<15;i++)
		{
			if(!buttons.get(i).equals(i)) 
			{
				result= false;
				break;
			}
		}

		if (!buttons.get(15).equals(0)) 
			result = false;	
		else 
			result = true;
		return result;		
	}

	public void actionPerformed(ActionEvent e) 
	{	
		if(e.getSource()==shuffle)
		shuffle();	
			
		else if(e.getSource()==exit)
		{
			System.exit(0);
		}
			
		else 
		{
			for( int i =0; i<15;i++)
			{
				if(e.getSource() == buttons.get(i)) clicked = i;
			}
			if ((clicked%4-empty%4 == 1 || clicked%4-empty%4 == -1) && clicked/4-empty/4==0) 
			{
				movebutton(clicked);
			}
			else if((clicked/4-empty/4 == 1|| clicked/4-empty/4 == -1 ) && clicked%4-empty%4 == 0)
			{
				movebutton(clicked);
			}
			else 
				alert();
		}
	}
}
