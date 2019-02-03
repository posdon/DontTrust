package model;

public class FamilyBuilder {

	private String familyName;
	
	public FamilyBuilder() {
		setFamilyName("undefined");
	}

	public String getFamilyName() {
		return familyName;
	}

	public FamilyBuilder setFamilyName(String familyName) {
		this.familyName = familyName;
		return this;
	}
	
	public Family build() {
		return new Family(familyName);
	}
}
