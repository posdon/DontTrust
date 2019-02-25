package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.FamilyListException;

public class FamilyBook {

	private final Map<Family,List<String>> families;
	
	public final static FamilyBook familyBook = new FamilyBook();
	
	private FamilyBook() {
		families = new HashMap<Family,List<String>>();
	}
	
	public void addIntoFamily(String creatureName, Family family) {
		if(!families.containsKey(family)) {
			families.put(family, new ArrayList<String>());
		}
		families.get(family).add(creatureName);
	}
	
	public void addFamily(Family family) throws FamilyListException {
		if(families.containsKey(family)) throw new FamilyListException(true, family.getFamilyName());
		families.put(family, new ArrayList<String>());
	}
	
	public void removeFromFamily(String creatureName, Family family) throws FamilyListException {
		if(!families.containsKey(family)) throw new FamilyListException(false, family.getFamilyName());
		families.get(family).remove(creatureName);
	}
	
	public void removeFamily(String familyName) throws FamilyListException {
		for(Family family : families.keySet()) {
			if(family.getFamilyName().equals(familyName)) {
				families.remove(family);
				return;
			}
		}
		throw new FamilyListException(false, familyName);
	}
	
	public Family getFamily(String familyName) {
		for(Family family : families.keySet()) {
			if(family.getFamilyName().equals(familyName)) {
				return family;
			}
		}
		return null;
	}
	
	public Collection<Family> getFamilies() {
		return families.keySet();
	}

	public Collection<String> getAllFamiliesName() {
		List<String> result = new ArrayList<String>();
		for(Family family : families.keySet()) {
			result.add(family.getFamilyName());
		}
		return result;
	}
	
	public List<String> getCreaturesFromFamily(Family family){
		return families.get(family);
	}

	public boolean isValidName(String name) {
		return this.getFamily(name) == null;
	}
}
