package r2bits.apps.chess;

import java.io.*;

import android.content.Context;
public class ObjectClonerBackup
{
   
	static String FILENAME = "gameState";
	
	// so that nobody can accidentally create an ObjectCloner object
   private ObjectClonerBackup(){}
   // returns a deep copy of an object
   static public Object deepCopy(Object oldObj, Context ctx) throws Exception 
   {
      
	  ObjectOutputStream oos = null;
	  ObjectInputStream ois = null;
	   
      try
      {
    	 FileInputStream fin;
    	 
         //ctx.deleteFile (FILENAME); 	 
         
         
    	 FileOutputStream fos;
    	 fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
         oos = new ObjectOutputStream(fos);
         // serialize and pass the object
         oos.writeObject(oldObj);   
         oos.flush();
         fin = ctx.openFileInput(FILENAME);
         ois = 	new ObjectInputStream(fin);        
       
      }
      catch(IOException e){
    	  e.getMessage();
      }
      catch(Exception e)
      {
         System.out.println("Exception in ObjectCloner = " + e);
         throw(e);
      }
      // return the new object
      return (ChessBoard)ois.readObject();
   
   }
   
}
