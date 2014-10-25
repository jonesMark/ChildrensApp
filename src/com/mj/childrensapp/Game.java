package com.mj.childrensapp;

import java.io.IOException;
import java.util.Random;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Game extends MainActivity {

	private MediaRecorder myRecorder;
	private MediaPlayer myPlayer;
	private String outputFile = null;
	//private Button startBtn;
	private ImageButton imageStop;
	private ImageButton imageStart;
	private ImageButton slidecont;
	private ImageButton slideagain;
	private ImageButton exitbtn;
	private int mInterval = 150; //this is the timestep in miliseconds 
	private Handler mHandler;
	private double multiplier = 1.05; //a little more control added, maybe use for the future.
	private double ampNum = 0;
	private double veiwNum = 0;
	private int winNum = 0; //counts how long they're in the winning range
	private int animalVal = 0;
	private int ampMin;
	private int ampMax;
	int timeleftmaster = 100;
	int timeleft;
	Random random = new Random();
	
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
		
		animalVal = setAnimal(animalVal);
		if(animalVal ==1){
			findViewById(R.id.acowsays).setVisibility(View.VISIBLE);
			findViewById(R.id.gamecow).setVisibility(View.VISIBLE);
			findViewById(R.id.rangboxcow).setVisibility(View.VISIBLE);
			ampMin =6;
			ampMax =11;
		}
		else if(animalVal == 2){
			findViewById(R.id.atigersays).setVisibility(View.VISIBLE);
			findViewById(R.id.gametiger).setVisibility(View.VISIBLE);
			findViewById(R.id.rangboxtiger).setVisibility(View.VISIBLE);
			ampMin =10;
			ampMax =15;
		}
		imageStart = (ImageButton)findViewById(R.id.ImageStart);
		imageStart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start(v);
			}
		});
		imageStop = (ImageButton)findViewById(R.id.ImageStop);
		imageStop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stop(v);
			}
		});
		slidecont = (ImageButton)findViewById(R.id.slidecont);
		slidecont.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cont(v);
			}
		});
		slideagain = (ImageButton)findViewById(R.id.slidetryagain);
		slideagain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				again(v);
			}
		});
		exitbtn = (ImageButton)findViewById(R.id.exit);
		exitbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exit(v);
			}
		});
		slidecont.setEnabled(false);
		slideagain.setEnabled(false);
		imageStart.setBackgroundColor(Color.TRANSPARENT);
		imageStop.setBackgroundColor(Color.TRANSPARENT);
		slidecont.setBackgroundColor(Color.TRANSPARENT);
		slideagain.setBackgroundColor(Color.TRANSPARENT);
		exitbtn.setBackgroundColor(Color.TRANSPARENT);

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
		imageStart.setVisibility(View.INVISIBLE);
		imageStop.setEnabled(true);
		imageStop.setVisibility(View.VISIBLE);
		//text.setText("Recording Point: Recording, Amp: "+ getAmplitude());
		//start the repeating task
		timeleft = timeleftmaster;
		startRepeatingTask();
	}

	public void stop(View view){
		   try {
		      myRecorder.stop();
		      myRecorder.release();
		      myRecorder  = null;
		      
		      imageStop.setEnabled(false);
		      imageStop.setVisibility(View.INVISIBLE);
		      imageStart.setEnabled(true);
		      imageStart.setVisibility(View.VISIBLE);
		      
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
		   
		   myRecorder = new MediaRecorder();
		   myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		   myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		   myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		   myRecorder.setOutputFile(outputFile);
		   mHandler = new Handler();
	   }
	
	public void cont(View view){
		animalVal = setAnimal(animalVal);
		if(animalVal ==1){
			findViewById(R.id.atigersays).setVisibility(View.INVISIBLE);
			findViewById(R.id.gametiger).setVisibility(View.INVISIBLE);
			findViewById(R.id.rangboxtiger).setVisibility(View.INVISIBLE);
			findViewById(R.id.acowsays).setVisibility(View.VISIBLE);
			findViewById(R.id.gamecow).setVisibility(View.VISIBLE);
			findViewById(R.id.rangboxcow).setVisibility(View.VISIBLE);
			ampMin =6;
			ampMax =11;
		}
		else if(animalVal == 2){
			findViewById(R.id.acowsays).setVisibility(View.INVISIBLE);
			findViewById(R.id.gamecow).setVisibility(View.INVISIBLE);
			findViewById(R.id.rangboxcow).setVisibility(View.INVISIBLE);
			findViewById(R.id.atigersays).setVisibility(View.VISIBLE);
			findViewById(R.id.gametiger).setVisibility(View.VISIBLE);
			findViewById(R.id.rangboxtiger).setVisibility(View.VISIBLE);
			ampMin =10;
			ampMax =15;
		}
		findViewById(R.id.popblueback).setVisibility(View.INVISIBLE);
		findViewById(R.id.popback).setVisibility(View.INVISIBLE);
		findViewById(R.id.popcow).setVisibility(View.INVISIBLE);
		findViewById(R.id.poptiger).setVisibility(View.INVISIBLE);
		findViewById(R.id.popwin).setVisibility(View.INVISIBLE);
		slidecont.setEnabled(false);
		exitbtn.setEnabled(true);
	    slidecont.setVisibility(View.INVISIBLE);
	    
	    imageStop.setEnabled(false);
	    imageStop.setVisibility(View.INVISIBLE);
	    imageStart.setEnabled(true);
	    imageStart.setVisibility(View.VISIBLE);
	    
	    ImageView[] soundBar = {(ImageView)findViewById(R.id.imageView1),(ImageView)findViewById(R.id.imageView2),(ImageView)findViewById(R.id.imageView3),(ImageView)findViewById(R.id.imageView4),(ImageView)findViewById(R.id.imageView5),(ImageView)findViewById(R.id.imageView6),(ImageView)findViewById(R.id.imageView7),(ImageView)findViewById(R.id.imageView8),(ImageView)findViewById(R.id.imageView9),(ImageView)findViewById(R.id.imageView10),(ImageView)findViewById(R.id.imageView11),(ImageView)findViewById(R.id.imageView12),(ImageView)findViewById(R.id.imageView13),(ImageView)findViewById(R.id.imageView14),(ImageView)findViewById(R.id.imageView15)};
	    for (int k=0; k<15; k++) {
			soundBar[k].setVisibility(View.INVISIBLE);
		}
		
	    myRecorder = new MediaRecorder();
		   myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		   myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		   myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		   myRecorder.setOutputFile(outputFile);
		   mHandler = new Handler();
		
	}
	public void again(View view){
		
		
		findViewById(R.id.popblueback).setVisibility(View.INVISIBLE);
		findViewById(R.id.popback).setVisibility(View.INVISIBLE);
		findViewById(R.id.popcow).setVisibility(View.INVISIBLE);
		findViewById(R.id.poptiger).setVisibility(View.INVISIBLE);
		findViewById(R.id.poptime).setVisibility(View.INVISIBLE);
		slideagain.setEnabled(false);
		exitbtn.setEnabled(true);
	    slideagain.setVisibility(View.INVISIBLE);
	    
	    imageStop.setEnabled(false);
	    imageStop.setVisibility(View.INVISIBLE);
	    imageStart.setEnabled(true);
	    imageStart.setVisibility(View.VISIBLE);
	    
	    ImageView[] soundBar = {(ImageView)findViewById(R.id.imageView1),(ImageView)findViewById(R.id.imageView2),(ImageView)findViewById(R.id.imageView3),(ImageView)findViewById(R.id.imageView4),(ImageView)findViewById(R.id.imageView5),(ImageView)findViewById(R.id.imageView6),(ImageView)findViewById(R.id.imageView7),(ImageView)findViewById(R.id.imageView8),(ImageView)findViewById(R.id.imageView9),(ImageView)findViewById(R.id.imageView10),(ImageView)findViewById(R.id.imageView11),(ImageView)findViewById(R.id.imageView12),(ImageView)findViewById(R.id.imageView13),(ImageView)findViewById(R.id.imageView14),(ImageView)findViewById(R.id.imageView15)};
	    for (int k=0; k<15; k++) {
			soundBar[k].setVisibility(View.INVISIBLE);
		}
		
	    myRecorder = new MediaRecorder();
		   myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		   myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		   myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		   myRecorder.setOutputFile(outputFile);
		   mHandler = new Handler();
		
	}

	public void exit(View view){
		stop(view);
		Intent intent = new Intent (this, MainActivity.class);
		startActivity(intent);
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
			ImageView[] soundBar = {(ImageView)findViewById(R.id.imageView1),(ImageView)findViewById(R.id.imageView2),(ImageView)findViewById(R.id.imageView3),(ImageView)findViewById(R.id.imageView4),(ImageView)findViewById(R.id.imageView5),(ImageView)findViewById(R.id.imageView6),(ImageView)findViewById(R.id.imageView7),(ImageView)findViewById(R.id.imageView8),(ImageView)findViewById(R.id.imageView9),(ImageView)findViewById(R.id.imageView10),(ImageView)findViewById(R.id.imageView11),(ImageView)findViewById(R.id.imageView12),(ImageView)findViewById(R.id.imageView13),(ImageView)findViewById(R.id.imageView14),(ImageView)findViewById(R.id.imageView15)};
			veiwNum = ampNum/2500*multiplier;
			int i=0;
			//set bar to be invisible
			for (int k=0; k<15; k++) {
				soundBar[k].setVisibility(View.INVISIBLE);
			}
			if (veiwNum > ampMin && veiwNum < ampMax){
				winNum++;
				if (winNum ==10){
					try {
						myRecorder.stop();
						myRecorder.release();
						myRecorder  = null;

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
					findViewById(R.id.popblueback).setVisibility(View.VISIBLE);
					findViewById(R.id.popback).setVisibility(View.VISIBLE);
					findViewById(R.id.popwin).setVisibility(View.VISIBLE);
					if (animalVal == 1){
						findViewById(R.id.popcow).setVisibility(View.VISIBLE);
					}
					if (animalVal == 2){
						findViewById(R.id.poptiger).setVisibility(View.VISIBLE);
					}
					slidecont.setEnabled(true);
					exitbtn.setEnabled(false);
				    slidecont.setVisibility(View.VISIBLE);
				}
				
			}
			else{
				timeleft--;
				winNum =0;
			}
			if (timeleft <= 0){
				try {
					myRecorder.stop();
					myRecorder.release();
					myRecorder  = null;

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
				findViewById(R.id.popblueback).setVisibility(View.VISIBLE);
				findViewById(R.id.popback).setVisibility(View.VISIBLE);
				findViewById(R.id.poptime).setVisibility(View.VISIBLE);
				if (animalVal == 1){
					findViewById(R.id.popcow).setVisibility(View.VISIBLE);
				}
				if (animalVal == 2){
					findViewById(R.id.poptiger).setVisibility(View.VISIBLE);
				}
				slideagain.setEnabled(true);
				exitbtn.setEnabled(false);
			    slideagain.setVisibility(View.VISIBLE);
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
	
	private int setAnimal(int current){
		int ran = current;
		while (ran == current){
		ran = random.nextInt(2)+1;
		}
		return ran;
	}

}