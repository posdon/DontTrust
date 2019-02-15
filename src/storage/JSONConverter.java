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
			return creatureToString((Creature) object).toJSONString();
		}else if(Family.class.equals(objectClass)) {
			return familyToString((Family) object).toJSONString();
		}
		throw new JSONConverterException();
	}
	
	@Override
	public Creature stringToCreature(String jsonContent) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(jsonContent);
		CreatureBuilder builder = new CreatureBuilder();
		if(object.get("name") != null) builder.setName((String) object.get("name"));
		if(object.get("physique") != null) builder.setDescriptionPhysique((String) object.get("physique"));
		if(object.get("caracteristic") != null) builder.setCaracteristic(stringToCaracteristic(object.get("caracteristic").toString()));
		if(object.get("faction") != null) builder.setFactionState(FactionState.getFactionState((String) object.get("faction")));
		if(object.get("family") != null) builder.setFamily(stringToFamily(object.get("family").toString()));
		return builder.build();
	}

	@Override
	public JSONObject creatureToString(Creature creature) {
		JSONObject jsonObject = new JSONObject();
		if(creature == null) return jsonObject;
		String name = creature.getName();
		FactionState faction = creature.getFaction();
		String descriptionPhysique = creature.getDescriptionPhysique();
		Family family = creature.getFamily();
		jsonObject.put("name", name);
		jsonObject.put("caracteristic", caracteristicToString(creature.getCaracteristic()));
		jsonObject.put("faction", faction.toString());
		jsonObject.put("physique", descriptionPhysique);
		jsonObject.put("family", familyToString(family));
		return jsonObject;
	}

	@Override
	public Caracteristic stringToCaracteristic(String jsonContent) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(jsonContent);
		CaracteristicBuilder builder = new CaracteristicBuilder();
		if(object.get("life") != null) builder.setLife(Integer.parseInt(object.get("life").toString()));
		if(object.get("madness") != null) builder.setMadness(Integer.parseInt(object.get("madness").toString()));
		if(object.get("strength") != null) builder.setStrength(Integer.parseInt(object.get("strength").toString()));
		return builder.build();
	}

	@Override
	public JSONObject caracteristicToString(Caracteristic caracteristic) {
		JSONObject jsonObject = new JSONObject();
		if(caracteristic == null) return jsonObject;
		int life = caracteristic.getLife();
		int strength = caracteristic.getStrength();
		int madness = caracteristic.getMadness();
		jsonObject.put("life", life);
		jsonObject.put("strength", strength);
		jsonObject.put("madness", madness);
		return jsonObject;
	}

	@Override
	public Family stringToFamily(String jsonContent) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(jsonContent);
		FamilyBuilder builder = new FamilyBuilder();
		if(object.get("name") != null)  builder.setFamilyName(object.get("name").toString());
		return builder.build();
	}

	@Override
	public JSONObject familyToString(Family family) {
		JSONObject jsonObject = new JSONObject();
		if(family == null) return jsonObject;
		String name = family.getFamilyName();
		jsonObject.put("name", name);
		return jsonObject;
	}
}
