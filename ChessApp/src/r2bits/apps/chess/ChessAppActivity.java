package r2bits.apps.chess;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ChessAppActivity extends Activity {
    

	private static final String dirName = "data";
	private static final String fileName = "users.dat"; 
	public RecordedGames recordedGames;
    Button newGame;
    Button recordedGame;
    
    
    
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        recordedGames = getRecordedGames();
        newGame = (Button) findViewById(R.id.newgame);
        newGame.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				 Intent i = new Intent(ChessAppActivity.this, NewGameActivity.class);
		         startActivity(i);				
			} 
		});
        recordedGame = (Button) findViewById(R.id.recordedgame); 
        recordedGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(ChessAppActivity.this, RecordedGamesActivity.class);
		        startActivity(i);
			}
			
 
		});     
        
    }


    private RecordedGames getRecordedGames() {
    	RecordedGames games = new RecordedGames();
		try{
			games = readGames();
			return games;
		}
		catch(FileNotFoundException e){			
			return games;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
		return games;
	}


	/**
	 * Loads recorded games from memory.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public RecordedGames readGames() throws FileNotFoundException, IOException, ClassNotFoundException{
		try{
		ObjectInputStream ois = 
			new ObjectInputStream(new FileInputStream(dirName + File.separator + fileName));
		return (RecordedGames)ois.readObject();
		}
		catch(Exception e){
			RecordedGames games = new RecordedGames();
			return games;
		}
	}

	/**
	 * Saves recorded games into memory.
	 * @param .
	 */
	/*public void writeGames() 
	throws IOException {
		
		ObjectOutputStream oos = 
			new ObjectOutputStream(new FileOutputStream(dirName + File.separator + fileName));
		oos.writeObject(RecordedGames);
		
	}*/
    
 }