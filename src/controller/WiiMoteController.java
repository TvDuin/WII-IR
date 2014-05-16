package controller;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
import wiiusej.wiiusejevents.wiiuseapievents.NunchukInsertedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.NunchukRemovedEvent;
import wiiusej.wiiusejevents.wiiuseapievents.StatusEvent;

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

	private ArrayList<Long> times = new ArrayList<Long>();


	public WiiMoteController() {
		this.model = new WiiMoteModel();


		// init WiiMote
		// 
		wiimotes = WiiUseApiManager.getWiimotes(2, true);
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
				wiimotes[i].setIrSensitivity(99);
				getPlayers().add(new Player());				
			}
		}


	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) 
	{

		int player = arg0.getWiimoteId();
		if(arg0.isButtonAJustPressed())
		{
			switch(player)
			{
			case 1: model.playReload();
			getPlayers().get(0).reload();
			break;

			case 2: model.playReload();
			getPlayers().get(1).reload();
			break;

			default: break;
			}
		}
		else if(arg0.isButtonBJustPressed() && !buttonPressed)
		{
			buttonPressed = true;
			switch(player)
			{
			case 1: 
				if(getPlayers().get(0).getBullets() > 0)
				{
					model.playShot();
					getPlayers().get(0).shot();

					if(getPlayers().get(0).getAmountIR() > 0)
					{
						Point2D.Double point = new Point2D.Double(getPlayers().get(0).getIrsource()[0].getX(), getPlayers().get(0).getIrsource()[0].getY());
						int dist = (int) point.distance(new Point2D.Double(512, 384));
						getPlayers().get(0).hit(dist);
					}
					try{getPlayers().get(1).setDeaths(getPlayers().get(1).getDeaths() + 1);} catch(Exception e){}
				}

				break;

			case 2: 
				if(getPlayers().get(1).getBullets() > 0)
				{
					model.playShot();
					getPlayers().get(1).shot();

					if(getPlayers().get(1).getAmountIR() > 0)
					{
						Point2D.Double point = new Point2D.Double(getPlayers().get(1).getIrsource()[0].getX(), getPlayers().get(1).getIrsource()[0].getY());
						int dist = (int) point.distance(new Point2D.Double(512, 384));
						getPlayers().get(1).hit(dist);
					}
				}
				break;			
			}
		}
		if(arg0.isButtonBJustReleased())
		{
			buttonPressed = false;
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
			return getPlayers().get(player-1).getHit();}
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
		//		if(arg0 instanceof NunchukEvent)
		//		{
		//			
		//			NunchukEvent nun = (NunchukEvent) arg0;
		//			NunchukButtonsEvent buttons = nun.getButtonsEvent();
		//			if(buttons.isButtonCPressed())
		//			{
		//				
		//				model.playReload();
		//			}
		//		}
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

		int player = arg0.getWiimoteId();	
		long timeInMillis = 0;
		long newTimeInMillis = 0;
		boolean newIRFound = false;

		switch(player)
		{
		case 1: getPlayers().get(0).setAmountIR(arg0.getIRPoints().length);
		getPlayers().get(0).setIrsource(arg0.getIRPoints());
		break;

		case 2: 
			getPlayers().get(1).setAmountIR(arg0.getIRPoints().length);	
			getPlayers().get(1).setIrsource(arg0.getIRPoints());	
			break;

		default: break;
		}


		//frequency check
		for(int i = 0; i < 20; i ++)
		{
			if(arg0.getIRPoints().length != 0)
			{
				timeInMillis = System.currentTimeMillis();

				while(newIRFound == false)
				{
					if(arg0.getIRPoints().length != 0)
					{
						newTimeInMillis = System.currentTimeMillis();
						newIRFound = true;
					}
				}
			}

			times.add(newTimeInMillis - timeInMillis);
		}
		//End of frequency check
	}

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


}
