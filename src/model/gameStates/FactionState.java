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
	AGGRESSIVE
}
