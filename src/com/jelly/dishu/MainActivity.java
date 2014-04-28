package com.jelly.dishu;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	ImageView[] im = new ImageView[9];
	Random r = new Random();
	int score = 0;
	volatile boolean running = true;
	TextView tv;
	Thread mainThread;
	AudioManager audio;
	float viewX = -1f, viewY = -1f;
	boolean excel = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ����ϵͳ�����Ķ���
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

		mainThread = new Thread(new MyThread());
		mainThread.start();
	}

	/**
	 * ImageView����¼�
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
			startMusic();
			while (running) {
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

	/*
	 * ���¼Ʒְ��handler
	 */
	@SuppressLint("HandlerLeak")
	Handler scoreHandler = new Handler() {

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			tv.setText(msg.what + "");
		}

	};

	/*
	 * �ı����imagView����״̬���Լ�ʵ��ֹͣ��Ϸ��handler
	 */
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
			} else if (i < 0) {
				im[-1 - i].setVisibility(View.INVISIBLE);
				viewX = -1f;
				viewY = -1f;
			} else if (i == 0) {
				Toast toast = Toast.makeText(MainActivity.this,"��δ�㵽����Ϸ����", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.BOTTOM, 0, 0);
				toast.show();
				createDialog();
			}

		}

	};

	/*
	 * ��д�������������ӡ��������ؼ�
	 * 
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
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

	/*
	 * ������������
	 */
	public void startMusic() {
		Intent bgmusic = new Intent(getApplicationContext(), DSMusic.class);
		MainActivity.this.startService(bgmusic);
	}

	/*
	 * �رձ�������
	 */
	public void closeMusic() {
		/*
		 * close the background music �رձ�������
		 */
		Intent bgmusic = new Intent(getApplicationContext(), DSMusic.class);
		MainActivity.this.stopService(bgmusic);
	}

	
	/*
	 * ��д�����¼�
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
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
				running = false;
			}
		Log.i("onTouchEvent", e.getX() + ":" + e.getY());
		return super.onTouchEvent(e);
	}

	/*
	 * ��Ϸ����ʱ����
	 */
	public void gameOver() {
		closeMusic();
		Message msg = new Message();
		msg.what = 0;
		handler.sendMessage(msg);
	}

	/*
	 * ��Ϸ��������ʾ�ĶԻ���
	 */
	public void createDialog() {
		SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
		int s = sp.getInt("MAX_SCORE", -1);
		Log.i("max", s + "");
		if (s == -1 || s < score) {
			Editor editor = sp.edit();
			editor.putInt("MAX_SCORE", score);
			boolean b1 = editor.commit();
			Log.i("bbb", b1 + "");
			excel = true;
		}

		AlertDialog.Builder builder = new Builder(MainActivity.this);
		if (excel)
			builder.setTitle("��ϲ���Ƽ�¼");
		else
			builder.setTitle("��߼�¼Ϊ" + s);
		builder.setMessage("���ε÷� " + score + "��");
		builder.setPositiveButton("�˳�", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				MainActivity.this.finish();
			}
		});
		builder.setNegativeButton("����һ��",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Message msg = new Message();
						msg.what = 0;
						score = 0;
						scoreHandler.sendMessage(msg);
						running = true;
						mainThread = new Thread(new MyThread());
						mainThread.start();
					}
				});
		builder.setCancelable(false);
		builder.create().show();
	}

}
