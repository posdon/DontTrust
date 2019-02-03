package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public Family getFamily(String familyName) {
		for(Family family : families.keySet()) {
			if(family.getFamilyName().equals(familyName)) {
				return family;
			}
		}
		return null;
	}
	
	public List<String> getCreaturesFromFamily(Family family){
		return families.get(family);
	}
	
	public Collection<Family> getFamilies() {
		return families.keySet();
	}
}
