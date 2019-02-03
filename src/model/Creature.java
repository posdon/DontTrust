package model;

import model.gameStates.FactionState;

public class Creature {

	private String name;
	private Caracteristic caracteristic;
	private FactionState faction;
	private String descriptionPhysique;
	private Family family;
	
	public Creature(String name, Caracteristic caracteristic, FactionState faction, String descriptionPhysique, Family family) {
		this.name = name;
		this.caracteristic = caracteristic;
		this.faction = faction;
		this.descriptionPhysique = descriptionPhysique;
		this.family = family;
		FamilyBook.familyBook.addIntoFamily(name, family);
	}

	public String getName() {
		return name;
	}

	public Caracteristic getCaracteristic() {
		return caracteristic;
	}
	
	public FactionState getFaction() {
		return faction;
	}

	public String getDescriptionPhysique() {
		return descriptionPhysique;
	}
	
	public Family getFamily() {
		return this.family;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Creature other = (Creature) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
