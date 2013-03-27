package r2bits.apps.chess;


/**
 * @author Roberto Ronderos Botero
 */
public class Knight extends Piece{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3733454017573932572L;


	public Knight(String team, String type, String col, String row,
			ChessBoard board) {
		super(team, type, col, row, board);
	}
	
	public int isLegalMove(String toL, String toN){
		if(isMoveInsideBoard(toL,toN)&&this.status.equals("alive")){
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
			return -1;
		}
	}

	protected int isPieceMove(String toL, String toN) {
		
		int horizontalMoves = Math.abs(UtilityClass.LetterToNum(toL.charAt(0))-UtilityClass.LetterToNum(this.colPosition.charAt(0)));
		int verticalMoves = Math.abs(toN.charAt(0)-this.rowPosition.charAt(0));
		
		if((horizontalMoves==2&&verticalMoves==1)||(horizontalMoves==1&&verticalMoves==2)){
			return 0;
		}
		
		return 12;
		
	}

	
	public void calculatePossibleMoves(String fromL, String fromN) {
		int fL= UtilityClass.LetterToNum(fromL.charAt(0));
		int fN = Integer.parseInt(fromN);
		
		
		for(int L= fL-2;L<=fL+2;L++){
			for(int N= fN+2;N>=(fN-2);N--){
				if((N>0&&L>0&&N<9&&L<9)&&N!=fN&&L!=fL){
					int code = isLegalMove(UtilityClass.NumToLetter(L),Integer.toString(N));
					if(code==0){ //legal move
						this.possibleMoves.add(UtilityClass.NumToLetter(L)+Integer.toString(N));
					}
					else if(code==100){//legal attacking move
						this.possibleAttackMoves.add(UtilityClass.NumToLetter(L)+Integer.toString(N));
					}
				}
			}
		}
	}

	
	
	
	

}
