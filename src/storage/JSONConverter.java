package storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import exception.JSONConverterException;
import model.Caracteristic;
import model.Creature;
import model.CreatureBuilder;
import model.gameStates.FactionState;

public class JSONConverter implements Converter {
	
	public static JSONConverter INSTANCE = new JSONConverter();
	
	private JSONConverter() {}
	
	public String modelToJson(Object object) throws JSONConverterException {
		if(object == null) return "null";
		Class objectClass = object.getClass();
		if(Creature.class.equals(objectClass)) {
			return creatureToString((Creature) object);
		}
		throw new JSONConverterException();
	}
	
	@Override
	public Creature stringToCreature(String jsonContent) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(jsonContent);
		return (new CreatureBuilder())
				.setName((String) object.get("name"))
				.setFactionState(FactionState.getFactionState((String) object.get("faction"))) 
				.build();
	}

	@Override
	public String creatureToString(Creature creature) {
		JSONObject jsonObject = new JSONObject();
		String name = creature.getName();
		FactionState faction = creature.getFaction();
		String jsonCaracteristic = caracteristicToString(creature.getCaracteristic());
		jsonObject.put("name", name);
		jsonObject.put("caracteristic", jsonCaracteristic);
		jsonObject.put("faction", faction.toString());
		return jsonObject.toJSONString();
	}

	@Override
	public Caracteristic stringToCaracteristic(String string) throws ParseException {
		return new Caracteristic();
	}

	@Override
	public String caracteristicToString(Caracteristic caracteristic) {
		return "<Not implemented yet>";
	}
}
