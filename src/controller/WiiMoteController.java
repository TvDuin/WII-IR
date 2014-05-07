package controller;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import model.WiiMoteModel;
import view.WiiMoteView;
import wiiusej.WiiUseApiManager;
import wiiusej.Wiimote;
import wiiusej.utils.NunchukJoystickEventPanel;
import wiiusej.values.IRSource;
import wiiusej.values.Orientation;
import wiiusej.wiiusejevents.physicalevents.ExpansionEvent;
import wiiusej.wiiusejevents.physicalevents.IREvent;
import wiiusej.wiiusejevents.physicalevents.JoystickEvent;
import wiiusej.wiiusejevents.physicalevents.MotionSensingEvent;
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
	private int amount;
	private IRSource[] irlights;
	
	public WiiMoteController() {
		this.model = model;

		// init WiiMote
		// 
		wiimotes = WiiUseApiManager.getWiimotes(1, true);
		if(wiimotes != null)
		{
			try{
				Wiimote wiimote = wiimotes[0];
				wiimote.setLeds(true, false, false, false);
				wiimote.activateIRTRacking();
				wiimote.addWiiMoteEventListeners(this);

				// Set IR sensivity stuff
				wiimote.setIrSensitivity(0);
				wiimote.setIrSensitivity(3);
			}
			catch(Exception e)
			{

			}
		}

	}

	@Override
	public void onButtonsEvent(WiimoteButtonsEvent arg0) 
	{
		if(arg0.isButtonBPressed())
		{
			wiimotes[0].activateRumble();
			try 
			{
				Thread.sleep(100);
				wiimotes[0].deactivateRumble();
			} 
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
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
		irlights = (arg0.getIRPoints());
	}

	public IRSource[] getIrlights() {
		return irlights;
	}

	public void setIrlights(IRSource[] a_irlights) {
		irlights = a_irlights;
	}

	public int getAmount() {
		//return amount;
		return getIrlights().length;
	}

	public void setAmount(int a_amount) {
		amount = a_amount;
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
