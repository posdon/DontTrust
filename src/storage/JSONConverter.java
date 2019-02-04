package storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import exception.JSONConverterException;
import model.Caracteristic;
import model.CaracteristicBuilder;
import model.Creature;
import model.CreatureBuilder;
import model.Family;
import model.FamilyBuilder;
import model.gameStates.FactionState;

public class JSONConverter implements Converter {
	
	public static JSONConverter INSTANCE = new JSONConverter();
	
	private JSONConverter() {}
	
	public String modelToJson(Object object) throws JSONConverterException {
		if(object == null) return "null";
		Class objectClass = object.getClass();
		if(Creature.class.equals(objectClass)) {
			return creatureToString((Creature) object);
		}else if(Family.class.equals(objectClass)) {
			return familyToString((Family) object);
		}
		throw new JSONConverterException();
	}
	
	@Override
	public Creature stringToCreature(String jsonContent) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(jsonContent);
		return (new CreatureBuilder())
				.setName((String) object.get("name"))
				.setCaracteristic(stringToCaracteristic(object.get("caracteristic").toString()))
				.setFactionState(FactionState.getFactionState((String) object.get("faction"))) 
				.setDescriptionPhysique((String) object.get("physique"))
				.setFamily(stringToFamily(object.get("family").toString()))
				.build();
	}

	@Override
	public String creatureToString(Creature creature) {
		JSONObject jsonObject = new JSONObject();
		String name = creature.getName();
		FactionState faction = creature.getFaction();
		String jsonCaracteristic = caracteristicToString(creature.getCaracteristic());
		String descriptionPhysique = creature.getDescriptionPhysique();
		Family family = creature.getFamily();
		jsonObject.put("name", name);
		jsonObject.put("caracteristic", jsonCaracteristic);
		jsonObject.put("faction", faction.toString());
		jsonObject.put("physique", descriptionPhysique);
		jsonObject.put("family", familyToString(family));
		return jsonObject.toJSONString();
	}

	@Override
	public Caracteristic stringToCaracteristic(String jsonContent) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(jsonContent);
		int life = Integer.parseInt((String) object.get("life"));
		int strength = Integer.parseInt((String) object.get("strength"));
		int madness = Integer.parseInt((String) object.get("madness"));
		return (new CaracteristicBuilder()
				.setLife(life)
				.setStrength(strength)
				.setMadness(madness)
				.build());
	}

	@Override
	public String caracteristicToString(Caracteristic caracteristic) {
		JSONObject jsonObject = new JSONObject();
		int life = caracteristic.getLife();
		int strength = caracteristic.getStrength();
		int madness = caracteristic.getMadness();
		jsonObject.put("life", life);
		jsonObject.put("strength", strength);
		jsonObject.put("madness", madness);
		return jsonObject.toJSONString();
	}

	@Override
	public Family stringToFamily(String jsonContent) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(jsonContent);
		String familyName = (String) object.get("name");
		return (new FamilyBuilder()
				.setFamilyName(familyName)
				.build());
	}

	@Override
	public String familyToString(Family family) {
		JSONObject jsonObject = new JSONObject();
		String name = family.getFamilyName();
		jsonObject.put("name", name);
		return jsonObject.toJSONString();
	}
}
