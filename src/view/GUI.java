package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI 
{	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Infra Shooter");
		frame.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel player1 = new EllipsePanel();
		JPanel player2 = new EllipsePanel();
		
		centerPanel.setLayout(new GridLayout(2,1));
		centerPanel.add(player1);
		centerPanel.add(player2);
		
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		frame.getContentPane().add(BorderLayout.EAST, rightPanel);
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);	
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
		
	}
}
	
	class EllipsePanel extends JPanel
	{
		private String name = "Daan";
		private String kills = "666";
		private String deaths = "69";
		private String hits = "1332";
		private String misses = "69";
		private String accuracy = "100%";
		
		public EllipsePanel()
		{
			
		}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)g;
		
		Ellipse2D ellipse = new Ellipse2D.Double(100, 100, 50, 50);
		Rectangle2D rectangle = new Rectangle2D.Double(5, 5, 1352, 340);
		g2.setColor(Color.blue);
		
		g2.draw(ellipse);
		g2.fill(ellipse);
		g2.draw(rectangle);
		
		g2.setColor(Color.black);
		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		g2.setFont(f);
		
		g2.drawString("Name: ", 200, 135);
		g2.drawString("Kills: ", 250, 200);
		g2.drawString("Deaths: ", 250, 225);
		g2.drawString("Hits: ", 250, 250);
		g2.drawString("Misses: ", 250, 275);
		g2.drawString("Accuracy: ", 250, 300);

		g2.drawString(name, 275, 135);
		g2.drawString(kills, 360, 200);
		g2.drawString(deaths, 360, 225);
		g2.drawString(hits, 360, 250);
		g2.drawString(misses, 360, 275);
		g2.drawString(accuracy, 360, 300);
	}
}
