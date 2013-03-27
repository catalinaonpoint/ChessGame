package r2bits.apps.chess;


/**
 * @author Roberto Ronderos Botero
 */
public class Queen extends Piece {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8410527810717706871L;

	public Queen(String team, String type, String col, String row,ChessBoard board) {
		super(team, type, col, row,board);
	}

	protected int isPieceMove(String toL, String toN) {
		int bothflexCode = bothFlexNNotFlex(toL,toN);
		if(bothflexCode==0){
			return 0;
		}
		else{
			return bothflexCode;
		}

	}

	@Override
	public void calculatePossibleMoves(String fromL, String fromN) {		
			calculateDiagonalMoves(fromL,fromN);		
			calculateHorizontalAndVerticalMoves(fromL,fromN);			
	}

	private void calculateHorizontalAndVerticalMoves(String fromL, String fromN) {
		int fL= UtilityClass.LetterToNum(fromL.charAt(0));
		int fN = Integer.parseInt(fromN);		
		
		boolean cU = true; //continuous up
		boolean cD = true; //continuous down 
		boolean cL = true; //continuous left
		boolean cR = true; //continuous right
		boolean done = false;
		int code1,code2;		
		int toND = fN; //row
		int toNU = fN;
		int toLL = fL; //col left
		int toLR = fL; //col right
		
		while(!done){
			//down its position
			toND-= 1;
			
			if(toND>=1 && cD){
				code1 = isLegalMove(fromL,Integer.toString(toND));				
				if(cD){
					if(code1==0){
						this.possibleMoves.add(fromL+Integer.toString(toND));
					}
					else if(code1==100){
						this.possibleAttackMoves.add(fromL+Integer.toString(toND));
					}
					else{
						cD=false;
					}
				}
			}
			else{
				cD=false;
			}
			
			//up its position
			toNU+= 1;
			
			if(toNU>=1 && cU){
				code2 = isLegalMove(fromL,Integer.toString(toNU));
				if(cU){
					if(code2==0){
						this.possibleMoves.add(fromL+Integer.toString(toNU));
					}
					else if(code2==100){
						this.possibleAttackMoves.add(fromL+Integer.toString(toNU));
					}
					else{
						cU=false;
					}
				}
			}else{
				cU=false;
			}
			
			//left its position			
			toLL-= 1;
			
			
			if(toLL>=1 && cL){
				code1 = isLegalMove(UtilityClass.NumToLetter(toLL),fromN);				
				if(cL){
					if(code1==0){
						this.possibleMoves.add(UtilityClass.NumToLetter(toLL)+fromN);
					}
					else if(code1==100){
						this.possibleAttackMoves.add(UtilityClass.NumToLetter(toLL)+fromN);
					}
					else{
						cL=false;
					}
				}
			}
			else{
				cL=false;
			}
			
			//right its position
			toLR+= 1;
			
			if(toLR<=8 && cR){
				code2 = isLegalMove(UtilityClass.NumToLetter(toLR),fromN);
				if(cR){
					if(code2==0){
						this.possibleMoves.add(UtilityClass.NumToLetter(toLR)+fromN);
					}
					else if(code2==100){
						this.possibleAttackMoves.add(UtilityClass.NumToLetter(toLR)+fromN);
					}
					else{
						cR=false;
					}
				}
			}else{
				cR=false;
			}
			
			if(!cD&&!cU&&!cL&&!cR)
				done=true;
			
			
		}	
	
		
	}

	private void calculateDiagonalMoves(String fromL, String fromN)  {
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
			toN = fN; //row
			toLL = fL; //col left
			toLR = fL; //col right
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
