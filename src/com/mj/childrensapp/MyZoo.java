package com.mj.childrensapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
public class MyZoo extends MainActivity {
	//Saved animals are "Cow" and "Tiger" so far.
	private int cyclenum = 0;
	private int lastnum = 0;
	private boolean tiger = false;
	private boolean cow = false;
	private boolean goBack = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_zoo);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());//uses the default app preferences
		//Next up, check all animals to see if they were won
		if (settings.getInt("Tiger", 0) ==1 ) {
			tiger = true;
		}
		if (settings.getInt("Cow", 0) ==1 ) {
			cow = true;
		}
		//run the initial buttonClick to have an animal showing or the default screen.
		buttonClick();
		ImageView leftButton = (ImageView)findViewById(R.id.myzooleft);
		//Setting left and right click buttons
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				goBack = true;
				buttonClick();
			}
		});
		ImageView rightButton = (ImageView)findViewById(R.id.myzooright);
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonClick();
			}
		});
		//setting home button
		ImageView homeButton = (ImageView)findViewById(R.id.myzoohome);
		homeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoHome(v);
			}
		});
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
	public void gotoHome(View view) {
		//Sends user to "my zoo" screen
		Intent intent = new Intent (this, MainActivity.class);
		startActivity(intent);
	}

	private void buttonClick() {
		//0 = cow 
		//1 = tiger
		//2 = ?
		//3 = ?
		//Must turn off initial 
		switch (cyclenum) {
		case 0: //make cow invisible
			findViewById(R.id.myzoocow).setVisibility(View.INVISIBLE);
			break;
		case 1: //make tiger invisible
			findViewById(R.id.myzootiger).setVisibility(View.INVISIBLE);
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
			//must have at least one animal to work right.
			

			//master switch statement, allows for cycling through images.
			if (goBack) {
				cyclenum=lastnum;
				goBack=false;
			}
			while (keepgoing) {
				switch (cyclenum)  {
				case 0: if (cow) {
					keepgoing=false;
					//make cow visible
					findViewById(R.id.myzoocow).setVisibility(View.VISIBLE);
				}
				else { 	
					cyclenum ++;
				}
				break;
				case 1: if (tiger) {
					keepgoing=false;
					//make tiger visible
					findViewById(R.id.myzootiger).setVisibility(View.VISIBLE);
				}
				else { 	
					cyclenum++;
				}
				break;
				case 2: if (false) {//put in new image for these
					keepgoing=false;
				}
				else { 	
					cyclenum++;
				}
				break;
				case 3: if (false) {
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
		else {
			//no animals default screen, prevents infinite loops
			
		}	

		//this allows for the back button to work
		lastnum = cyclenum;
	}
}
