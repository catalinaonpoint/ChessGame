package r2bits.apps.chess;


import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author Roberto Ronderos Botero
 */
public abstract class Piece implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4877050010818828391L;

	ChessBoard board;
	
	public String team;
	public String type;
	protected String rowPosition;
	protected String colPosition;
	public String status;
	public int numKills;
	public int numMoves;
	public ArrayList<String> possibleMoves;
	public ArrayList<String> possibleAttackMoves;
	public boolean selected ;
	
	public Piece(String team, String type, String col, String row, ChessBoard board){
		this.team = team;
		this.type = type;
		this.rowPosition = row;
		this.colPosition = col;
		this.status = "alive";
		this.numKills = 0;
		this.numMoves = 0;
		this.board = board;
		this.selected=false;
		possibleMoves= new ArrayList<String>();		
	    possibleAttackMoves = new ArrayList<String>();	   
	}
	
	
		
	public abstract void calculatePossibleMoves(String fromL, String fromN);



	public String getRowPosition() {
		return rowPosition;
	}



	public void setRowPosition(String rowPosition) throws Exception {
		
		this.rowPosition = rowPosition;
	}



	public String getColPosition() {
		return colPosition;
	}



	public void setColPosition(String colPosition) {
		this.colPosition = colPosition;
	}



	protected int slowTest(String toL, String toN){
		//two cases for each letter and number.
		
		char fromLch = this.colPosition.charAt(0);
		int fromNint = Integer.parseInt(this.rowPosition);
		char toLch = toL.charAt(0);
		int toNint = Integer.parseInt(toN);
		//for Number
		//if destiny number is greater than source number
		if(toNint>fromNint){
			//it can only be greater by one.
			if((toNint-1)!=fromNint){
				return 1; //Error code 1. Piece can only move one square at a time
			}
		}
		else if(toNint<fromNint){
			//it can only be smaller by one.
			if((toNint+1)!=fromNint){
				return 1; //Error code 1. Piece can only move one square at a time
			}
		}
		//for Letter
		//if destiny number is greater than source number
		if(toLch>fromLch){
			//it can only be greater by one.
			if((toLch-1)!=fromLch){
				return 1; //Error code 1. Piece can only move one square at a time
			}
		}
		else if(toLch<fromLch){
			//it can only be smaller by one.
			if((toLch+1)!=fromLch){
				return 1; //Error code 1. Piece can only move one square at a time
			}
		}
		
		return 0;
	}
	
	protected int flexibleTest(String toL, String toN){
		//common conversions for tests
		char fromLch = this.colPosition.charAt(0);
		int fromNint = Integer.parseInt(this.rowPosition);
		char toLch = toL.charAt(0);
		int toNint = Integer.parseInt(toN);
		int fromLint = UtilityClass.LetterToNum(fromLch);
		int toLint = UtilityClass.LetterToNum(toLch);
		
		if(Math.abs(fromLint-toLint)!=Math.abs(fromNint-toNint)){
			return 2; //error code 2. Piece can only move diagonally
		}
		
		return 0;		
	}
	
	protected int notFlexibleTest(String toL, String toN){
		//common conversions for tests
		char fromLch = this.colPosition.charAt(0);
		int fromNint = Integer.parseInt(this.rowPosition);
		char toLch = toL.charAt(0);
		int toNint = Integer.parseInt(toN);
		
		//static letter
		if(fromLch==toLch || fromNint==toNint){
			return 0;
		}
		
		return 3; //Error code 3. Piece can only move vertically or horizontally.
	}
	
	protected int bothFlexNNotFlex(String toL, String toN){
		if(flexibleTest(toL,toN)==0 ^ notFlexibleTest(toL,toN)==0){
			return 0;
		}
		
		return 3;
	}
	
	protected boolean isMoveInsideBoard(String toL, String toN){
		char toLch = toL.charAt(0);
		int toNint = Integer.parseInt(toN);
		if(toLch>'h' ||toNint>8)
			return false;
		return true;
	}	
	
	protected boolean isAttacking(String toLetter, String toNumber){
		Piece piece = board.getPiece(toNumber, toLetter);
		
		if(piece !=null){
			if(piece.status.equals("alive")&&piece.team!=this.team)
				return true;
		}
		
		return false;
	}
	
	protected boolean leapsOver(String toLetter, String toNumber){
		//common conversions for tests
		char fromLch = this.colPosition.charAt(0);
		int fromNint = Integer.parseInt(this.rowPosition);
		char toLch = toLetter.charAt(0);
		int toNint = Integer.parseInt(toNumber);
		char l;
		int n;
		if(toNint>fromNint){ //going up				
			if(toLch>fromLch){//diagonally right
				for(l=(char) (toLch-1),n=toNint-1;l>fromLch&&n>fromNint;l--,n--){
					if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
						return true;
				}
			}
			else if( toLch >= fromLch){ //strictly going up
				l = fromLch;
				for(n=toNint-1;n>fromNint;n--){
					if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
						return true;
				}
			}
			else{ //diagonally left
				for(l=(char) (toLch+1),n=toNint-1;l<fromLch&&n>fromNint;l++,n--){
					if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
						return true;
				}
			}
		}
		else if(toNint==fromNint){ //move is within same row
			n= fromNint;
			if(toLch>fromLch){//strictly going right
				for(l=(char) (toLch-1);l>fromLch;l--){
					if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
						return true;
				}
			}			
			else{ //strictly going left
				for(l=(char) (toLch+1);l<fromLch;l++){
					if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
						return true;
				}
			}
		}
		else{ //going down
			if(toLch>fromLch){//diagonally right
				for(l=(char) (toLch-1),n=toNint+1;l>fromLch&&n<fromNint;l--,n++){
					if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
						return true;
				}
			}
			else if( toLch == fromLch){ //strictly going down
				l = fromLch;
				for(n=toNint+1;n<fromNint;n++){
					if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
						return true;
				}
			}
			else{ //diagonally left
				for(l=(char) (toLch+1),n=toNint+1;l<fromLch&&n<fromNint;l++,n++){
					if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
						return true;
				}
			}
		}
		return false;		
	}
	
	public int isLegalMove(String toL, String toN){
		if(isMoveInsideBoard(toL,toN)&&this.status.equals("alive")){
			
				if(!leapsOver(toL,toN)){
					if(canMoveThere(toL,toN)){
						
						if(isAttacking(toL,toN)){
							int Code = isPieceMove(toL,toN);
							if(Code==0){
								return 100; //successful attack return attack code
							}
							else{
								return Code;
							}
						}
						else{
							int Code = isPieceMove(toL,toN);
							if(Code==0){
								return 0;
							}
							else{
								return Code;
							}
						}
					}
					else{
						return 13;
					}
				}
				else{
					return 8;
				}
			
		}
		else{
			return -1;
		}
	}



	protected boolean canMoveThere(String toL, String toN) {
		Piece piece = board.getPiece(toN, toL);
		if(piece==null){
			return true;
		}
		else{
			if(!piece.team.equals(this.team)){
				return true;
			}
		}
		
		return false;
	}



	protected abstract int  isPieceMove(String toL, String toN);
}
