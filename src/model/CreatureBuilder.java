package model;

public class CreatureBuilder {

	private String name;
	
	public CreatureBuilder() {
		this.name = null;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public CreatureBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public Creature build() {
		return new Creature(name, new Caracteristic());
	}
}
