package r2bits.apps.chess;

import java.io.Serializable;

public class Move implements Serializable{
	/**
	 * SUID
	 */
	private static final long serialVersionUID = -6752796506062987749L;
	
	int fromLetter;
	int fromNumber;
	int toLetter;
	int toNumber;
	
	/**
	 * Move Constructor
	 * @param fromL
	 * @param fromN
	 * @param toL
	 * @param toN
	 */
	Move(int fromL, int fromN, int toL, int toN){
		this.fromLetter = fromL;
		this.fromNumber = fromN;
		this.toLetter = toL;
		this.toNumber = toN;
	}
}
