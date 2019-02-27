package model;

public class FamilyBuilder {

	private String familyName;
	private String description;
	private String histoire;
	
	public FamilyBuilder() {
		setFamilyName("undefined");
		setFamilyName("undefined");
		setFamilyName("undefined");
	}

	public String getFamilyName() {
		return familyName;
	}

	public FamilyBuilder setFamilyName(String familyName) {
		this.familyName = familyName;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public FamilyBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getHistoire() {
		return histoire;
	}

	public FamilyBuilder setHistoire(String histoire) {
		this.histoire = histoire;
		return this;
	}
	
	public Family build() {
		return new Family(familyName,description,histoire);
	}
}
