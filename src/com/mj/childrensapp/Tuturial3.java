package com.mj.childrensapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class Tuturial3 extends Activity {
	private ImageButton homebtn;
	
	public void sendHome(View view){
		Intent intent = new Intent (this, MainActivity.class);
		startActivity(intent);
	}
	
	public void sendRight(View view){
		Intent intent = new Intent (this, Tuturial4.class);
		startActivity(intent);
	}
	
	public void sendLeft(View view){
		Intent intent = new Intent (this, Tuturial2.class);
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tuturial3);
		
		homebtn = (ImageButton)findViewById(R.id.myzoohome);
		homebtn.setBackgroundColor(Color.TRANSPARENT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tuturial3, menu);
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
