package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import controller.WiiMoteController;
import wiiusej.values.IRSource;
import wiiusej.wiiusejevents.physicalevents.IREvent;


public class WiiMoteView extends JPanel implements ActionListener, KeyListener  {

	static final long serialVersionUID = 101;

	private WiiMoteController controller;

	public WiiMoteView(WiiMoteController controller) {
		setPreferredSize( new Dimension(960, 540) );
		this.controller = controller;
		this.addKeyListener(this);
		this.setBackground(Color.white);
	}

	// Called by 
	public void update(IREvent arg0)
	{
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
//		g2.setPaint(new GradientPaint(new Point2D.Double(0, 0), Color.magenta, new Point2D.Double(960, 540), Color.orange));
		g2.drawLine(960, 100, 960, 980);
		g2.drawLine(100, 540, 1820, 540);
		g2.drawString("leds : " + controller.getAmount(), 10, 10);
		if(controller.getIrlights() != null)
		{
			for(IRSource i : controller.getIrlights())
			{
				g2.draw(new Ellipse2D.Double(i.getX(), i.getY(), 50, 50));
				g2.drawLine(i.getX() + 25, i.getY(), i.getX() + 25, i.getY() + 20);
				g2.drawLine(i.getX() + 25, i.getY() + 30, i.getX() + 25, i.getY() + 50);
				g2.drawLine(i.getX(), i.getY() + 25, i.getX() + 20, i.getY()+ 25);
				g2.drawLine(i.getX() + 30, i.getY()  + 25,  i.getX() + 50, i.getY()+25);
				g2.draw(new Ellipse2D.Double(i.getX() + 23, i.getY() + 23, 4, 4));
			}
		}
		g2.drawString("x : " + controller.getX(), 10, 20);
		g2.drawString("y : " + controller.getY(), 10, 30);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}


}