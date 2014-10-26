package com.mj.childrensapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
public class MyZoo extends MainActivity {
	//Saved animals are "Cow" and "Tiger" so far.
	private int cyclenum = 0;
	private boolean tiger = false;
	private boolean cow = false;
	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());//uses the default app preferences
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_zoo);

		//Next up, check all animals to see if they were won
		if (settings.getInt("Tiger", 0) ==1 ) {
			tiger = true;
		}
		if (settings.getInt("Cow", 0) ==1 ) {
			cow = true;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_zoo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void buttonClick() {
		//0 = cow 
		//1 = tiger
		//2 = ?
		//3 = ?
		switch (cyclenum) {
		case 0: //make cow invisible
			break;
		case 1: //make tiger invisible
			break;
		case 2: //make ?? invisible
			break;
		case 3: //Make ?? invisible
			break;
		default:  //do nothing, nothing is visible
		}
		cyclenum++;
		boolean keepgoing = true;
		if (cow || tiger || cow || tiger) {
			//no animals default screen, prevents infinite loops
		}
		{
			//master switch statement, allows for cycling through images.
			while (keepgoing) {
				switch (cyclenum)  {
				case 0: if (cow) {
					keepgoing=false;
					//make cow visible
				}
				else { 	
					cyclenum ++;
				}
				break;
				case 1: if (tiger) {
					keepgoing=false;
					//make tiger visible
				}
				else { 	
					cyclenum++;
				}
				break;
				case 2: if (cow) {
					keepgoing=false;
				}
				else { 	
					cyclenum++;
				}
				break;
				case 3: if (cow) {
					keepgoing=false;
				}
				else { 	
					cyclenum++;
				}
				break;
				default: cyclenum = 0;
				break;
				}
			}
		}
	}
}
