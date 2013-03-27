package r2bits.apps.chess;


/**
 * @author Roberto Ronderos Botero
 */
public class King extends Piece{
	/**
	 * 
	 */
	private static final long serialVersionUID = -437852127107894880L;
	public Boolean castlingUsed ;

	public King(String team, String type, String col, String row,
			ChessBoard board) {
		super(team, type, col, row, board);
		this.castlingUsed=false;
	}
	
	public int isLegalMove(String toL, String toN) {
		if(isMoveInsideBoard(toL,toN)&&this.status.equals("alive")){			
				boolean castling = checkCastling(toL,toN);
				if(canMoveThere(toL,toN)||castling){
					//check if your' trying to do a castling move
					if(castling){ 
						return 103;
					}
					else{
						//check of moves leaps over
						if(!leapsOver(toL,toN)){
						  if(!board.kingscheck(toL, toN)){
								//check if it's attacking
								if(isAttacking(toL,toN)){
									//move has to be vertically or horizontally and slow
									int flexNnotCode = bothFlexNNotFlex(toL,toN);
									if(flexNnotCode==0){
										int slowTestCode = slowTest(toL,toN);
										if(slowTestCode==0){
											return 100; //attack successful return offense move.
										}
										else{
											return slowTestCode;
										}
									}
									else{
										return flexNnotCode;
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
							  return 14;
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
		return -1;
	}


	public int castling(String toL,String toN) {
		if(!this.castlingUsed){			
			if(!leapsOver(toL,toN)){
				char lTo= toL.charAt(0);
				char lFrom = this.getColPosition().charAt(0);
				Rook rook;
				if(lTo=='g'){
					char N=++lTo;
					rook = (Rook)board.getPiece(toN,Character.toString(N));
				}
				else{
					char N=--lTo;
					N--;
					rook = (Rook)board.getPiece(toN,Character.toString(N));
				}
					if(this.numMoves==0&&rook.numMoves==0){
						if(lTo>lFrom){ //castling right rook				
							rook.setColPosition("f");
							rook.numMoves++;
							this.setColPosition("g");
							this.castlingUsed=true;
							return 0;
						}
						else{ //castling left Rook				
							rook.setColPosition("d");
							rook.numMoves++;
							this.setColPosition("c");
							this.castlingUsed=true;
							return 0;
						}
					}
					else{
						return 11; //Neither of the pieces involved in castling may have been previously moved during the game.
					}
				
			}
			else{
				return 10; //Error, Castling can't be done when there are pieces in between.
			}
		}
		else{
			return 9; //Error , Castling can only be done once per game.
		}
	}
	
	public boolean possibleCastling(String toL,String toN) {
		if(!this.castlingUsed){			
			if(!leapsOver(toL,toN)){
				char lTo= toL.charAt(0);
				char lFrom = this.getColPosition().charAt(0);
				Rook rook;
				if(lTo=='g'){
					char N=++lTo;
					rook = (Rook)board.getPiece(toN,Character.toString(N));
				}
				else{
					char N=--lTo;
					N--;
					rook = (Rook)board.getPiece(toN,Character.toString(N));
				}
					if(this.numMoves==0&&rook.numMoves==0){
						if(lTo>lFrom){ //castling right rook				
							return true;
						}
						else{ //castling left Rook				
							return true;
						}
					}
					else{
						return false; //Neither of the pieces involved in castling may have been previously moved during the game.
					}
				
			}
			else{
				return false; //Error, Castling can't be done when there are pieces in between.
			}
		}
		else{
			return false; //Error , Castling can only be done once per game.
		}
	}

	private boolean checkCastling(String toL, String toN) {
		char lTo= toL.charAt(0);
		Piece rook;
		
		if((this.rowPosition.equals("8")||this.rowPosition.equals("1"))&&(toL.equals("c")||toL.equals("g"))&& !this.castlingUsed){ //if your move is in the same row and if you are going to col a or g
				if(lTo=='g'){
					char N=++lTo;
					rook = (Rook)board.getPiece(toN,Character.toString(N));
				}
				else{
					char N=--lTo;
					N--;
					rook = (Rook)board.getPiece(toN,Character.toString(N));
				}
				Piece piece = board.getPiece(toN,toL);
				if(piece==null && rook!=null && !this.castlingUsed){
					return true;
				}
				else{
					return false; 
				}
		}
		
		return false;
	}

	@Override
	protected int isPieceMove(String toL, String toN) {
		int flexNnotCode = bothFlexNNotFlex(toL,toN);
		if(flexNnotCode==0){
			int slowTestCode = slowTest(toL,toN);
			if(slowTestCode==0){
				return 0; //successful move.
			}
			else{
				return slowTestCode;
			}
		}
		else{
			return flexNnotCode;
		}
	}

	@Override
	public void calculatePossibleMoves(String fromL, String fromN) {
		int N = Integer.parseInt(fromN);
		char L = fromL.charAt(0);
		
			if(this.getRowPosition().equals("1")||this.getRowPosition().equals("8")){
				//l,n
				if(this.team.equals("w")){
					if(!board.kingscheck("g","1")){
						boolean castling = possibleCastling("g","1");
						if(castling){
							this.possibleMoves.add("g"+"1");
						}					
					}
					if(!board.kingscheck("c","1")){
						boolean castling = possibleCastling("c","1");
						if(castling){
							this.possibleMoves.add("c"+"1");
						}					
					}
				}
				else{
					if(!board.kingscheck("g","8")){
						boolean castling = possibleCastling("g","8");
						if(castling){
							this.possibleMoves.add("g"+"8");
						}					
					}
					if(!board.kingscheck("c","8")){
						boolean castling = possibleCastling("c","8");
						if(castling){
							this.possibleMoves.add("c"+"8");
						}					
					}
				}
			}
		
		
		for(int i=(N+1);i>=(N-1);i--){
			for(char j=(char) (L-1);j<=(L+1);j++){
				if(!(i==N&&j==L)&&((i>0&&j<'i'))&&(i<9&&j>='a')){					
					if(!board.kingscheck(Character.toString(j),Integer.toString(i))){
						int code = isLegalMove(Character.toString(j),Integer.toString(i));
						if(code==0){
							this.possibleMoves.add(Character.toString(j)+Integer.toString(i));
						}
						else if(code==100){
							this.possibleAttackMoves.add(Character.toString(j)+Integer.toString(i));
						}
					}					
				}
			}
		}
		
	}

}
