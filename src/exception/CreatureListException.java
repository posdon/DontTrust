package exception;

public class CreatureListException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -103268559985910798L;
	
	private static String isAlreadyInListMessage = "The creature '<creatureName>' is already in list.";
	
	private static String isNotAlreadyInListMessage = "The creature '<creatureName>' is not in list.";
	
	private static String replacerCreatureNameMarker = "<creatureName>";

	public CreatureListException(boolean isAlreadyInList, String creatureName) {
		super((isAlreadyInList)?isAlreadyInListMessage.replace(replacerCreatureNameMarker, creatureName):isNotAlreadyInListMessage.replace(replacerCreatureNameMarker, creatureName));
	}
	
}
