package r2bits.apps.chess;

import java.io.Serializable;
import java.util.ArrayList;




/**
 * @author Roberto Ronderos Botero
 */
public class Team implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5543051774539741765L;
	// ArrayList to hold instances of a pieces.
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	//name of the team (white or black)
	String teamName; 
	//board where team is playing
	ChessBoard board;
	/**
	 * Team constructor
	 * @param teamName
	 * @param board
	 */
	Team(String teamName, ChessBoard board){
		this.teamName = teamName;
		this.board = board;
		if(teamName.equals("black")){
			this.pieces.add(new Pawn("b","p","a","7", board));
			this.pieces.add(new Pawn("b","p","b","7", board));
			this.pieces.add(new Pawn("b","p","c","7", board));
			this.pieces.add(new Pawn("b","p","d","7", board));
			this.pieces.add(new Pawn("b","p","e","7", board));
			this.pieces.add(new Pawn("b","p","f","7", board));
			this.pieces.add(new Pawn("b","p","g","7", board));
			this.pieces.add(new Pawn("b","p","h","7", board));
			this.pieces.add(new King("b","K","e","8", board));
			this.pieces.add(new Queen("b","Q","d","8", board));
			this.pieces.add(new Rook("b","R","a","8", board));
			this.pieces.add(new Rook("b","R","h","8", board));
			this.pieces.add(new Bishop("b","B","c","8", board));
			this.pieces.add(new Bishop("b","B","f","8", board));
			this.pieces.add(new Knight("b","N","b","8", board));
			this.pieces.add(new Knight("b","N","g","8", board));
		}
		else{
			this.pieces.add(new Pawn("w","p","a","2", board));
			this.pieces.add(new Pawn("w","p","b","2", board));
			this.pieces.add(new Pawn("w","p","c","2", board));
			this.pieces.add(new Pawn("w","p","d","2", board));
			this.pieces.add(new Pawn("w","p","e","2", board));			
			this.pieces.add(new Pawn("w","p","f","2", board));
			this.pieces.add(new Pawn("w","p","g","2", board));
			this.pieces.add(new Pawn("w","p","h","2", board));
			this.pieces.add(new King("w","K","e","1", board));
			this.pieces.add(new Queen("w","Q","d","1", board));
			this.pieces.add(new Rook("w","R","a","1", board));
			this.pieces.add(new Rook("w","R","h","1", board));
			this.pieces.add(new Bishop("w","B","c","1", board));
			this.pieces.add(new Bishop("w","B","f","1", board));
			this.pieces.add(new Knight("w","N","b","1", board));
			this.pieces.add(new Knight("w","N","g","1", board));
		}
		
	}
	/**
	 * Places each team's piece in the array board.
	 * @param board
	 * @return board
	 */
	public int[][] placePieces(int[][] board){
		
		for(Piece piece:this.pieces){
			if(piece.status.equals("alive")){
				int row = Integer.parseInt(piece.getRowPosition());
				int col = UtilityClass.LetterToNum(piece.getColPosition().charAt(0));				
				int pieceType = UtilityClass.getPieceUnicodeID(piece.team, piece.type);
				row--;
				col--;
				board[row][col]= pieceType;							
			}
		}
		
		
		return board;
	}
	
	
	
}
