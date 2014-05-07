package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GUI extends JFrame
{	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Infra Shooter");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
}
