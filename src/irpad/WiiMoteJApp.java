package irpad;

import java.awt.*;
import java.awt.Event.*;

import javax.swing.*;

import model.WiiMoteModel;
import controller.WiiMoteController;
import view.WiiMoteView;
import wiiusej.values.IRSource;

import java.awt.geom.*;

public class WiiMoteJApp {
	
	static final long serialVersionUID = 100;
	
	public static void main(String s[])	{
		JFrame frame = new JFrame("Wii Infrarood camera, tracking 4 dots");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JPanel panel = new WiiIRPanel();
		
		
		WiiMoteController controller = new WiiMoteController();
		WiiMoteView view = new WiiMoteView(controller);
		
		frame.getContentPane().add(view);
		frame.addKeyListener(view);
		frame.pack();
		frame.setVisible(true);
		
		while(true)
		{
			view.updateUI();
		}
	}
}
