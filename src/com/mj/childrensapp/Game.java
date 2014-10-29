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
		//set the animal to a random animal
		animalVal = setAnimal(animalVal);
		//if animaval == 1 then it's a cow
		if(animalVal ==1){
			findViewById(R.id.acowsays).setVisibility(View.VISIBLE);
			findViewById(R.id.gamecow).setVisibility(View.VISIBLE);
			findViewById(R.id.rangboxcow).setVisibility(View.VISIBLE);
			ampMin =6;
			ampMax =11;
		}
		// if it's 2 then it's a tiger
		else if(animalVal == 2){
			findViewById(R.id.atigersays).setVisibility(View.VISIBLE);
			findViewById(R.id.gametiger).setVisibility(View.VISIBLE);
			findViewById(R.id.rangboxtiger).setVisibility(View.VISIBLE);
			ampMin =10;
			ampMax =15;
		}
		// set up all the buttons for the page
		// image start will start the amp meter
		imageStart = (ImageButton)findViewById(R.id.ImageStart);
		imageStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				start(v);
			}
		});
		//image stop will pause the game
		imageStop = (ImageButton)findViewById(R.id.ImageStop);
		imageStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stop(v);
			}
		});
		//slide cont will bring you to the next animal
		slidecont = (ImageButton)findViewById(R.id.slidecont);
		slidecont.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cont(v);
			}
		});
		//slide again will bring you back to the same animal so you can try again
		slideagain = (ImageButton)findViewById(R.id.slidetryagain);
		slideagain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				again(v);
			}
		});
		//exitbtn will take you back to the main menu
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
		imageStart.setEnabled(false);
		imageStart.setVisibility(View.INVISIBLE);
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
		//imageStart.setEnabled(false);
		//imageStart.setVisibility(View.INVISIBLE);
		imageStop.setEnabled(true);
		imageStop.setVisibility(View.VISIBLE);
		//text.setText("Recording Point: Recording, Amp: "+ getAmplitude());
		//start the repeating task
		timeleft = timeleftmaster;
		startRepeatingTask();
	}
	//what happens when you press the pause button, 
	public void stop(View view){
		try {
			myRecorder.stop();
			myRecorder.release();
			myRecorder = null;
			imageStop.setEnabled(false);
			imageStop.setVisibility(View.INVISIBLE);
			//imageStart.setEnabled(true);
			//imageStart.setVisibility(View.VISIBLE);
			stopRepeatingTask();
		} catch (IllegalStateException e) {
			// it is called before start()
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
		imageStart.setEnabled(true);
		imageStart.setVisibility(View.VISIBLE);
	}
	//what happens when you press slidetocontinue
	public void cont(View view){
		//reset the animal val to something else
		animalVal = setAnimal(animalVal);
		//set up the new animal based upon the animal val
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
		//make the pop up disapear
		findViewById(R.id.popblueback).setVisibility(View.INVISIBLE);
		findViewById(R.id.popback).setVisibility(View.INVISIBLE);
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
	//what happens when you press the slide again button
	public void again(View view){
		findViewById(R.id.popblueback).setVisibility(View.INVISIBLE);
		findViewById(R.id.popback).setVisibility(View.INVISIBLE);
		findViewById(R.id.popoot).setVisibility(View.INVISIBLE);
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
	//what happens when you press the exit button
	public void exit(View view){
		stop(view);
		Intent intent = new Intent (this, MainActivity.class);
		startActivity(intent);
	}
	//a method that finds the the amplitude
	private double getAmplitude() {
		if (myRecorder != null) {
			double m = myRecorder.getMaxAmplitude();
			return (m);
		} else {
			return 0;
		}
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
			//check if amp is in range
			if (veiwNum > ampMin && veiwNum < ampMax){
				winNum++;
				//check if player has won
				if (winNum ==10){
					//stop recording and display pop up
					try {
						myRecorder.stop();
						myRecorder.release();
						myRecorder = null;
						//text.setText("Recording Point: Stop recording");
						stopRepeatingTask();
					} catch (IllegalStateException e) {
						// it is called before start()
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
						//This is saving the file system for the cow, so MyZoo remembers it.
						int cow = 1;//won the cow
						//open settings and add the editor
						SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
						SharedPreferences.Editor editor = settings.edit();
						//animalTiger is the name the value is stored under, tiger is the int itself.
						editor.putInt("Cow", cow);
						editor.commit();
					}
					if (animalVal == 2){
						//This is saving the file system for the cow, so MyZoo remembers it.
						int tiger = 1;//won the cow
						//open settings and add the editor
						SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
						SharedPreferences.Editor editor = settings.edit();
						//animalTiger is the name the value is stored under, tiger is the int itself.
						editor.putInt("Tiger", tiger);
						editor.commit();
					}
					slidecont.setEnabled(true);
					exitbtn.setEnabled(false);
					slidecont.setVisibility(View.VISIBLE);
					//Put in data saving here for Myzoo remembering.
				}
			}
			//else decrement the remaining time
			else{
				timeleft--;
				winNum =0;
			}
			//check if there is time left
			if (timeleft <= 0){
				//if not display the times up pop up
				try {
					myRecorder.stop();
					myRecorder.release();
					myRecorder = null;
					//text.setText("Recording Point: Stop recording");
					stopRepeatingTask();
				} catch (IllegalStateException e) {
					// it is called before start()
					e.printStackTrace();
				} catch (RuntimeException e) {
					// no valid audio/video data has been received
					e.printStackTrace();
				}
				outputFile = Environment.getExternalStorageDirectory().
						getAbsolutePath() + "/javacodegeeksRecording.3gpp";
				findViewById(R.id.popblueback).setVisibility(View.VISIBLE);
				findViewById(R.id.popback).setVisibility(View.VISIBLE);
				findViewById(R.id.popoot).setVisibility(View.VISIBLE);
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
	//methods for the handler
	void startRepeatingTask() {
		mStatusChecker.run();
	}
	void stopRepeatingTask() {
		mHandler.removeCallbacks(mStatusChecker);
	}
	//sets animal val to a number not equal to the current animal val
	private int setAnimal(int current){
		int ran = current;
		while (ran == current){
			ran = random.nextInt(2)+1;
		}
		return ran;
	}
}