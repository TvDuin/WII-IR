package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.WiiMoteController;
import model.Player;

public class GUI 
{	
	private static String player1Name;
	private static String player2Name;
	
	public static void main(String[] args)
	{		
		JFrame frame = new JFrame("Infra Shooter");
		
		frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
		frame.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		player1Name = JOptionPane.showInputDialog("Enter name player1: ");
		player2Name = JOptionPane.showInputDialog("Enter name player2: ");
		
		JPanel firstPanel = new EllipsePanel(player1Name, 0);
		JPanel secondPanel = new EllipsePanel(player2Name, 1);
		
		centerPanel.setLayout(new GridLayout(2,1));
		centerPanel.add(firstPanel);
		centerPanel.add(secondPanel);
		
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);	
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
	private Image background = Toolkit.getDefaultToolkit().createImage("background3.png");
	
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
		
		g2.drawImage(background, 0, 0, null);
		
		g2.setColor(Color.white);
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 20);
		g2.setFont(f);
		
		g2.drawString("Name: ", 600, 75);
		g2.drawString("Score: ", 650, 125);
		g2.drawString("Kills: ", 650, 150);
		g2.drawString("Deaths: ", 650, 175);
		g2.drawString("Shots: ", 650, 200);
		g2.drawString("Accuracy: ", 650, 225);

		g2.drawString(name, 675, 75);
		g2.drawString(score + "", 775, 125);
		g2.drawString(kills + "", 775, 150);
		g2.drawString(deaths + "", 775, 175);
		g2.drawString(shots + "", 775, 200);
		g2.drawString(accuracy + "", 775, 225);
		
		repaint();
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
