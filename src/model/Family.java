package model;

public class Family {

	private String familyName;
	private String description;
	private String histoire;
	
	public Family(String name, String description, String histoire) {
		this.familyName = name;
		this.description = description;
		this.histoire = histoire;
	}
	
	public String getFamilyName() {
		return this.familyName;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getHistoire() {
		return this.histoire;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((familyName == null) ? 0 : familyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Family other = (Family) obj;
		if (familyName == null) {
			if (other.familyName != null)
				return false;
		} else if (!familyName.equals(other.familyName))
			return false;
		return true;
	}
	
	public String toString() {
		return familyName;
	}
}
