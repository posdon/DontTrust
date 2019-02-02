package exception;

public class MessageOver2000Exception extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4085412339683764603L;

	public MessageOver2000Exception(String message) {
		super("The following message is more than 2000 character : '"+message+"'");
	}
}
