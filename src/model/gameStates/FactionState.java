package model.gameStates;

/**
 * PEACEFUL : The creature will never attack
 * NEUTRAL : If hitted, the creature will attack
 * DEVIANT : Randomly, the creature will attack
 * AGGRESSIVE : Anyway, the creature will attack
 * @author alexa
 *
 */
public enum FactionState {
	PEACEFUL,
	NEUTRAL,
	DEVIANT,
	AGGRESSIVE;
	
	public static FactionState getFactionState(String faction) {
		switch(faction) {
		case "PEACEFUL" : return PEACEFUL;
		case "NEUTRAL" : return NEUTRAL;
		case "DEVIANT" : return DEVIANT;
		case "AGGRESSIVE" : return AGGRESSIVE;
		}
		return null;
	}
}
