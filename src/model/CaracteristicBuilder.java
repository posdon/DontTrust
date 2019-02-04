package model;

public class CaracteristicBuilder {

	private int life;
	private int strength;
	private int madness;
	
	public CaracteristicBuilder() {
		this.life = 0;
		this.strength = 0;
		this.madness = 0;
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
	
	public CaracteristicBuilder setLife(int life) {
		this.life = life;
		return this;
	}
	
	public CaracteristicBuilder setStrength(int strength) {
		this.strength = strength;
		return this;
	}
	
	public CaracteristicBuilder setMadness(int madness) {
		this.madness = madness;
		return this;
	}
	
	public Caracteristic build() {
		return new Caracteristic(life, strength, madness);
	}
}
