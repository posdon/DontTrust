package storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import model.Caracteristic;
import model.Creature;
import model.Family;

public interface Converter {

	Creature stringToCreature(String jsonContent) throws Exception; 
	
	JSONObject creatureToString(Creature creature);
	
	Caracteristic stringToCaracteristic(String jsonContent) throws ParseException;
	
	JSONObject caracteristicToString(Caracteristic caracteristic);
	
	Family stringToFamily(String jsonContent) throws ParseException;
	
	JSONObject familyToString(Family family);
}
