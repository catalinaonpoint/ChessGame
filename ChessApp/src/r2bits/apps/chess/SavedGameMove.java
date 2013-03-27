package r2bits.apps.chess;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;

public class SavedGameMove implements Serializable{
	
	
	/**
	 * SUID
	 */
	private static final long serialVersionUID = -1059891522649776112L;
	
	private String fileName;
	private Calendar date; 
	private String dateString;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy\nHH:mm:ss");
	private ArrayList<Move>  moves ;
	int pointer;
	
	
	public SavedGameMove(){
		moves = new ArrayList<Move>();
		setFileName(new String());
		setDate(this.date);
		this.dateString = sdf.format(date.getTime());
		pointer = 0;
	}
	
	/**
	 * Gets the date the photo was taken.
	 * @return  String Date of the photo.
	 */
	public String getStringDate() {
		return this.dateString;
	}
	/**
	 * Gets the date the photo was taken.
	 * @return Calendar object Date of the photo.
	 */
	public Calendar getCalendarDate() {
		return this.date;
	}
	
	/**
	 * Sets the date of the photo to a new value.
	 * @param date New date of photo.
	 */
	private void setDate(Calendar date) {
		this.date = Calendar.getInstance();
	}
	/**
	 * Gets filename
	 * @return filename
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Sets filename
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public void addMove(Move move){
		moves.add(move);
	}
	
	public void removeLast(){
		int index = moves.size()-1;
		moves.remove(index);
	}
	
	public Move getNextMove(){
		Move move =  moves.get(pointer);
		pointer++;
		return move;
	}
	
	public void decrementPointer(){
		pointer--;
	}
	
	
	public boolean hasNext(){		
		if(pointer==moves.size()){
			pointer--;
			return false;
		}
		return true;
	}
	
	
	
	public String toString(){
		int counter=1;
		StringBuffer result = new StringBuffer();
		result.append("Moves: ");
		for(Move move:moves){
			result.append(counter+"("+move.fromLetter+move.fromNumber+"-"+move.toLetter+move.toNumber+") ->");
		}		
		return result.toString();
	}

	
	

	
	
	public static Comparator<SavedGameMove> COMPARE_BY_DATE = new Comparator<SavedGameMove>(){		
	   public int compare(SavedGameMove first, SavedGameMove second) {
		Calendar date1 = first.getCalendarDate();
		Calendar date2 = second.getCalendarDate();		
		return date1.compareTo(date2);
	   }
	};
	
	public static Comparator<SavedGameMove> COMPARE_BY_FILENAME = new Comparator<SavedGameMove>(){		
		public int compare(SavedGameMove first, SavedGameMove second) {			   
		   return first.fileName.compareTo(second.fileName);
		}
	};


	
		
	
}
