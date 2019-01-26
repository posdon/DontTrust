package storage;

import org.json.simple.parser.ParseException;

import model.Caracteristic;
import model.Creature;

public interface Converter {

	Creature stringToCreature(String jsonContent) throws ParseException; 
	
	String creatureToString(Creature creature);
	
	Caracteristic stringToCaracteristic(String jsonContent) throws ParseException;
	
	String caracteristicToString(Caracteristic caracteristic);
}
