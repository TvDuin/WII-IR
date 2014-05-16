package model;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class WiiMoteModel 
{
	private Clip clip;
	private Clip reload;
	
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
		
		try{
			reload = AudioSystem.getClip();
			AudioInputStream inputstream = AudioSystem.getAudioInputStream(new File("reload.wav"));
			reload.open(inputstream);
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
	
	public void playReload()
	{
		reload.setMicrosecondPosition(0);
		reload.start();	
	}
}
