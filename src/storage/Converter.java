package storage;

import org.json.simple.parser.ParseException;

import model.Caracteristic;
import model.Creature;
import model.Family;

public interface Converter {

	Creature stringToCreature(String jsonContent) throws ParseException; 
	
	String creatureToString(Creature creature);
	
	Caracteristic stringToCaracteristic(String jsonContent) throws ParseException;
	
	String caracteristicToString(Caracteristic caracteristic);
	
	Family stringToFamily(String jsonContent) throws ParseException;
	
	String familyToString(Family family);
}
