package com.mj.childrensapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
/*
 * This is how to use file saving on the system (using 0 for no, 1 for yes (won that animal):
 * This can also work for saving any variable necessary (strings and ints are the easiest to use for preferences)
 *      int tiger = 1;//won the tiger
 *      //open settings and add the editor
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = settings.edit();
        //animalTiger is the name the value is stored under, tiger is the int itself.
        editor.putInt("animalTiger", tiger);
        editor.commit();
 * 
 * Clarified--checking a value
 * 		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());//uses the default app preferences
		int tiger = settings.getInt("animalTiger", 0);//The name is how it is save, the 0 is defaulted to if no number exists
		if (tiger == 1) {
			findViewById(R.id.imageView19).setVisibility(View.INVISIBLE);//just a placeholder
		}
		else {}
 * 
 * 
 */
public class MainActivity extends Activity {
	
	public void gotoGame(View view) {
		//Sends user to game screen
		Intent intent = new Intent (this, Game.class);
		startActivity(intent);
	}
	public void gotoTut(View view) {
		//Sends user to tutorial screen 1
		Intent intent = new Intent (this, Tuturial.class);
		startActivity(intent);
	}
	public void gotoMyzoo(View view) {
		//Sends user to "my zoo" screen
		Intent intent = new Intent (this, MyZoo.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//initiallize buttones
		ImageButton gameButton = (ImageButton) findViewById(R.id.playbutton);
		ImageButton tutButton = (ImageButton) findViewById(R.id.tutbutton);
		ImageButton myzooButton = (ImageButton) findViewById(R.id.myzoobutton);
		//Make buttons invisisble
		//gameButton.setVisibility(View.VISIBLE);
		gameButton.setBackgroundColor(Color.TRANSPARENT);
		//tutButton.setVisibility(View.VISIBLE);
		tutButton.setBackgroundColor(Color.TRANSPARENT);
		//myzooButton.setVisibility(View.VISIBLE);
		myzooButton.setBackgroundColor(Color.TRANSPARENT);

		//Set click listeners for all buttons
        gameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	gotoGame(v);
            }
        });
        tutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	gotoTut(v);
            }
        });
        myzooButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	gotoMyzoo(v);
            }
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}

