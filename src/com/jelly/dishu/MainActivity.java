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

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	ImageView[] im = new ImageView[9];
	Random r = new Random();
	int score = 0;
	volatile int count = 30;
	TextView tv;
	Thread mainThread;
	AudioManager audio;
	float viewX = -1f, viewY = -1f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 控制系统音量的对象
		audio = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

		tv = (TextView) findViewById(R.id.textView1);
		im[0] = (ImageView) findViewById(R.id.ImageView01);
		im[1] = (ImageView) findViewById(R.id.ImageView02);
		im[2] = (ImageView) findViewById(R.id.ImageView03);
		im[3] = (ImageView) findViewById(R.id.ImageView04);
		im[4] = (ImageView) findViewById(R.id.ImageView05);
		im[5] = (ImageView) findViewById(R.id.ImageView06);
		im[6] = (ImageView) findViewById(R.id.ImageView07);
		im[7] = (ImageView) findViewById(R.id.imageView1);
		im[8] = (ImageView) findViewById(R.id.ImageView08);

		// for (int i = 0; i < 9; i++)
		// im[i].setOnTouchListener(clickedView);
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
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			gameOver();
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
			int i = msg.what;
			if (i > 0) {
				im[i - 1].setVisibility(View.VISIBLE);
				viewX = im[i - 1].getX();
				viewY = im[i - 1].getY();
				Log.i("view", viewX + ":" + viewY);
			}
			if (i < 0) {
				im[-1 - i].setVisibility(View.INVISIBLE);
				viewX = -1f;
				viewY = -1f;
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

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN)
			if (viewX >= 0 && viewY >= 0 && viewX <= e.getX()
					&& viewX <= viewX + 81 && viewY <= e.getY()
					&& viewY <= viewY + 118) {
				Message msg = new Message();
				score += 50;
				msg.what = score;
				scoreHandler.sendMessage(msg);
				viewX = -1f;
				viewY = -1f;
			} else {
				count=0;
			}
		Log.i("onTouchEvent", e.getX() + ":" + e.getY());
		return super.onTouchEvent(e);
	}

	public void gameOver() {
		Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
		Bundle b = new Bundle();
		b.putInt("score", score);
		intent.putExtras(b);
		startActivity(intent);
		MainActivity.this.finish();
		closeMusic();
	}

}
