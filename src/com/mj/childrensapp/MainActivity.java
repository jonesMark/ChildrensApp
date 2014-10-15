package com.mj.childrensapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
	public void gotoGame(View view) {
		//Sends user to screen 2
		Intent intent = new Intent (this, Game.class);
		startActivity(intent);
	}
	public void gotoTut(View view) {
		//Sends user to screen 2
		Intent intent = new Intent (this, Tuturial.class);
		startActivity(intent);
	}
	public void gotoMyzoo(View view) {
		//Sends user to screen 2
		Intent intent = new Intent (this, MyZoo.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//initiallize buttones
		Button gameButton = (Button) findViewById(R.id.playbutton);
		Button tutButton = (Button) findViewById(R.id.tutbutton);
		Button myzooButton = (Button) findViewById(R.id.myzoobutton);
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

