package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import exception.CreatureListException;

public class Bestiary {

	private Map<String,Creature> allCreatures;
	
	public final static Bestiary bestiary = new Bestiary();
	
	private Bestiary() {
		this.allCreatures = new HashMap<String,Creature>();
	}
	
	public Creature getCreature(String creatureName) {
		return this.allCreatures.get(creatureName);
	}
	
	public Collection<Creature> getAllCreatures(){
		return allCreatures.values();
	}
	
	public Collection<String> getAllCreaturesName(){
		return allCreatures.keySet();
	}
	
	public void addCreature(Creature creature) throws CreatureListException {
		String creatureName = creature.getName();
		if(this.allCreatures.containsKey(creatureName)) throw new CreatureListException(true, creatureName);
		this.allCreatures.put(creatureName,creature);
	}
	
	public void removeCreature(String creatureName) throws CreatureListException {
		if(!this.allCreatures.containsKey(creatureName)) throw new CreatureListException(false, creatureName);
		this.allCreatures.remove(creatureName);
	}
}
