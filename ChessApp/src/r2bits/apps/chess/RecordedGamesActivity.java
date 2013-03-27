package r2bits.apps.chess;


import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RecordedGamesActivity extends Activity {
	 
	
	RecordedGames files;
	
	
	public void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	        setContentView(R.layout.recorded_games);
	        
	        try {
				RecordedGames rg = this.files = GamesReadWrite.readGames(this.getApplicationContext());
				if(rg==null){
					this.files = new RecordedGames();
					GamesReadWrite.writeGames(this.files,this.getApplicationContext());
				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	        
	        fillTable();			
			
	        
	 }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_r, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.sort_date:
	        	Collections.sort(files.recordedGames, SavedGameMove.COMPARE_BY_DATE);
	        	clearTable();
	        	fillTable();
	            return true;
	        case R.id.sort_filename:
	        	Collections.sort(files.recordedGames, SavedGameMove.COMPARE_BY_FILENAME);
	        	clearTable();
	        	fillTable();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void clearTable() {
		TableLayout table = (TableLayout) findViewById(R.id.files);	
		table.removeAllViews();		
	}

	private void fillTable(){
		Typeface font = Typeface.createFromAsset(getAssets(),"fonts/Fontastique.ttf");
		TextView fnHeader = (TextView) findViewById(R.id.filename);
		TextView dHeader = (TextView) findViewById(R.id.date);
		fnHeader.setTypeface(font);
		dHeader.setTypeface(font);
		
		TableLayout table = (TableLayout) findViewById(R.id.files);	
		
		
		for(SavedGameMove file:files.recordedGames){
			TableRow row = new TableRow(this);
			TextView filename = new TextView(this);
			TextView date = new TextView(this);
			filename.setWidth(200);			
			filename.setText(file.getFileName());
			filename.setTypeface(font);
			date.setText(file.getStringDate());
			date.setTypeface(font);
			row.addView(filename);
			row.addView(date);
			row.setTag(file.getFileName());
			row.setPadding(0, 4, 0, 2);
			row.setBackgroundColor(Color.TRANSPARENT);
			addlistener(row);
			table.addView(row);
			//TableRow row2 = new TableRow(this);
			ImageView divisorLine = new ImageView(this);		
			divisorLine.setImageResource(R.drawable.lineadivisora);
			//row2.addView(divisorLine);
			table.addView(divisorLine);
		}
	}
	
	private void addlistener(final TableRow row) {			
		
		row.setOnClickListener(new OnClickListener() {	    

				public void onClick(View v) {					
					
					Intent i =  new Intent(RecordedGamesActivity.this,GamePlayerActivity.class);
					String filename = (String)row.getTag();
					i.putExtra("filename", filename);
					
					startActivity(i);					
				}	
				
		});
	
	}
}
