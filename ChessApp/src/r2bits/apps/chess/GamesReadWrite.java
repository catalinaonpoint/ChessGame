package r2bits.apps.chess;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

import android.content.Context;

public class GamesReadWrite {
	
	private static final String FILENAME = "savedGames.chess";
	
	private GamesReadWrite(){};
	public static RecordedGames readGames(Context ctx) throws OptionalDataException, ClassNotFoundException, IOException {
		ObjectInputStream ois = null;
		FileInputStream fin = null;
		 try{
			 fin = ctx.openFileInput(FILENAME);
	         ois = 	new ObjectInputStream(fin);
	         
		 }
		 catch(IOException e){
			 return null;
		 }
		 catch(Exception e){
			 System.out.println(e.getMessage());
		 }
		 return (RecordedGames)ois.readObject(); 
	}
	
	public static void writeGames(RecordedGames rg,Context ctx) throws OptionalDataException, ClassNotFoundException, IOException {
		FileOutputStream fon = null;
		ObjectOutputStream oos = null;
		
		 try{			 
			 ctx.deleteFile (FILENAME); 
			 
			 
			 fon = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			 oos = 	new ObjectOutputStream(fon);
			 oos.writeObject(rg);   
	         oos.flush();
		 }
		 catch(Exception e){
			 System.out.println(e.getMessage());
		 }
	}
}
