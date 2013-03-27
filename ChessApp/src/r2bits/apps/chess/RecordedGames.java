package r2bits.apps.chess;


import java.io.Serializable;
import java.util.ArrayList;

public class RecordedGames implements Serializable {
	
	
	/**
	 * SUID
	 */
	private static final long serialVersionUID = 4733430505225620629L;
	ArrayList<SavedGameMove> recordedGames;
	
	public RecordedGames(){
		recordedGames = new ArrayList<SavedGameMove>();
	}
	
	public int size(){
		return recordedGames.size();
	}
	
	public SavedGameMove getFile(String filename){
		for(SavedGameMove file:recordedGames){
			if(file.getFileName().equals(filename)){
				return file;
			}
		}
		return null;
	}
	
	
	
	
}
