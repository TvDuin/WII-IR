package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.WiiMoteController;
import model.Player;

public class GUI 
{	
	public static void main(String[] args)
	{		
		JFrame frame = new JFrame("Infra Shooter");
		frame.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		JPanel player1Panel = new EllipsePanel("Daan", 0);
		JPanel player2Panel = new EllipsePanel("Kasper", 1);
		
		centerPanel.setLayout(new GridLayout(2,1));
		centerPanel.add(player1Panel);
		centerPanel.add(player2Panel);
		
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		frame.getContentPane().add(BorderLayout.EAST, rightPanel);
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);	
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setVisible(true);
		
	}
}
	
class EllipsePanel extends JPanel implements ActionListener
{
	private Timer t = new Timer(200, this);
	private WiiMoteController controller;
	
	private String name;
	private int score = 0;
	private int kills = 0;
	private int deaths = 0;
	private int shots = 25;
	private int accuracy = 0;
	private int playernr;
		
	public EllipsePanel(String name, int playernr)
	{
		controller = new WiiMoteController();
		this.playernr = playernr;
		this.name = name;
		t.start();
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
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
		g2.drawString("Score: ", 250, 200);
		g2.drawString("Kills: ", 250, 225);
		g2.drawString("Deaths: ", 250, 250);
		g2.drawString("Shots: ", 250, 275);
		g2.drawString("Accuracy: ", 250, 300);

		g2.drawString(name, 275, 135);
		g2.drawString(score + "", 360, 200);
		g2.drawString(kills + "", 360, 225);
		g2.drawString(deaths + "", 360, 250);
		g2.drawString(shots + "", 360, 275);
		g2.drawString(accuracy + "", 360, 300);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		score = (int)controller.getPlayers().get(playernr).getScore(); //Integer.toString(player.getScore());
		kills = (int)controller.getPlayers().get(playernr).getHit(); //Integer.toString(player.getHit());
		deaths = (int)controller.getPlayers().get(playernr).getDeaths(); //"0";
		shots = (int)controller.getPlayers().get(playernr).getShots();
		accuracy =(int)controller.getPlayers().get(playernr).getAccuracy();
		repaint();
	}
}
