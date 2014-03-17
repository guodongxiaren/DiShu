package com.jelly.dishu;

import java.util.Random;

import com.jelly.dishu.tool.DSEngine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("UseSparseArrays")
public class MainActivity extends Activity {
	ImageView im1, im2, im3, im4, im5, im6, im7, im8, im9;
	Random r = new Random();
	int score = 0;
	volatile int count = 30;
	TextView tv;
	Thread mainThread;
	AudioManager audio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

		tv = (TextView) findViewById(R.id.textView1);
		im1 = (ImageView) findViewById(R.id.ImageView01);
		im2 = (ImageView) findViewById(R.id.ImageView02);
		im3 = (ImageView) findViewById(R.id.ImageView03);
		im4 = (ImageView) findViewById(R.id.ImageView04);
		im5 = (ImageView) findViewById(R.id.ImageView05);
		im6 = (ImageView) findViewById(R.id.ImageView06);
		im7 = (ImageView) findViewById(R.id.ImageView07);
		im8 = (ImageView) findViewById(R.id.imageView1);
		im9 = (ImageView) findViewById(R.id.ImageView08);

		im1.setOnTouchListener(clickedView);
		im2.setOnTouchListener(clickedView);
		im3.setOnTouchListener(clickedView);
		im4.setOnTouchListener(clickedView);
		im5.setOnTouchListener(clickedView);
		im6.setOnTouchListener(clickedView);
		im7.setOnTouchListener(clickedView);
		im8.setOnTouchListener(clickedView);
		im9.setOnTouchListener(clickedView);

		mainThread = new Thread(new MyThread());
		mainThread.start();

		DSEngine.musicThread = new Thread() {
			public void run() {
				Intent bgmusic = new Intent(getApplicationContext(),
						DSMusic.class);
				startService(bgmusic);
				DSEngine.context = getApplicationContext();
			}
		};
		DSEngine.musicThread.start();
	}

	/**
	 * ImageView点击事件
	 */
	public OnTouchListener clickedView = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (v.getVisibility() == View.VISIBLE) {
				Message msg = new Message();
				score += 50;
				msg.what = score;
				scoreHandler.sendMessage(msg);
			}
			return false;
		}
	};

	public class MyThread implements Runnable {

		private static final String TAG = "tag";

		@Override
		public void run() {

			while (count != 0) {
				count--;
				try {
					Thread.sleep(1000);
					int a = r.nextInt(9) + 1;
					Message msg = new Message();
					msg.what = a;
					handler.sendMessage(msg);
					Thread.sleep(500);
					Message msg2 = new Message();
					msg2.what = -a;
					handler.sendMessage(msg2);
					Log.i(TAG, count + "");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Intent intent = new Intent(MainActivity.this,
					StatisticsActivity.class);
			Bundle b = new Bundle();
			b.putInt("score", score);
			intent.putExtras(b);
			startActivity(intent);
			MainActivity.this.finish();
			closeMusic();
		}
	}

	@SuppressLint("HandlerLeak")
	Handler scoreHandler = new Handler() {

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			tv.setText(msg.what + "");
		}

	};
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				im1.setVisibility(View.VISIBLE);
				break;
			case 2:
				im2.setVisibility(View.VISIBLE);
				break;
			case 3:
				im3.setVisibility(View.VISIBLE);
				break;
			case 4:
				im4.setVisibility(View.VISIBLE);
				break;
			case 5:
				im5.setVisibility(View.VISIBLE);
				break;
			case 6:
				im6.setVisibility(View.VISIBLE);
				break;
			case 7:
				im7.setVisibility(View.VISIBLE);
				break;
			case 8:
				im8.setVisibility(View.VISIBLE);
				break;
			case 9:
				im9.setVisibility(View.VISIBLE);
				break;
			case -1:
				im1.setVisibility(View.INVISIBLE);
				break;
			case -2:
				im2.setVisibility(View.INVISIBLE);
				break;
			case -3:
				im3.setVisibility(View.INVISIBLE);
				break;
			case -4:
				im4.setVisibility(View.INVISIBLE);
				break;
			case -5:
				im5.setVisibility(View.INVISIBLE);
				break;
			case -6:
				im6.setVisibility(View.INVISIBLE);
				break;
			case -7:
				im7.setVisibility(View.INVISIBLE);
				break;
			case -8:
				im8.setVisibility(View.INVISIBLE);
				break;
			case -9:
				im9.setVisibility(View.INVISIBLE);
				break;
			}
		}

	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND
							| AudioManager.FLAG_SHOW_UI);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND
							| AudioManager.FLAG_SHOW_UI);
			return true;
		default:
			closeMusic();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void closeMusic() {
		/*
		 * close the background music 关闭背景音乐
		 */
		Intent bgmusic = new Intent(getApplicationContext(), DSMusic.class);
		MainActivity.this.stopService(bgmusic);
	}

}
