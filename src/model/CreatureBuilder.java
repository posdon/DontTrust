package model;

import org.apache.commons.lang3.StringUtils;

import model.gameStates.FactionState;

public class CreatureBuilder {
	
	private String name;
	private FactionState factionState;
	private Caracteristic caracteristic;
	private String descriptionPhysique;
	private Family family;

	public CreatureBuilder() {
		this.name = "undefined";
		this.factionState = FactionState.NEUTRAL;
	}
	
	public String getName() {
		return this.name;
	}
	
	public CreatureBuilder setName(String name) throws Exception {
		if(StringUtils.isBlank(name)) throw new Exception();
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
	
	public Caracteristic getCaracteristic() {
		return caracteristic;
	}

	public CreatureBuilder setCaracteristic(Caracteristic caracteristic) {
		this.caracteristic = caracteristic;
		return this;
	}

	public String getDescriptionPhysique() {
		return descriptionPhysique;
	}
	
	public CreatureBuilder setDescriptionPhysique(String descriptionPhysique) {
		this.descriptionPhysique = descriptionPhysique;
		return this;
	}
	
	public Family getFamily() {
		return this.family;
	}
	
	public CreatureBuilder setFamily(Family family) {
		this.family = family;
		return this;
	}
	
	public Creature build() {
		return new Creature(name, caracteristic, factionState, descriptionPhysique, family);
	}
}
