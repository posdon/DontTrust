package model;

import model.gameStates.FactionState;

public class CreatureBuilder {

	private String name = "undefined";
	private FactionState factionState = FactionState.NEUTRAL;
	private String descriptionPhysique;

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
	
	public String getDescriptionPhysique() {
		return descriptionPhysique;
	}
	
	public CreatureBuilder setDescriptionPhysique(String descriptionPhysique) {
		this.descriptionPhysique = descriptionPhysique;
		return this;
	}
	
	public Creature build() {
		return new Creature(name, new Caracteristic(), factionState, descriptionPhysique);
	}
}
