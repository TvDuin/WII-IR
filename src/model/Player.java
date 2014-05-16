package model;

import java.awt.geom.Point2D;

import wiiusej.values.IRSource;

public class Player {
	
	private int score;
	private int bullets;
	private int amountIR;
	private double shots;
	private double hits;
	private double accuracy;
	private int deaths;
	private IRSource[] irsource;
	
	public Player()
	{
		this.score = 0;
		this.bullets = 99999999;
		this.setShots(0);
	}
	
	public void update()
	{

	}
	public int getScore() {
		return score;
	}
	
	public void shot()
	{
		bullets--;
		setShots(getShots() + 1);
	}
	
	public void hit3()
	{
		setHit(getHit() + 1);
	}
	
	public void hit()
	{
		for(IRSource i : irsource)
		{
		Point2D.Double point = new Point2D.Double(i.getX(), i.getY());
		System.out.println("//////////////////////////////////////////////////////////////////////////////" + point.distance(new Point2D.Double(512,384)));
		int dist = (int) point.distance(new Point2D.Double(512,384));
		if(dist <= 50)
			this.score += 100;
		else if(dist <= 150)
			this.score += 50;
		else if(dist <= 500)
			this.score += 10;
		}
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
	public int getHit() {
		return (int)hits;
	}
	public void setHit(int hit) {
		this.hits = hit;
	}
	public double getAccuracy() 
	{
		accuracy = (hits / shots);
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
