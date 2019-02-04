package model;

public class Caracteristic {

	private int life;
	private int strength;
	private int madness;
	
	public Caracteristic(int life, int strength, int madness) {
		this.life = life;
		this.strength = strength;
		this.madness = madness;
	}

	public int getLife() {
		return life;
	}

	public int getStrength() {
		return strength;
	}

	public int getMadness() {
		return madness;
	}
}
