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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

import controller.WiiMoteController;
import model.Player;

public class GUI 
{
	private static ArrayList<String> names;
	private static int playersnmb;
	public static ArrayList<EllipsePanel> panels;
	
	public static void main(String[] args)
	{		
		JFrame frame = new JFrame("Infra Shooter");
		frame.setPreferredSize(new Dimension(1425,759));
		frame.setLayout(new BorderLayout());		
		frame.setResizable(false);
		
		
		names = new ArrayList<String>();
		panels = new ArrayList<EllipsePanel>();
		JPanel centerPanel = new JPanel();
		Integer[] possibilities = {1, 2, 3, 4};
		playersnmb = (int) JOptionPane.showInputDialog(frame,"Selecteer het aantal spelers","Customized Dialog",JOptionPane.PLAIN_MESSAGE,null, possibilities,1);
		
		
		for(int i = 1; i <= playersnmb; i++)
		{
			String name = JOptionPane.showInputDialog("Naam speler " + i + ": ");
			names.add(name);
		}
		
		JOptionPane.showMessageDialog(frame, "Houdt uw WiiMotes naar beneden gericht en klik op 'OK' ");
		int idx = 0;
		while(idx < playersnmb)
		{
			
			
			EllipsePanel p = new EllipsePanel(names.get(idx), idx);
			panels.add(new EllipsePanel(names.get(idx), idx));
			panels.get(idx).setPreferredSize(new Dimension(1425, 360));
			idx++;
		}
		
		centerPanel.setLayout(new GridLayout(playersnmb,1));
		for(int s = 0; s<panels.size(); s++)
		{
			centerPanel.add(panels.get(s));
		}
		
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		
		JScrollPane sp = new JScrollPane(centerPanel);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(BorderLayout.CENTER,sp);
		
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
	public int playernr;
	public int bulletCount;
	private int xPos = 850;
	private Image background = Toolkit.getDefaultToolkit().createImage("background3.png");
	private Image bullet = Toolkit.getDefaultToolkit().createImage("bullet.png");
	
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
		g2.drawLine(0, 360, 1425, 360);
		g2.setFont(f);
		
		g2.drawString("Name: ", 600, 75);
		
		g2.drawString("Score: ", 650, 125);
		g2.drawString("Kills: ", 650, 150);
		g2.drawString("Deaths: ", 650, 175);
		g2.drawString("Shots: ", 650, 200);
		g2.drawString("Accuracy: ", 650, 225);

		g2.drawString(name, 675, 75);
		try{
		g2.drawString((int)controller.getPlayers().get(playernr).getScore() + "", 775, 125);
		g2.drawString((int)controller.getPlayers().get(playernr).getKill() + "", 775, 150);
		g2.drawString((int)controller.getPlayers().get(playernr).getDeaths() + "", 775, 175);
		g2.drawString((int)controller.getPlayers().get(playernr).getShots() + "", 775, 200);
		g2.drawString((int)controller.getPlayers().get(playernr).getAccuracy() + "%", 775, 225);
		
		
		for(int i = 0; i < controller.getPlayers().get(playernr).getBullets(); i++)
		{
			g2.drawImage(bullet, xPos, 50, null);
			xPos += 15;
		}
		
		xPos = 850;
		}
		catch(Exception e){}
	}

	@Override
	public void actionPerformed(ActionEvent e){		
		repaint();
	}
}
