package com.mj.childrensapp;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Game extends Activity {

	private MediaRecorder myRecorder;
	private MediaPlayer myPlayer;
	private String outputFile = null;
	//private Button startBtn;
	private Button stopBtn;
	private ImageButton imageStart;
	private int mInterval = 250; //this is the timestep in miliseconds 
	private Handler mHandler;
	private double ampNum = 0;
	private double veiwNum = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		//connect text1 to text so we can update it easily
		//text = (TextView) findViewById(R.id.text1);
		// store it to sd card
		outputFile = Environment.getExternalStorageDirectory().
				getAbsolutePath() + "/javacodegeeksRecording.3gpp";

		myRecorder = new MediaRecorder();
		myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		myRecorder.setOutputFile(outputFile);
		mHandler = new Handler();

		imageStart = (ImageButton)findViewById(R.id.ImageStart);
		imageStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start(v);
			}
		});

		stopBtn = (Button)findViewById(R.id.stop);
		stopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stop(v);
			}
		});

	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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
	//what happens when you press the start button
	public void start(View view){
		try {
			myRecorder.prepare();
			myRecorder.start();
		} catch (IllegalStateException e) {
			// start:it is called before prepare()
			// prepare: it is called after start() or before setOutputFormat() 
			e.printStackTrace();
		} catch (IOException e) {
			// prepare() fails
			e.printStackTrace();
		}
		// disable start button, enable stop
		imageStart.setEnabled(false);
		stopBtn.setEnabled(true);

		//text.setText("Recording Point: Recording, Amp: "+ getAmplitude());
		//start the repeating task
		startRepeatingTask();
	}
	// what happens when you press the stop button
	public void stop(View view){
		try {
			myRecorder.stop();
			myRecorder.release();
			myRecorder  = null;

			stopBtn.setEnabled(false);
			imageStart.setEnabled(true);
			//text.setText("Recording Point: Stop recording");
			stopRepeatingTask();
		} catch (IllegalStateException e) {
			//  it is called before start()
			e.printStackTrace();
		} catch (RuntimeException e) {
			// no valid audio/video data has been received
			e.printStackTrace();
		}
		outputFile = Environment.getExternalStorageDirectory().
				getAbsolutePath() + "/javacodegeeksRecording.3gpp";
		//reset the recorder and handler so you can start again if you want
		myRecorder = new MediaRecorder();
		myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		myRecorder.setOutputFile(outputFile);
		mHandler = new Handler();
	}

	
	private double getAmplitude() {
		if (myRecorder != null) {
			double m = myRecorder.getMaxAmplitude();
			return (m);
		} else {

			return 0;
		}
	}
	public void GetAmp(View view) {
		//text.setText("Recording Point: Recording, Amp: "+ getAmplitude());
	}
	//this is the repeating task
	Runnable mStatusChecker = new Runnable() {
		@Override 
		public void run() {
			mHandler.postDelayed(mStatusChecker, mInterval);
			ampNum =getAmplitude();
			//update text displaying amp
			//text.setText("Recording Point: Recording, Amp: "+ ampNum);
			//load the bar into an array
			ImageView[] soundBar = {(ImageView)findViewById(R.id.imageView1),(ImageView)findViewById(R.id.imageView2),(ImageView)findViewById(R.id.imageView3),(ImageView)findViewById(R.id.imageView4),(ImageView)findViewById(R.id.imageView5),(ImageView)findViewById(R.id.imageView6),(ImageView)findViewById(R.id.imageView7),(ImageView)findViewById(R.id.imageView8),(ImageView)findViewById(R.id.imageView9),(ImageView)findViewById(R.id.imageView10),(ImageView)findViewById(R.id.imageView11),(ImageView)findViewById(R.id.imageView12),(ImageView)findViewById(R.id.imageView13),(ImageView)findViewById(R.id.imageView14),(ImageView)findViewById(R.id.imageView15),(ImageView)findViewById(R.id.imageView16)};
			veiwNum = ampNum/2500;
			int i=0;
			//set bar to be invisible
			for (int k=0; k<16; k++) {
				soundBar[k].setVisibility(View.INVISIBLE);
			}
			//make the bars visible based on the amp.
			while (veiwNum >0){
				soundBar[i].setVisibility(View.VISIBLE);
				i++;
				veiwNum--;
				
			}
		}
	};

	void startRepeatingTask() {
		mStatusChecker.run(); 
	}

	void stopRepeatingTask() {
		mHandler.removeCallbacks(mStatusChecker);
	}

}