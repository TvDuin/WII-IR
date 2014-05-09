package model;

import wiiusej.values.IRSource;

public class Player {
	
	private int score;
	private int bullets;
	private int amountIR;
	private IRSource[] irsource;
	
	public Player()
	{
		this.score = 0;
		this.bullets = 25;
	}
	public int getScore() {
		return score;
	}
	
	public void shot()
	{
		bullets--;
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
	
}
