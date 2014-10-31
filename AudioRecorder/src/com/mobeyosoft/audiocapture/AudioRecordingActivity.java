package com.mobeyosoft.audiocapture;

import java.io.IOException;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AudioRecordingActivity extends BaseActivity {

	private MediaRecorder myAudioRecorder;
	private String outputFile = null;
	private Button start,stop;
	private Context mContext;
	private View mPlayMedia;
	private View mPauseMedia;
	private SeekBar mMediaSeekBar;
	private TextView mRunTime;
	private TextView mTotalTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audiorecording);
		initComponents();
		setListeners();	
	}

	private void initComponents() {

		start = (Button)findViewById(R.id.button_start);
		stop = (Button)findViewById(R.id.button_stop);
		mContext = AudioRecordingActivity.this;
		mPlayMedia = findViewById(R.id.play);
		mPauseMedia = findViewById(R.id.pause);
		mMediaSeekBar = (SeekBar) findViewById(R.id.media_seekbar);
		mRunTime = (TextView) findViewById(R.id.run_time);
		mTotalTime = (TextView) findViewById(R.id.total_time);
		stop.setEnabled(false);
	}

	private void setListeners() {

		outputFile = Environment.getExternalStorageDirectory().	getAbsolutePath() + "/myrecording.3gp";;
		myAudioRecorder = new MediaRecorder();
		myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
		myAudioRecorder.setOutputFile(outputFile);

		mPlayMedia.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mUri == null)
					Toast.makeText(mContext, "Pick an audio file before playing", Toast.LENGTH_LONG).show();
			}
		});
	}

	public void start (View view){

		try {
			myAudioRecorder.prepare();
			myAudioRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		start.setEnabled(false);
		stop.setEnabled(true);
		start.setBackgroundColor(Color.RED);
		Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
	}

	public void stop(View view){

		myAudioRecorder.stop();
		myAudioRecorder.release();
		myAudioRecorder  = null;
		stop.setEnabled(false);

		start.setBackgroundColor(Color.WHITE);
		Toast.makeText(getApplicationContext(), "Audio recorded successfully",
				Toast.LENGTH_LONG).show();
		try {
			play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void play() throws IllegalArgumentException,   
	SecurityException, IllegalStateException, IOException{

		Uri uri = Uri.parse(outputFile);
		mUri = uri;
		AudioWife.getInstance()
		.init(mContext, uri)
		.setPlayView(mPlayMedia)
		.setPauseView(mPauseMedia)
		.setSeekBar(mMediaSeekBar)
		.setRuntimeView(mRunTime)
		.setTotalTimeView(mTotalTime);

		AudioWife.getInstance().addOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(getBaseContext(), "Completed", Toast.LENGTH_SHORT).show();
			}
		});

		AudioWife.getInstance().addOnPlayClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Play", Toast.LENGTH_SHORT).show();
			}
		});

		AudioWife.getInstance().addOnPauseClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getBaseContext(), "Pause", Toast.LENGTH_SHORT).show();
			}
		});

	}

}
