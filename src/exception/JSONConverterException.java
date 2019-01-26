package exception;

public class JSONConverterException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6575467173624046388L;
	
	private final static String message = "JSONConverter can't managed this class.";
	
	public JSONConverterException() {
		super(message);
	}

}
