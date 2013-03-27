package r2bits.apps.chess;



import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GamePlayerActivity extends Activity {
	 
	ChessBoard game;
	SavedGameMove file;
	SavedGameUndo gameUndo;
	RecordedGames savedGames;
	Button prev;
	Button next;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.game_player);
        
        String filename = getIntent().getExtras().getString("filename");
       
       
        try {
			this.savedGames = GamesReadWrite.readGames(this.getApplicationContext());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
        
        file = savedGames.getFile(filename);
        gameUndo = new SavedGameUndo(); 
        
        game = new ChessBoard();
        
        
        
        drawBoard();
        
        prev = (Button) findViewById(R.id.prevbtn);
        prev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					GamePlayerActivity.this.undo();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}				
			} 
		});
        next = (Button) findViewById(R.id.nextbtn); 
        next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(file.hasNext()){
					Move move = file.getNextMove();					
					String fromL = UtilityClass.NumToLetter(move.fromLetter);
					String fromN = Integer.toString(move.fromNumber);
					String toL = UtilityClass.NumToLetter(move.toLetter);
					String toN = Integer.toString(move.toNumber);
					
					
					preMove(fromL,fromN,toL,toN);					
					
				}
				else{
					showToast("No more moves");
				}
			}
			
 
		});     
	}
	
	private void showToast(String message){
		
			Toast toast=Toast.makeText(this, message, 7000);
			toast.setGravity(Gravity.TOP, -30, 50);
			toast.show();
		
	}
	
	void addMoveToUndo() throws Exception {
		ChessBoard gameState = null;
		// deep copy
		gameState = (ChessBoard)(ObjectCloner.deepCopy(game)); 		
		gameUndo.addMove(gameState);
		
		
	}
	
	protected void undo() throws Exception{
		if(game.moveNumber>0){			
			file.decrementPointer();
			ChessBoard recoveredState = gameUndo.undoMove();
			this.game = recoveredState;
			game.placeAndUpdatePieces();					    		
			drawBoard();
				
		}
		else{
			showToast("No more moves");
		}
	   			
	}
	
	
	//add promotion!
	protected int move(String fromLetter, String fromNumber,String toLetter, String toNumber) throws Exception{
				
		Piece piece = game.getPiece(fromNumber,fromLetter);			
			
			int Code=piece.isLegalMove(toLetter, toNumber);
			
			if(Code==0){
				game.promotion= false;
				if(piece.type.equals("p")){
					if(((Pawn)piece).advancedTwoSquares(toNumber)){
						((Pawn)piece).EnPassantMoveNumber=game.moveNumber+1;
					}
					else{
						((Pawn)piece).EnPassantMoveNumber=0;
					}
				}
				
				//save state before moving
				addMoveToUndo();
				
				//move piece
				String row= toNumber;
				String col = toLetter;			
				piece.setRowPosition(row);
				piece.setColPosition(col);				
				
				//increment piece moves
				piece.numMoves++;
				
				return 0;
			}
			else if(Code==99){//en pasant
				
				game.promotion= false;
				
				int enPassantCode = ((Pawn)piece).checkForEnPassant(toLetter,toNumber);
				
					if(enPassantCode==0){
						int toN = Integer.parseInt(toNumber);
						if(game.turn.equals("w")){
							toN--;
						}
						else{
							toN++;
						}
						String toKillN = Integer.toString(toN); 
						Piece target = game.getPiece(toKillN,toLetter);
						target.status="death";
						piece.numKills++;
						
						//save state before moving
						addMoveToUndo();
						
						//move piece
						String row= toNumber;
						String col = toLetter;			
						piece.setRowPosition(row);
						piece.setColPosition(col);				
						
						//increment piece moves
						piece.numMoves++;
						
						return 0; //en passant successful return offense move. 
					}
					else{
						return enPassantCode;
					}
				
			}
			else if(Code==100){ //offensive legal move.
				game.promotion= false;
				Piece target = game.getPiece(toNumber,toLetter);
				target.status="death";
				piece.numKills++;
				
				//save state before moving
				addMoveToUndo();
				
				//move piece
				String row= toNumber;
				String col = toLetter;			
				piece.setRowPosition(row);
				piece.setColPosition(col);				
				
				//increment piece moves
				piece.numMoves++;
				return 0;
			}
			else if(Code==101){ //offensive legal move with promotion.

				game.promotion= true;
				//kill enemy
				Piece target = game.getPiece(toNumber,toLetter);
				target.status="death";
				piece.numKills++;
				
				//save state before moving
				addMoveToUndo();
				
				//move piece
				String row= toNumber;
				String col = toLetter;			
				piece.setRowPosition(row);
				piece.setColPosition(col);
				
				//promote piece to be done
				
				
				
				//increment piece moves
				piece.numMoves++;
				return 0;
			}
			else if(Code==102){ //pawn's promotion 

				game.promotion= true;
				
				//save state before moving
				addMoveToUndo();
				
				//move piece
				String row= toNumber;
				String col = toLetter;			
				piece.setRowPosition(row);
				piece.setColPosition(col);	
				
				//promote piece to be done
				
				
				//increment piece moves
				piece.numMoves++;
				return 0;
			}
			else if(Code==103){ //castling done successful
				game.promotion= false;
				//save state before moving
				addMoveToUndo();
				
				//perform castling
				int castlingCode= ((King)piece).castling(toLetter,toNumber);
				if(castlingCode==0){
					((King)piece).castlingUsed=true;
					piece.numMoves++;//increment piece moves
					return 0; //Castling done successful
				}
				else{
					return castlingCode;
				}
				
			}
			else{
				return Code;
			}
			
	}
	
	private void preMove(String fromL,String fromN, String toL, String toN) {
		try{
		    move(fromL,fromN, toL, toN);
			game.moveNumber++;	    			    		
		    //place and update pieces
		    game.placeAndUpdatePieces();	    		
		    drawBoard();		    
		   
			
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
							
	}
	
	
	
	private void drawBoard(){		
		Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/Symbola.ttf");		
		int piece = 0;
		int i;//rows
		int j;//col
			for(i=0;i<game.rows;i++){	
				
				for(j=0;j<game.cols;j++){					
						
					    piece = game.board[i][j];
						
						int id;
						TextView tv;
						String notRotatedPos,letter,number;
						
						id = UtilityClass.getCellID(i+1,j+1);
						tv = (TextView) findViewById(id);
						notRotatedPos ="";
						letter = UtilityClass.NumToLetter(j+1);
						number = Integer.toString(i+1);
						notRotatedPos = letter+number;
						
												
						if(piece != 0){
							String row = Integer.toString(i+1);
							String col = UtilityClass.NumToLetter(j+1);
							Piece p = game.getPiece(row,col);
							p.selected=false;							
							tv.setTypeface(font);
							tv.setTextColor(Color.BLACK);
							tv.setText(piece);
							if(tv.getText().equals("\u2654")||tv.getText().equals("\u2655")||
							   tv.getText().equals("\u2656")||tv.getText().equals("\u2657")||
							   tv.getText().equals("\u2658")||tv.getText().equals("\u2659")){
							   tv.setShadowLayer(3.0f, 0.0f, 0.0f, Color.WHITE);
							}
							
							tv.setTag(notRotatedPos);							
							
						}
						else{
							tv.setBackgroundColor(00000000);
							tv.setShadowLayer(0.0f, 0.0f, 0.0f, Color.WHITE);
							tv.setTypeface(font);
							tv.setText("\u2588");
							tv.setTextColor(00000000);							
							tv.setTag(notRotatedPos);							
							
						}
				}
				
			}			
	
	}
	
	
}
