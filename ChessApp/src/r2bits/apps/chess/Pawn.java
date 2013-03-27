package r2bits.apps.chess;


/**
 * @author Roberto Ronderos Botero
 */
public class Pawn extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6239180037777798806L;
	public boolean allowEnPassant;
	public int EnPassantMoveNumber;
	
	public Pawn(String team, String type, String col, String row,ChessBoard board) {
		super(team, type, col, row,board);
		this.allowEnPassant=false;
		// TODO Auto-generated constructor stub
	}
	
	
	public void setRowPosition(String rowPosition) {		
		
		if(this.team=="w"){
			if(rowPosition.equals("4")){
				this.rowPosition = rowPosition;
				this.allowEnPassant=true;				
			}
			else{
				this.rowPosition = rowPosition;
				this.allowEnPassant=false;
			}
		}
		else{
			if(rowPosition.equals("5")){
				this.rowPosition = rowPosition;
				this.allowEnPassant=true;				
			}
			else{
				this.rowPosition = rowPosition;
				this.allowEnPassant=false;
			}
		}
		
		
	}
	
	
	
	public int isLegalMove(String toL, String toN){
		
		if(isMoveInsideBoard(toL,toN)&&this.status.equals("alive")){
			
				if(canMoveThere(toL,toN)){
					int enPassantCode = checkForEnPassantPM(toL,toN);
					if(enPassantCode!=-1){ //if there's en passant move
						if(enPassantCode==0){
							return 99; //en passant successful return offense move. 
						}
						else{
							return enPassantCode;
						}
					}
					else{
						//check of moves leaps over
						if(!leapsOver(toL,toN)){		
							//check if it's attacking
							if(isAttacking(toL,toN)){
								//if so, move can only be done slow and flexible
								int slowAndFlexibleCode = slowAndFlexbible(toL,toN);
								if(slowAndFlexibleCode==0){ //slow tests passed
									
									//check for promotion
									if(isPromotion(toN)){
										return 101; //attack with promotion.
									}
									
									
									return 100; //attack successful return offense move.
								}
								else{
									return slowAndFlexibleCode;
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
							return 8;
						}
					}
				
			}
			else{
				return 13;
			}
		}
		else{
			return -1;
		}
		
		
		
	}
	
	public boolean advancedTwoSquares(String toN){
		int fromNu = Integer.parseInt(this.getRowPosition());
		int toNu = Integer.parseInt(toN);		
		if(this.team.equals("w")){
			if(Math.abs(toNu-fromNu)==2){
				return true;
			}
		}
		else{
			if(Math.abs(fromNu-toNu)==2){
				return true;
			}
		}
		
		return false;
	}
	
	protected int checkForEnPassantPM(String toL,String toN){
		//if you are in row 5 and you are moving diagonally, check for both of your sides for en passant if a pawn is founded.
		if(this.team=="w"){
			if(this.getRowPosition().equals("5")){
				//check for both sides if you are moving left or right in the same row
				if(toL!=this.getColPosition()){
					Pawn enemyPawn = pawnFoundAt(toL);
					if(enemyPawn!=null){
						int nextTurnImmediateTurn = board.moveNumber - enemyPawn.EnPassantMoveNumber;
						if(enemyPawn.allowEnPassant&&nextTurnImmediateTurn==0){ //en passant is only allowed in the next immediate move
							//check if move is flexible and slow
							int slowAndFlexibleCode = slowAndFlexbible(toL,toN);
							if(slowAndFlexibleCode==0){ //slow tests passed								
								return 0;
							}
							else{
								return slowAndFlexibleCode;
							}						
						}
						else{
							return 5; //En Passant move can only be done in the next immediate turn. 
						}
					}
				}
			}			
		}
		else{
			if(this.getRowPosition().equals("4")){
				//check for both sides if you are moving left or right in the same row
				if(toL!=this.getColPosition()){
					Pawn enemyPawn = pawnFoundAt(toL);
					if(enemyPawn!=null){
						int nextTurnImmediateTurn = board.moveNumber - enemyPawn.EnPassantMoveNumber;
						if(enemyPawn.allowEnPassant&&nextTurnImmediateTurn==0){ //en passant is only allowed in the next immediate move
							//check if move is flexible and slow
							int slowAndFlexibleCode = slowAndFlexbible(toL,toN);
							if(slowAndFlexibleCode==0){ //slow tests passed								
								return 0;
							}
							else{
								return slowAndFlexibleCode;
							}						
						}
						else{
							return 5; //En Passant move can only be done in the next immediate turn. 
						}
					}
				}
			}		
		}
		
		return -1; //not en passant
	}
	
	public int checkForEnPassant(String toL,String toN){ //0 testing , 1 live
		//if you are in row 5 and you are moving diagonally, check for both of your sides for en passant if a pawn is founded.
		if(this.team=="w"){
			if(this.getRowPosition().equals("5")){
				//check for both sides if you are moving left or right in the same row
				if(toL!=this.getColPosition()){
					Pawn enemyPawn = pawnFoundAt(toL);
					if(enemyPawn!=null){
						int nextTurnImmediateTurn = board.moveNumber - enemyPawn.EnPassantMoveNumber;
						if(enemyPawn.allowEnPassant&&nextTurnImmediateTurn==0){ //en passant is only allowed in the next immediate move
							//check if move is flexible and slow
							int slowAndFlexibleCode = slowAndFlexbible(toL,toN);
							if(slowAndFlexibleCode==0){ //slow tests passed
								
								return 0;
							}
							else{
								return slowAndFlexibleCode;
							}						
						}
						else{
							return 5; //En Passant move can only be done in the next immediate turn. 
						}
					}
				}
			}			
		}
		else{
			if(this.getRowPosition().equals("4")){
				//check for both sides if you are moving left or right in the same row
				if(toL!=this.getColPosition()){
					Pawn enemyPawn = pawnFoundAt(toL);
					if(enemyPawn!=null){
						int nextTurnImmediateTurn = board.moveNumber - enemyPawn.EnPassantMoveNumber;
						if(enemyPawn.allowEnPassant&&nextTurnImmediateTurn==0){ //en passant is only allowed in the next immediate move
							//check if move is flexible and slow
							int slowAndFlexibleCode = slowAndFlexbible(toL,toN);
							if(slowAndFlexibleCode==0){ //slow tests passed
								
								return 0;
							}
							else{
								return slowAndFlexibleCode;
							}						
						}
						else{
							return 5; //En Passant move can only be done in the next immediate turn. 
						}
					}
				}
			}		
		}
		
		return -1; //not en passant
	}
	
	protected int slowAndFlexbible(String toL, String toN){
		int slowTestCode = slowTest(toL,toN);
		if(slowTestCode==0){ //slow tests passed
			int flexibleTestCode= flexibleTest(toL,toN);
			if(flexibleTestCode==0){
				return 0;
			}
			else{
				return flexibleTestCode;
			}
		}
		else{
			return slowTestCode;
		}
	}
	
	protected int slowAndNotFlexbible(String toL, String toN){
		int slowTestCode = slowTest(toL,toN);
		if(slowTestCode==0){ //slow tests passed
			int notFlexibleTestCode= notFlexibleTest(toL,toN);
			if(notFlexibleTestCode==0){
				return 0;
			}
			else{
				return notFlexibleTestCode;
			}
		}
		else{
			return slowTestCode;
		}
	}

	
	protected Pawn pawnFoundAt( String toL){
		if(this.team.equals("w")){
			Piece piece = board.getPiece("5",toL);
			if(piece!=null){
				if(piece.team!=this.team)
					if(piece.type=="p")
						return (Pawn) piece;
			}
		}
		else{
			Piece piece = board.getPiece("4",toL);
			if(piece!=null){
				if(piece.team!=this.team)
					if(piece.type=="p")
						return (Pawn) piece;
			}
		}
		return null;
	}
	
	protected int notFlexibleTest(String toL, String toN){
		//common conversions for tests
		char fromLch = this.getColPosition().charAt(0);		
		char toLch = toL.charAt(0);		
		//static letter
		if(fromLch==toLch){
			return 0;
		}
		
		return 6; //Error code 3. Piece can only move forward.
	}
	
	protected int slowTest(String toL, String toN){
		//two cases for each letter and number.
		
		char fromLch = this.getColPosition().charAt(0);
		int fromNint = Integer.parseInt(this.getRowPosition());
		char toLch = toL.charAt(0);
		int toNint = Integer.parseInt(toN);
		
		if(this.numMoves>0){
			//for Number
			if(this.team.equals("w")){ //white's turn
				//if destiny number is greater than source number
				if(toNint>fromNint){
					//it can only be greater by one.
					if((toNint-1)!=fromNint){
						return 1; //Error code 1. Piece can only move one square at a time
					}
				}
				else {
					return 6; //Error code 1. Piece can only move forward
					
				}
			}
			else{
				//if destiny number is smaller than source number
				if(toNint<fromNint){
					//it can only be smaller by one.
					if((toNint+1)!=fromNint){
						return 1; //Error code 1. Piece can only move one square at a time
					}
				}
				else {
					return 6; //Error code 1. Piece can only move forward
					
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
		}
		else{
			//for Number
			if(this.team.equals("w")){ //white's turn
				//if destiny number is greater than source number
				if(toNint>fromNint){
					//it can only be greater by one.
					if((toNint-1)!=fromNint){
						if((toNint-2)!=fromNint)
							return 7; //Error code 7. On Pawn's first move you can only move one or two squares forward.
					}
				}
				else {
					return 6; //Error code 1. Piece can only move forward
				}
			}
			else{
				//if destiny number is smaller than source number
				if(toNint<fromNint){
					//it can only be smaller by one or 2
					if((toNint+1)!=fromNint){
						if((toNint+2)!=fromNint) 
							return 7; //Error code 7. On Pawn's first move you can only move one or two squares forward.
					}
				}
				else {
					return 6; //Error code 1. Piece can only move forward
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
		}
		
		
	  return 0;
		
	}
	
	protected boolean leapsOver(String toLetter, String toNumber){
		//common conversions for tests
		char fromLch = this.colPosition.charAt(0);
		int fromNint = Integer.parseInt(this.rowPosition);
		char toLch = toLetter.charAt(0);
		int toNint = Integer.parseInt(toNumber);
		char l;
		int n;
		if(this.team.equals("w")){
			if(toNint>fromNint){ //going up				
				
				if( toLch == fromLch){ 
					l = fromLch;
					for(n=toNint;n>fromNint;n--){
						if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
							return true;
					}
				}
				
			}
		}
		else{
			if(toNint<fromNint){ //going down				
				
				if( toLch == fromLch){ 
					l = fromLch;
					for(n=toNint;n<fromNint;n++){
						if(board.getPiece(Integer.toString(n),Character.toString(l))!=null) //if piece found then move leaps over.
							return true;
					}
				}
				
			}
		}
		
		return false;		
	}
	
	protected boolean isPromotion(String toN){
		
		if(this.team.equals("w")){
			if(toN.equals("8")){
				return true;
			}
		}
		else{
			if(toN.equals("1")){
				return true;
			}
		}
		
		return false;
	}

	@Override
	protected int isPieceMove(String toL, String toN) {
		int slowAndNotFlexibleCode = slowAndNotFlexbible(toL,toN);
		if(slowAndNotFlexibleCode==0){ //slow tests passed
				//check for promotion
				if(isPromotion(toN)){
					return 102; //move with promotion;
				}
				return 0;
		}
		else{
			return slowAndNotFlexibleCode;
		}		
	}

	@Override
	public void calculatePossibleMoves(String fromL, String fromN) {
		int code00=-1,code01=-1;
		int code,code1;
		int fN = Integer.parseInt(fromN);
		char fL = fromL.charAt(0);
		if(this.numMoves==0){//first move
			if(this.team.equals("w")){
				fN++;					
				code = isLegalMove(fromL,Integer.toString(fN));
				code1 = isLegalMove(fromL,Integer.toString(fN+1));
			}
			else{
				fN--;
				code = isLegalMove(fromL,Integer.toString(fN));
				code1 = isLegalMove(fromL,Integer.toString(fN-1));
			}			
			if(code==0){
				this.possibleMoves.add(fromL+Integer.toString(fN));
			}
			if(code1==0){
				if(this.team.equals("w")){
					this.possibleMoves.add(fromL+Integer.toString(fN+1));
				}
				else{
					this.possibleMoves.add(fromL+Integer.toString(fN-1));
				}
			}
			char L = --fL;
			int codeAttackL = isLegalMove(Character.toString(L),Integer.toString(fN));
			if(codeAttackL==100||codeAttackL==101){
				this.possibleAttackMoves.add(Character.toString(L)+Integer.toString(fN));
			}
			fL ++;
			char R = ++fL;
			int codeAttackR = isLegalMove(Character.toString(R),Integer.toString(fN));
			if(codeAttackR==100||codeAttackR==101){
				this.possibleAttackMoves.add(Character.toString(R)+Integer.toString(fN));
			}
			
		}
		else{ 
			if(this.team.equals("w")){
				fN++;
				if(this.rowPosition.equals("5")){
					char L = fromL.charAt(0);
					char R = L;
					L--;
					R++;
					Piece pieceR = board.getPiece(this.rowPosition, Character.toString(R));
					Piece pieceL = board.getPiece(this.rowPosition, Character.toString(L));
					if(pieceR!=null){
						if(pieceR.type.equals("p")){
							code00 = checkForEnPassantPM(Character.toString(R),Integer.toString(fN));							
						}
					}
					if(pieceL!=null){
						if(pieceL.type.equals("p")){
							code01 = checkForEnPassantPM(Character.toString(L),Integer.toString(fN));							
						}
					}
				}		
				code = isLegalMove(fromL,Integer.toString(fN));
				if(code00==0){
					char R = fromL.charAt(0);
					R++;
					this.possibleAttackMoves.add(Character.toString(R)+Integer.toString(fN));
				}
				if(code01==0){
					char L = fromL.charAt(0);
					L--;
					this.possibleAttackMoves.add(Character.toString(L)+Integer.toString(fN));
				}
			}
			else{
				fN--;
				if(this.rowPosition.equals("4")){
					char L = fromL.charAt(0);
					char R = L;
					L++;
					R--;
					Piece pieceR = board.getPiece(this.rowPosition, Character.toString(R));
					Piece pieceL = board.getPiece(this.rowPosition, Character.toString(L));
					if(pieceR!=null){
						if(pieceR.type.equals("p")){
							code00 = checkForEnPassantPM(Character.toString(R),Integer.toString(fN));							
						}
					}
					if(pieceL!=null){
						if(pieceL.type.equals("p")){
							code01 = checkForEnPassantPM(Character.toString(L),Integer.toString(fN));							
						}
					}
				}		
				code = isLegalMove(fromL,Integer.toString(fN));
				if(code01==0){
					char R = fromL.charAt(0);
					R++;
					this.possibleAttackMoves.add(Character.toString(R)+Integer.toString(fN));
				}
				if(code00==0){
					char L = fromL.charAt(0);
					L--;
					this.possibleAttackMoves.add(Character.toString(L)+Integer.toString(fN));
				}
			}
			
			
			
			if(code==0){
				this.possibleMoves.add(fromL+Integer.toString(fN));
			}
			char L = --fL;
			int codeAttackL = isLegalMove(Character.toString(L),Integer.toString(fN));
			if(codeAttackL==100||codeAttackL==101){
				this.possibleAttackMoves.add(Character.toString(L)+Integer.toString(fN));
			}
			fL ++;
			char R = ++fL;
			int codeAttackR = isLegalMove(Character.toString(R),Integer.toString(fN));
			if(codeAttackR==100||codeAttackR==101){
				this.possibleAttackMoves.add(Character.toString(R)+Integer.toString(fN));
			}
		}
		
	}
	
	
}
