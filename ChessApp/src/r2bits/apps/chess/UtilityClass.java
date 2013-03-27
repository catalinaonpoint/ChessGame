package r2bits.apps.chess;
/**
 * This class will contain all utility methods
 * @author Roberto Ronderos Botero
 */
public class UtilityClass {
	/**
	 * This Method receives an integer error code and decodes it to its correspondent string error.
	 * @param errorCode
	 * @return
	 */
	public static String errorTranslator(int errorCode){
		switch(errorCode){
		case -3:
			return "Error: You can only move your own pieces.";
		case -2:
			return "Error: Unkown Illegal move, try again.";
		case -1:
			return "Error: Move goes beyond board limits, try again.";
		case 1:
			return "Error: This piece can only move one square at a time, try again.";		
		case 2:
			return "Error: Piece can only move diagonally, try again.";
		case 3:
			return "Error: Piece can only move vertically or horizontally, try again.";
		case 4:
			return "Error: Piece can only move vertically, horizontally or diagonally, try again.";
		case 5:
			return "Error: En Passant move can only be done in the next immediate turn, try again.";
		case 6:
			return "Error: Piece can only move forward,try again.";
		case 7:
			return "Error: On Pawn's first move you can only move one or two squares forward, try again.";
		case 8:
			return "Error: This piece can't leap over another piece try again.";
		case 9:
			return "Error: Castling can only be done once per game, try again.";
		case 10:
			return "Error: Castling can't be done when there are pieces in between, try again.";
		case 11:
			return "Error: Neither of the pieces involved in castling may have been previously moved during the game, try again";
		case 12:
			return "Error: Knight can only move in L shape, try again.";
		case 13:
			return "Error: There's a piece of your own team there, try again.";
		case 14:
			return "Error: This moves generates a check, try again.";
		}
		return null;
	}
	/**
	 * Converts the incoming letter to its correspondent position in the board array.
	 * @param position
	 * @return
	 */	
	public static int getCellID(int row, int col){
		if(col==1){
			if(row==1){
				return R.id.A1;
			}
			else if(row==2){
				return R.id.A2;
			}
			else if(row==3){
				return R.id.A3;		
			}
			else if(row==4){
				return R.id.A4;
			}
			else if(row==5){
				return R.id.A5;
			}
			else if(row==6){
				return R.id.A6;
			}
			else if(row==7){
				return R.id.A7;
			}
			else if(row==8){
				return R.id.A8;
			}
		}
		else if(col==2){
			if(row==1){
				return R.id.B1;
			}
			else if(row==2){
				return R.id.B2;
			}
			else if(row==3){
				return R.id.B3;		
			}
			else if(row==4){
				return R.id.B4;
			}
			else if(row==5){
				return R.id.B5;
			}
			else if(row==6){
				return R.id.B6;
			}
			else if(row==7){
				return R.id.B7;
			}
			else if(row==8){
				return R.id.B8;
			}
		}
		else if(col==3){
			if(row==1){
				return R.id.C1;
			}
			else if(row==2){
				return R.id.C2;
			}
			else if(row==3){
				return R.id.C3;		
			}
			else if(row==4){
				return R.id.C4;
			}
			else if(row==5){
				return R.id.C5;
			}
			else if(row==6){
				return R.id.C6;
			}
			else if(row==7){
				return R.id.C7;
			}
			else if(row==8){
				return R.id.C8;
			}
		}
		else if(col==4){
			if(row==1){
				return R.id.D1;
			}
			else if(row==2){
				return R.id.D2;
			}
			else if(row==3){
				return R.id.D3;		
			}
			else if(row==4){
				return R.id.D4;
			}
			else if(row==5){
				return R.id.D5;
			}
			else if(row==6){
				return R.id.D6;
			}
			else if(row==7){
				return R.id.D7;
			}
			else if(row==8){
				return R.id.D8;
			}
		}
		else if(col==5){
			if(row==1){
				return R.id.E1;
			}
			else if(row==2){
				return R.id.E2;
			}
			else if(row==3){
				return R.id.E3;		
			}
			else if(row==4){
				return R.id.E4;
			}
			else if(row==5){
				return R.id.E5;
			}
			else if(row==6){
				return R.id.E6;
			}
			else if(row==7){
				return R.id.E7;
			}
			else if(row==8){
				return R.id.E8;
			}
		}
		else if(col==6){
			if(row==1){
				return R.id.F1;
			}
			else if(row==2){
				return R.id.F2;
			}
			else if(row==3){
				return R.id.F3;		
			}
			else if(row==4){
				return R.id.F4;
			}
			else if(row==5){
				return R.id.F5;
			}
			else if(row==6){
				return R.id.F6;
			}
			else if(row==7){
				return R.id.F7;
			}
			else if(row==8){
				return R.id.F8;
			}
		}
		else if(col==7){
			if(row==1){
				return R.id.G1;
			}
			else if(row==2){
				return R.id.G2;
			}
			else if(row==3){
				return R.id.G3;		
			}
			else if(row==4){
				return R.id.G4;
			}
			else if(row==5){
				return R.id.G5;
			}
			else if(row==6){
				return R.id.G6;
			}
			else if(row==7){
				return R.id.G7;
			}
			else if(row==8){
				return R.id.G8;
			}
		}
		else if(col==8){
			if(row==1){
				return R.id.H1;
			}
			else if(row==2){
				return R.id.H2;
			}
			else if(row==3){
				return R.id.H3;		
			}
			else if(row==4){
				return R.id.H4;
			}
			else if(row==5){
				return R.id.H5;
			}
			else if(row==6){
				return R.id.H6;
			}
			else if(row==7){
				return R.id.H7;
			}
			else if(row==8){
				return R.id.H8;
			}
		}
		
		return -1;
	}
	
	public static int getPieceUnicodeID(String team, String type){
		
		if(team.equals("w")){
			if(type.equals("p")){ //pawn
				return R.string.whitepawn;
			}
			else if(type.equals("K")){ //King
				return R.string.whiteking;
			}
			else if(type.equals("Q")){ //Queen
				return R.string.whitequeen;
			}
			else if(type.equals("R")){ //Rook
				return R.string.whiterook;
			}
			else if(type.equals("B")){ //Bishop
				return R.string.whitebioshop;
			}
			else if(type.equals("N")){ //Knight
				return R.string.whiteknight;
			}
				
		}
		else{
			if(type.equals("p")){ //pawn
				return R.string.blackpawn;
			}
			else if(type.equals("K")){ //King
				return R.string.blackking;
			}
			else if(type.equals("Q")){ //Queen
				return R.string.blackqueen;
			}
			else if(type.equals("R")){ //Rook
				return R.string.blackrook;
			}
			else if(type.equals("B")){ //Bishop
				return R.string.blackbioshop;
			}
			else if(type.equals("N")){ //Knight
				return R.string.blackknight;
			}
		}
		return 0;		
		
		
	}
	
	
	/**
	 * Returns the equivalent number from an incoming letter eg. B = 1;
	 * @param ch
	 * @return
	 */
	public static int LetterToNum(char ch){
		switch(ch){
		case 'a':
			return 1;
		case 'b':
			return 2;
		case 'c':
			return 3;
		case 'd':
			return 4;
		case 'e':
			return 5;
		case 'f':
			return 6;
		case 'g':
			return 7;
		case 'h':
			return 8;			
		}
		
		return -1; //error
	}
	/**
	 * Returns the equivlent letter from an incoming number ed. 1 = a.
	 * @param n
	 * @return
	 */
	public static String NumToLetter(int n){
		switch(n){
		case 1:
			return "a";
		case 2:
			return "b";
		case 3:
			return "c";
		case 4:
			return "d";
		case 5:
			return "e";
		case 6:
			return "f";
		case 7:
			return "g";
		case 8:
			return "h";			
		}
		
		return "-"; //error
	}
	
	public static int getRowID(String row, boolean isRotated){
		if(!isRotated){
			if(row.equals("1")){
				return R.id.row1;
			}
			else if(row.equals("2")){
				return R.id.row2;
			}
			else if(row.equals("3")){
				return R.id.row3;		
			}
			else if(row.equals("4")){
				return R.id.row4;
			}
			else if(row.equals("5")){
				return R.id.row5;
			}
			else if(row.equals("6")){
				return R.id.row6;
			}
			else if(row.equals("7")){
				return R.id.row7;
			}
			else if(row.equals("8")){
				return R.id.row8;
			}
		}
		else{
			if(row.equals("1")){
				return R.id.row8;
			}
			else if(row.equals("2")){
				return R.id.row7;
			}
			else if(row.equals("3")){
				return R.id.row6;		
			}
			else if(row.equals("4")){
				return R.id.row5;
			}
			else if(row.equals("5")){
				return R.id.row4;
			}
			else if(row.equals("6")){
				return R.id.row3;
			}
			else if(row.equals("7")){
				return R.id.row2;
			}
			else if(row.equals("8")){
				return R.id.row1;
			}
		}
		return 0;
	}
	
}
