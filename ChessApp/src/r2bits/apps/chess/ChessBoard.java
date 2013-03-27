package r2bits.apps.chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;




/**
 * @author Roberto Ronderos Botero
 */
public class ChessBoard implements Serializable {
	
	/**
	 * SUID
	 */
	private static final long serialVersionUID = -3971191419261581603L;
	
	
	protected int prevPieceSelectedID = 0;
	protected Piece prevPieceSelectedP = null;
	final int rows=8;
	final int cols=8;
	Team white,black;
	protected String turn;
	int board[][] = new int[rows][cols];
	ArrayList<Piece> piecesInGame = new ArrayList<Piece>();
	protected boolean isRotated = false;
	protected int moveNumber;
	boolean kingInCheck = false;
	Piece checksAttacker;
	public boolean promotion=false;
	
	
	/**
	 * Initializes chess board
	 * 
	 */
	protected ChessBoard(){
		this.moveNumber = 0;
		white = new Team("white",this);
		black = new Team("black",this);
		placeAndUpdatePieces();
		turn = "w";
		
	}
	
	void placeAndUpdatePieces(){			
		this.piecesInGame.clear();
		clearBoard();
		for(Piece piece: this.white.pieces){
			if(piece.status.equals("alive"))
			this.piecesInGame.add(piece);
		}
		for(Piece piece: this.black.pieces){
			if(piece.status.equals("alive"))
			this.piecesInGame.add(piece);
		}
		this.board = this.white.placePieces(this.board);
		this.board = this.black.placePieces(this.board);	
	}
	
	private void clearBoard(){
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board.length;j++){
				board[i][j]=0;
			}
		}
	}
	
	protected Piece getPiece(String r,String c){
		try{
			int row = Integer.parseInt(r);
			int col = UtilityClass.LetterToNum(c.charAt(0));
			int piece = this.board[row-1][col-1];
			if(piece!=0){			
					for(Piece pieceInGame:this.piecesInGame){
						if(pieceInGame.getRowPosition().equals(r)&&pieceInGame.getColPosition().equals(c)){
							return pieceInGame;
						}
					}				
			}
			else{
				return null;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			return null;
		}
		
		return null;
	}
	
	int translateTeamToNum(String pieceTeam){
		if(pieceTeam.endsWith("b")){
			return 1;
		}
		return 0;
	}
	
	protected int gameOver() throws Exception{
		if(this.turn.equals("w")){
			King King = findKing("w");
			if(King.possibleMoves.size()==0&&King.possibleAttackMoves.size()==0){				
				if(!pieceMovesinCheck("w")){ //if all pieces from that team generate generatesCheck proceed to next step;
					return -1;	
				}
				for(Iterator<Piece> iter = black.pieces.iterator(); iter.hasNext();){
					Piece piece = iter.next();
					if(piece.possibleAttackMoves.contains(King.getColPosition()+King.getRowPosition())){						
						return 0;//checkmate = 0;
					}
				}				
				return 1;//stalemate = 1;
			}
				
		}
		else{
			King King = findKing("b");
			if(King.possibleMoves.size()==0&&King.possibleAttackMoves.size()==0){
				if(!pieceMovesinCheck("b")){ //if all pieces from that team generate generatesCheck proceed to next step;
					return -1;	
				}
				for(Iterator<Piece> iter = white.pieces.iterator(); iter.hasNext();){
					Piece piece = iter.next();
					if(piece.possibleAttackMoves.contains(King.getColPosition()+King.getRowPosition())){						
						return 2;//checkmate;
					}
				}
				return 3;//stalemate;
			}
		}	
		
		return -1;
	}
	
	private boolean pieceMovesinCheck(String team) throws Exception{
		if(team.equals("w")){
			for(Iterator<Piece> iter = white.pieces.iterator(); iter.hasNext();){
				Piece piece = iter.next();
				if(piece.status.equals("alive")){
					if(piece.possibleMoves.size()>0){
						for(Iterator<String> iter2 = piece.possibleMoves.iterator(); iter2.hasNext();){
							String move = iter2.next();
							String N,L;
							L = move.substring(0,1);
							N = move.substring(1);
							if(!generatesCheck(L,N,piece))
							{
								return false;
							}
						}					
					}
					if(piece.possibleAttackMoves.size()>0){
						for(Iterator<String> iter2 = piece.possibleAttackMoves.iterator(); iter2.hasNext();){
							String move = iter2.next();
							String N,L;
							L = move.substring(0,1);
							N = move.substring(1);
							if(!generatesCheck(L,N,piece))
							{
								return false;
							}
						}
					}
				}
			}
		}
		else{
			for(Iterator<Piece> iter = black.pieces.iterator(); iter.hasNext();){
				Piece piece = iter.next();
				if(piece.status.equals("alive")){
					if(piece.possibleMoves.size()>0){
						for(Iterator<String> iter2 = piece.possibleMoves.iterator(); iter2.hasNext();){
							String move = iter2.next();
							String N,L;
							L = move.substring(0,1);
							N = move.substring(1);
							if(!generatesCheck(L,N,piece))
							{
								return false;
							}
						}					
					}
					if(piece.possibleAttackMoves.size()>0){
						for(Iterator<String> iter2 = piece.possibleAttackMoves.iterator(); iter2.hasNext();){
							String move = iter2.next();
							String N,L;
							L = move.substring(0,1);
							N = move.substring(1);
							if(!generatesCheck(L,N,piece))
							{
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	
		
	

	void calculatePossibleMovesForAll() {
		for(Piece piece:this.piecesInGame){
			//calculate possible moves
			piece.possibleAttackMoves.clear();
			piece.possibleMoves.clear();				
			piece.calculatePossibleMoves(piece.getColPosition(), piece.getRowPosition());
		}
		
	}

	
	
	void rotateBoard() throws Exception {		
		if(!isRotated){
			isRotated = true;
		}
		else{
			isRotated = false;
		}
							
	}
	
	

	protected void showPossibleMoves(String pos) {
		String L = pos.substring(0,1);
		String N = pos.substring(1);
		Piece piece = getPiece(N,L);
		
		if(piece!=null){
			System.out.println("Possible Moves for "+piece.team+piece.type+" at position "+L+N+" :");
			if(piece.possibleMoves.size()>0){
				for(String move:piece.possibleMoves){				
				System.out.println(move);
				}
			}
			else{
				System.out.println("No Possible Moves for that piece");
			}
		}
		else{
			System.out.println("No piece at that position.");
		}
		
	}
	protected void showPossibleAttackMoves(String pos) {
		String L = pos.substring(0,1);
		String N = pos.substring(1);
		Piece piece = getPiece(N,L);
		
		if(piece!=null){
			System.out.println("Possible Attack Moves for "+piece.team+piece.type+" at position "+L+N+" :");
			if(piece.possibleAttackMoves.size()>0){
				for(String move:piece.possibleAttackMoves){				
					System.out.println(move);
				}
			}
			else{
				System.out.println("No Possible Attack Moves for that piece");
			}
		}
		else{
			System.out.println("No piece at that position.");
		}
		
	}
	

	protected boolean kingscheck(String L, String N) {
		if(this.turn.equals("w")){
			for(Piece piece:black.pieces){
				if(!piece.status.equals("death")){
					
					if(piece.possibleMoves.contains(L+N)){
						if(piece.possibleAttackMoves.contains(L+N)){
							return true;
						}
					  return true;
					}
				}
			}
		}
		else{
			for(Piece piece:white.pieces){
				if(!piece.status.equals("death")){					
					if(piece.possibleMoves.contains(L+N)){
						if(piece.possibleAttackMoves.contains(L+N)){
							return true;
						}
					  return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	
	boolean unChecks(String L, String N, Piece piece){
		if(!piece.type.equals("K")){
			ArrayList<String> pathToDeath = new ArrayList<String>();
			King king = null;		
			String move = L+N; //specific move
			//Finding King
			if(this.turn.equals("w")){
				king = findKing("w");
			}
			else{
				king = findKing("b");
			}
			//Configuring positions
			int fromN = Integer.parseInt(checksAttacker.getRowPosition());
			int fromL = UtilityClass.LetterToNum(checksAttacker.getColPosition().charAt(0));
			int toN = Integer.parseInt(king.getRowPosition());
			int toL = UtilityClass.LetterToNum(king.getColPosition().charAt(0));
			//finding right direction
			if(!checksAttacker.type.equals("N")){ //change this instead of piece it should be attackers
				if(toL<fromL){ //Going left
					if(toN<fromN){ //Diagonally down
						pathToDeath = pathOfDeath(1,fromL,fromN,toL,toN);
					}
					else if(toN>fromN){ //Diagonally up
						pathToDeath = pathOfDeath(2,fromL,fromN,toL,toN);
					}
					else{ //Strictly going left
						pathToDeath = pathOfDeath(3,fromL,fromN,toL,toN);
					}
				}
				else if(toL>fromL){ //Going right
					if(toN<fromN){ //Diagonally down
						pathToDeath = pathOfDeath(4,fromL,fromN,toL,toN);
					}
					else if(toN>fromN){ //Diagonally up
						pathToDeath = pathOfDeath(5,fromL,fromN,toL,toN);
					}
					else{ //Strictly going right
						pathToDeath = pathOfDeath(6,fromL,fromN,toL,toN);
					}
				}
				else{
					if(toN>fromN){ // Strictly Going Up
						pathToDeath = pathOfDeath(7,fromL,fromN,toL,toN);
					}
					else if(toN<fromN){ //Strictly Going Down
						pathToDeath = pathOfDeath(8,fromL,fromN,toL,toN);
					}
				}
			}
			else{
				//to be done for kight
			}
			
			if(pathToDeath.contains(move)){
				this.kingInCheck=false;
				return true;
			}
		}
		else{
			String kingsMove = L+N;
			String attackersPos = checksAttacker.getColPosition()+checksAttacker.getRowPosition();
			if((!this.checksAttacker.possibleMoves.contains(kingsMove)&&!this.checksAttacker.possibleAttackMoves.contains(kingsMove))
				||piece.possibleAttackMoves.contains(attackersPos)){
				this.kingInCheck=false;
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList<String> pathOfDeath(int option, int fromL, int fromN, int toL, int toN){
		ArrayList<String> path = new ArrayList<String>();
		path.add(UtilityClass.NumToLetter(fromL)+Integer.toString(fromN)); //adding itself to path
		int fL = fromL;
		int fN = fromN;
		int tL = toL;
		int tR = toL;
		int tN = toN;
		int tU = toN;
		int tD = toN;
		boolean done=false;
		int counter;
		
		/*
		 * options:
		 * 1. Going Diagonally left down
		 * 2. Going Diagonally left Up
		 * 3. Going Strictly left
		 * 4. Going Diagonally right down
		 * 5. Going Diagonally right up
		 * 6. Going Strictly right
		 * 7. Going Strictly up
		 * 8. Going Strictly down
		 */
		
		switch(option){
			case 1: //Going Diagonally left down
				int tLL;
				counter = 1;
				while(!done){					
					tN = fN; //row from
					tLL = fL; //col from	(LL =  lower left)				
					tN-= counter; //going down by counter
					tLL-= counter; //going left by counter
					if(tN>=1 && tLL>=1 && (tN!=toN && tLL!=toL)){
						path.add(UtilityClass.NumToLetter(tLL)+Integer.toString(tN));
					}
					else{
						done = true;
					}
					counter++;
				}
				break;
			case 2: //Going Diagonally left Up
				int tUL;
				counter = 1;
				while(!done){					
					tN = fN; //row from
					tUL = fL; //col from	(UL =  Upper left)				
					tN+= counter; //going up by counter
					tUL-= counter; //going left by counter
					if(tN<=8 && tUL>=1 && (tN!=toN && tUL!=toL)){
						path.add(UtilityClass.NumToLetter(tUL)+Integer.toString(tN));
					}
					else{
						done = true;
					}
					counter++;
				}
				break;
			case 3: // Strictly left
				while(!done){
					tL-= 1; //Going left				
					if(tL>=1 && tL!=toL){
						path.add(UtilityClass.NumToLetter(tL)+Integer.toString(tN));
					}
					else{
						done = true;
					}
				}
				break;
			case 4: //Diagonally right down
				int tLR;
				counter = 1;
				while(!done){					
					tN = fN; //row from
					tLR = fL; //col from	(LL =  lower right)				
					tN-= counter; //going down by counter
					tLR+= counter; //going right by counter
					if(tN>=1 && tLR<=8 && (tN!=toN && tLR!=toL)){
						path.add(UtilityClass.NumToLetter(tLR)+Integer.toString(tN));
					}
					else{
						done = true;
					}
					counter++;
				}
				break;
			case 5: //Going Diagonally right up
				int tUR;
				counter = 1;
				while(!done){					
					tN = fN; //row from
					tUR = fL; //col from	(LL =  upper right)				
					tN+= counter; //going up by counter
					tUR+= counter; //going right by counter
					if(tN<=8 && tUR<=8 && (tN!=toN && tUR!=toL)){
						path.add(UtilityClass.NumToLetter(tUR)+Integer.toString(tN));
					}
					else{
						done = true;
					}
					counter++;
				}
				break;
			case 6: //Strictly going right 
				while(!done){
					tR+= 1; //Going right				
					if(tR<=8 && tR!=toL){
						path.add(UtilityClass.NumToLetter(tR)+Integer.toString(tN));
					}
					else{
						done = true;
					}
				}
				break;
			case 7: //Going Strictly up
				while(!done){
					tU+= 1; //Going up				
					if(tU<=8 && tU!=toN){
						path.add(UtilityClass.NumToLetter(tL)+Integer.toString(tU));
					}
					else{
						done = true;
					}
				}
				break;
			default: //Going Strictly down
				while(!done){
					tD-= 1; //Going down				
					if(tD>=1 && tD!=toN){
						path.add(UtilityClass.NumToLetter(tL)+Integer.toString(tD));
					}
					else{
						done = true;
					}
				}				
		}
		
		return path;
	}
	
	protected boolean generatesCheck(String L, String N, Piece incomingPiece) throws Exception {
		
		if(this.turn.equals("w")){
			King whiteKing = findKing("w");
			for(Piece piece:black.pieces){
				if(!piece.status.equals("death")){					
					String savedL = incomingPiece.getColPosition();
					String savedN = incomingPiece.getRowPosition();
					ArrayList<String> savedMoves = new ArrayList<String>();
					ArrayList<String> savedAttackMoves = new ArrayList<String>();
					for(String move:piece.possibleMoves){
						savedMoves.add(move);
					}
					for(String move:piece.possibleAttackMoves){
						savedAttackMoves.add(move);
					}
					int code = incomingPiece.isLegalMove(L, N);
					if(code==0||code==102){ //is a legal proceed to temporarily move the piece and generatesCheck moves
						incomingPiece.setColPosition(L);
						incomingPiece.setRowPosition(N);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						piece.calculatePossibleMoves(piece.getColPosition(), piece.getRowPosition());
						if(piece.possibleAttackMoves.contains(whiteKing.getColPosition()+whiteKing.getRowPosition())){
							incomingPiece.setColPosition(savedL);
							incomingPiece.setRowPosition(savedN);
							piece.possibleAttackMoves.clear();
							piece.possibleMoves.clear();
							for(String move:savedMoves){
								piece.possibleMoves.add(move);
							}
							for(String move:savedAttackMoves){
								piece.possibleAttackMoves.add(move);
							}
							return true;
						}
						incomingPiece.setColPosition(savedL);
						incomingPiece.setRowPosition(savedN);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						for(String move:savedMoves){
							piece.possibleMoves.add(move);
						}
						for(String move:savedAttackMoves){
							piece.possibleAttackMoves.add(move);
						}
					}
					else if(code==100||code==101){
						
						//get target
						Piece targetPiece = getPiece(N,L);
						targetPiece.status="death"; //to be later revived.
						
						
						incomingPiece.setColPosition(L);
						incomingPiece.setRowPosition(N);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						piece.calculatePossibleMoves(piece.getColPosition(), piece.getRowPosition());
						if(piece.possibleAttackMoves.contains(whiteKing.getColPosition()+whiteKing.getRowPosition())){
							targetPiece.status="alive";
							incomingPiece.setColPosition(savedL);
							incomingPiece.setRowPosition(savedN);
							piece.possibleAttackMoves.clear();
							piece.possibleMoves.clear();
							for(String move:savedMoves){
								piece.possibleMoves.add(move);
							}
							for(String move:savedAttackMoves){
								piece.possibleAttackMoves.add(move);
							}
							return true;
						}						
						targetPiece.status="alive";
						incomingPiece.setColPosition(savedL);
						incomingPiece.setRowPosition(savedN);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						for(String move:savedMoves){
							piece.possibleMoves.add(move);
						}
						for(String move:savedAttackMoves){
							piece.possibleAttackMoves.add(move);
						}
					}
					else if(code==103){						
						char lTo= L.charAt(0);
						char lFrom = incomingPiece.getColPosition().charAt(0);
						Rook rook;
						if(lTo>lFrom){ //castling right rook
							rook = (Rook) getPiece(incomingPiece.getRowPosition(),"h");
							rook.setColPosition("f");
						}
						else{
							rook = (Rook) getPiece(incomingPiece.getRowPosition(),"a"); 
							rook.setColPosition("d");
						}
						incomingPiece.setColPosition(L);
						incomingPiece.setRowPosition(N);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						piece.calculatePossibleMoves(piece.getColPosition(), piece.getRowPosition());
						if(piece.possibleAttackMoves.contains(whiteKing.getColPosition()+whiteKing.getRowPosition())){
							if(lTo>lFrom){ //castling right rook									 
								rook.setColPosition("h");//ret
							}
							else{									 
								rook.setColPosition("a");//ret
							}
							incomingPiece.setColPosition(savedL);
							incomingPiece.setRowPosition(savedN);
							piece.possibleAttackMoves.clear();
							piece.possibleMoves.clear();
							for(String move:savedMoves){
								piece.possibleMoves.add(move);
							}
							for(String move:savedAttackMoves){
								piece.possibleAttackMoves.add(move);
							}
							return true;
						}
						if(lTo>lFrom){ //castling right rook									 
							rook.setColPosition("h");//ret
						}
						else{									 
							rook.setColPosition("a");//ret
						}
						incomingPiece.setColPosition(savedL);
						incomingPiece.setRowPosition(savedN);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						for(String move:savedMoves){
							piece.possibleMoves.add(move);
						}
						for(String move:savedAttackMoves){
							piece.possibleAttackMoves.add(move);
						}
					}
					
					
				}
			}
		}
		else{
			King blackKing = findKing("b");
			for(Piece piece:white.pieces){
				if(!piece.status.equals("death")){					
					String savedL = incomingPiece.getColPosition();
					String savedN = incomingPiece.getRowPosition();
					ArrayList<String> savedMoves = new ArrayList<String>();
					ArrayList<String> savedAttackMoves = new ArrayList<String>();
					for(String move:piece.possibleMoves){
						savedMoves.add(move);
					}
					for(String move:piece.possibleAttackMoves){
						savedAttackMoves.add(move);
					}
					int code = incomingPiece.isLegalMove(L, N);
					if(code==0||code==102){ //is a legal proceed to temporarily move the piece and generatesCheck moves
						incomingPiece.setColPosition(L);
						incomingPiece.setRowPosition(N);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						piece.calculatePossibleMoves(piece.getColPosition(), piece.getRowPosition());
						if(piece.possibleAttackMoves.contains(blackKing.getColPosition()+blackKing.getRowPosition())){
							incomingPiece.setColPosition(savedL);
							incomingPiece.setRowPosition(savedN);
							piece.possibleAttackMoves.clear();
							piece.possibleMoves.clear();
							for(String move:savedMoves){
								piece.possibleMoves.add(move);
							}
							for(String move:savedAttackMoves){
								piece.possibleAttackMoves.add(move);
							}
							return true;
						}
						incomingPiece.setColPosition(savedL);
						incomingPiece.setRowPosition(savedN);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						for(String move:savedMoves){
							piece.possibleMoves.add(move);
						}
						for(String move:savedAttackMoves){
							piece.possibleAttackMoves.add(move);
						}
					}
					else if(code==100||code==101){
						
						//get target
						Piece targetPiece = getPiece(N,L);
						targetPiece.status="death"; //to be later revived.
						
						
						incomingPiece.setColPosition(L);
						incomingPiece.setRowPosition(N);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						piece.calculatePossibleMoves(piece.getColPosition(), piece.getRowPosition());
						if(piece.possibleAttackMoves.contains(blackKing.getColPosition()+blackKing.getRowPosition())){
							targetPiece.status="alive";
							incomingPiece.setColPosition(savedL);
							incomingPiece.setRowPosition(savedN);
							piece.possibleAttackMoves.clear();
							piece.possibleMoves.clear();
							for(String move:savedMoves){
								piece.possibleMoves.add(move);
							}
							for(String move:savedAttackMoves){
								piece.possibleAttackMoves.add(move);
							}
							return true;
						}
						targetPiece.status="alive";
						incomingPiece.setColPosition(savedL);
						incomingPiece.setRowPosition(savedN);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						for(String move:savedMoves){
							piece.possibleMoves.add(move);
						}
						for(String move:savedAttackMoves){
							piece.possibleAttackMoves.add(move);
						}
					}
					else if(code==103){
						char lTo= L.charAt(0);
						char lFrom = incomingPiece.getColPosition().charAt(0);
						Rook rook;
						if(lTo>lFrom){ //castling right rook
							rook = (Rook) getPiece(incomingPiece.getRowPosition(),"h");
							rook.setColPosition("f");
						}
						else{
							rook = (Rook) getPiece(incomingPiece.getRowPosition(),"a"); 
							rook.setColPosition("d");
						}
						incomingPiece.setColPosition(L);
						incomingPiece.setRowPosition(N);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						piece.calculatePossibleMoves(piece.getColPosition(), piece.getRowPosition());
						if(piece.possibleAttackMoves.contains(blackKing.getColPosition()+blackKing.getRowPosition())){
							if(lTo>lFrom){ //castling right rook									 
								rook.setColPosition("h");//ret
							}
							else{									 
								rook.setColPosition("a");//ret
							}
							incomingPiece.setColPosition(savedL);
							incomingPiece.setRowPosition(savedN);
							piece.possibleAttackMoves.clear();
							piece.possibleMoves.clear();
							for(String move:savedMoves){
								piece.possibleMoves.add(move);
							}
							for(String move:savedAttackMoves){
								piece.possibleAttackMoves.add(move);
							}
							return true;
						}
						if(lTo>lFrom){ //castling right rook									 
							rook.setColPosition("h");//ret
						}
						else{									 
							rook.setColPosition("a");//ret
						}
						incomingPiece.setColPosition(savedL);
						incomingPiece.setRowPosition(savedN);
						piece.possibleAttackMoves.clear();
						piece.possibleMoves.clear();
						for(String move:savedMoves){
							piece.possibleMoves.add(move);
						}
						for(String move:savedAttackMoves){
							piece.possibleAttackMoves.add(move);
						}
					}
					
					
					
				}
			}
		}
		
		return false;
	}
		
	King findKing(String team){
		
		
		for(Piece piece:this.piecesInGame){
			if(piece.team.equals(team)&&piece.type.equals("K"))
				return (King) piece;
		}
		
		return null;
	}

	protected ArrayList<Integer> showGenStats() {

		int countWhite=0;
		int numMovesWhite=0;
		int countBlack=0;
		int numMovesBlack=0;
		int numKillsWhite=0;
		int numKillsBlack=0;

		ArrayList<Integer> stats = new ArrayList<Integer>();

		for(Piece piece:this.piecesInGame){
		if(piece.team.equals("w")){
		countWhite++;
		}
		else{
		countBlack++;
		}
		}

		for(Piece piece:white.pieces){	

		numMovesWhite+=piece.numMoves;
		numKillsWhite+=piece.numKills;
		}
		for(Piece piece:black.pieces){

		numMovesBlack+=piece.numMoves;
		numKillsBlack+=piece.numKills;
		}

		stats.add(numKillsWhite); //Number of kills White
		stats.add(numMovesWhite); //Number of moves White
		stats.add(countWhite); //Number of pieces Left White

		stats.add(numKillsBlack); //Number of kills Black
		stats.add(numMovesBlack); //Number of moves Black
		stats.add(countBlack); //Number of pieces Left Black

		return stats;
		}
	
	protected void showSpecificStats() {
		
		System.out.println("Specific Statistics:\n");
		
		System.out.println("\tWhite team:");
		
		for(Piece piece:white.pieces){
			System.out.println("\t-Piece type: "+piece.type);
			System.out.println("\t-Piece status: "+piece.status);
			System.out.println("\t-Piece last file position: "+piece.getColPosition());
			System.out.println("\t-Piece last rank position: "+piece.getRowPosition());
			System.out.println("\t-Piece number of moves: "+piece.numMoves);
			System.out.println("\t-Piece number of kills: "+piece.numKills);
		}
		
		
		System.out.println("\tBlack team:");
		
		for(Piece piece:black.pieces){
			System.out.println("\t-Piece type: "+piece.type);
			System.out.println("\t-Piece status: "+piece.status);
			System.out.println("\t-Piece last file position: "+piece.getColPosition());
			System.out.println("\t-Piece last rank position: "+piece.getRowPosition());
			System.out.println("\t-Piece number of moves: "+piece.numMoves);
			System.out.println("\t-Piece number of kills: "+piece.numKills);
		}
		
	}

	
}

