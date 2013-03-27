package r2bits.apps.chess;


/**
 * @author Roberto Ronderos Botero
 */
public class Bishop extends Piece {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6246108636126852430L;

	public Bishop(String team, String type, String col, String row,
			ChessBoard board) {
		super(team, type, col, row, board);
	}

	protected int isPieceMove(String toL, String toN) {
		//is a rook movement if it is not flexible and fast
		int flexCode = flexibleTest(toL,toN);
		if(flexCode==0){
			return 0;
		}
		else{
			return flexCode;
		}
	}

	public void calculatePossibleMoves(String fromL, String fromN) {
		int fL= UtilityClass.LetterToNum(fromL.charAt(0));
		int fN = Integer.parseInt(fromN);		
		
		boolean cDL = true; //continuous down left
		boolean cDR = true; //continuous down right
		boolean cUL = true; //continuous up left
		boolean cUR = true; //continuous up right
		boolean done = false;
		int code1,code2;
		int counter=1;
		int toN = fN; //row
		int toLL = fL; //col left
		int toLR = fL; //col right
		
		while(!done){
			//down its position
			toN = fN; 
			toLL = fL; 
			toLR = fL; 
			toN-= counter;
			toLL-= counter;
			toLR+= counter;
			
			if(toN>=1 && toLL>=1 && cDL){
				code1 = isLegalMove(UtilityClass.NumToLetter(toLL),Integer.toString(toN));				
				if(cDL){
					if(code1==0){
						this.possibleMoves.add(UtilityClass.NumToLetter(toLL)+Integer.toString(toN));
					}
					else if(code1==100){
						this.possibleAttackMoves.add(UtilityClass.NumToLetter(toLL)+Integer.toString(toN));
					}
					else{
						cDL=false;
					}
				}
			}
			else{
				cDL=false;
			}
			if(toN>=1 && toLR<=8 && cDR){
				code2 = isLegalMove(UtilityClass.NumToLetter(toLR),Integer.toString(toN));
				if(cDR){
					if(code2==0){
						this.possibleMoves.add(UtilityClass.NumToLetter(toLR)+Integer.toString(toN));
					}
					else if(code2==100){
						this.possibleAttackMoves.add(UtilityClass.NumToLetter(toLR)+Integer.toString(toN));
					}
					else{
						cDR=false;
					}
				}
			}else{
				cDR=false;
			}
			
			//upper to its position
			
			//reset variables to position
			toN = fN; //row
			toLL = fL; //col left
			toLR = fL; //col right
			// start
			toN+= counter;
			toLL-= counter;
			toLR+= counter;
			
			if(toN<=8 && toLL>=1 && cUL){
				code1 = isLegalMove(UtilityClass.NumToLetter(toLL),Integer.toString(toN));				
				if(cUL){
					if(code1==0){
						this.possibleMoves.add(UtilityClass.NumToLetter(toLL)+Integer.toString(toN));
					}
					else if(code1==100){
						this.possibleAttackMoves.add(UtilityClass.NumToLetter(toLL)+Integer.toString(toN));
					}
					else{
						cUL=false;
					}
				}
			}
			else{
				cUL=false;
			}
			if(toN<=8 && toLR<=8 && cUR){
				code2 = isLegalMove(UtilityClass.NumToLetter(toLR),Integer.toString(toN));
				if(cUR){
					if(code2==0){
						this.possibleMoves.add(UtilityClass.NumToLetter(toLR)+Integer.toString(toN));
					}
					else if(code2==100){
						this.possibleAttackMoves.add(UtilityClass.NumToLetter(toLR)+Integer.toString(toN));
					}
					else{
						cUR=false;
					}
				}
			}else{
				cUR=false;
			}
			
			if(!cDL&&!cDR&&!cUR&&!cUL)
				done=true;
			
			counter++;
		}
		
	}
		
				
		

}
