package model;

import java.awt.geom.Point2D;
import java.util.LinkedList;

import wiiusej.values.IRSource;

public class Player {

	private int score;
	private int bullets;
	private int maxBullets = 15;
	private int amountIR;
	private double shots;
	private double hits;
	private double accuracy;
	private int deaths;
	private IRSource[] irsource;


	private boolean seen = false;

	private LinkedList<Long> times = new LinkedList<Long>();

	public Player()
	{
		this.score = 0;
		this.bullets = maxBullets;
		this.setShots(0);
	}
	public void addTime(long time)
	{
		if(!seen) //Led nog niet gezien?
		{
			times.add(time);
			seen = true;
		}
		if(times.size()>3)
		{
			long difference = time - times.get(times.size()-2);

			//System.out.println("Verschil: " + difference);
			//System.out.println("Size: " + times.size());
			if(difference > 2 && difference < 500) //900//Dan is led uit voor korte tijd (zelfde speler)
			{ 
				seen = false; //Led is uit geweest
			}
			else if(difference > 500) //1000 //Dan is led uit voor 10 seconde (speler weg geweest)
			{ 
				seen = false; //Led is uit geweest
				times.clear(); //leeg arraylist
			}
		}
		else
		{
			seen = false;
		}
		if(times.size() > 100)
		{
			times.removeFirst();
		}
	}
	public double getFrequency()
	{
		int sum = 0;
		int total = 0;
		int freq = 0;
		for(int i = 1; i < times.size(); i ++)
		{
			if(times.size() > 1 && times.size() <= 100)
			{
				sum += (times.get(i) - times.get(i-1));
				total++;
			}
		}

		if (total!= 0)
		{
			freq = sum / total;
		}
		else
		{
			freq = 1;
		}

		return freq;
	}
	public int getPlayerSeen()
	{
		double value = getFrequency();
		if(value > 30)
			return 2;
		else if(value < 30 && value > 1.1)
			return 1;

		return 0;

	}
	public void update()
	{

	}
	
	public void cleatTimes()
	{
		times.clear();
	}
	public int getScore() {
		return score;
	}

	public void shot()
	{
		bullets--;
		setShots(getShots() + 1);
	}

	public void reload()
	{
		bullets = maxBullets;
	}


	public void hit(int dist)
	{
		if(dist <= 70)
			this.score += 100;
		else if(dist <= 250)
			this.score += 50;
		else if(dist <= 700)
			this.score += 10;
		
		hits++;

		//	this.score = getScore() + a;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getBullets() {
		return bullets;
	}
	public void setBullets(int bullets) {
		this.bullets = bullets;
	}
	public int getAmountIR() {
		return amountIR;
	}
	public void setAmountIR(int amountIR) {
		this.amountIR = amountIR;
	}
	public IRSource[] getIrsource() {
		return irsource;
	}
	public void setIrsource(IRSource[] irsource) {
		this.irsource = irsource;
	}
	public int getShots() {
		return (int)shots;
	}
	public void setShots(int shots) {
		this.shots = shots;
	}
	public int getKill() {
		return (int)hits;
	}
	public void setHit(int hit) {
		this.hits = hit;

	}
	public double getAccuracy() 
	{
		accuracy = (hits / shots) * 100;
		//System.out.println("Accuracy: -----------------------------------" + accuracy);
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

}
