package controller;

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

@SuppressWarnings("restriction")
public class WiiMoteController implements WiimoteListener {

	private WiiMoteModel model;
	private Wiimote[] wiimotes;

	private float joystick;
	private float magnitude;
	private Orientation orientation;
	private int x;
	private int y;
	private int amount;
	private IRSource[] irlightsP1, irlightsP2;
	private int scoreP1, scoreP2;
	private ArrayList<Player> players = new ArrayList<Player>();

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
				players.add(new Player());				
			}
		}


	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) 
	{
		int player = arg0.getWiimoteId();
		if(arg0.isButtonBJustPressed())
		{
			switch(player)
			{
			case 1: 
				if(players.get(0).getBullets() > 0)
				{
					model.playShot();
					players.get(0).shot();
					System.out.println("BULLETSSS:    " + players.get(0).getBullets() + "-------------");
				}				
				if(players.get(0).getAmountIR() > 1)
					players.get(0).setScore(players.get(0).getScore() + 1);
				break;

			case 2: 
				if(players.get(1).getBullets() > 0)
					model.playShot();
				if(players.get(1).getAmountIR() > 1)
					players.get(1).setScore(players.get(1).getScore() + 1);
				break;			
			}
		}	
		
	}

	public int getScoreP1()
	{
		return players.get(0).getScore();
	}
	public int getScoreP2()
	{
		return players.get(1).getScore();
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

		int player = arg0.getWiimoteId();			
		switch(player)
		{
		case 1: players.get(0).setAmountIR(arg0.getIRPoints().length);
		players.get(0).setIrsource(arg0.getIRPoints());
		break;

		case 2: players.get(1).setAmountIR(arg0.getIRPoints().length);
		players.get(1).setIrsource(arg0.getIRPoints());
		break;

		default: break;
		}
	}

	public IRSource[] getIrlightsP1() {
		return players.get(0).getIrsource();
	}
	public IRSource[] getIrlightsP2() {
		return players.get(1).getIrsource();
	}


	public int getAmountP1() {
		try{
			return players.get(0).getIrsource().length;
		}
		catch(Exception e)
		{
			return 0;
		}
	}

	public int getAmountP2() {
		try{
			return players.get(0).getIrsource().length;
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


}
