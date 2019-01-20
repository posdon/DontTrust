package model;

import model.gameStates.FactionState;

public class CreatureBuilder {

	private String name;
	private FactionState factionState;
	
	public CreatureBuilder() {
		this.name = null;
	}
	
	public String getName() {
		return this.name;
	}
	
	public CreatureBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public FactionState getFactionState() {
		return this.factionState;
	}
	
	public CreatureBuilder setFactionState(FactionState factionState) {
		this.factionState = factionState;
		return this;
	}
	
	public Creature build() {
		return new Creature(name, new Caracteristic());
	}
}
