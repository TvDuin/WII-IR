package controller;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

import model.Player;
import model.WiiMoteModel;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.values.IRSource;
import wiiusej.values.Orientation;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
import wiiusej.wiiusejevents.physicalevents.NunchukButtonsEvent;
import wiiusej.wiiusejevents.physicalevents.NunchukEvent;
import wiiusej.wiiusejevents.physicalevents.WiimoteButtonsEvent;
import wiiusej.wiiusejevents.utils.WiimoteListener;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.ClassicControllerRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.DisconnectionEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.GuitarHeroRemovedEvent;
import wiiusej.
wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.OutputStream;

public class WiiMoteController implements WiimoteListener {

	private WiiMoteModel model;
	private Wiimote[] wiimotes;

	private float joystick;
	private float magnitude;
	private Orientation orientation;
	private int x;
	private int y;
	private ArrayList<Player> players = new ArrayList<Player>();
	private boolean buttonPressed;

	private LinkedList<Long> times = new LinkedList<Long>();
	private double sum = 0;
	private double total = 0;
	private double freq = 0;
	private boolean seen = false;
	private long tempTime = 0;


	//For communication Arduino
	private static CommPortIdentifier portID;
	private static SerialPort serialPort;
	private static OutputStream output;



	public WiiMoteController() {
		this.model = new WiiMoteModel();


		// init WiiMote
		// 
		wiimotes = WiiUseApiManager.getWiimotes(4, true);
		if(wiimotes != null)
		{
			try{
				Wiimote wiimote = wiimotes[0];
				wiimote.setLeds(true, false, false, false);
				wiimote.activateIRTRacking();
				wiimote.addWiiMoteEventListeners(this);
			}
			catch(Exception e)
			{

			}

			for(int i=0; i<wiimotes.length; i++)
			{
				wiimotes[i].activateIRTRacking();
				wiimotes[i].activateSmoothing();
				wiimotes[i].activateContinuous();
				wiimotes[i].addWiiMoteEventListeners(this);
				wiimotes[i].setIrSensitivity(10);
				players.add(new Player());
			}
		}

		//connect to Arduino
		try{connectArduino("COM10"); System.out.println("Verbonden met Arduino!");}
		catch(Exception e){ e.printStackTrace(); System.out.println("Niet verbonden met Arduino!!");}
	}




	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) 
	{

		int player = arg0.getWiimoteId()-1;
		if(arg0.isButtonAJustPressed())
		{
			model.playReload();
			getPlayers().get(player).reload();
		}
		else if(arg0.isButtonBJustPressed() && !buttonPressed)
		{
			buttonPressed = true;
			if(getPlayers().get(player).getBullets() > 0)
			{
				model.playShot();
				getPlayers().get(player).shot();

				if(getPlayers().get(player).getAmountIR() > 0)
				{
					for(int i = 0; i < getPlayers().get(player).getAmountIR(); i++)
					{ ////
						try{
							Point2D.Double point = new Point2D.Double(getPlayers().get(player).getIrsource()[i].getX(), 
									getPlayers().get(player).getIrsource()[i].getY());
							int dist = (int) point.distance(new Point2D.Double(512, 384));
							getPlayers().get(player).hit(dist);
							//switch(player)
							//{
							int playerShot = getPlayers().get(player).getPlayerSeen();
							//case 0:
							try{
								getPlayers().get(playerShot-1).setDeaths(getPlayers().get(playerShot-1).getDeaths() + 1); playerKilled(playerShot);
							} 
							catch(Exception e1){}
						}
						catch(Exception e2){}

					}
				}
				else
				{
					model.playEmpty();
				}
			}
		}
		if(arg0.isButtonBJustReleased())
		{
			buttonPressed = false;
			getPlayers().get(player).setAmountIR(0);
			getPlayers().get(player).setIrsource(null);

		}
	}

	public int getScore(int player)
	{
		try{
			return getPlayers().get(player-1).getScore();}
		catch(Exception e){return 0;}
	}

	public double getAccuracy(int player)
	{
		try{
			return getPlayers().get(player-1).getAccuracy();}
		catch(Exception e){return 0;}	
	}

	public int getHits(int player)
	{
		try{
			return getPlayers().get(player-1).getKill();}
		catch(Exception e){return 0;}
	}

	public int getShots(int player)
	{
		try{
			return getPlayers().get(player-1).getShots();}
		catch(Exception e){return 0;}
	}

	@Override
	public void onClassicControllerInsertedEvent(
			ClassicControllerInsertedEvent arg0) 
	{

		// TODO Auto-generated method stub

	}

	@Override
	public void onClassicControllerRemovedEvent(
			ClassicControllerRemovedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnectionEvent(DisconnectionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onExpansionEvent(ExpansionEvent arg0) {

	}

	@Override
	public void onGuitarHeroInsertedEvent(GuitarHeroInsertedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGuitarHeroRemovedEvent(GuitarHeroRemovedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIrEvent(IREvent arg0) {

		int player = arg0.getWiimoteId()-1;
		if(getPlayers().get(player).getAmountIR() == 0)
			getPlayers().get(player).setAmountIR(arg0.getIRPoints().length);
		
		getPlayers().get(player).setIrsource(arg0.getIRPoints());
		getPlayers().get(player).addTime(System.currentTimeMillis());
		/*
		 * 1000  = 1 Seconde uit 
		 * 100 = 0.1 Seconde uit
		 * 10 = 0.01 Seconde uit
		 * 1 = 0.001 Seconde uit
		 * 
		 */

	}
	//System.out.println("----------------------------------------------------------------------------------------------------"+times.size());


	public IRSource[] getIrlightsP1() {
		try{
			return getPlayers().get(0).getIrsource();}

		catch(Exception e){return null;}
	}
	public IRSource[] getIrlightsP2() {
		try{ 
			return getPlayers().get(1).getIrsource();}
		catch(Exception e){return null;}
	}


	public int getAmountP1() {
		try{
			return getPlayers().get(0).getIrsource().length;
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public int getAmountP2() {
		try{
			return getPlayers().get(0).getIrsource().length;
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	@Override
	public void onMotionSensingEvent(MotionSensingEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNunchukInsertedEvent(NunchukInsertedEvent arg0) {
	}

	@Override
	public void onNunchukRemovedEvent(NunchukRemovedEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusEvent(StatusEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the joystick
	 */
	public float getJoystick() {
		return joystick;
	}

	/**
	 * @param a_joystick the joystick to set
	 */
	public void setJoystick(float a_joystick) {
		joystick = a_joystick;
	}

	/**
	 * @return the magnitude
	 */
	public float getMagnitude() {
		return magnitude;
	}

	/**
	 * @param a_magnitude the magnitude to set
	 */
	public void setMagnitude(float a_magnitude) {
		magnitude = a_magnitude;
	}

	/**
	 * @return the orientation
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * @param a_orientation the orientation to set
	 */
	public void setOrientation(Orientation a_orientation) {
		orientation = a_orientation;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param a_x the x to set
	 */
	public void setX(int a_x) {
		x = a_x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param a_y the y to set
	 */
	public void setY(int a_y) {
		y = a_y;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void connectArduino(String com)
	{
		try 
		{
			portID = CommPortIdentifier.getPortIdentifier(com); //Standard COM6
		} catch (NoSuchPortException e) { e.printStackTrace(); }

		try 
		{
			serialPort = (SerialPort) portID.open("SimpleWriteApp", 2000);
		} catch (PortInUseException e) { e.printStackTrace(); }

		try 
		{
			output = serialPort.getOutputStream();
		} catch (IOException e) { e.printStackTrace(); }

		try 
		{
			serialPort.setSerialPortParams(9600,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		} 
		catch (UnsupportedCommOperationException e) {	}
	}

	public void playerKilled(int playernr)
	{
		//48 ascii is 0
		int send = 48 + playernr;
		try 
		{
			output.write(send);
			System.out.println("Verstuurd: " + send);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
