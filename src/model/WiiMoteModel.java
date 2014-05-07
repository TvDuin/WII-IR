package model;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class WiiMoteModel 
{
	private Clip clip;
	
	public WiiMoteModel()
	{
		try{
			clip = AudioSystem.getClip();
			AudioInputStream inputstream = AudioSystem.getAudioInputStream(new File("gunshot.wav"));
			clip.open(inputstream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public void playShot()
	{
		clip.setMicrosecondPosition(0);
		clip.start();	
	}
}
