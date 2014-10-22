package com.mj.childrensapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
		gameButton.setVisibility(View.VISIBLE);
		gameButton.setBackgroundColor(Color.TRANSPARENT);
		tutButton.setVisibility(View.VISIBLE);
		tutButton.setBackgroundColor(Color.TRANSPARENT);
		myzooButton.setVisibility(View.VISIBLE);
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
		//Next up is the file saving system.  This will open or make the file for the game

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

