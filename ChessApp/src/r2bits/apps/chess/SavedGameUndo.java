package r2bits.apps.chess;

import java.io.Serializable;

public class SavedGameUndo implements Serializable {
	/**
	 * SUID
	 */
	private static final long serialVersionUID = 8225728534795140231L;
	
	
	private DoubleLinkedList  moves ;
	
	public SavedGameUndo(){
		moves = new DoubleLinkedList();
		
	}
	
	
	public void addMove(ChessBoard board){
		moves.addTail(board);
	}
	
	public ChessBoard undoMove(){
		int size = moves.size();
		if(size>0){
			ChessBoard board= moves.undo();
			return board;
		}
		else{
			return null;
		}
	}	
	
	public boolean hasPrev(){
		if(moves.hasPrev()){
			return true;
		}
		
		return false;
	}
	
	public ChessBoard initializeBoard(){
		
		ChessBoard gameState = moves.initialize();
		return gameState;
	}
	
	
}
