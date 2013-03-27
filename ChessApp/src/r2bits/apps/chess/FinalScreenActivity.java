package r2bits.apps.chess;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinalScreenActivity extends Activity {

	ChessBoard game;
	ArrayList<Integer> stats;
	protected Button close;
	protected TextView title;
	protected TextView winner;
	protected TextView winnerTeam;
	protected TextView killsWinner;
	protected TextView numberKillsWinner;
	protected TextView piecesLeftWinner;
	protected TextView numberLeftPiecesWinner;
	protected TextView movesWinner;
	protected TextView numberMovesWinner;
	protected TextView loser;
	protected TextView loserTeam;
	protected TextView killsLoser;
	protected TextView numberKillsLoser;
	protected TextView piecesLeftLoser;
	protected TextView numberLeftPiecesLoser;
	protected TextView movesLoser;
	protected TextView numberMovesLoser;
	
	
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.final_screen);
      
        Bundle bundle = getIntent().getExtras();
        stats = bundle.getIntegerArrayList("final");

        loadFonts();
        loadValues();
        
        close = (Button) findViewById(R.id.close);
        close.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				 onBackPressed();	
			} 
		});
    
        
    }
    
    private void loadValues() {
    	
    	int status = stats.get(6);
    	
    	if(status==3){ //It was a draw
    		
    		winner.setText("White Team");
    		winnerTeam.setText("		");
    		
    		//White Stats
      		numberKillsWinner.setText(stats.get(0)+"");	
    		numberMovesWinner.setText(stats.get(1)+"");
    		numberLeftPiecesWinner.setText(stats.get(2)+"");
    		
    		loser.setText("Black Team");
    		loserTeam.setText("		");
    		
    		//Black Stats
    		numberKillsLoser.setText(stats.get(3)+"");
    		numberMovesLoser.setText(stats.get(4)+"");
    		numberLeftPiecesLoser.setText(stats.get(5)+"");
    		
    	}
    	
    	if(status==1){ //It was a draw
   
    		winnerTeam.setText("White Team	  ");
    		
    		//White Stats
      		numberKillsWinner.setText(stats.get(0)+"");	
    		numberMovesWinner.setText(stats.get(1)+"");
    		numberLeftPiecesWinner.setText(stats.get(2)+"");
    		
    		loserTeam.setText("Black Team	  ");
    		
    		//Black Stats
    		numberKillsLoser.setText(stats.get(3)+"");
    		numberMovesLoser.setText(stats.get(4)+"");
    		numberLeftPiecesLoser.setText(stats.get(5)+"");
    		
    	}
    	
    	if(status==2){ //It was a draw
    		   
    		winnerTeam.setText("Black Team	  ");
    		
    		//Black Stats
      		numberKillsWinner.setText(stats.get(3)+"");	
    		numberMovesWinner.setText(stats.get(4)+"");
    		numberLeftPiecesWinner.setText(stats.get(5)+"");
    		
    		loserTeam.setText("White Team	  ");
    		
    		//White Stats
    		numberKillsLoser.setText(stats.get(0)+"");
    		numberMovesLoser.setText(stats.get(1)+"");
    		numberLeftPiecesLoser.setText(stats.get(2)+"");
    		
    	}
    	

	}

	private void loadFonts(){
    	
		Typeface font = Typeface.createFromAsset(getAssets(),"fonts/Fontastique.ttf");
		
		title = (TextView) findViewById(R.id.title);
		title.setTypeface(font);
		
		winner = (TextView) findViewById(R.id.winner);
		winner.setTypeface(font);
		
		winnerTeam = (TextView) findViewById(R.id.winnerTeam);
		winnerTeam.setTypeface(font);
		
		killsWinner = (TextView) findViewById(R.id.kills);
		killsWinner.setTypeface(font);
		
		numberKillsWinner = (TextView) findViewById(R.id.numberKills);
		numberKillsWinner.setTypeface(font);
		
		piecesLeftWinner = (TextView) findViewById(R.id.piecesLeft);
		piecesLeftWinner.setTypeface(font);
		
		numberLeftPiecesWinner = (TextView) findViewById(R.id.numberPiecesLeft);
		numberLeftPiecesWinner.setTypeface(font);
        
		movesWinner = (TextView) findViewById(R.id.moves);
		movesWinner.setTypeface(font);
		
		numberMovesWinner = (TextView) findViewById(R.id.numberMoves);
		numberMovesWinner.setTypeface(font);
		
		loser = (TextView) findViewById(R.id.loser);
		loser.setTypeface(font);
		
		loserTeam = (TextView) findViewById(R.id.loserTeam);
		loserTeam.setTypeface(font);
		
		killsLoser = (TextView) findViewById(R.id.kills2);
		killsLoser.setTypeface(font);
		
		numberKillsLoser = (TextView) findViewById(R.id.numberKills2);
		numberKillsLoser.setTypeface(font);
		
		piecesLeftLoser = (TextView) findViewById(R.id.piecesLeft2);
		piecesLeftLoser.setTypeface(font);
		
		numberLeftPiecesLoser = (TextView) findViewById(R.id.numberPiecesLeft2);
		numberLeftPiecesLoser.setTypeface(font);
        
		movesLoser = (TextView) findViewById(R.id.moves2);
		movesLoser.setTypeface(font);
		
		numberMovesLoser = (TextView) findViewById(R.id.numberMoves2);
		numberMovesLoser.setTypeface(font);		
    }
	
	public void onBackPressed(){
		
		 Intent i = new Intent(this, ChessAppActivity.class);
         startActivity(i);	
		
	}

	
}
