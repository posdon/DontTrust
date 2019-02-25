package exception;

public class FamilyListException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1512011732526003428L;

	private static String isAlreadyInListMessage = "The family '<familyName>' is already in list.";
	
	private static String isNotAlreadyInListMessage = "The family '<familyName>' is not in list.";
	
	private static String replacerFamilyNameMarker = "<familyName>";

	public FamilyListException(boolean isAlreadyInList, String familyName) {
		super((isAlreadyInList)?isAlreadyInListMessage.replace(replacerFamilyNameMarker, familyName):isNotAlreadyInListMessage.replace(replacerFamilyNameMarker, familyName));
	}
	
}

