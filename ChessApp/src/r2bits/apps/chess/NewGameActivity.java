package r2bits.apps.chess;



import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class NewGameActivity extends Activity {
	
	
	Button undo,automatic,draw,resign;
	ChessBoard game;
	protected TextView origin = null;
	protected TextView destiny = null;
	protected String prevoriginS = null;
	protected String prevdestinyS = null;
	protected TextView prevPieceSelectedTV=null;
	public SavedGameUndo gameUndo;
	public SavedGameMove gameMoves;
	public RecordedGames savedGames;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);
        	try {
        		RecordedGames rg;
        		rg = GamesReadWrite.readGames(this.getApplicationContext());
        		if(rg==null){
					this.savedGames = new RecordedGames();
					GamesReadWrite.writeGames(this.savedGames,this.getApplicationContext());
				}
        		else{
        			this.savedGames = rg;
        		}
        	} catch (Exception e) {
        		System.out.println(e.getMessage());
        	} 
        	gameMoves = new SavedGameMove();
        	gameUndo = new SavedGameUndo();
			game = new ChessBoard();
			
			drawBoard();
		
        
        
        
        undo = (Button) findViewById(R.id.undoBtn);
        undo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					undo();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}							
			} 
		});
        automatic = (Button) findViewById(R.id.automaticBtn); 
        automatic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<Piece> pieces = new ArrayList<Piece>();
				
					if(game.turn.equals("w")){
						for(Piece piece: game.white.pieces){
							if(!piece.status.equals("death")){
								pieces.add(piece);
							}
						}
					}
					else{
						for(Piece piece: game.black.pieces){
							if(!piece.status.equals("death")){
								pieces.add(piece);
							}
						}
					}
					
					Random randomGenerator = new Random();					   
					int randomIndex = randomGenerator.nextInt(pieces.size());
					Piece piece = pieces.get(randomIndex);
					
					ArrayList<String> allPossibleMoves = new ArrayList<String>();
					allPossibleMoves.addAll(piece.possibleAttackMoves);
					allPossibleMoves.addAll(piece.possibleMoves);
					
					while(allPossibleMoves.size()==0){
						allPossibleMoves.clear();
						randomIndex = randomGenerator.nextInt(pieces.size());
						piece = pieces.get(randomIndex);
						allPossibleMoves.addAll(piece.possibleAttackMoves);
						allPossibleMoves.addAll(piece.possibleMoves);
					}
					
					String c = piece.getColPosition();
					String r = piece.getRowPosition();
					String move = c+r;
					
					TableRow tr = (TableRow) findViewById(UtilityClass.getRowID(r,game.isRotated));
					TextView tv = (TextView) tr.findViewWithTag(move);						
	    			origin = tv;	
					
	    			
					if(allPossibleMoves.size()>0){
						int randomIndex2 = randomGenerator.nextInt(allPossibleMoves.size());
						String randomMove = allPossibleMoves.get(randomIndex2);
						String toN = randomMove.substring(1);						
						tr = (TableRow) findViewById(UtilityClass.getRowID(toN,game.isRotated));
						tv = (TextView) tr.findViewWithTag(randomMove);						
		    			destiny = tv;	
		    			try {
							preMove(tv);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				
			}
			
 
		});
        
        draw = (Button) findViewById(R.id.drawBtn); 
        draw.setOnClickListener(new OnClickListener() {


        public void onClick(View v) {
        String message = "Are you sure you want to end the game in a draw?";
            showDialog(message,1);
        }


        }); 
        
        resign = (Button) findViewById(R.id.resignBtn); 
        resign.setOnClickListener(new OnClickListener() {


        public void onClick(View v) {
        String message = "Are you sure you want to resign?";
            showDialog(message,2);
        }


        });
        
	}
	
	
	
	public void onBackPressed(){
		AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(this);
		builderConfirmation.setMessage("Are you sure you want to exit the game?")
			   .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();	
						NewGameActivity.this.finish();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alertConfirmation = builderConfirmation.create();
		alertConfirmation.show();
	}
	
	public void showDialog(String message, int option){
		
		switch(option){
		
		//One of the players is asking for a draw
		case 1:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(message)
					   .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	 
				        	    String team = game.turn;
				        	   	String drawTeam;
				        	   	
				        	   	if(team.equals("w"))
				        	   		drawTeam = "White";
				        	   	else
				        	   		drawTeam = "Black";
				        	   	
				                String messageConfirmation = drawTeam+" team is asking for a draw. Would you like to accept it?";
				                showDialog(messageConfirmation,3);
				                dialog.cancel();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			break;
			
		//One of the players resigns 
		case 2: 
				AlertDialog.Builder builderResign = new AlertDialog.Builder(this);
				builderResign.setMessage(message)
					   .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				    			        	   	
				                String messageSave = "Would you like to save this game?";
				                showDialog(messageSave,8);
				                dialog.cancel();
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alertResign = builderResign.create();
				alertResign.show();
				break;
			
		//Confirmation of Draw request to the other player
		case 3:
			AlertDialog.Builder builderConfirmation = new AlertDialog.Builder(this);
			builderConfirmation.setMessage(message)
				   .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                String messageSave = "Would you like to save this game?";
			                showDialog(messageSave,4);
			                dialog.cancel();			        
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alertConfirmation = builderConfirmation.create();
			alertConfirmation.show();
			break;
		
		//Save game question
		case 4:
			AlertDialog.Builder builderSave = new AlertDialog.Builder(this);
			builderSave.setMessage(message)
				   .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                String messageSave = "Please enter a name for the game: ";
			                showDialog(messageSave,5);
			                dialog.cancel();			        
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			                finalScreenLoader(3);
			           }
			       });
			AlertDialog alertSave = builderSave.create();
			alertSave.show();
			break;
		
		
		//File name dialog
		case 5:
			
			AlertDialog.Builder builderFileName = new AlertDialog.Builder(this);
			builderFileName.setMessage(message);

			final EditText input = new EditText(this);
			builderFileName.setView(input);

				builderFileName.setPositiveButton("Save", new DialogInterface.OnClickListener() {
					
				public void onClick(DialogInterface dialog, int whichButton) {
				  Editable value = input.getText();
				  String newGameName = value.toString();
				  
				  if(newGameName.equals("")){
		                String messageResign = "Please enter a name for the game";
		                showDialog(messageResign,5);
		          }
				  
				  else{
					 SavedGameMove gM = NewGameActivity.this.gameMoves;
					 gM.setFileName(value.toString());
					 RecordedGames sG = NewGameActivity.this.savedGames;
					 sG.recordedGames.add(gM);
					  try {
						GamesReadWrite.writeGames(savedGames,NewGameActivity.this.getApplicationContext());
					  } catch (Exception e) {						
						System.out.println(e.getMessage());
					  }
					
					  dialog.cancel();
					  finalScreenLoader(3);
				  }
				  }
			});
	
			AlertDialog alertFileName = builderFileName.create();
			alertFileName.show();
			break;
			
		//Notification dialog when one of the players resigns
		case 6:
			AlertDialog.Builder builderNotification = new AlertDialog.Builder(this);
			builderNotification.setMessage(message)
				   .setCancelable(false)
				   .setNeutralButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();	
			                NewGameActivity.this.finish();
			           }
			       });
			AlertDialog alertNotification = builderNotification.create();
			alertNotification.show();
			break;
			
		case 7:
			AlertDialog.Builder builderNotification2 = new AlertDialog.Builder(this);
			builderNotification2.setMessage(message)
				   .setCancelable(false)
				   .setNeutralButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();	
			           }
			       });
			AlertDialog alertNotification2 = builderNotification2.create();
			alertNotification2.show();
			break;
		
		
		case 8:

			AlertDialog.Builder builderSave2 = new AlertDialog.Builder(this);
				builderSave2.setMessage(message)
				.setCancelable(false)

				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				String messageSave = "Please enter a name for the game: ";
				showDialog(messageSave,9);
				dialog.cancel();	
				}
				})

				.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();

				String team = game.turn;
	
					if(team.equals("w"))
						finalScreenLoader(2);
					else
						finalScreenLoader(1);
		
				}
				});

				AlertDialog alertSave2 = builderSave2.create();
				alertSave2.show();
			break;
		
		
		case 9:

			AlertDialog.Builder builderFileName2 = new AlertDialog.Builder(this);
			builderFileName2.setMessage(message);

			final EditText input2 = new EditText(this);
			builderFileName2.setView(input2);

			builderFileName2.setPositiveButton("Save", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton) {
	
					Editable value = input2.getText();
					String newGameName = value.toString();
		
						if(newGameName.equals("")){
							String messageResign = "Please enter a name for the game";
							showDialog(messageResign,5);
						}
			
						else{
							 SavedGameMove gM = NewGameActivity.this.gameMoves;
							 gM.setFileName(value.toString());
							 RecordedGames sG = NewGameActivity.this.savedGames;
							 sG.recordedGames.add(gM);
							  try {
								GamesReadWrite.writeGames(savedGames,NewGameActivity.this.getApplicationContext());
							  } catch (Exception e) {						
								System.out.println(e.getMessage());
							  }
							dialog.cancel();
				
							String team = game.turn;
			
						if(team.equals("w"))
							finalScreenLoader(2);
						else
							finalScreenLoader(1);
						}
					}
				});

			AlertDialog alertFileName2 = builderFileName2.create();
			alertFileName2.show();
			break;
	}
		
	}
	
	protected void finalScreenLoader(int status){

		//1 For White Team Winning
		//2 For Black Team Winning
		//3 For a Draw

		//Creating Array
		ArrayList<Integer> stats = game.showGenStats();
		stats.add(status);

		//Creating intent
		Intent i = new Intent(NewGameActivity.this, FinalScreenActivity.class);
		Bundle bundle = new Bundle();
		bundle.putIntegerArrayList("final", stats);
		i.putExtras(bundle);

		//Initializing Activity
		startActivity(i);
		}
	
	protected void showPromotionAlert(final Piece piece) {
		final String[] items = {"Queen", "Rook", "Bishop","Knight"};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Choose promotion piece");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {		      
		       promote(piece,items[item]);
		    }
		});
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}
	
		
	// game methods
	
	void addMoveToUndo() throws Exception {
		ChessBoard gameState = null;
		// deep copy
		gameState = (ChessBoard)(ObjectCloner.deepCopy(game)); 		
		gameUndo.addMove(gameState);		
		
	}
	
	void addMoveToMoves(String fromL, String fromN, String toL, String toN){
		int fL = UtilityClass.LetterToNum(fromL.charAt(0));
		int fN = Integer.parseInt(fromN);
		int tL = UtilityClass.LetterToNum(toL.charAt(0));
		int tN = Integer.parseInt(toN);
		
		Move move = new Move(fL,fN,tL,tN);
		
		gameMoves.addMove(move);
	}
	
	protected void undo() throws Exception{
		if(game.moveNumber>0){
			if(prevoriginS!=null)
		   		removeTraceMove(prevoriginS,prevdestinyS);
			
			gameMoves.removeLast();
			ChessBoard recoveredState = gameUndo.undoMove();
			this.game = recoveredState;
								    		
			drawBoard();
				
			//is check?   		
		   	isCheck();	
		}
	   			
	}
	
	protected void drawBoard(){		
		Typeface font = Typeface.createFromAsset(getAssets(),
                "fonts/Symbola.ttf");		
		int piece = 0;
		int i,x;//rows
		int j,z;//col
			for(i=0,x=7;i<game.rows;i++,x--){	
				
				for(j=0,z=7;j<game.cols;j++,z--){					
						
					    piece = game.board[i][j];
						
						int id;
						TextView tv;
						String notRotatedPos,letter,number;
						
						if(!game.isRotated){
							id = UtilityClass.getCellID(i+1,j+1);
							tv = (TextView) findViewById(id);
							notRotatedPos ="";
							letter = UtilityClass.NumToLetter(j+1);
							number = Integer.toString(i+1);
							notRotatedPos = letter+number;
						}
						else{
							id = UtilityClass.getCellID(x+1,z+1);
							tv = (TextView) findViewById(id);
							notRotatedPos ="";
							letter = UtilityClass.NumToLetter(j+1);
							number = Integer.toString(i+1);
							notRotatedPos = letter+number;
						}
												
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
							addlistener(tv,id,i+1,j+1);								
							
						}
						else{
							tv.setBackgroundColor(00000000);
							tv.setShadowLayer(0.0f, 0.0f, 0.0f, Color.WHITE);
							tv.setTypeface(font);
							tv.setText("\u2588");
							tv.setTextColor(00000000);							
							tv.setTag(notRotatedPos);
							addlistener(tv,id,i+1,j+1);								
							
						}
				}
				
			}
			game.calculatePossibleMovesForAll();
			
	
	}
	
	private void addlistener(final TextView textView, final int id, final int row, final int col) {			
		
		textView.setOnClickListener(new OnClickListener() {						
			
			    

				public void onClick(View v) {					
			    	
			    	Piece piece = game.getPiece(Integer.toString(row),UtilityClass.NumToLetter(col));
			    						    	
				    	if(piece != null){// selecting a piece
				    		if(game.turn.equals(piece.team)){//Selecting piece of your own team
						    	if(!piece.selected){
						    		//selecting piece
							       v.setBackgroundColor(Color.BLACK);
							       v.getBackground().setAlpha(50);
							       changefontColor(id,piece);
						    	   piece.selected=true;
						    	   //setting origin
						    	   TextView cell = (TextView) v;
						    	   origin = cell;
						    	   
						    	   //calculate possible moves
						    	    piece.possibleAttackMoves.clear();
									piece.possibleMoves.clear();				
									piece.calculatePossibleMoves(piece.getColPosition(), piece.getRowPosition());
						    	   	
						    	   //deselecting prev piece
						    	   deselectPrevPiece(prevPieceSelectedTV,
						    			   			 game.prevPieceSelectedID,
						    			   			 game.prevPieceSelectedP);
						    	   prevPieceSelectedTV = textView;
						    	   game.prevPieceSelectedID = id;
						    	   game.prevPieceSelectedP = piece;
						    	 //Show Possible Moves
						    	    paintPossibleMoves(piece,1);
						    	}
						    	else{
						    	  //deselect piece	
						    	   v.setBackgroundColor(00000000);
						    	   changefontColor(id,piece);
						    	   piece.selected=false;
						    	   //Erase Possible Moves
						    	   paintPossibleMoves(piece,0);
						    	   //reseting origin
						    	   origin = null;								    	   
						    	   //reset prev piece
						    	   prevPieceSelectedTV = null;
						    	   game.prevPieceSelectedID = 0;
						    	   game.prevPieceSelectedP = null;
						    	}
				    		}
				    		else{//selected a piece from the other team, attacking
				    			try {
									preMove(v);
								} catch (Exception e) {
									
									System.out.println(e.getMessage());
								}
				    		}
				    	}
				    	else{ //selecting a destiny						    		
				    		try {
								preMove(v);
							} catch (Exception e) {
								
								String message = e.getMessage();
								System.out.println(message);
							}						    		
				    	}				    	
			    	
			    	}			
			    
		});
	

	}
	
	private void preMove(View v) throws Exception {
		if(origin!=null){
    		
			TextView cell = (TextView) v;
			destiny = cell;			
    		int[] success = tryMove();
    		
    		if( success[0] == 0){    			
    			game.moveNumber++;
	    		//reset
    			//deselect piece	
    			origin.setBackgroundColor(00000000);
	    		//Erase Possible Moves							    	
	    		origin = null;
	    		destiny = null;
	    		game.prevPieceSelectedP = null;
	    		game.prevPieceSelectedID = 0;
	    		prevPieceSelectedTV = null;
	    		
	    		
	    		//place and update pieces
	    		game.placeAndUpdatePieces();
	    		if(!game.promotion){
	    			//rotate board						    		
	    			game.rotateBoard();									
	    			//draw board;						    		
	    			drawBoard();
	    		}
	    		else{
	    			drawBoard();
	    		}
		   		//change turns
		   		if(success[1]==1){ //success[1] contains boolean to allow next turn or not
				    if(game.turn.equals("w")){				    	
				    	game.turn="b";
				    }
				    else{				    	
				    	game.turn="w";
				    }
			    }
		   		
		   		isCheck();
		   		int over = game.gameOver();
			    if(over==0||over==1||over==2|over==3)
			    	endgame(over);			    
			    
			    //trace move
	    		paintTraceMove(prevoriginS,prevdestinyS);
    		}
		}							
	}
	
	private void endgame(int over) {
		String endString;
		String question= "\nDo you want to record this game?";
		if(over==0){
			endString="GAME OVER - BLACK WINS\n\n***CHECKMATE***\n";
		}
		else if(over==1) {
			endString="GAME OVER - BLACK WINS\n\n***STALEMATE***\n";
		}
		else if(over==2){
			endString="GAME OVER - WHITE WINS\n\n***CHECKMATE***\n";
		}
		else if(over==3){
			endString="GAME OVER - WHITE WINS\n\n***STALEMATE***\n";
		}
		else if(over==4){
			endString="GAME OVER \n\n WHITE RESIGNED, BLACK WINS\n\n";
		}
		else if(over==5){
			endString="GAME OVER \n\n BLACK RESIGNED, WHITE WINS\n\n";
		}
		else{
			endString="GAME OVER \n\n DRAW\n\n";
		}
		
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
   		dialogBuilder.setTitle("Game Over");
   		dialogBuilder.setMessage(endString+question);
   		dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int which) {
								
			}
		});
   		AlertDialog alertDialog = dialogBuilder.create();
   		alertDialog.show();	
	}
	
	private void changefontColor(int id, Piece piece) {
		if(!piece.selected){
			TextView txt = (TextView)findViewById(id);
			txt.setTextColor(Color.WHITE);
		}
		else{
			TextView txt = (TextView)findViewById(id);
			txt.setTextColor(Color.BLACK);
		}
		
	}
	
	private void deselectPrevPiece(TextView prevPiece, int id, Piece piece ){
		if(prevPiece!=null){
			//erase prev Possible Moves
	    	paintPossibleMoves(piece,0);
	    	//erase selection
			prevPiece.setBackgroundColor(00000000);			
			changefontColor(id,piece);			
	    	piece.selected = false;
		}
	}
	
private void paintPossibleMoves(Piece piece, int mode) {
		
		String cellId;
		TextView tv ;
		TableRow tr;
		String row;
		String col;
		
		if(mode==1){ //1 for paint, 0 for erase
			for(String move:piece.possibleMoves){
				
				row = move.substring(1);
				col = move.substring(0,1);
				cellId = col+row;
				
				
				tr = (TableRow) findViewById(UtilityClass.getRowID(row,game.isRotated));
				tv = (TextView) tr.findViewWithTag(cellId);
				tv.setBackgroundColor(Color.BLUE);
				tv.getBackground().setAlpha(50);				
				
			}
			for(String aMove : piece.possibleAttackMoves){
				row = aMove.substring(1);
				col = aMove.substring(0,1);
				cellId = col+row;
				
				
				tr = (TableRow) findViewById(UtilityClass.getRowID(row,game.isRotated));
				tv = (TextView) tr.findViewWithTag(cellId);
				tv.setBackgroundColor(Color.GREEN);
				tv.getBackground().setAlpha(50);
			}
		}
		else{
			for(String move:piece.possibleMoves){
				
				row = move.substring(1);
				col = move.substring(0,1);
				cellId = col+row;
				
				
				tr = (TableRow) findViewById(UtilityClass.getRowID(row,game.isRotated));
				tv = (TextView) tr.findViewWithTag(cellId);
				tv.setBackgroundColor(Color.TRANSPARENT);					
			}
			for(String aMove : piece.possibleAttackMoves){
				row = aMove.substring(1);
				col = aMove.substring(0,1);
				cellId = col+row;
				
				
				tr = (TableRow) findViewById(UtilityClass.getRowID(row,game.isRotated));
				tv = (TextView) tr.findViewWithTag(cellId);
				tv.setBackgroundColor(Color.TRANSPARENT);
			}
		}
							
	}
	
	protected int move(String fromLetter, String fromNumber,String toLetter, String toNumber, int turn) throws Exception{
		
		boolean canContinue=true;
		int errorCode=0;
		Piece piece = game.getPiece(fromNumber,fromLetter);
		String pieceTeam = piece.team;
		int pieceTeamInt = game.translateTeamToNum(pieceTeam);
		if(turn==pieceTeamInt){
			
			String fileFrom = fromLetter+fromNumber;
			String fileTo = toLetter+toNumber;
			String[] vals = new String[3];
			vals[0] = game.turn;
			vals[1] = fileFrom;
			vals[2] = fileTo;
			
			if(!game.kingInCheck){
				if(game.generatesCheck(toLetter,toNumber,piece)){
					errorCode= 14;
					canContinue=false;
				}
				
			}
			else{
				//if it unchecks leave it alone, other wise check if moves generatesCheck
				if(!game.unChecks(toLetter,toNumber, piece)){					
					errorCode= 14;
					canContinue=false;					
				}				
				
			}						
			
			if(canContinue){
				
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
				//add move
				addMoveToMoves(fromLetter, fromNumber, toLetter, toNumber);
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
						//add move
						addMoveToMoves(fromLetter, fromNumber, toLetter, toNumber);
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
				//add move
				addMoveToMoves(fromLetter, fromNumber, toLetter, toNumber);
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
				//add move
				addMoveToMoves(fromLetter, fromNumber, toLetter, toNumber);
				//move piece
				String row= toNumber;
				String col = toLetter;			
				piece.setRowPosition(row);
				piece.setColPosition(col);
				
				//promote piece
				showPromotionAlert(piece);
				
				
				//increment piece moves
				piece.numMoves++;
				return 0;
			}
			else if(Code==102){ //pawn's promotion 

				game.promotion= true;
				//save state before moving
				addMoveToUndo();
				//add move
				addMoveToMoves(fromLetter, fromNumber, toLetter, toNumber);
				//move piece
				String row= toNumber;
				String col = toLetter;			
				piece.setRowPosition(row);
				piece.setColPosition(col);	
				
				//promote piece
				showPromotionAlert(piece);
				
				//increment piece moves
				piece.numMoves++;
				return 0;
			}
			else if(Code==103){ //castling done successful
				game.promotion= false;
				//save state before moving
				addMoveToUndo();
				//add move
				addMoveToMoves(fromLetter, fromNumber, toLetter, toNumber);
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
			else{
				return errorCode;
			}
		}
		else{
			return -3; //you can only move your own pieces.
		}
	}
	
	protected int[] tryMove() throws Exception {	
		
		int[] errorCodeNallowNextTurn= new int[2];
		errorCodeNallowNextTurn[1] = 1;
	        if(origin!=null && destiny!=null){
		        String originTag = (String)origin.getTag();
		        String destinyTag = (String)destiny.getTag();
		        
		        String fromLetter = originTag.substring(0,1);
		        String fromNumber = originTag.substring(1);
		        		        
		        String toLetter = destinyTag.substring(0,1);
		        String toNumber = destinyTag.substring(1);
		        
		        
		        
		        Piece piece = game.getPiece(fromNumber,fromLetter);  

				try {
					errorCodeNallowNextTurn[0] = move(fromLetter,fromNumber,toLetter,toNumber,game.translateTeamToNum(game.turn));
				
				   	if(errorCodeNallowNextTurn[0]!=0){
				   		String message = Integer.toString(errorCodeNallowNextTurn[0]);
				   		showToast(message,true);
				   		errorCodeNallowNextTurn[1]=0;
				   	}
				   	else{
				   		if(prevoriginS!=null)
				   		removeTraceMove(prevoriginS,prevdestinyS);
				   		prevoriginS = fromLetter+fromNumber;
				        prevdestinyS = toLetter+toNumber;
				   		paintPossibleMoves(piece, 0);
				   		clearPos();
				   	}
			   	} catch (Exception e) {
					
					System.out.println(e.getMessage());
				} 			
			   	
	        }
	        
	        return errorCodeNallowNextTurn;
		
	}
	
	private void paintTraceMove(String moveFrom, String moveTo) {
		String fromRow = moveFrom.substring(1);
		String toRow = moveTo.substring(1);		
		
		TableRow tr = (TableRow) findViewById(UtilityClass.getRowID(fromRow,game.isRotated));
		TextView prevorigin = (TextView) tr.findViewWithTag(moveFrom);
		prevorigin.setBackgroundColor(Color.MAGENTA);
		prevorigin.getBackground().setAlpha(70);
		tr = (TableRow) findViewById(UtilityClass.getRowID(toRow,game.isRotated));
		TextView prevdestiny = (TextView) tr.findViewWithTag(moveTo);
		prevdestiny.setBackgroundColor(Color.MAGENTA);
		prevdestiny.getBackground().setAlpha(70);
	}
	private void removeTraceMove(String moveFrom,String moveTo){
		String fromRow = moveFrom.substring(1);
		String toRow = moveTo.substring(1);		
		
		TableRow tr = (TableRow) findViewById(UtilityClass.getRowID(fromRow,game.isRotated));
		TextView prevorigin = (TextView) tr.findViewWithTag(moveFrom);
		prevorigin.setBackgroundColor(Color.TRANSPARENT);
		tr = (TableRow) findViewById(UtilityClass.getRowID(toRow,game.isRotated));
		TextView prevdestiny = (TextView) tr.findViewWithTag(moveTo);
		prevdestiny.setBackgroundColor(Color.TRANSPARENT);
	}

	private void clearPos() {
		
		destiny.setBackgroundColor(Color.TRANSPARENT);
		String originTag = (String)origin.getTag();
                
        String fromLetter = originTag.substring(0,1);
        String fromNumber = originTag.substring(1);
        
        int fLetter = (UtilityClass.LetterToNum(fromLetter.charAt(0))-1);
        int fNumber = (Integer.parseInt(fromNumber)-1);
        
        game.board[fNumber][fLetter]=0;
        
		
	}
	
	void isCheck(){		
		if(game.turn.equals("b")){
			String message = "Black King in Check!";
			King blackKing = game.findKing("b");
			String blackKingPos = blackKing.getColPosition()+blackKing.getRowPosition();
			for(Piece piece:game.piecesInGame){
				if(piece.team.equals("w")){
					if(piece.possibleAttackMoves.contains(blackKingPos)){
						showToast(message,false);
						game.kingInCheck=true;
						game.checksAttacker=piece;
					}
				}
			}
		}
		else{
			String message = "White King in Check!";
			King whiteKing = game.findKing("w");
			String blackKingPos = whiteKing.getColPosition()+whiteKing.getRowPosition();
			for(Piece piece:game.piecesInGame){
				if(piece.team.equals("w")){
					if(piece.possibleAttackMoves.contains(blackKingPos)){
						showToast(message,false);
						game.kingInCheck=true;
						game.checksAttacker=piece;
					}
				}
			}
		}
	}
	
	private void showToast(String message,boolean isError){
		if(isError){
			int errorCode = Integer.parseInt(message);
			Toast toast=Toast.makeText(this, UtilityClass.errorTranslator(errorCode), 7000);
			toast.setGravity(Gravity.TOP, -30, 50);
			toast.show();
		}
		else{
			Toast toast=Toast.makeText(this, message, 7000);
			toast.setGravity(Gravity.TOP, -30, 50);
			toast.show();
		}
	}
	
	protected boolean promote(Piece pawn, String promoteTo){
		try{
			Piece newPiece = new Queen(pawn.team,"Q",pawn.getColPosition(),pawn.getRowPosition(),game);
			
			if(promoteTo!=null){
				char pTo = promoteTo.charAt(0);
				switch(pTo){					
				case 'R':
					newPiece = new Rook(pawn.team,"R",pawn.getColPosition(),pawn.getRowPosition(),game);
					break;
				case 'B':
					newPiece = new Bishop(pawn.team,"B",pawn.getColPosition(),pawn.getRowPosition(),game);
					break;
				case 'K':
					newPiece = new Knight(pawn.team,"N",pawn.getColPosition(),pawn.getRowPosition(),game);
					break;
				}
			}
			
			if(pawn.team.equals("w")){
				game.white.pieces.add(newPiece);
			}
			else{
				game.black.pieces.add(newPiece);
			}
			pawn.status="death";
			//place and update pieces
    		game.placeAndUpdatePieces();
			//rotate board						    		
			game.rotateBoard();									
			//draw board;						    		
			drawBoard();
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
	

		
	
}
